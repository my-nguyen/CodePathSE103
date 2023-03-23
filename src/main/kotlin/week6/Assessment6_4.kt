package week6

class Assessment6_4 {
    fun possibleBipartition(n: Int, dislikes: Array<IntArray>): Boolean {
        val graph = mutableMapOf<Int, MutableList<Int>>()
        for (dislike in dislikes) {
            val source = dislike[0]
            val dest = dislike[1]
            if (!graph.containsKey(source)) {
                val list = mutableListOf<Int>()
                graph[source] = list
            }
            graph[source]!!.add(dest)
        }
        val groups = IntArray(n + 1)
        for (i in 0..n)
            groups[i] = -1
        for (i in 0 until n) {
            if (!dfs(i, graph, groups, 0))
                return false
        }
        return true
    }

    private fun dfs(index: Int, graph: Map<Int, MutableList<Int>>, groups: IntArray, next: Int): Boolean {
        if (!graph.containsKey(index))
            return true

        groups[index] = next
        for (neighbor in graph[index]!!) {
            if (groups[neighbor] == -1)
                dfs(neighbor, graph, groups, (next + 1) % 2)
            else if (groups[neighbor] == next)
                return false
        }
        return true
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val sizes = arrayOf(4, 3, 5)
            val dislikes = arrayOf(
                arrayOf(intArrayOf(1,2), intArrayOf(1,3), intArrayOf(2,4)),
                arrayOf(intArrayOf(1,2), intArrayOf(1,3), intArrayOf(2,3)),
                arrayOf(intArrayOf(1,2), intArrayOf(2,3), intArrayOf(3,4), intArrayOf(4,5), intArrayOf(1,5)),
            )
            for (i in sizes.indices) {
                val possible = Assessment6_4().possibleBipartition(sizes[i], dislikes[i])
                println("possible partition? $possible")
            }
        }
    }
}