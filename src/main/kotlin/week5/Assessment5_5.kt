package week5

import java.util.*


class Assessment5_5 {
    fun countComponents(n: Int, edges: Array<IntArray>): Int {
        // return mine(n, edges)
        // return solution1(n, edges)
        return solution2(n, edges)
    }

    private fun mine(n: Int, edges: Array<IntArray>): Int {
        // build a map of connections between the nodes:
        // edge (i, j) means there's 2 directed paths, from i to j and from j to i
        // so with edges: [0, 1], [1, 2], [3, 4], the map will be:
        // 0: {1}, 1: {0, 2}, 2: {1}, 3: {4}
        val map = mutableMapOf<Int, MutableList<Int>>()
        for (edge in edges) {
            val i = edge[0]
            val j = edge[1]
            add(map, i, j)
            add(map, j, i)
        }

        // there's a total of n nodes
        val visited = BooleanArray(n)
        val stack = Stack<Int>()
        var count = 0
        for (i in 0 until n) {
            // skip a visited node
            if (visited[i])
                continue

            // save unvisited node onto stack
            stack.add(i)
            while (stack.isNotEmpty()) {
                // retrieve node from stack
                val top = stack.pop()
                if (visited[top])
                    continue

                visited[top] = true

                // retrieve all neighbors of this top node
                val neighbors = map[top]!!
                for (neighbor in neighbors) {
                    // save unvisited neighbor onto stack
                    if (!visited[neighbor])
                        stack.add(neighbor)
                }
            }

            // outside the while loop, we look for another connected component, so must increment count
            count++
        }
        return count
    }

    private fun solution1(n: Int, edges: Array<IntArray>): Int {
        var components = 0
        val visited = IntArray(n)
        val adjList = Array(n) {
            mutableListOf<Int>()
        }
        // val adjList = Array<MutableList<Int>> = arrayOfNulls<ArrayList<*>>(n)
        /*for (i in 0 until n) {
            adjList[i] = ArrayList()
        }*/
        for (i in edges.indices) {
            adjList[edges[i][0]].add(edges[i][1])
            adjList[edges[i][1]].add(edges[i][0])
        }
        for (i in 0 until n) {
            if (visited[i] == 0) {
                components++
                dfs(adjList, visited, i)
            }
        }
        return components
    }

    private fun solution2(n: Int, edges: Array<IntArray>): Int {
        val representative = IntArray(n) { it }
        val size = IntArray(n) { it }
        /*for (i in 0 until n) {
            representative[i] = i
            size[i] = 1
        }*/
        var components = n
        for (i in edges.indices) {
            components -= combine(representative, size, edges[i][0], edges[i][1])
        }
        return components
    }

    private fun add(map: MutableMap<Int, MutableList<Int>>, key: Int, value: Int) {
        val list = map.getOrPut(key) { mutableListOf() }
        list.add(value)
    }

    private fun dfs(adjList: Array<MutableList<Int>>, visited: IntArray, startNode: Int) {
        visited[startNode] = 1
        for (i in adjList[startNode].indices) {
            if (visited[adjList[startNode][i]] == 0) {
                dfs(adjList, visited, adjList[startNode][i])
            }
        }
    }

    private fun find(representative: IntArray, vertex: Int): Int {
        return if (vertex == representative[vertex]) {
            vertex
        } else {
            find(representative, representative[vertex]).also {
                representative[vertex] = it
            }
        }
    }

    private fun combine(representative: IntArray, size: IntArray, vertex1: Int, vertex2: Int): Int {
        var vertex1 = find(representative, vertex1)
        var vertex2 = find(representative, vertex2)
        return if (vertex1 == vertex2) {
            0
        } else {
            if (size[vertex1] > size[vertex2]) {
                size[vertex1] += size[vertex2]
                representative[vertex2] = vertex1
            } else {
                size[vertex2] += size[vertex1]
                representative[vertex1] = vertex2
            }
            1
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val ns = arrayOf(5, 5)
            val edges = arrayOf(
                arrayOf(intArrayOf(0,1), intArrayOf(1,2), intArrayOf(3,4)),
                arrayOf(intArrayOf(0,1), intArrayOf(1,2), intArrayOf(2,3), intArrayOf(3,4))
            )
            for (i in ns.indices) {
                print("edges: ")
                for (edge in edges[i]) {
                    print("${edge.contentToString()}, ")
                }
                val count = Assessment5_5().countComponents(ns[i], edges[i])
                println("connected components: $count")
            }
        }
    }
}