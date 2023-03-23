package week11

import java.util.*


class MaximalRectangle {
    fun maximalRectangle(matrix: Array<CharArray>): Int {
        // return solutionDPBruteForce(matrix)
        // return solutionHistograms(matrix)
        return solutionDPMaximumHeight(matrix)
    }

    private fun solutionDPBruteForce(matrix: Array<CharArray>): Int {
        if (matrix.isEmpty())
            return 0

        var maxarea = 0
        val dp = Array(matrix.size) { IntArray(matrix[0].size) }
        for (i in matrix.indices) {
            for (j in 0 until matrix[0].size) {
                if (matrix[i][j] == '1') {

                    // compute the maximum width and update dp with it
                    dp[i][j] = if (j == 0) 1 else dp[i][j - 1] + 1
                    var width = dp[i][j]

                    // compute the maximum area rectangle with a lower right corner at [i, j]
                    for (k in i downTo 0) {
                        width = minOf(width, dp[k][j])
                        maxarea = maxOf(maxarea, width * (i - k + 1))
                    }
                }
            }
        }
        return maxarea
    }

    // Get the maximum area in a histogram given its heights
    private fun leetcode84(heights: IntArray): Int {
        val stack = Stack<Int>()
        stack.push(-1)
        var maxarea = 0
        for (i in heights.indices) {
            while (stack.peek() != -1 && heights[stack.peek()] >= heights[i])
                maxarea = maxOf(maxarea, heights[stack.pop()] * (i - stack.peek() - 1))
            stack.push(i)
        }
        while (stack.peek() != -1)
            maxarea = maxOf(maxarea, heights[stack.pop()] * (heights.size - stack.peek() - 1))
        return maxarea
    }

    private fun solutionHistograms(matrix: Array<CharArray>): Int {
        if (matrix.isEmpty())
            return 0

        var maxarea = 0
        val dp = IntArray(matrix[0].size)
        for (i in matrix.indices) {
            for (j in 0 until matrix[0].size) {

                // update the state of this row's histogram using the last row's histogram
                // by keeping track of the number of consecutive ones
                dp[j] = if (matrix[i][j] == '1') dp[j] + 1 else 0
            }
            // update maxarea with the maximum area from this row's histogram
            maxarea = maxOf(maxarea, leetcode84(dp))
        }
        return maxarea
    }

    private fun solutionDPMaximumHeight(matrix: Array<CharArray>): Int {
        if (matrix.isEmpty())
            return 0

        val m = matrix.size
        val n: Int = matrix[0].size
        val left = IntArray(n) // initialize left as the leftmost boundary possible
        val right = IntArray(n)
        val height = IntArray(n)
        Arrays.fill(right, n) // initialize right as the rightmost boundary possible
        var maxarea = 0
        for (i in 0 until m) {
            var curLeft = 0
            var curRight = n
            // update height
            for (j in 0 until n) {
                if (matrix[i][j] == '1')
                    height[j]++
                else
                    height[j] = 0
            }
            // update left
            for (j in 0 until n) {
                if (matrix[i][j] == '1')
                    left[j] = maxOf(left[j], curLeft)
                else {
                    left[j] = 0
                    curLeft = j + 1
                }
            }
            // update right
            for (j in n - 1 downTo 0) {
                if (matrix[i][j] == '1')
                    right[j] = minOf(right[j], curRight)
                else {
                    right[j] = n
                    curRight = j
                }
            }
            // update area
            for (j in 0 until n) {
                maxarea = maxOf(maxarea, (right[j] - left[j]) * height[j])
            }
        }
        return maxarea
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val matrices = arrayOf(
                arrayOf(charArrayOf('1','0','1','0','0'), charArrayOf('1','0','1','1','1'), charArrayOf('1','1','1','1','1'), charArrayOf('1','0','0','1','0')),
                arrayOf(),
                arrayOf(charArrayOf('0')),
                arrayOf(charArrayOf('1')),
                arrayOf(charArrayOf('0','0')),
            )
            for (matrix in matrices) {
                println("matrix:")
                for (row in matrix) {
                    println(row.contentToString())
                }
                val rectangle = MaximalRectangle().maximalRectangle(matrix)
                println("rectangle: $rectangle")
            }
        }
    }
}
