package week2

import Node

class StackImplLinkedList {
    var top: Node? = null

    fun push(x: Int) {
        val node = Node(x, top)
        top = node
    }

    fun pop() {
        if (top == null)
            return
        else
            top = top!!.next
    }
}