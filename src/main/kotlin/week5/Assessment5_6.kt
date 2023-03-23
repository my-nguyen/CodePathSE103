package week5

import java.util.*


class Assessment5_6 {
    fun validTree(n: Int, edges: Array<IntArray>): Boolean {
        // return mine(n, edges)
        // return iterativeDFS1(n, edges)
        // return recursiveDFS1(n, edges)
        return iterativeBFS1(n, edges)
    }

    private fun iterativeDFS1(n: Int, edges: Array<IntArray>): Boolean {
        val adjacencyList = Array(n) { mutableListOf<Int>() }
        for (edge in edges) {
            adjacencyList[edge[0]].add(edge[1])
            adjacencyList[edge[1]].add(edge[0])
        }
        val parent = mutableMapOf<Int, Int>()
        parent[0] = -1
        val stack = Stack<Int>()
        stack.push(0)
        while (stack.isNotEmpty()) {
            val node = stack.pop()
            for (neighbour in adjacencyList[node]) {
                if (parent[node] == neighbour)
                    continue
                if (parent.containsKey(neighbour))
                    return false

                stack.push(neighbour)
                parent[neighbour] = node
            }
        }
        return parent.size == n
    }

    private val adjacencyList = mutableListOf<MutableList<Int>>()
    private val seen = mutableSetOf<Int>()
    private fun recursiveDFS1(n: Int, edges: Array<IntArray>): Boolean {
        if (edges.size != n - 1)
            return false

        for (i in 0 until n) {
            adjacencyList.add(mutableListOf())
        }
        for (edge in edges) {
            adjacencyList[edge[0]].add(edge[1])
            adjacencyList[edge[1]].add(edge[0])
        }

        // We return true iff no cycles were detected, AND the entire graph has been reached.
        return dfs(0, -1) && seen.size == n
    }

    private fun iterativeBFS1(n: Int, edges: Array<IntArray>): Boolean {
        val adjacencyList = Array(n) { mutableListOf<Int>() }
        for (edge in edges) {
            adjacencyList[edge[0]].add(edge[1])
            adjacencyList[edge[1]].add(edge[0])
        }
        val parent = mutableMapOf<Int, Int>()
        parent[0] = -1
        val queue = LinkedList<Int>()
        queue.offer(0)
        while (!queue.isEmpty()) {
            val node = queue.poll()
            for (neighbour in adjacencyList[node]) {
                if (parent[node] == neighbour)
                    continue
                if (parent.containsKey(neighbour))
                    return false

                queue.offer(neighbour)
                parent[neighbour] = node
            }
        }
        return parent.size == n
    }

    private fun iterativeDFS2(n: Int, edges: Array<IntArray>): Boolean {
        if (edges.size != n - 1)
            return false

        // Make the adjacency list.
        val adjacencyList = Array(n) { mutableListOf<Int>() }
        for (edge in edges) {
            adjacencyList[edge[0]].add(edge[1])
            adjacencyList[edge[1]].add(edge[0])
        }

        val stack = Stack<Int>()
        val seen = mutableSetOf<Int>()
        stack.push(0)
        seen.add(0)
        while (stack.isNotEmpty()) {
            val node = stack.pop()
            for (neighbour in adjacencyList[node]) {
                if (seen.contains(neighbour))
                    continue
                seen.add(neighbour)
                stack.push(neighbour)
            }
        }
        return seen.size == n
    }

    private fun recursiveDFS2(n: Int, edges: Array<IntArray>): Boolean {
        if (edges.size != n - 1)
            return false

        // Make the adjacency list.
        for (i in 0 until n) {
            adjacencyList.add(mutableListOf())
        }
        for (edge in edges) {
            adjacencyList[edge[0]].add(edge[1])
            adjacencyList[edge[1]].add(edge[0])
        }

        // Carry out depth first search.
        dfs(0)
        // Inspect result and return the verdict.
        return seen.size == n
    }

