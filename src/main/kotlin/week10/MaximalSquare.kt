package week10

class MaximalSquare {
    fun maximalSquare(matrix: Array<CharArray>): Int {
        // return solutionBruteForce(matrix)
        return solutionDP(matrix)
    }

    fun solutionBruteForce(matrix: Array<CharArray>): Int {
        val rows = matrix.size
        val cols = if (rows > 0) matrix[0].size else 0
        var maxsqlen = 0
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                if (matrix[i][j] == '1') {
                    var sqlen = 1
                    var flag = true
                    while (sqlen + i < rows && sqlen + j < cols && flag) {
                        for (k in j..sqlen + j) {
                            if (matrix[i + sqlen][k] == '0') {
                                flag = false
                                break
                            }
                        }
                        for (k in i..sqlen + i) {
                            if (matrix[k][j + sqlen] == '0') {
                                flag = false
                                break
                            }
                        }
                        if (flag) sqlen++
                    }
                    if (maxsqlen < sqlen) {
                        maxsqlen = sqlen
                    }
                }
            }
        }
        return maxsqlen * maxsqlen
    }

    fun solutionDP(matrix: Array<CharArray>): Int {
        val rows = matrix.size
        val cols = if (rows > 0) matrix[0].size else 0
        val dp = Array(rows + 1) { IntArray(cols + 1) }
        var maxsqlen = 0
        for (i in 1..rows) {
            for (j in 1..cols) {
                if (matrix[i - 1][j - 1] == '1') {
                    val tmp = minOf(dp[i][j - 1], dp[i - 1][j])
                    dp[i][j] = minOf(tmp, dp[i - 1][j - 1]) + 1
                    maxsqlen = maxOf(maxsqlen, dp[i][j])
                }
            }
        }
        return maxsqlen * maxsqlen
    }

    fun solutionBetterDP(matrix: Array<CharArray>): Int {
        val rows = matrix.size
        val cols = if (rows > 0) matrix[0].size else 0
        val dp = IntArray(cols + 1)
        var maxsqlen = 0
        var prev = 0
        for (i in 1..rows) {
            for (j in 1..cols) {
                val temp = dp[j]
                if (matrix[i - 1][j - 1] == '1') {
                    dp[j] = Math.min(Math.min(dp[j - 1], prev), dp[j]) + 1
                    maxsqlen = Math.max(maxsqlen, dp[j])
                } else {
                    dp[j] = 0
                }
                prev = temp
            }
        }
        return maxsqlen * maxsqlen
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val matrices = arrayOf(
                arrayOf(
                    charArrayOf('1','0','1','0','0'), charArrayOf('1','0','1','1','1'),
                    charArrayOf('1','1','1','1','1'), charArrayOf('1','0','0','1','0')
                ),
                arrayOf(
                    charArrayOf('0','1'), charArrayOf('1','0')
                ),
                arrayOf(
                    charArrayOf('0')
                )
            )
            for (matrix in matrices) {
                val square = MaximalSquare().maximalSquare(matrix)
                println("square: $square")
            }
        }
    }
}
