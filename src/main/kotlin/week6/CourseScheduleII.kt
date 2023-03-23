package week6

import java.util.*


class CourseScheduleII {
    fun findOrder(numCourses: Int, prerequisites: Array<IntArray>): IntArray {
        return codepath(numCourses, prerequisites)
        // return solution1(numCourses, prerequisites)
    }

    val WHITE = 1
    val GRAY = 2
    val BLACK = 3

    var isPossible = true
    val color = mutableMapOf<Int, Int>()
    val adjList = mutableMapOf<Int, MutableList<Int>>()
    val topologicalOrder = mutableListOf<Int>()

    fun solution1(numCourses: Int, prerequisites: Array<IntArray>): IntArray {
        init(numCourses)

        // Create the adjacency list representation of the graph
        for (i in prerequisites.indices) {
            val dest = prerequisites[i][0]
            val source = prerequisites[i][1]
            val list = adjList.getOrDefault(source, ArrayList())
            list.add(dest)
            adjList[source] = list
        }

        // If the node is unprocessed, then call dfs on it.
        for (i in 0 until numCourses) {
            if (color[i] == WHITE) {
                dfs(i)
            }
        }
        val order: IntArray
        if (isPossible) {
            order = IntArray(numCourses)
            for (i in 0 until numCourses) {
                order[i] = topologicalOrder[numCourses - i - 1]
            }
        } else {
            order = IntArray(0)
        }
        return order
    }

    private fun solution2(numCourses: Int, prerequisites: Array<IntArray>): IntArray {
        val adjList = mutableMapOf<Int, MutableList<Int>>()
        val indegree = IntArray(numCourses)
        val topologicalOrder = IntArray(numCourses)

        // Create the adjacency list representation of the graph
        for (i in prerequisites.indices) {
            val dest = prerequisites[i][0]
            val src = prerequisites[i][1]
            val lst = adjList.getOrDefault(src, mutableListOf())
            lst.add(dest)
            adjList[src] = lst

            // Record in-degree of each vertex
            indegree[dest] += 1
        }

        // Add all vertices with 0 in-degree to the queue
        val q = LinkedList<Int>()
        for (i in 0 until numCourses) {
            if (indegree[i] == 0)
                q.add(i)
        }
        var i = 0
        // Process until the Q becomes empty
        while (q.isNotEmpty()) {
            val node = q.remove()
            topologicalOrder[i++] = node

            // Reduce the in-degree of each neighbor by 1
            if (adjList.containsKey(node)) {
                for (neighbor in adjList[node]!!) {
                    indegree[neighbor]--

                    // If in-degree of a neighbor becomes 0, add it to the Q
                    if (indegree[neighbor] == 0) {
                        q.add(neighbor)
                    }
                }
            }
        }

        // Check to see if topological sort is possible or not.
        return if (i == numCourses) topologicalOrder else IntArray(0)
    }

    private fun init(numCourses: Int) {
        // By default all vertices are WHITE
        for (i in 0 until numCourses) {
            color[i] = WHITE
        }
    }

    private fun dfs(node: Int) {
        // Don't recurse further if we found a cycle already
        if (!isPossible)
            return

        // Start the recursion
        color[node] = GRAY

        // Traverse on neighboring vertices
        for (neighbor in adjList.getOrDefault(node, mutableListOf())) {
            if (color[neighbor] == WHITE) {
                dfs(neighbor)
            } else if (color[neighbor] == GRAY) {
                // An edge to a GRAY vertex represents a cycle
                isPossible = false
            }
        }

        // Recursion ends. We mark it as black
        color[node] = BLACK
        topologicalOrder.add(node)
    }

    val UNVISITED = 0
    val VISITING = 1
    val VISITED = 2
    var isItPossible = true

    // 1. translate the problem into graph terminology
    //    * vertices: each number in numCourses and in prerequisites
    //    * edges: the pairs of nodes in prerequisites
    // 2. build out graph
    //    build out adjacency list with directed edges from prerequisite to course
    // 3. traverse graph
    //    just do topological sort, if there's a cycle then no topological ordering is possible
    private fun codepath(size: Int, prerequisites: Array<IntArray>): IntArray {
        val graph = buildGraph(prerequisites)
        val states = IntArray(size) { UNVISITED }
        val ordering = mutableListOf<Int>()

        // navigate thru each course index
        for (i in 0 until size) {
            if (states[i] == UNVISITED)
                dfs(i, graph, states, ordering)
        }

        return if (isItPossible) ordering.toIntArray() else intArrayOf()
    }

    private fun buildGraph(prerequisites: Array<IntArray>): Map<Int, List<Int>> {
        val graph = mutableMapOf<Int, MutableList<Int>>()
        for (prerequisite in prerequisites) {
            val source = prerequisite[1]
            val dest = prerequisite[0]
            graph.getOrPut(source) { mutableListOf() }
            graph[source]!!.add(dest)
        }
        return graph
    }

    private fun dfs(course: Int, graph: Map<Int, List<Int>>, states: IntArray, ordering: MutableList<Int>) {
        // temporarily mark course as VISITING to avoid looping back in dfs()
        states[course] = VISITING

        // println("course: $course, graph[course]: ${graph[course]}")
        // make sure course has neighbors, because sometimes a course doesn't
        if (graph.containsKey(course)) {
            // check all neighbors of the current course
            for (neighbor in graph[course]!!) {
                if (states[neighbor] == UNVISITED)
                    // dfs() on neighbor that's UNVISITED
                    dfs(neighbor, graph, states, ordering)
                else if (states[neighbor] == VISITING)
                    // skip neighbor that's VISITING
                    isItPossible = false
            }
        }

        // mark the real state of VISITED
        states[course] = VISITED
        // save the course
        ordering.add(0, course)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val numCourses = arrayOf(2, 4, 1)
            val prerequisites = arrayOf(
                arrayOf(intArrayOf(1,0)),
                arrayOf(intArrayOf(1,0), intArrayOf(2,0), intArrayOf(3,1), intArrayOf(3,2)),
                arrayOf()
            )
            for (i in numCourses.indices) {
                print("prerequisites: ")
                for (j in prerequisites[i].indices) {
                    print("${prerequisites[i][j].contentToString()}, ")
                }
                val order = CourseScheduleII().findOrder(numCourses[i], prerequisites[i])
                println("course order: ${order.contentToString()}")
            }
        }
    }
}
