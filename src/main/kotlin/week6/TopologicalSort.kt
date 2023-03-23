package week6

import java.util.*

class TopologicalSort {
    fun sort(size: Int, graph: Map<Int, List<Int>>): List<Int> {
        // boolean array to mark nodes that have been visited
        val visited = BooleanArray(size)
        // stack to save nodes whose neighbors are all exhausted
        val stack = Stack<Int>()
        // keys are indices: 0, 1, 2, etc. so this iterates by index
        for (key in graph.keys) {
            // make sure not to visit nodes that have been visited
            if (!visited[key])
                dfs(graph, key, visited, stack)
        }
        return stack.toList().reversed()
    }

    private fun dfs(graph: Map<Int, List<Int>>, node: Int, visited: BooleanArray, stack: Stack<Int>) {
        // mark current node as visited to avoid looping back into it
        visited[node] = true
        // extra measure to make sure node is in graph, like when data is corrupt
        if (graph.containsKey(node)) {
            // println("node: $node, neighbors: ${graph[node]}")
            // go thru all neighbors of the current node
            for (neighbor in graph[node]!!) {
                // println("neighbor: $neighbor, visited? ${visited[neighbor]}")
                // make sure not to visit neighbors that have been visited
                if (!visited[neighbor])
                    dfs(graph, neighbor, visited, stack)
            }
        }
        // println("pushed $node")
        // save the current node
        stack.push(node)
    }

    companion object {
        private fun build1(arrays: Array<IntArray>): Map<Int, List<Int>> {
            val map = mutableMapOf<Int, MutableList<Int>>()
            // each entry is a 2-element array in the form of [dest, source]
            for (array in arrays) {
                val source = array[1]
                val dest = array[0]
                map.getOrPut(source) { mutableListOf() }
                map[source]!!.add(dest)
            }
            return map
        }

        private fun build2(arrays: Array<IntArray>): Map<Int, List<Int>> {
            val map = mutableMapOf<Int, List<Int>>()
            // each entry is the value and its index is the key, to a map
            for (i in arrays.indices) {
                map[i] = arrays[i].toList()
            }
            return map
        }

        private fun test1() {
            val sizes = arrayOf(2, 4)
            val arrays = arrayOf(
                arrayOf(intArrayOf(1,0)),
                arrayOf(intArrayOf(1,0), intArrayOf(2,0), intArrayOf(3,1), intArrayOf(3,2)),
            )
            for (i in sizes.indices) {
                val graph = build1(arrays[i])
                val sorted = TopologicalSort().sort(sizes[i], graph)
                println("sorted: $sorted")
            }
        }

        private fun test2() {
            val sizes = arrayOf(6, /*7*/)
            val arrays = arrayOf(
                arrayOf(intArrayOf(), intArrayOf(), intArrayOf(3), intArrayOf(1), intArrayOf(0,1), intArrayOf(2,0)),
                arrayOf(intArrayOf(1,2), intArrayOf(2,5), intArrayOf(3), intArrayOf(), intArrayOf(), intArrayOf(3,4), intArrayOf(1,5))
            )
            for (i in sizes.indices) {
                val graph = build2(arrays[i])
                val sorted = TopologicalSort().sort(sizes[i], graph)
                // println("sorted: $sorted")
            }
        }

        @JvmStatic
        fun main(args: Array<String>) {
            // test1()
            test2()
        }
    }
}