    private fun iterativeBFS2(n: Int, edges: Array<IntArray>): Boolean {
        if (edges.size != n - 1)
            return false

        // Make the adjacency list.
        val adjacencyList = Array(n) { mutableListOf<Int>() }
        for (edge in edges) {
            adjacencyList[edge[0]].add(edge[1])
            adjacencyList[edge[1]].add(edge[0])
        }
        val queue = LinkedList<Int>()
        val seen = mutableSetOf<Int>()
        queue.offer(0)
        seen.add(0)
        while (queue.isNotEmpty()) {
            val node = queue.poll()
            for (neighbour in adjacencyList[node]) {
                if (seen.contains(neighbour))
                    continue
                seen.add(neighbour)
                queue.offer(neighbour)
            }
        }
        return seen.size == n
    }

    class UnionFind(n: Int) {
        // For efficiency, we aren't using makeset, but instead initialising all the sets at the same time in the constructor.
        private val parent = IntArray(n) { it }

        // The find method, without any optimizations. It traces up the parent links until it finds the root node for A,
        // and returns that root.
        private fun find(A: Int): Int {
            var A = A
            while (parent[A] != A) {
                A = parent[A]
            }
            return A
        }

        // The union method, without any optimizations. It returns True if a merge happened, False if otherwise.
        fun union(A: Int, B: Int): Boolean {
            // Find the roots for A and B.
            val rootA = find(A)
            val rootB = find(B)
            // Check if A and B are already in the same set.
            if (rootA == rootB)
                return false

            // Merge the sets containing A and B.
            parent[rootA] = rootB
            return true
        }
    }

    private fun unionFind(n: Int, edges: Array<IntArray>): Boolean {
        // Condition 1: The graph must contain n - 1 edges.
        if (edges.size != n - 1)
            return false

        // Condition 2: The graph must contain a single connected component. Create a new UnionFind object with n nodes.
        val unionFind = UnionFind(n)
        // Add each edge. Check if a merge happened, because if it didn't, there must be a cycle.
        for (edge in edges) {
            val A = edge[0]
            val B = edge[1]
            if (!unionFind.union(A, B))
                return false
        }

        // If we got this far, there's no cycles!
        return true
    }

    private fun mine(n: Int, edges: Array<IntArray>): Boolean {
        val map = mutableMapOf<Int, MutableList<Int>>()
        for (edge in edges) {
            val i = edge[0]
            val j = edge[1]
            add(map, i, j)
            add(map, j, i)
        }

        val visited = BooleanArray(n)
        val stack = Stack<Int>()
        for (i in 0 until n) {
            if (visited[i])
                continue

            stack.add(i)
            while (stack.isNotEmpty()) {
                val top = stack.pop()
                if (visited[top])
                    continue

                visited[top] = true
                val neighbors = map[top]!!
                var count = 0
                for (neighbor in neighbors) {
                    if (!visited[neighbor]) {
                        stack.push(neighbor)
                        count++
                    }
                }

                if (count == 0) {
                    if (map[top]!!.size != 1)
                        return false
                }
            }
        }
        return true
    }

    private fun add(map: MutableMap<Int, MutableList<Int>>, key: Int, value: Int) {
        val list = map.getOrPut(key) { mutableListOf() }
        list.add(value)
    }

    private fun dfs(node: Int, parent: Int): Boolean {
        if (seen.contains(node))
            return false

        seen.add(node)
        for (neighbour in adjacencyList[node]) {
            if (parent != neighbour) {
                val result = dfs(neighbour, node)
                if (!result)
                    return false
            }
        }
        return true
    }

    private fun dfs(node: Int) {
        if (seen.contains(node))
            return
        seen.add(node)
        for (neighbour in adjacencyList[node]) {
            dfs(neighbour)
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val n = arrayOf(5, 5)
            val edges = arrayOf(
                arrayOf(intArrayOf(0,1), intArrayOf(0,2), intArrayOf(0,3), intArrayOf(1,4)),
                arrayOf(intArrayOf(0,1), intArrayOf(1,2), intArrayOf(2,3), intArrayOf(1,3), intArrayOf(1,4)),
            )
            for (i in n.indices) {
                val isValid = Assessment5_6().validTree(n[i], edges[i])
                println("is valid? $isValid")
            }
        }
    }
}
