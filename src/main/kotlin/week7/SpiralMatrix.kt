package week7

class SpiralMatrix {
    fun spiralOrder(matrix: Array<IntArray>): List<Int> {
        var top = 0
        var right = matrix[0].lastIndex
        var bottom = matrix.lastIndex
        var left = 0
        val result = mutableListOf<Int>()
        while (left <= right && top <= bottom) {
            for (i in left..right) {
                result.add(matrix[top][i])
                // println("LTR adding ${matrix[top][i]}")
            }
            top++
            for (i in top..bottom) {
                result.add(matrix[i][right])
                // println("TTB adding ${matrix[i][right]}")
            }
            right--
            if (top <= bottom) {
                for (i in right downTo left) {
                    result.add(matrix[bottom][i])
                    // println("RTL adding ${matrix[bottom][i]}")
                }
                bottom--
            }
            // println("bottom: $bottom, top: $top, left: $left")
            if (left <= right) {
                for (i in bottom downTo top) {
                    result.add(matrix[i][left])
                    // println("BTT adding ${matrix[i][left]}")
                }
                left++
            }
        }
        return result
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val matrices = arrayOf(
                arrayOf(intArrayOf(1,2,3), intArrayOf(4,5,6), intArrayOf(7,8,9)),
                // arrayOf(intArrayOf(1,2,3,4), intArrayOf(5,6,7,8), intArrayOf(9,10,11,12))
            )
            for (matrix in matrices) {
                for (row in matrix)
                    println(row.contentToString())
                val spirals = SpiralMatrix().spiralOrder(matrix)
                println("spirals: $spirals")
            }
        }
    }
}