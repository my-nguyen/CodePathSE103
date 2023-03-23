package week1

import Node

class CopyListWithRandomPointer {
    fun copyRandomList(node: Node?): Node? {
        if (node == null)
            return null

        val map = mutableMapOf<Node, Int>()
        // store the linked list as a List
        val original = mutableListOf<Node>()
        // store the cloned list as a List
        val cloned = mutableListOf<Node>()
        var current = node
        // go thru the linked list
        while (current != null) {
            // save the current node in a list
            original.add(current)
            // save the position of current node in a map
            map[current] = original.lastIndex
            // clone and save the current node in a list
            cloned.add(Node(current.value))
            current = current.next
        }

        // go thru the cloned list
        for (i in cloned.indices) {
            // create a link from one cloned node to the next
            if (i < cloned.lastIndex)
                cloned[i].next = cloned[i+1]

            // get the original node random pointer as key to the map
            val key = original[i].random
            if (key != null) {
                // obtain the map value, which is the random object index within the original list
                val index = map[key]!!
                // set the cloned random pointer to the corresponding cloned object
                cloned[i].random = cloned[index]
            }
        }

        return cloned[0]
    }
}