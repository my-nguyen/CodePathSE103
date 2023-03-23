package week6

import java.util.*


class Graph(private val numVertices: Int) {
    private val adjacency = Array(numVertices) {
        mutableListOf<Int>()
    }

    fun addEdge(startVertex: Int, endVertex: Int) {
        adjacency[startVertex].add(endVertex)
    }

    fun topologicalSort(): List<Char> {
        val stack = Stack<Int>()

        // Mark all the vertices as not visited
        val visited = BooleanArray(numVertices)

        // Call the recursive helper function to store Topological Sort starting from all vertices one by one
        for (i in 0 until numVertices) {
            // println("vertex: $i, visited? ${visited[i]}")
            if (!visited[i])
                dfs(i, visited, stack)
        }

        return stack.toList().reversed().map { 'a' + it }
    }

    private fun dfs(vertex: Int, visited: BooleanArray, stack: Stack<Int>) {
        // Mark the current node as visited.
        visited[vertex] = true

        // Recur for all the vertices adjacent to this vertex
        val it = adjacency[vertex].iterator()
        while (it.hasNext()) {
            val i = it.next()
            // println("i: $i, visited? ${visited[i]}")
            if (!visited[i])
                dfs(i, visited, stack)
        }

        // Push current vertex to stack which stores result
        stack.push(vertex)
    }
}

class Assessment6_2 {
    fun getOrder(words: Array<String>, alpha: Int): List<Char> {
        val graph = Graph(alpha)
        for (i in 0 until words.size - 1) {
            val first = words[i]
            val second = words[i + 1]
            // println("first: $first, second: $second")
            val max = minOf(first.length, second.length)
            for (j in 0 until max) {
                val source = first[j]
                val dest = second[j]
                if (source != dest) {
                    graph.addEdge(source-'a', dest-'a')
                    break
                }
            }
        }
        return graph.topologicalSort()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val words = arrayOf(
                arrayOf("baa", "abcd", "abca", "cab", "cad"),
                arrayOf("caa", "aaa", "aab")
            )
            val alphas = arrayOf(4, 3)
            for (i in words.indices) {
                val sorted = Assessment6_2().getOrder(words[i], alphas[i])
                println("topologically sorted: $sorted")
            }
        }
    }
}