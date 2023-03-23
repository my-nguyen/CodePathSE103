package week9

class MaximalSquare {
    fun maximalSquare(matrix: Array<CharArray>): Int {
        return 0
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
