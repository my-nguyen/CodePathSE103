package week7

class SearchA2DMatrix {
    fun searchMatrix(matrix: Array<IntArray>, target: Int): Boolean {
        var low = 0
        var high = matrix.size * matrix[0].size - 1
        while (low <= high) {
            val mid = low + (high-low)/2
            val i = mid / matrix[0].size
            val j = mid % matrix[0].size
            when {
                matrix[i][j] < target -> low = mid + 1
                matrix[i][j] > target -> high = mid - 1
                else -> return true
            }
        }
        return false
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val matrices = arrayOf(
                arrayOf(intArrayOf(1,3,5,7), intArrayOf(10,11,16,20), intArrayOf(23,30,34,60)),
                arrayOf(intArrayOf(1,3,5,7), intArrayOf(10,11,16,20), intArrayOf(23,30,34,60)),
            )
            val targets = arrayOf(3, 13)
            for (i in matrices.indices) {
                val found = SearchA2DMatrix().searchMatrix(matrices[i], targets[i])
                println("target: ${targets[i]}, found? $found")
            }
        }
    }
}