package week6

class MostStonesRemovedWithSameRowOrColumn {
    fun removeStones(stones: Array<IntArray>): Int {
        return leetcode(stones)
    }

    // https://leetcode.com/problems/most-stones-removed-with-same-row-or-column/discuss/1258480/Java-Standard-Union-Find-Template-with-Arrays-Quicker-and-Easier-than-Other-UF-Submissions
    private fun leetcode(stones: Array<IntArray>): Int {
        val uf = UnionFind(stones)

        // check all the elements with each other and try to union them if they have same column or row
        for (i in stones.indices) {
            for (j in stones.indices) {
                if (stones[i][0] == stones[j][0] || stones[i][1] == stones[j][1]) { // in the same column or row
                    // union based on index
                    uf.union(i, j)
                }
            }
        }

        // take the total number of nodes subtracted by the number of islands
        return stones.size - uf.count
    }

    class UnionFind(stones: Array<IntArray>) {
        // # of connected components (or islands)
        var count = 0
        var parent: IntArray
        var rank: IntArray

        // find with path compression
        fun find(i: Int): Int {
            if (parent[i] != i) {
                parent[i] = find(parent[i])
            }
            return parent[i]
        }

        fun union(x: Int, y: Int) { // union with rank
            val rootx = find(x)
            val rooty = find(y)
            if (rootx != rooty) {
                if (rank[rootx] > rank[rooty]) {
                    parent[rooty] = rootx
                } else if (rank[rootx] < rank[rooty]) {
                    parent[rootx] = rooty
                } else {
                    parent[rooty] = rootx
                    rank[rootx] += 1
                }
                count--
            }
        }

        // set each node to be it's own parent
        init {
            val m = stones.size
            parent = IntArray(m)
            rank = IntArray(m)
            for (i in 0 until m) {
                parent[i] = i
                count++
                rank[i] = 1
            }
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val data = arrayOf(
                arrayOf(intArrayOf(0,0), intArrayOf(0,1), intArrayOf(1,0), intArrayOf(1,2), intArrayOf(2,1), intArrayOf(2,2)),
                arrayOf(intArrayOf(0,0), intArrayOf(0,2), intArrayOf(1,1), intArrayOf(2,0), intArrayOf(2,2)),
                arrayOf(intArrayOf(0,0))
            )
            for (stones in data) {
                val removed = MostStonesRemovedWithSameRowOrColumn().removeStones(stones)
                println("stones removed: $removed")
            }
        }
    }
}

