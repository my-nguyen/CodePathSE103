package week6

import java.util.*

// This class represents a directed graph using adjacency list representation
class TopologicalSort2(private val size: Int) {
    private val adjacency = Array(size) {
        mutableListOf<Int>()
    }

    fun addEdge(v: Int, w: Int) {
        adjacency[v].add(w)
    }

    fun sort(): List<Int> {
        val stack = Stack<Int>()

        // Mark all the vertices as not visited
        val visited = BooleanArray(size)

        // Call the recursive helper function to store Topological Sort starting from all vertices one by one
        for (i in 0 until size) {
            // println("vertex: $i, visited? ${visited[i]}")
            if (!visited[i])
                dfs(i, visited, stack)
        }

        return stack.toList().reversed()
    }

    // A recursive function used by topologicalSort
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

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            // Create a graph given in the above diagram
            val g = TopologicalSort2(6)
            g.addEdge(5, 2)
            g.addEdge(5, 0)
            g.addEdge(4, 0)
            g.addEdge(4, 1)
            g.addEdge(2, 3)
            g.addEdge(3, 1)
            print("topological sort: ")
            val sorted = g.sort()
            println(sorted)
        }
    }
}