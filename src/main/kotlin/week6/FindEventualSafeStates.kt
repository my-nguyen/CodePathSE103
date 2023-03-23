package week6

import java.util.*


class FindEventualSafeStates {
    fun eventualSafeNodes(graph: Array<IntArray>): List<Int> {
        return codepath(graph)
        // return solution1(graph)
        // return solution2(graph)
    }

    private fun solution1(graph: Array<IntArray>): List<Int> {
        val safe = BooleanArray(graph.size)
        val graph = Array(graph.size) { mutableSetOf<Int>() }
        val rgraph = Array(graph.size) { mutableSetOf<Int>() }
        val queue = LinkedList<Int>()
        for (i in graph.indices) {
            if (graph[i].isEmpty())
                queue.offer(i)
            for (j in graph[i]) {
                graph[i]!!.add(j)
                rgraph[j]!!.add(i)
            }
        }
        while (queue.isNotEmpty()) {
            val j = queue.poll()
            safe[j] = true
            for (i in rgraph[j]!!) {
                graph[i]!!.remove(j)
                if (graph[i]!!.isEmpty())
                    queue.offer(i)
            }
        }
        val ans = mutableListOf<Int>()
        for (i in graph.indices) {
            if (safe[i])
                ans.add(i)
        }
        return ans
    }

    private fun solution2(graph: Array<IntArray>): List<Int> {
        val color = IntArray(graph.size)
        val ans = mutableListOf<Int>()
        for (i in graph.indices) {
            if (dfs(i, color, graph))
                ans.add(i)
        }
        return ans
    }

    // colors: WHITE 0, GRAY 1, BLACK 2;
    fun dfs(node: Int, color: IntArray, graph: Array<IntArray>): Boolean {
        if (color[node] > 0)
            return color[node] == 2

        color[node] = 1
        for (nei in graph[node]) {
            if (color[node] == 2)
                continue
            if (color[nei] == 1 || !dfs(nei, color, graph))
                return false
        }
        color[node] = 2
        return true
    }

    // all the possible states
    val UNVISITED = 0
    val VISITING = 1
    val SAFE = 2
    val UNSAFE = 3
    private fun codepath(graph: Array<IntArray>): List<Int> {
        // create a state array, initialize all its elements to UNVISITED
        val states = IntArray(graph.size) { UNVISITED }
        for (node in graph.indices) {
            if (states[node] == UNVISITED)
                dfs(graph, node, states)
        }

        // collect the safe nodes
        val safe = mutableListOf<Int>()
        for (i in states.indices) {
            if (states[i] == SAFE)
                safe.add(i)
        }
        return safe
    }

    private fun dfs(graph: Array<IntArray>, node: Int, states: IntArray): Boolean {
        return when (states[node]) {
            SAFE -> true
            VISITING, UNSAFE -> false
            else -> { // UNVISITED
                // mark the current node so dfs() won't re-visit it
                states[node] = VISITING

                var isSafe = true
                // check each neighbor of the current node
                for (neighbor in graph[node]) {
                    // a node is safe only when all of its neighbors are safe
                    if (!dfs(graph, neighbor, states)) {
                        isSafe = false
                        break
                    }
                    /*isSafe = isSafe and dfs(graph, neighbor, states)
                    if (!isSafe)
                        break*/
                }
                // now set the correct state and return
                states[node] = if (isSafe) SAFE else UNSAFE
                isSafe
            }
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val graphs = arrayOf(
                arrayOf(intArrayOf(1,2), intArrayOf(2,3), intArrayOf(5), intArrayOf(0), intArrayOf(5), intArrayOf(), intArrayOf()),
                arrayOf(intArrayOf(1,2,3,4), intArrayOf(1,2), intArrayOf(3,4), intArrayOf(0,4), intArrayOf())
            )
            for (graph in graphs) {
                print("graph: ")
                for (array in graph) {
                    print("${array.contentToString()}, ")
                }
                val safeNodes = FindEventualSafeStates().eventualSafeNodes(graph)
                println("safe nodes: $safeNodes")
            }
        }
    }
}



