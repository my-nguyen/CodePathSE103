package week5


class MinimumHeightTrees {
    fun findMinHeightTrees(n: Int, edges: Array<IntArray>): List<Int> {
        return solution(n, edges)
    }

    private fun codepath(n: Int, edges: Array<IntArray>): List<Int> {
        if (n == 0) {
            return ArrayList()
        } else if (n == 1) {
            val ret: MutableList<Int> = ArrayList()
            ret.add(0)
            return ret
        }
        val graph = buildGraph(n, edges)
        var leaves: MutableList<Int> = ArrayList()
        for (i in 0 until n) {
            if (graph[i].size == 1) {
                leaves.add(i)
            }
        }
        var count = n

        // Keep removing leaves until there are only 1 or 2 nodes left
        var newLeaves: MutableList<Int>
        while (count > 2) {
            val size = leaves.size
            newLeaves = ArrayList()
            count -= size
            for (i in 0 until size) {
                val leaf = leaves[i]
                for (j in graph[leaf].indices) {
                    val toRemove = graph[leaf][j]
                    graph[toRemove].remove(Integer.valueOf(leaf))
                    if (graph[toRemove].size == 1) newLeaves.add(toRemove)
                }
            }
            leaves = newLeaves
        }
        return leaves
    }

    private fun buildGraph(n: Int, edges: Array<IntArray>): Array<MutableList<Int>> {
        val graph = Array(n) { mutableListOf<Int>() }
        for (i in 0 until n) {
            graph[i] = ArrayList()
        }
        for (i in edges.indices) {
            val v1 = edges[i][0]
            val v2 = edges[i][1]
            graph[v1].add(v2)
            graph[v2].add(v1)
        }
        return graph
    }

    private fun solution(n: Int, edges: Array<IntArray>): List<Int> {
        // base cases
        if (n < 2) {
            val centroids = IntArray(n) { it }
            // println("centroids: ${centroids.contentToString()}")
            return centroids.toList()
        }

        // Build the graph with the adjacency list
        val neighbors = mutableListOf<MutableSet<Int>>()
        for (i in 0 until n) {
            neighbors.add(mutableSetOf())
        }
        for (edge in edges) {
            val start = edge[0]
            val end = edge[1]
            neighbors[start].add(end)
            neighbors[end].add(start)
        }

        // Initialize the first layer of leaves
        var leaves = mutableListOf<Int>()
        for (i in 0 until n) {
            if (neighbors[i].size == 1)
                leaves.add(i)
        }

        // Trim the leaves until reaching the centroids
        var remainingNodes = n
        while (remainingNodes > 2) {
            remainingNodes -= leaves.size
            val newLeaves = mutableListOf<Int>()

            // remove the current leaves along with the edges
            for (leaf in leaves) {
                // the only neighbor left for the leaf node
                val neighbor = neighbors[leaf].iterator().next()
                // remove the edge along with the leaf node
                neighbors[neighbor].remove(leaf)
                if (neighbors[neighbor].size == 1)
                    newLeaves.add(neighbor)
            }

            // prepare for the next round
            leaves = newLeaves
        }

        // The remaining nodes are the centroids of the graph
        return leaves
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val nodes = arrayOf(4, 6, 1, 2)
            val edges = arrayOf(
                arrayOf(intArrayOf(1,0), intArrayOf(1,2), intArrayOf(1,3)),
                arrayOf(intArrayOf(3,0), intArrayOf(3,1), intArrayOf(3,2), intArrayOf(3,4), intArrayOf(5,4)),
                arrayOf(),
                arrayOf(intArrayOf(0,1))
            )
            for (i in nodes.indices) {
                print("nodes: ${nodes[i]}, edges: ")
                for (edge in edges[i]) {
                    print("${edge.contentToString()}, ")
                }
                val trees = MinimumHeightTrees().findMinHeightTrees(nodes[i], edges[i])
                println("MHT: $trees")
            }
        }
    }
}
