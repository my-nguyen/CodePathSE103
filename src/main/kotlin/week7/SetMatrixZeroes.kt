package week7

class SetMatrixZeroes {
    fun setZeroes(matrix: Array<IntArray>) {
        val rows = mutableSetOf<Int>()
        val cols = mutableSetOf<Int>()
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (matrix[i][j] == 0) {
                    rows.add(i)
                    cols.add(j)
                    // println("i $i, j $j")
                }
            }
        }

        for (i in rows) {
            // println("row: $i")
            for (j in matrix[i].indices)
                matrix[i][j] = 0
        }
        for (j in cols) {
            // println("col: $j")
            for (i in matrix.indices)
                matrix[i][j] = 0
        }
    }

    companion object {
        fun print(matrix: Array<IntArray>) {
            for (i in matrix.indices) {
                println(matrix[i].contentToString())
            }
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val matrices = arrayOf(
                arrayOf(intArrayOf(1,1,1), intArrayOf(1,0,1), intArrayOf(1,1,1)),
                arrayOf(intArrayOf(0,1,2,0), intArrayOf(3,4,5,2), intArrayOf(1,3,1,5)),
            )
            for (matrix in matrices) {
                println("original:")
                print(matrix)
                SetMatrixZeroes().setZeroes(matrix)
                println("zeroed")
                print(matrix)
            }
        }
    }
}