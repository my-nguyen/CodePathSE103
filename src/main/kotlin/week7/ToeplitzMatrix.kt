package week7

class ToeplitzMatrix {
    // 7/15/2021
    fun isToeplitzMatrix(matrix: Array<IntArray>): Boolean {
        // check top row, from left to right
        for (k in matrix[0].indices) {
            if (!isToeplitz(matrix, 0, k))
                return false
        }

        // check first column, from top to bottom
        for (k in 1..matrix.lastIndex) {
            if (!isToeplitz(matrix, k, 0))
                return false
        }

        return true
    }

    // 2/7/2021
    fun isToeplitzMatrix2(matrix: Array<IntArray>): Boolean {
        val rows = matrix.size
        val columns = matrix[0].size
        if (rows == 1 || columns == 1) {
            return true
        } else {
            for (k in (rows-2) downTo 0) {
                val sample = matrix[k][0]
                var i = k+1
                var j = 1
                while (i < rows && j < columns) {
                    if (matrix[i][j] != sample) {
                        return false
                    } else {
                        i++
                        j++
                    }
                }
            }
            for (k in 1..(columns-1)) {
                val sample = matrix[0][k]
                var i = 1
                var j = k + 1
                while (i < rows && j < columns) {
                    if (matrix[i][j] != sample) {
                        return false
                    } else {
                        i++
                        j++
                    }
                }
            }
            return true
        }
    }

    private fun isToeplitz(matrix: Array<IntArray>, row: Int, col: Int): Boolean {
        val sample = matrix[row][col]
        var i = row + 1
        var j = col + 1
        while (i < matrix.size && j < matrix[i].size) {
            if (matrix[i][j] != sample)
                return false
            i++
            j++
        }
        return true
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val matrices = arrayOf(
                arrayOf(intArrayOf(1,2,3,4), intArrayOf(5,1,2,3), intArrayOf(9,5,1,2)),
                arrayOf(intArrayOf(1,2), intArrayOf(2,2))
            )
            for (matrix in matrices) {
                for (row in matrix)
                    println(row.contentToString())
                val isToeplitz = ToeplitzMatrix().isToeplitzMatrix(matrix)
                println("is Toepliz? $isToeplitz")
            }
        }
    }
}