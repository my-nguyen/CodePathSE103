package week6

import java.util.*


class SurroundedRegions {
    fun solve(board: Array<CharArray>) {
        solution1(board)
        // solution2(board)
    }

    private var ROWS = 0
    private var COLS = 0

    private fun solution1(board: Array<CharArray>?) {
        if (board.isNullOrEmpty())
            return

        ROWS = board.size
        COLS = board[0].size
        val borders = mutableListOf<Pair<Int, Int>>()

        // collect position of all the cells of the 4 borders
        for (r in 0 until ROWS) {
            // leftmost and rightmost columns
            borders.add(Pair(r, 0))
            borders.add(Pair(r, COLS - 1))
        }
        for (c in 0 until COLS) {
            // top and bottom rows
            borders.add(Pair(0, c))
            borders.add(Pair(ROWS - 1, c))
        }

        // temporarily flip all 'O' border cells to 'E'
        for (pair in borders) {
            DFS(board, pair.first, pair.second)
        }

        // now the inside cells are either 'O' or 'X': flip the 'O' cells to 'X'
        // the border cells are either 'E' or 'X': flip the 'E' cells back to 'O'
        for (r in 0 until ROWS) {
            for (c in 0 until COLS) {
                if (board[r][c] == 'O')
                    board[r][c] = 'X'
                if (board[r][c] == 'E')
                    board[r][c] = 'O'
            }
        }
    }

    private fun solution2(board: Array<CharArray>) {
        if (board.isNullOrEmpty())
            return

        ROWS = board.size
        COLS = board[0].size
        val borders = mutableListOf<Pair<Int, Int>>()

        // Step 1). construct the list of border cells
        for (r in 0 until ROWS) {
            borders.add(Pair(r, 0))
            borders.add(Pair(r, COLS - 1))
        }
        for (c in 0 until COLS) {
            borders.add(Pair(0, c))
            borders.add(Pair(ROWS - 1, c))
        }

        // Step 2). mark the escaped cells
        for (pair in borders) {
            BFS(board, pair.first, pair.second)
        }

        // Step 3). flip the cells to their correct final states
        for (r in 0 until ROWS) {
            for (c in 0 until COLS) {
                if (board[r][c] == 'O')
                    board[r][c] = 'X'
                if (board[r][c] == 'E')
                    board[r][c] = 'O'
            }
        }
    }

    private fun BFS(board: Array<CharArray>, r: Int, c: Int) {
        val queue = LinkedList<Pair<Int, Int>>()
        queue.offer(Pair(r, c))
        while (queue.isNotEmpty()) {
            val pair = queue.poll()
            val row = pair.first
            val col = pair.second
            if (board[row][col] != 'O')
                continue

            board[row][col] = 'E'
            if (col < COLS - 1)
                queue.offer(Pair(row, col + 1))
            if (row < ROWS - 1)
                queue.offer(Pair(row + 1, col))
            if (col > 0)
                queue.offer(Pair(row, col - 1))
            if (row > 0)
                queue.offer(Pair(row - 1, col))
        }
    }

    private fun DFS(board: Array<CharArray>, row: Int, col: Int) {
        if (board[row][col] != 'O')
            return

        // temporarily flip the current border cell at (row, col) from 'O' to 'E'
        board[row][col] = 'E'

        // proceed to the 4 surrounding cells
        if (col < COLS - 1)
            DFS(board, row, col + 1)
        if (row < ROWS - 1)
            DFS(board, row + 1, col)
        if (col > 0)
            DFS(board, row, col - 1)
        if (row > 0)
            DFS(board, row - 1, col)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val boards = arrayOf(
                arrayOf(charArrayOf('X','X','X','X'), charArrayOf('X','O','O','X'), charArrayOf('X','X','O','X'), charArrayOf('X','O','X','X')),
                // arrayOf(charArrayOf('X'))
            )
            for (board in boards) {
                println("PRE-solved")
                for (row in board) {
                    println(row.contentToString())
                }
                SurroundedRegions().solve(board)
                println("POST-solved")
                for (row in board) {
                    println(row.contentToString())
                }
            }
        }
    }
}
