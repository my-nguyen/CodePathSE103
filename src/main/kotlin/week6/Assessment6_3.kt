package week6

class Assessment6_3 {
    fun numIslands(grid: Array<IntArray>): Int {
        var count = 0
        for (i in grid.indices) {
            for (j in grid[i].indices) {
                if (grid[i][j] == 1) {
                    count++
                    bfs(grid, i, j)
                }
            }
        }
        return count
    }

    private fun bfs(grid: Array<IntArray>, i: Int, j: Int) {
        if (i < 0 || i >= grid.size || j < 0 || j >= grid[0].size || grid[i][j] == 0)
            return

        grid[i][j] = 0
        bfs(grid, i + 1, j)
        bfs(grid, i, j + 1)
        bfs(grid, i - 1, j)
        bfs(grid, i, j - 1)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val grids = arrayOf(
                arrayOf(intArrayOf(1,1,1,1,0), intArrayOf(1,1,0,1,0), intArrayOf(1,1,0,0,0), intArrayOf(0,0,0,0,0)),
                arrayOf(intArrayOf(1,1,0,0,0), intArrayOf(1,1,0,0,0), intArrayOf(0,0,1,0,0), intArrayOf(0,0,0,1,1))
            )
            for (grid in grids) {
                val count = Assessment6_3().numIslands(grid)
                println("number of islands: $count")
            }
        }
    }
}