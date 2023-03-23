package week6


class CourseSchedule {
    fun canFinish(numCourses: Int, prerequisites: Array<IntArray>): Boolean {
        // return mine(numCourses, prerequisites)
        return solution1(numCourses, prerequisites)
        // return solution2(numCourses, prerequisites)
    }

    private fun solution1(size: Int, prerequisites: Array<IntArray>): Boolean {
        // course -> list of next courses
        val graph = mutableMapOf<Int, MutableList<Int>>()
        // build the graph
        for (relation in prerequisites) {
            val source = relation[1]
            val dest = relation[0]
            graph.getOrPut(source) { mutableListOf() }
            graph[source]!!.add(dest)
        }

        val visited = BooleanArray(size)
        for (course in 0 until size) {
            if (isCyclic(course, graph, visited)) {
                return false
            }
        }
        return true
    }

    private fun solution2(size: Int, prerequisites: Array<IntArray>): Boolean {
        val graph = mutableMapOf<Int, MutableList<Int>>()
        // build the graph
        for (relation in prerequisites) {
            val source = relation[1]
            val dest = relation[0]
            graph.getOrPut(source) { mutableListOf() }
            graph[source]!!.add(dest)
        }

        val checked = BooleanArray(size)
        val visited = BooleanArray(size)
        for (course in 0 until size) {
            if (isCyclic(course, graph, checked, visited))
                return false
        }
        return true
    }

    // postorder DFS check that no cycle would be formed starting from currCourse
    private fun isCyclic(node: Int, graph: Map<Int, List<Int>>, checked: BooleanArray, visited: BooleanArray): Boolean {
        // node is checked, so no cycle
        if (checked[node])
            return false
        // node is visited, so no cycle
        if (visited[node])
            return true
        // there's no nodes following this node, so no cycle
        if (!graph.containsKey(node))
            return false

        // before backtracking, mark the node as visited
        visited[node] = true

        // start backtracking using postorder DFS, to visit all its children first.
        var result = false
        for (child in graph[node]!!) {
            result = isCyclic(child, graph, checked, visited)
            if (result)
                break
        }

        // after visiting all children, now process the parent node
        checked[node] = true

        // unmark the node
        visited[node] = false

        return result
    }

    private fun isCyclic(node: Int, graph: Map<Int, List<Int>>, visited: BooleanArray): Boolean {
        // node is visited: there's a cycle
        if (visited[node])
            return true

        // no following courses means no cycle
        if (!graph.containsKey(node))
            return false

        // before backtracking, mark the node as visited
        visited[node] = true

        // start backtracking
        var isCyclic = false
        for (nextCourse in graph[node]!!) {
            isCyclic = isCyclic(nextCourse, graph, visited)
            if (isCyclic)
                break
        }

        // after backtracking, unmark the node
        visited[node] = false
        return isCyclic
    }

    val UNVISITED = 0
    val VISITING = 1
    val VISITED = 2
    var isPossible = true

    // incorrect solution
    private fun mine(size: Int, prerequisites: Array<IntArray>): Boolean {
        val states = IntArray(size) { UNVISITED }
        val graph = buildGraph(prerequisites)
        for (i in 0 until size) {
            if (states[i] == UNVISITED) {
                if (!dfs(i, states, graph))
                    return false
            }
        }
        return true
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

    private fun dfs(course: Int, states: IntArray, graph: Map<Int, List<Int>>): Boolean {
        states[course] = VISITING
        if (graph.containsKey(course)) {
            for (neighbor in graph[course]!!) {
                if (states[neighbor] == UNVISITED) {
                    if (!dfs(neighbor, states, graph))
                        return false
                } else if (states[neighbor] == VISITED)
                    return false
            }
        }

        states[course] = VISITED
        return true
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val numCourses = arrayOf(2, 2)
            val prerequisites = arrayOf(
                arrayOf(intArrayOf(1,0)),
                arrayOf(intArrayOf(1,0), intArrayOf(0,1))
            )
            for (i in numCourses.indices) {
                print("prerequisites: ")
                for (j in prerequisites[i].indices) {
                    print("${prerequisites[i][j].contentToString()}, ")
                }
                val canFinish = CourseSchedule().canFinish(numCourses[i], prerequisites[i])
                println("can finish? $canFinish")
            }
        }
    }
}