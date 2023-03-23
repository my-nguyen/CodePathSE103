package week9

class PascalsTriangle {
    fun generate(numRows: Int): List<List<Int>> {
        val triangle = mutableListOf<List<Int>>()
        triangle.add(listOf(1))
        when (numRows) {
            1 -> return triangle
            else -> {
                for (i in 1 until numRows) {
                    val row = mutableListOf<Int>()
                    row.add(1)
                    val previous = triangle.last()
                    for (j in 1 until previous.size) {
                        val sum = previous[j-1] + previous[j]
                        row.add(sum)
                    }
                    row.add(1)
                    triangle.add(row)
                }
                return triangle
            }
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val array = arrayOf(5, 1)
            for (numRows in array) {
                println("number of rows: $numRows, triangle:")
                val triangle = PascalsTriangle().generate(numRows)
                for (row in triangle) {
                    println(row)
                }
            }
        }
    }
}