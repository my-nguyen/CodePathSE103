package week5

import java.util.*

class Assessment5_4 {
    fun wallsAndGates(rooms: Array<IntArray>) {
        // mine(rooms)
        solution1(rooms)
        // solution2(rooms)
    }

    private val EMPTY = Int.MAX_VALUE
    private val GATE = 0
    private val WALL = -1
    private val DIRECTIONS = listOf(intArrayOf(1, 0), intArrayOf(-1, 0), intArrayOf(0, 1), intArrayOf(0, -1))

    private fun solution1(rooms: Array<IntArray>) {
        if (rooms.isEmpty()) return

        for (row in rooms.indices) {
            for (col in rooms[0].indices) {
                if (rooms[row][col] == EMPTY) {
                    rooms[row][col] = distanceToNearestGate(rooms, row, col)
                }
            }
        }
    }

    private fun distanceToNearestGate(rooms: Array<IntArray>, startRow: Int, startCol: Int): Int {
        val m = rooms.size
        val n: Int = rooms[0].size
        val distance = Array(m) { IntArray(n) }
        val q = LinkedList<IntArray>()
        q.add(intArrayOf(startRow, startCol))
        while (!q.isEmpty()) {
            val point = q.poll()
            val row = point[0]
            val col = point[1]
            for (direction in DIRECTIONS) {
                val r = row + direction[0]
                val c = col + direction[1]
                if (r < 0 || c < 0 || r >= m || c >= n || rooms[r][c] == WALL || distance[r][c] != 0) {
                    continue
                }

                distance[r][c] = distance[row][col] + 1
                if (rooms[r][c] == GATE) {
                    return distance[r][c]
                }

                q.add(intArrayOf(r, c))
            }
        }
        return Int.MAX_VALUE
    }

    private fun solution2(rooms: Array<IntArray>) {
        val m = rooms.size
        if (m == 0) return

        val n = rooms[0].size
        val q = LinkedList<IntArray>()
        for (row in 0 until m) {
            for (col in 0 until n) {
                if (rooms[row][col] == GATE) {
                    q.add(intArrayOf(row, col))
                }
            }
        }

        while (q.isNotEmpty()) {
            val point = q.poll()
            val row = point[0]
            val col = point[1]
            for (direction in DIRECTIONS) {
                val r = row + direction[0]
                val c = col + direction[1]
                if (r < 0 || c < 0 || r >= m || c >= n || rooms[r][c] != EMPTY) {
                    continue
                }
                rooms[r][c] = rooms[row][col] + 1
                q.add(intArrayOf(r, c))
            }
        }
    }

    private fun mine(rooms: Array<IntArray>) {
        val gates = mutableListOf<IntArray>()
        for (i in rooms.indices) {
            for (j in rooms[i].indices) {
                // collect all gates into a list
                if (rooms[i][j] == 0)
                    gates.add(intArrayOf(i, j))
            }
        }

        if (gates.isEmpty())
            return

        // a queue of IntArray, each array having 3 elements: row index, column index, and depth,
        // which is how far a cell is away from the current gate
        val queue = LinkedList<IntArray>()
        // go thru each gate
        for (k in gates.indices) {
            // println("gate $k: ${gates[k][0]}, ${gates[k][1]}")
            // a gate is at depth 0
            queue.add(intArrayOf(gates[k][0], gates[k][1], 0))
            // each Set entry is a String instead of an IntArray of (i, j) because comparing strings of the form "i-j"
            // is easier than comparing 2 IntArray's of (i, j)
            val visited = mutableSetOf<String>()
            while (queue.isNotEmpty()) {
                // take cell from top of the queue
                val cell = queue.poll()
                // break up cell into row index, column index, and depth, for ease of processing
                val i = cell[0]
                val j = cell[1]
                val depth = cell[2]

                // mark current cell as visited
                visited.add("$i-$j")
                // print("i: $i, j: $j, ")

                // if current cell is not a gate, then set the depth level to the current cell
                // if room is empty, then set it to the depth
                // if not, then set it to the min between the current cell's depth and the current depth
                if (!gates.contains(cell)) {
                    if (rooms[i][j] == 2147483647)
                        rooms[i][j] = depth
                    else
                        rooms[i][j] = minOf(rooms[i][j], depth)
                    // print("count: $depth, ")
                }

                // try to add neighboring cells as children of the current cell. any successfully added cell will have
                // its depth level incremented so that children will be at 1 depth level higher than their parent.
                if (i-1 >= 0 && isValid(rooms, i-1, j, visited)) {
                    queue.add(intArrayOf(i-1, j, depth+1))
                    // print("added up, ")
                }
                if (i+1 < rooms.size && isValid(rooms, i+1, j, visited)) {
                    queue.add(intArrayOf(i+1, j, depth+1))
                    // print("added down, ")
                }
                if (j-1 >= 0 && isValid(rooms, i, j-1, visited)) {
                    queue.add(intArrayOf(i, j-1, depth+1))
                    // print("added left, ")
                }
                if (j+1 < rooms[i].size && isValid(rooms, i, j+1, visited)) {
                    queue.add(intArrayOf(i, j+1, depth+1))
                    // print("added right, ")
                }
                // println()
            }
        }
    }

    private fun isValid(rooms: Array<IntArray>, i: Int, j: Int, visited: Set<String>): Boolean {
        return rooms[i][j] != -1 && rooms[i][j] != 0 && !visited.contains("$i-$j")
    }

    fun print(rooms: Array<IntArray>) {
        for (room in rooms) {
            println(room.contentToString())
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(
                arrayOf(
                    intArrayOf(2147483647,-1,0,2147483647), intArrayOf(2147483647,2147483647,2147483647,-1),
                    intArrayOf(2147483647,-1,2147483647,-1), intArrayOf(0,-1,2147483647,2147483647)
                ),
                arrayOf(intArrayOf(-1)),
                arrayOf(intArrayOf(2147483647))
            )
            for (rooms in arrays) {
                val ass = Assessment5_4()
                println("pre-filled rooms:")
                ass.print(rooms)
                ass.wallsAndGates(rooms)
                println("post-filled rooms:")
                ass.print(rooms)
            }
        }
    }
}