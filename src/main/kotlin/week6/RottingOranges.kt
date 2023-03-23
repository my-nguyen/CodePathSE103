package week6

import java.util.*

class RottingOranges {
    fun orangesRotting(grid: Array<IntArray>): Int {
        // return solution1(grid)
        return solution2(grid)
    }

    private fun solution1(grid: Array<IntArray>): Int {
        val rotten = LinkedList<Pair<Int, Int>>()

        // Step 1). collect the positions of all rotten oranges
        var fresh = 0
        for (r in grid.indices) {
            for (c in grid[r].indices) {
                if (grid[r][c] == 2)
                    // it's an rotten orange: save its location
                    rotten.offer(Pair(r, c))
                else if (grid[r][c] == 1)
                    // it's a fresh orange: increment count
                    fresh++
            }
        }

        // Mark the round / level, _i.e_ the ticker of timestamp
        rotten.offer(Pair(-1, -1))

        // Step 2). start the rotting process via BFS
        var minutes = -1
        val directions = arrayOf(intArrayOf(-1, 0), intArrayOf(0, 1), intArrayOf(1, 0), intArrayOf(0, -1))

        // go thru the collection of rotten oranges
        while (rotten.isNotEmpty()) {
            val top = rotten.poll()
            val row = top.first
            val col = top.second
            if (row == -1) {
                // We finish one round of processing
                minutes++
                // to avoid the endless loop
                if (rotten.isNotEmpty())
                    rotten.offer(Pair(-1, -1))
            } else {
                // this is a rotten orange: check all 4 of its neighboring cells
                for (direction in directions) {
                    val i = row + direction[0]
                    val j = col + direction[1]
                    if (i in grid.indices && j in grid[0].indices) {
                        if (grid[i][j] == 1) {
                            // this orange is fresh: contaminate it
                            grid[i][j] = 2
                            // decrement fresh orange count
                            fresh--
                            // save this orange so it will contaminate other oranges in next round
                            rotten.offer(Pair(i, j))
                        }
                    }
                }
            }
        }

        // return elapsed minutes if no fresh orange left
        return if (fresh == 0) minutes else -1
    }

    private fun solution2(grid: Array<IntArray>): Int {
        var timestamp = 2
        while (runRottingProcess(timestamp, grid))
            timestamp++

        // end of process, to check if there are still fresh oranges left
        for (row in grid) {
            for (cell in row) {
                // still got a fresh orange left
                if (cell == 1)
                    return -1
            }
        }

        // return elapsed minutes if no fresh orange left
        return timestamp - 2
    }

    // run the rotting process, by marking the rotten oranges with the timestamp
    private fun runRottingProcess(timestamp: Int, grid: Array<IntArray>): Boolean {
        val directions = arrayOf(intArrayOf(-1, 0), intArrayOf(0, 1), intArrayOf(1, 0), intArrayOf(0, -1))
        // flag to indicate if the rotting process should be continued
        var toBeContinued = false
        for (row in grid.indices) {
            for (col in grid[row].indices) {
                if (grid[row][col] == timestamp) {
                    // current contaminated cell
                    for (direction in directions) {
                        val i = row + direction[0]
                        val j = col + direction[1]
                        if (i in grid.indices && j in grid[0].indices) {
                            if (grid[i][j] == 1) {
                                // this fresh orange would be contaminated next
                                grid[i][j] = timestamp + 1
                                toBeContinued = true
                            }
                        }
                    }
                }
            }
        }
        return toBeContinued
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val grids = arrayOf(
                arrayOf(intArrayOf(2,1,1), intArrayOf(1,1,0), intArrayOf(0,1,1)),
                arrayOf(intArrayOf(2,1,1), intArrayOf(0,1,1), intArrayOf(1,0,1)),
                arrayOf(intArrayOf(0,2))
            )
            for (grid in grids) {
                val minutes = RottingOranges().orangesRotting(grid)
                println("number of minutes: $minutes")
            }
        }
    }
}
