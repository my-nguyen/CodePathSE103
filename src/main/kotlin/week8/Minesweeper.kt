package week8

import java.util.*

class Minesweeper {
    fun updateBoard(board: Array<CharArray>, click: IntArray): Array<CharArray> {
        return codepath(board, click)
    }

    val directions = Array(8) { IntArray(2) }
    init {
        directions[0] = intArrayOf(-1, -1)
        directions[1] = intArrayOf(-1, 0)
        directions[2] = intArrayOf(-1, 1)
        directions[3] = intArrayOf(0, 1)
        directions[4] = intArrayOf(1, 1)
        directions[5] = intArrayOf(1, 0)
        directions[6] = intArrayOf(1, -1)
        directions[7] = intArrayOf(0, -1)
    }

    private fun codepath(board: Array<CharArray>, cell: IntArray): Array<CharArray> {
        val clickY = cell[0]
        val clickX = cell[1]
        // if you click on a mine ('M'), then reveal the mine ('X') and set game over
        if (board[clickY][clickX] == 'M') {
            board[clickY][clickX] = 'X'
            return board
        }

        val stack = Stack<IntArray>()
        stack.push(cell)
        val visited = mutableSetOf<IntArray>()
        visited.add(cell)
        while (stack.isNotEmpty()) {
            // fetch the top cell
            val top = stack.pop()
            val y = top[0]
            val x = top[1]

            // get the number of mines adjacent to the cell
            val numAdjacentMines = getNumAdjacentMines(y, x, board)
            // if there's no mines, reveal a blank ('B'), otherwise reveal the number of mines
            board[y][x] = if (numAdjacentMines == 0) 'B' else '0' + numAdjacentMines
            // skip the cell if there are mines adjacent to it
            if (numAdjacentMines > 0)
                continue

            // check all 8 surrounding cells
            for (direction in directions) {
                val newY = y + direction[0]
                val newX = x + direction[1]
                // make sure the new cell is within bounds
                if (!isWithinBounds(newY, newX, board))
                    continue

                val newCell = intArrayOf(newY, newX)
                // if new cell is empty and hasn't been visited, save cell to stack and mark it as visited
                if (board[newY][newX] == 'E' && !visited.contains(newCell)) {
                    stack.push(newCell)
                    visited.add(newCell)
                }
            }
        }
        return board
    }

    private fun getNumAdjacentMines(y: Int, x: Int, board: Array<CharArray>): Int {
        var count = 0
        for (direction in directions) {
            val newY = y + direction[0]
            val newX = x + direction[1]
            // (newY, newX) is within bounds and it contains a mine ('M')
            if (isWithinBounds(newY, newX, board) && board[newY][newX] == 'M')
                count++
        }
        return count
    }

    private fun isWithinBounds(y: Int, x: Int, board: Array<CharArray>): Boolean {
        return y in 0..board.lastIndex && x in 0..board[0].lastIndex
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val boards = arrayOf(
                arrayOf(charArrayOf('E','E','E','E','E'), charArrayOf('E','E','M','E','E'), charArrayOf('E','E','E','E','E'), charArrayOf('E','E','E','E','E')),
                arrayOf(charArrayOf('B','1','E','1','B'), charArrayOf('B','1','M','1','B'), charArrayOf('B','1','1','1','B'), charArrayOf('B','B','B','B','B'))
            )
            val clicks = arrayOf(intArrayOf(3,0), intArrayOf(1,2))
            for (i in boards.indices) {
                println("PRE-sweep board:")
                for (row in boards[i])
                    println(row.contentToString())
                Minesweeper().updateBoard(boards[i], clicks[i])
                println("POST-sweep board:")
                for (row in boards[i])
                    println(row.contentToString())
            }
        }
    }
}
