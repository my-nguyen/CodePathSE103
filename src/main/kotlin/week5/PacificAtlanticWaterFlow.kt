package week5

import java.util.*


class PacificAtlanticWaterFlow {
    fun pacificAtlantic(matrix: Array<IntArray>): List<List<Int>> {
        return solution1(matrix)
        // return solution2(matrix)
    }

    private var numRows = 0
    private var numCols = 0
    private lateinit var landHeights: Array<IntArray>

    private fun solution1(matrix: Array<IntArray>): List<List<Int>> {
        // Check if input is empty
        if (matrix.isEmpty() || matrix[0].isEmpty()) {
            return ArrayList()
        }

        // Save initial values to parameters
        numRows = matrix.size
        numCols = matrix[0].size
        landHeights = matrix

        // Setup each queue with cells adjacent to their respective ocean
        val pacificQueue = LinkedList<IntArray>()
        val atlanticQueue = LinkedList<IntArray>()
        for (i in 0 until numRows) {
            pacificQueue.offer(intArrayOf(i, 0))
            atlanticQueue.offer(intArrayOf(i, numCols - 1))
        }
        for (i in 0 until numCols) {
            pacificQueue.offer(intArrayOf(0, i))
            atlanticQueue.offer(intArrayOf(numRows - 1, i))
        }

        // Perform a BFS for each ocean to find all cells accessible by each ocean
        val pacificReachable = bfs(pacificQueue)
        val atlanticReachable = bfs(atlanticQueue)

        // Find all cells that can reach both oceans
        val commonCells = mutableListOf<List<Int>>()
        for (i in 0 until numRows) {
            for (j in 0 until numCols) {
                if (pacificReachable[i][j] && atlanticReachable[i][j]) {
                    commonCells.add(listOf(i, j))
                }
            }
        }
        return commonCells
    }

    private fun bfs(queue: Queue<IntArray>): Array<BooleanArray> {
        val reachable = Array(numRows) {
            BooleanArray(numCols)
        }

        while (!queue.isEmpty()) {
            val cell = queue.poll()
            // This cell is reachable, so mark it
            reachable[cell[0]][cell[1]] = true
            for (dir in DIRECTIONS) { // Check all 4 directions
                val newRow = cell[0] + dir[0]
                val newCol = cell[1] + dir[1]
                // Check if new cell is within bounds
                if (newRow < 0 || newRow >= numRows || newCol < 0 || newCol >= numCols) {
                    continue
                }

                // Check that the new cell hasn't already been visited
                if (reachable[newRow][newCol]) {
                    continue
                }

                // Check that the new cell has a higher or equal height,
                // So that water can flow from the new cell to the old cell
                if (landHeights[newRow][newCol] < landHeights[cell[0]][cell[1]]) {
                    continue
                }

                // If we've gotten this far, that means the new cell is reachable
                queue.offer(intArrayOf(newRow, newCol))
            }
        }
        return reachable
    }

    private fun solution2(matrix: Array<IntArray>): List<List<Int>> {
        // Check if input is empty
        if (matrix.isEmpty() || matrix[0].isEmpty()) {
            return ArrayList()
        }

        // Save initial values to parameters
        numRows = matrix.size
        numCols = matrix[0].size
        landHeights = matrix
        val pacificReachable = Array(numRows) {
            BooleanArray(numCols)
        }
        val atlanticReachable = Array(numRows) {
            BooleanArray(numCols)
        }

        // Loop through each cell adjacent to the oceans and start a DFS
        for (i in 0 until numRows) {
            dfs(i, 0, pacificReachable)
            dfs(i, numCols - 1, atlanticReachable)
        }
        for (i in 0 until numCols) {
            dfs(0, i, pacificReachable)
            dfs(numRows - 1, i, atlanticReachable)
        }

        // Find all cells that can reach both oceans
        val commonCells = mutableListOf<List<Int>>()
        for (i in 0 until numRows) {
            for (j in 0 until numCols) {
                if (pacificReachable[i][j] && atlanticReachable[i][j]) {
                    commonCells.add(java.util.List.of(i, j))
                }
            }
        }
        return commonCells
    }

    private fun dfs(row: Int, col: Int, reachable: Array<BooleanArray>) {
        // This cell is reachable, so mark it
        reachable[row][col] = true
        for (dir in DIRECTIONS) { // Check all 4 directions
            val newRow = row + dir[0]
            val newCol = col + dir[1]
            // Check if new cell is within bounds
            if (newRow < 0 || newRow >= numRows || newCol < 0 || newCol >= numCols) {
                continue
            }

            // Check that the new cell hasn't already been visited
            if (reachable[newRow][newCol]) {
                continue
            }

            // Check that the new cell has a higher or equal height,
            // So that water can flow from the new cell to the old cell
            if (landHeights[newRow][newCol] < landHeights[row][col]) {
                continue
            }

            // If we've gotten this far, that means the new cell is reachable
            dfs(newRow, newCol, reachable)
        }
    }

    companion object {
        private val DIRECTIONS = arrayOf(intArrayOf(0, 1), intArrayOf(1, 0), intArrayOf(-1, 0), intArrayOf(0, -1))

        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(
                arrayOf(intArrayOf(1,2,2,3,5), intArrayOf(3,2,3,4,4), intArrayOf(2,4,5,3,1), intArrayOf(6,7,1,4,5), intArrayOf(5,1,1,2,4)),
                arrayOf(intArrayOf(2,1), intArrayOf(1,2))
            )
            for (matrix in arrays) {
                val coordinates = PacificAtlanticWaterFlow().pacificAtlantic(matrix)
                for (coordinate in coordinates) {
                    print("${coordinate}, ")
                }
                println()
            }
        }
    }
}