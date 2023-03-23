package week8

class FloodFill {
    fun floodFill(image: Array<IntArray>, row: Int, col: Int, newColor: Int): Array<IntArray> {
        dfs(image, row, col, image[row][col], newColor)
        return image
    }

    private fun dfs(image: Array<IntArray>, row: Int, col: Int, oldColor: Int, newColor: Int) {
        if (row !in 0..image.lastIndex || col !in 0..image[0].lastIndex)
            return

        if (image[row][col] != oldColor || image[row][col] == newColor)
            return

        if (image[row][col] == oldColor)
            image[row][col] = newColor

        val directions = Array(4) { IntArray(2) }
        directions[0] = intArrayOf(row-1, col)
        directions[1] = intArrayOf(row, col+1)
        directions[2] = intArrayOf(row+1, col)
        directions[3] = intArrayOf(row, col-1)
        for (direction in directions) {
            dfs(image, direction[0], direction[1], oldColor, newColor)
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val images = arrayOf(
                arrayOf(intArrayOf(1,1,1), intArrayOf(1,1,0), intArrayOf(1,0,1)),
                arrayOf(intArrayOf(0,0,0), intArrayOf(0,0,0))
            )
            val rows = arrayOf(1, 0)
            val cols = arrayOf(1, 0)
            val colors = arrayOf(2, 2)
            for (i in images.indices) {
                println("PRE-fill image:")
                for (row in images[i]) {
                    println(row.contentToString())
                }
                FloodFill().floodFill(images[i], rows[i], cols[i], colors[i])
                println("POST-fill image:")
                for (row in images[i]) {
                    println(row.contentToString())
                }
            }
        }
    }
}