package week7

import java.util.*

class TrappingRainWater {
    fun trap(height: IntArray): Int {
        // return solution1(height)
        // return solution2(height)
        // return solution3(height)
        return solution4(height)
    }

    private fun solution1(height: IntArray): Int {
        var amount = 0
        for (i in height.indices) {
            var leftMax = 0
            var rightMax = 0
            for (j in i downTo 0) {
                leftMax = maxOf(leftMax, height[j])
            }
            for (j in i..height.lastIndex) {
                rightMax = maxOf(rightMax, height[j])
            }
            val min = minOf(leftMax, rightMax)
            amount += min - height[i]
        }
        return amount
    }

    private fun solution2(height: IntArray): Int {
        if (height.isEmpty())
            return 0

        var amount = 0
        val size = height.size

        // build an array of max-so-far going from left to right
        val leftMax = IntArray(size)
        leftMax[0] = height[0]
        for (i in 1 until size) {
            leftMax[i] = maxOf(leftMax[i-1], height[i])
            // println("i: $i, left: ${leftMax[i]}")
        }

        // build an array of max-so-far going from right to left
        val rightMax = IntArray(size)
        rightMax[size-1] = height[size-1]
        for (i in size-2 downTo 0) {
            rightMax[i] = maxOf(rightMax[i+1], height[i])
            // println("i: $i, right: ${rightMax[i]}")
        }

        for (i in 1 until size) {
            // as illustrated in the Solution tab, the areas that can contain water are the intersection (or min) of
            // leftMax and rightMax
            val min = minOf(leftMax[i], rightMax[i])
            // must also subtract the height from this intersected areas
            amount += min - height[i]
            println("i: $i, min: $min, height: ${height[i]}, diff: ${min-height[i]}")
        }
        return amount
    }

    private fun solution3(height: IntArray): Int {
        var amount = 0
        val stack = Stack<Int>()
        for (i in height.indices) {
            while (stack.isNotEmpty() && height[i] > height[stack.peek()]) {
                val top = stack.pop()
                if (stack.isEmpty())
                    break
                else {
                    val min = minOf(height[i], height[stack.peek()])
                    val boundedHeight = min - height[top]
                    val distance = i - stack.peek() - 1
                    amount += boundedHeight * distance
                }
            }
            stack.push(i)
        }
        return amount
    }

    private fun solution4(height: IntArray): Int {
        var leftMax = 0
        var rightMax = 0
        var amount = 0
        var left = 0
        var right = height.lastIndex
        while (left < right) {
            if (height[left] < height[right]) {
                if (height[left] >= leftMax)
                    leftMax = height[left]
                else
                    amount += leftMax - height[left]
                left++
            } else {
                if (height[right] >= rightMax)
                    rightMax = height[right]
                else
                    amount += rightMax - height[right]
                right--
            }
        }
        return amount
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val heights = arrayOf(intArrayOf(0,1,0,2,1,0,1,3,2,1,2,1), intArrayOf(4,2,0,3,2,5))
            for (height in heights) {
                print("heights: ${height.contentToString()}, ")
                val trapped = TrappingRainWater().trap(height)
                println("trapped: $trapped")
            }
        }
    }
}