package week11

class PathWithMaximumGold {
    fun getMaximumGold(grid: Array<IntArray>): Int {
        return user20184152(grid)
    }

    fun user20184152(grid: Array<IntArray>): Int {
        val visited = Array(grid.size) { BooleanArray(grid[0].size) }
        var max = 0
        for (i in grid.indices) {
            for (j in 0 until grid[0].size) {
                if (grid[i][j] != 0) {
                    val sum = dfs(grid, visited, i, j)
                    max = maxOf(sum, max)
                }
            }
        }
        return max
    }

    fun dfs(grid: Array<IntArray>, visited: Array<BooleanArray>, i: Int, j: Int): Int {
        // base case: cell out of bounds, or already visited, or contains a 0
        if (i !in grid.indices || j !in grid[0].indices || visited[i][j] || grid[i][j] == 0)
            return 0

        // mark the current cell as visited
        visited[i][j] = true

        // try the 4 surrounding cells
        val up = dfs(grid, visited, i + 1, j)
        val down = dfs(grid, visited, i - 1, j)
        val left = dfs(grid, visited, i, j - 1)
        val right = dfs(grid, visited, i, j + 1)

        // unmark the current cell
        visited[i][j] = false

        // return the sum of the current element and the max value of the 4 surrounding cells
        return grid[i][j] + maxOf(maxOf(up, down), maxOf(left, right))
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val grids = arrayOf(
                arrayOf(intArrayOf(0,6,0), intArrayOf(5,8,7), intArrayOf(0,9,0)),
                arrayOf(intArrayOf(1,0,7), intArrayOf(2,0,6), intArrayOf(3,4,5), intArrayOf(0,3,0), intArrayOf(9,0,20))
            )
            for (grid in grids) {
                println("grid:")
                for (row in grid) {
                    println(row.contentToString())
                }
                val gold = PathWithMaximumGold().getMaximumGold(grid)
                println("path with maximum gold: $gold")
            }
        }
    }
}