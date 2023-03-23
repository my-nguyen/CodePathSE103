package week5

class DetectCycleInADirectedGraph {
    fun hasCycle(graph: Graph): Boolean {
        // return codePath(graph)
        return geeksForGeeks(graph as Graph2)
    }

    fun geeksForGeeks(graph: Graph2): Boolean {
        return graph.hasCycle()
    }

    fun codePath(graph: Graph1): Boolean {
        val states = mutableMapOf<Int, State>()

        for ((k, v) in graph.edges) {
            if (hasCycle(k, graph, states))
                return true
        }
        return false
    }

    private fun hasCycle(vertex: Int, graph: Graph1, states: MutableMap<Int, State>): Boolean {
        if (states.containsKey(vertex) && states[vertex] == State.VISITIED)
            return false

        states[vertex] = State.VISITING
        for (neighbor in graph.edges[vertex]!!) {
            if (states.containsKey(neighbor)) {
                if (states[neighbor] == State.VISITING)
                    return true
            } else if (hasCycle(neighbor, graph, states))
                return true
        }

        states[vertex] = State.VISITIED
        return false
    }

    interface Graph {
        fun addEdge(source: Int, dest: Int)
    }

    class Graph1: Graph {
        val edges = mutableMapOf<Int, MutableList<Int>>()

        override fun addEdge(start: Int, end: Int) {
            edges.putIfAbsent(start, mutableListOf())
            edges[start]!!.add(end)
        }

        fun print() {
            for ((k, v) in edges) {
                print("$k -> $v, ")
            }
            println()
        }
    }

    class Graph2(private val vertices: Int): Graph {
        private val adj = Array(vertices) { mutableListOf<Int>() }

        override fun addEdge(source: Int, dest: Int) {
            adj[source].add(dest)
        }

        // Returns true if the graph contains a cycle, else false.
        // This function is a variation of DFS() in https://www.geeksforgeeks.org/archives/18212
        fun hasCycle(): Boolean {
            // Mark all the vertices as not visited and not part of recursion stack
            val visited = BooleanArray(vertices)
            val recStack = BooleanArray(vertices)

            // Call the recursive helper function to detect cycle in different DFS trees
            for (i in 0 until vertices) {
                if (isCyclic(i, visited, recStack))
                    return true
            }
            return false
        }

        // This function is a variation of DFSUtil() in https://www.geeksforgeeks.org/archives/18212
        private fun isCyclic(i: Int, visited: BooleanArray, recStack: BooleanArray): Boolean {
            // Mark the current node as visited and part of recursion stack
            if (recStack[i])
                return true
            if (visited[i])
                return false

            visited[i] = true
            recStack[i] = true
            val children = adj[i]
            for (child in children) {
                if (isCyclic(child, visited, recStack))
                    return true
            }
            recStack[i] = false
            return false
        }
    }

    enum class State {
        VISITING,
        VISITIED
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val source = arrayOf(arrayOf(1,2,3,1,1), arrayOf(0,0,1,2,2,3))
            val dest = arrayOf(arrayOf(2,3,1,3,4), arrayOf(1,2,2,0,3,3))
            val graphs = arrayOf(Graph1(), Graph2(4))
            for (i in source.indices) {
                val graph = graphs[i]
                for (j in source[i].indices) {
                    graph.addEdge(source[i][j], dest[i][j])
                }
                /*print("edges: ")
                graph.print()*/
                val detect = DetectCycleInADirectedGraph().hasCycle(graph)
                println("graph has cycle? $detect")
            }
        }
    }
}