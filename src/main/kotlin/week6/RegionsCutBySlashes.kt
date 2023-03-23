package week6

class RegionsCutBySlashes {
    fun regionsBySlashes(grid: Array<String>): Int {
        return solution(grid)
    }

    private fun solution(grid: Array<String>): Int {
        val N = grid.size
        val dsu = DSU(4 * N * N)
        for (r in grid.indices) {
            for (c in grid[0].indices) {
                val root = 4 * (r * N + c)
                val `val` = grid[r][c]
                if (`val` != '\\') {
                    dsu.union(root + 0, root + 1)
                    dsu.union(root + 2, root + 3)
                }
                if (`val` != '/') {
                    dsu.union(root + 0, root + 2)
                    dsu.union(root + 1, root + 3)
                }

                // north south
                if (r + 1 < N)
                    dsu.union(root + 3, root + 4 * N + 0)
                if (r - 1 >= 0)
                    dsu.union(root + 0, root - 4 * N + 3)
                // east west
                if (c + 1 < N)
                    dsu.union(root + 2, root + 4 + 1)
                if (c - 1 >= 0)
                    dsu.union(root + 1, root - 4 + 2)
            }
        }
        var ans = 0
        for (x in 0 until 4 * N * N) {
            if (dsu.find(x) == x) ans++
        }
        return ans
    }


    class DSU(N: Int) {
        var parent: IntArray
        fun find(x: Int): Int {
            if (parent[x] != x)
                parent[x] = find(parent[x])
            return parent[x]
        }

        fun union(x: Int, y: Int) {
            parent[find(x)] = find(y)
        }

        init {
            parent = IntArray(N)
            for (i in 0 until N)
                parent[i] = i
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val grids = arrayOf(
                arrayOf(" /", "/ "),
                arrayOf(" /", "  "),
                arrayOf("\\/", "/\\"),
                arrayOf("/\\", "\\/"),
                arrayOf("//", "/ ")
            )
            for (grid in grids) {
                val regions = RegionsCutBySlashes().regionsBySlashes(grid)
                println("regions: $regions")
            }
        }
    }
}
