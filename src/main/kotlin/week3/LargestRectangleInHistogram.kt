package week3

import java.util.*


class LargestRectangleInHistogram {
    fun largestRectangleArea(heights: IntArray): Int {
        // return mine(heights)
        // return leetcode1(heights)
        return leetcode2(heights)
    }

    /*
    For every bar ‘x’, we calculate the area with ‘x’ as the smallest bar in the
    rectangle. If we calculate such area for every bar ‘x’ and find the maximum of
    all areas, our task is done. How to calculate area with ‘x’ as smallest bar?
    We need to know index of the first smaller (smaller than ‘x’) bar on left of ‘x’
    and index of first smaller bar on right of ‘x’. Let us call these indexes as
    ‘left index’ and ‘right index’ respectively.

    We traverse all bars from left to right, maintaining a stack of bars. Every bar
    is pushed to the stack once. A bar is popped from stack when we see a bar of
    smaller  height. When a bar is popped, we calculate the area with the popped bar
    as smallest bar. How do we get left and right indexes of the popped bar? The
    current index tells us the ‘right index’ and index of previous item in stack is
    the ‘left index’.
    */
    private fun codepath(height: IntArray): Int {
        val len = height.size
        val s = Stack<Int>()
        var maxArea = 0
        var i = 0
        while (i <= len) {
            val h = if (i == len) 0 else height[i]
            if (s.isEmpty() || h >= height[s.peek()]) {
                s.push(i)
            } else {
                val tp = s.pop()
                maxArea = Math.max(
                    maxArea, height[tp] *
                            if (s.isEmpty()) i else i - 1 - s.peek()
                )
                i--
            }
            i++
        }
        return maxArea
    }

    // For every bar ‘x’, we calculate the area with ‘x’ as the smallest bar in the rectangle. If we calculate such area
    // for every bar ‘x’ and find the maximum of all areas, our task is done. How to calculate area with ‘x’ as smallest
    // bar? We need to know index of the first smaller (smaller than ‘x’) bar on left of ‘x’ and index of first smaller
    // bar on right of ‘x’. Let us call these indexes as ‘left index’ and ‘right index’ respectively.
    // We traverse all bars from left to right, maintaining a stack of bars. Every bar is pushed to the stack once. A bar
    // is popped from stack when we see a bar of smaller  height. When a bar is popped, we calculate the area with
    // the popped bar as smallest bar. How do we get left and right indexes of the popped bar? The current index tells us
    // the ‘right index’ and index of previous item in stack is the ‘left index’.
    fun hackmd(heights: IntArray): Int {
        val len = heights.size
        val s = Stack<Int>()
        var maxArea = 0
        var i = 0
        while (i <= len) {
            val h = if (i == len) 0 else heights[i]
            if (s.isEmpty() || h >= heights[s.peek()]) {
                s.push(i)
            } else {
                val top = s.pop()
                val width = if (s.isEmpty()) i else i - 1 - s.peek()
                val area = heights[top] * width
                maxArea = maxOf(maxArea, area)
                i--
            }
            i++
        }
        return maxArea
    }

    // time: O(n^2)
    // space: O(1)
    private fun leetcode1(heights: IntArray): Int {
        var maxArea = 0
        for (left in 0..heights.lastIndex) {
            var minHeight = Integer.MAX_VALUE
            for (right in left..heights.lastIndex) {
                // update min height whenever right moves forward
                minHeight = minOf(minHeight, heights[right])
                val width = right - left + 1
                val area = minHeight * width
                maxArea = maxOf(maxArea, area)
            }
        }
        return maxArea
    }

    // time: O(n)
    // space: O(n)
    private fun leetcode2(heights: IntArray): Int {
        // stack of indices to heights array
        val stack = Stack<Int>()
        // leftmost array boundary; rightmost boundary will be array size
        stack.push(-1)
        var maxArea = 0
        for (i in 0..heights.lastIndex) {
            // pop from stack when heights[i] is less than or equal to stack top
            while (stack.peek() != -1 && heights[stack.peek()] >= heights[i]) {
                val height = heights[stack.pop()]
                // right boundary is i since heights[i] is less than or equal to the popped height,
                // and left boundary is the new stack top since that's also less than the popped height
                val width = i - stack.peek() - 1
                val area = height * width
                maxArea = maxOf(maxArea, area)
            }

            // push onto stack when heights[i] is greater than stack top
            // that way stack will store heights in increasing values
            stack.push(i)
        }

        // take care of the remaining indices on stack
        while (stack.peek() != -1) {
            val height = heights[stack.pop()]
            // since we've traversed the full heights array, the right boundary now is the array size
            val width = heights.size - stack.peek() - 1
            val area = height * width
            maxArea = maxOf(maxArea, area)
        }
        return maxArea
    }

    private fun mine(heights: IntArray): Int {
        val minHeight = heights.minOrNull()!!
        // the default largest rectangle is the minimum height times the width
        var maxRectangle = minHeight * heights.size

        for (i in heights.indices) {
            var currentHeight = heights[i]
            // current rectangle is height of the current bar times width of 1
            var currentRectangle = heights[i]
            maxRectangle = maxOf(maxRectangle, currentRectangle)
            var j = i
            // try expanding local rectangle to include more than just 1 bar
            // while only considering bars with height greater than the min height
            while (j < heights.size && heights[j] > minHeight) {
                // local height is the minimum of all the "high" bars so far
                currentHeight = minOf(currentHeight, heights[j])
                currentRectangle = currentHeight * (j - i + 1)
                maxRectangle = maxOf(maxRectangle, currentRectangle)
                j++
            }
        }
        return maxRectangle
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val data = arrayOf(
                intArrayOf(2,1,5,6,2,3), intArrayOf(2,4),
                intArrayOf(1,1,1,1,1), intArrayOf(1,1,5,1,1), intArrayOf(1,1,5,2,1), intArrayOf(1,1,5,3,1),
                intArrayOf(1,1,5,5,1), intArrayOf(1,1,8,1,1,1), intArrayOf(1,7,1,9,1), intArrayOf(1,1,9,1,1,8,5)
            )
            for (heights in data) {
                val rectangle = LargestRectangleInHistogram().largestRectangleArea(heights)
                println("histogram: ${heights.contentToString()}, rectangle: $rectangle")
            }
        }
    }
}

