package week9

class MinimumPathSum {
    fun minPathSum(grid: Array<IntArray>): Int {
        val lastRow = grid.lastIndex
        val lastCol = grid[0].lastIndex

        // create a 2D sums array the same size as grid, and fill up all its cells with the path sum going backward
        // from destination to origin
        val sums = Array(grid.size) { IntArray(grid[0].size) }
        sums[lastRow][lastCol] = grid[lastRow][lastCol]
        // fill up last row based on the destination cell
        for (j in lastCol-1 downTo 0)
            sums[lastRow][j] = grid[lastRow][j] + sums[lastRow][j+1]
        // fill up last column based on the destination cell
        for (i in lastRow-1 downTo 0)
            sums[i][lastCol] = grid[i][lastCol] + sums[i+1][lastCol]
        // fill up the remaining cells, each based on the minimum of its right cell and down cell
        for (i in lastRow-1 downTo 0) {
            for (j in lastCol-1 downTo 0) {
                val min = minOf(sums[i+1][j], sums[i][j+1])
                sums[i][j] = grid[i][j] + min
            }
        }

        /*println("SUMS....")
        for (row in sums) {
            println(row.contentToString())
        }
        println("END>>>")*/

        var row = 0
        var col = 0
        var sum = grid[0][0]
        while (row != lastRow || col != lastCol) {
            if (row + 1 <= lastRow && col + 1 <= lastCol) {
                // either go down or go right: pick the smaller path sum
                if (sums[row + 1][col] < sums[row][col + 1]) {
                    row++
                } else {
                    col++
                }
            } else if (row + 1 <= lastRow) {
                // go down only
                row++
            } else {
                // go right only
                col++
            }
            sum += grid[row][col]
        }
        return sum
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val grids = arrayOf(
                arrayOf(intArrayOf(1,3,1), intArrayOf(1,5,1), intArrayOf(4,2,1)),
                arrayOf(intArrayOf(1,2,3), intArrayOf(4,5,6))
            )
            for (grid in grids) {
                for (row in grid)
                    println(row.contentToString())
                val sum = MinimumPathSum().minPathSum(grid)
                println("sum: $sum")
            }
        }
    }
}