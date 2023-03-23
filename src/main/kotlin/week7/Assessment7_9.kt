package week7

class Assessment7_9 {
    fun rotate(matrix: Array<IntArray>) {
        mine(matrix)
    }

    private fun solutionRotateGroups(matrix: Array<IntArray>) {
        val n = matrix.size
        for (i in 0 until (n + 1) / 2) {
            for (j in 0 until n / 2) {
                val temp = matrix[n - 1 - j][i]
                matrix[n - 1 - j][i] = matrix[n - 1 - i][n - j - 1]
                matrix[n - 1 - i][n - j - 1] = matrix[j][n - 1 - i]
                matrix[j][n - 1 - i] = matrix[i][j]
                matrix[i][j] = temp
            }
        }
    }

    private fun solutionTransposeReflect(matrix: Array<IntArray>) {
        transpose(matrix)
        reflect(matrix)
    }

    private fun transpose(matrix: Array<IntArray>) {
        val n = matrix.size
        // i goes by the rows
        for (i in 0 until n) {
            // j starts from i not from 0, so the starting elements are (0, 0), (1, 1), (2, 2), etc
            for (j in i until n) {
                // swap element at (i, j) with that at (j, i)
                val tmp = matrix[j][i]
                matrix[j][i] = matrix[i][j]
                matrix[i][j] = tmp
            }
        }
    }

    private fun reflect(matrix: Array<IntArray>) {
        val n = matrix.size
        // i goes by the rows
        for (i in 0 until n) {
            // for each row, swap element at left with its opposite at right
            for (j in 0 until n / 2) {
                val tmp = matrix[i][j]
                matrix[i][j] = matrix[i][n - j - 1]
                matrix[i][n - j - 1] = tmp
            }
        }
    }

    private fun mine(matrix: Array<IntArray>) {
        var top = 0
        var right = matrix.size - 1
        var bottom = matrix.size - 1
        var left = 0

        while (left < right) {
            val saved = IntArray(right-left)
            for (i in left until right) {
                // println("left $left to right $right, src ($top, $i), dst ${i-left}, value ${matrix[top][i]}")
                saved[i-left] = matrix[top][i]
            }

            for (i in bottom downTo top+1) {
                // println("bottom $bottom to top ${top+1}, src ($i, $left), dest ($top, ${bottom-i+left}), value: ${matrix[i][left]}")
                matrix[top][bottom-i+left] = matrix[i][left]
            }
            for (i in right downTo left+1) {
                // println("right $right to left ${left+1}, src ($bottom, $i), dest ($i, $left), value: ${matrix[bottom][i]}")
                matrix[i][left] = matrix[bottom][i]
            }
            for (i in top until bottom) {
                // println("top $top to bottom $bottom, src ($i, $right), dest ($bottom, ${right-i+left}), value ${matrix[i][right]}")
                matrix[bottom][right-i+left] = matrix[i][right]
            }
            for (i in left until right) {
                // println("left $left to right $right, src ${i-left}, dest ($i, $right), value ${saved[i-left]}")
                matrix[i][right] = saved[i-left]
            }

            top++
            right--
            bottom--
            left++
        }
    }

    companion object {
        private fun print(matrix: Array<IntArray>) {
            for (row in matrix) {
                println(row.contentToString())
            }
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val matrices = arrayOf(
                arrayOf(intArrayOf(1,2,3), intArrayOf(4,5,6), intArrayOf(7,8,9)),
                arrayOf(intArrayOf(5,1,9,11), intArrayOf(2,4,8,10), intArrayOf(13,3,6,7), intArrayOf(15,14,12,16)),
                arrayOf(intArrayOf(1)),
                arrayOf(intArrayOf(1,2), intArrayOf(3,4))
            )
            for (matrix in matrices) {
                println("PRE-rotate:")
                print(matrix)
                Assessment7_9().rotate(matrix)
                println("POST-rotate:")
                print(matrix)
            }
        }
    }
}

