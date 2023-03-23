package week5

import java.util.*


class IsGraphBipartite {
    fun isBipartite(graph: Array<IntArray>): Boolean {
        return solution1(graph)
    }

    private fun codepath(graph: Array<IntArray>): Boolean {
        val n = graph.size
        val colors = IntArray(n)
        Arrays.fill(colors, -1)

        //This graph might be a disconnected graph.
        //So check each unvisited node.
        for (i in 0 until n) {
            if (colors[i] == -1 && !validColor(graph, colors, 0, i)) {
                return false
            }
        }
        return true
    }

    private fun validColor(graph: Array<IntArray>, colors: IntArray, color: Int, node: Int): Boolean {
        if (colors[node] != -1) {
            return colors[node] == color
        }
        colors[node] = color
        for (next in graph[node]) {
            if (!validColor(graph, colors, 1 - color, next)) {
                return false
            }
        }
        return true
    }

    private fun solution1(graph: Array<IntArray>): Boolean {
        val n = graph.size
        // there can only be 3 values: 0, 1 or -1 (uncolored)
        val colors = IntArray(n) { -1 }
        for (i in 0 until n) {
            // println("n: $n, i: $i")
            if (colors[i] != -1)
                continue

            val stack = Stack<Int>()
            // save node in stack, for DFS processing
            stack.push(i)
            // current node is uncolored: color it 0
            colors[i] = 0
            while (stack.isNotEmpty()) {
                val node = stack.pop()
                // println("popped node: $node, neighbors: ${graph[node!!].contentToString()}")
                // check all neighbors of the current node
                for (neighbor in graph[node!!]) {
                    if (colors[neighbor] == -1) {
                        // save neighbor in stack, for DFS processing
                        stack.push(neighbor)
                        // neighbor is uncolored: color it opposite that of the current node
                        colors[neighbor] = colors[node] xor 1
                        // println("neighbor: $neighbor (uncolored), colors of node: ${colors[node]}, of neighbor: ${colors[neighbor]}")
                    } else {
                        if (colors[neighbor] != colors[node]) {
                            // do nothing
                            // println("neighbor: $neighbor, node: $node, different colors, do nothing")
                        } else {
                            // neighbor's color is the same as the current node: this graph is not bipartite
                            // println("neighbor: $neighbor, node: $node, same color, returning false")
                            return false
                        }
                    }
                }

            }
        }

        // if you get to this point, the graph is bipartite
        return true
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(
                arrayOf(intArrayOf(1,2,3), intArrayOf(0,2), intArrayOf(0,1,3), intArrayOf(0,2)),
                arrayOf(intArrayOf(1, 3), intArrayOf(0, 2), intArrayOf(1, 3), intArrayOf(0, 2))
            )
            for (graph in arrays) {
                print("graph: ")
                for (array in graph) {
                    print("${array.contentToString()}, ")
                }
                val isBipartite = IsGraphBipartite().isBipartite(graph)
                println("is bipartite? $isBipartite")
            }
        }
    }
}
