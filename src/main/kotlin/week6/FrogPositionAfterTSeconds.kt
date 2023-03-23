package week6

import java.util.*

class FrogPositionAfterTSeconds {
    fun frogPosition(n: Int, edges: Array<IntArray>, t: Int, target: Int): Double {
        return codepath(n, edges, t, target)
    }

    private fun codepath(n: Int, edges: Array<IntArray>, time: Int, target: Int): Double {
        val graph = buildGraph(edges)
        print("graph: ")
        for ((k, v) in graph) {
            print("node: $k, neighbors: $v, ")
        }
        println()
        val queue = LinkedList<Pair<Int, Double>>()
        queue.add(Pair(1, 1.0))
        var timeLeft = time

        while (queue.isNotEmpty() && timeLeft >= 0) {
            val size = queue.size
            for (i in 0 until size) {
                val top = queue.poll()
                val node = top.first
                val probability = top.second

                // println("node: $node, graph[node]: ${graph[node]}")
                if (node == target) {
                    if (timeLeft == 0 || graph[node] == null || graph[node]!!.isEmpty()) {
                        // println("timeLeft: $timeLeft, node: $node, graph[node]: ${graph[node]}, probability: $probability")
                        return probability
                    }
                } else if (graph[node] != null) {
                    if (graph[node]!!.isEmpty()) {
                        // println("node: $node, graph[node] is empty, queuing pair: ($node, $probability)")
                        queue.add(Pair(node, probability))
                    } else {
                        val removedEdges = mutableListOf<Int>()
                        for (neighbor in graph[node]!!) {
                            val chance = probability / graph[node]!!.size
                            // println("prob: $probability, size: ${graph[node]!!.size}, chance: $chance")
                            queue.add(Pair(neighbor, chance))
                            // println("queuing pair ($neighbor, $chance), graph[neighbor]: ${graph[neighbor]}, removing node $node from graph[neighbor], and saving neighbor $neighbor")
                            graph[neighbor]?.remove(node)
                            removedEdges.add(neighbor)
                        }
                        graph[node] = graph[node]!!.minus(removedEdges).toMutableList()
                        // println("node $node, post removal graph[node]: ${graph[node]}")
                    }
                }
            }
            timeLeft--
        }
        return 0.0
    }

    private fun buildGraph(edges: Array<IntArray>): MutableMap<Int, MutableList<Int>> {
        val graph = mutableMapOf<Int, MutableList<Int>>()
        for (edge in edges) {
            val source = edge[0]
            val dest = edge[1]
            graph.getOrPut(source) { mutableListOf() }
            graph[source]!!.add(dest)
        }
        return graph
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val counts = arrayOf(3, /*7, 7, 7*/)
            val edges = arrayOf(
                arrayOf(intArrayOf(2,1), intArrayOf(3,2)),
                arrayOf(intArrayOf(1,2), intArrayOf(1,3), intArrayOf(1,7), intArrayOf(2,4), intArrayOf(2,6), intArrayOf(3,5)),
                arrayOf(intArrayOf(1,2), intArrayOf(1,3), intArrayOf(1,7), intArrayOf(2,4), intArrayOf(2,6), intArrayOf(3,5)),
                arrayOf(intArrayOf(1,2), intArrayOf(1,3), intArrayOf(1,7), intArrayOf(2,4), intArrayOf(2,6), intArrayOf(3,5)),
            )
            val times = arrayOf(1, 2, 1, 20)
            val targets = arrayOf(2, 4, 7, 6)
            for (i in counts.indices) {
                val probability = FrogPositionAfterTSeconds().frogPosition(counts[i], edges[i], times[i], targets[i])
                println("probability: $probability")
            }
        }
    }
}