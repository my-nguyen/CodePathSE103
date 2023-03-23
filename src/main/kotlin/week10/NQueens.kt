package week10

class NQueens {
    fun solveNQueens(n: Int): List<List<String>> {
        return solution(n)
    }

    private var size = 0
    private val solutions: MutableList<List<String>> = ArrayList()
    fun solution(n: Int): List<List<String>> {
        size = n
        val emptyBoard = Array(size) { CharArray(size) }
        for (i in 0 until n) {
            for (j in 0 until n) {
                emptyBoard[i][j] = '.'
            }
        }
        backtrack(0, HashSet(), HashSet(), HashSet(), emptyBoard)
        return solutions
    }

    // Making use of a helper function to get the
    // solutions in the correct output format
    private fun createBoard(state: Array<CharArray>): List<String> {
        val board: MutableList<String> = ArrayList()
        for (row in 0 until size) {
            val current_row = String(state[row])
            board.add(current_row)
        }
        return board
    }

    private fun backtrack(row: Int, diagonals: MutableSet<Int>, antiDiagonals: MutableSet<Int>,
                          cols: MutableSet<Int>, state: Array<CharArray>) {
        // Base case - N queens have been placed
        if (row == size) {
            solutions.add(createBoard(state))
            return
        }
        for (col in 0 until size) {
            val currDiagonal = row - col
            val currAntiDiagonal = row + col
            // If the queen is not placeable
            if (cols.contains(col) || diagonals.contains(currDiagonal) || antiDiagonals.contains(currAntiDiagonal)) {
                continue
            }

            // "Add" the queen to the board
            cols.add(col)
            diagonals.add(currDiagonal)
            antiDiagonals.add(currAntiDiagonal)
            state[row][col] = 'Q'

            // Move on to the next row with the updated board state
            backtrack(row + 1, diagonals, antiDiagonals, cols, state)

            // "Remove" the queen from the board since we have already
            // explored all valid paths using the above function call
            cols.remove(col)
            diagonals.remove(currDiagonal)
            antiDiagonals.remove(currAntiDiagonal)
            state[row][col] = '.'
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val counts = arrayOf(4, 1)
            for (n in counts) {
                println("n: $n")
                val solutions = NQueens().solveNQueens(n)
                for (list in solutions) {
                    println("board:")
                    for (string in list)
                        println(string)
                }
            }
        }
    }
}