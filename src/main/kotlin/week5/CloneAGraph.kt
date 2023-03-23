package week5

import java.util.*

class CloneAGraph {
    data class Node(val value: Int, val neighbors: MutableList<Node> = mutableListOf())

    fun clone(root: Node?): Node? {
        if (root == null)
            return null

        val queue = LinkedList<Node>()
        val visited = mutableMapOf<Node, Node>()
        visited[root] = Node(root.value)
        while (queue.isNotEmpty()) {
            val node = queue.poll()
            val clone = visited[node]
            for (neighbor in node.neighbors) {
                if (!visited.containsKey(neighbor)) {
                    visited[neighbor] = Node(neighbor.value)
                    queue.add(neighbor)
                }
                clone!!.neighbors.add(visited[neighbor]!!)
            }
        }
        return visited[root]
    }
}