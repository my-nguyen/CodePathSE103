package week2

import Node

class QueueImplLinkedList {
    var front: Node? = null
    var back: Node? = null

    fun enqueue(x: Int) {
        val node = Node(x)
        if (front == null && back == null) {
            front = node
            back = node
        } else {
            back!!.next = node
            back = node
        }
    }

    fun dequeue() {
        if (front == null)
            return
        else if (front == back) {
            front = null
            back = null
        } else {
            front = front!!.next
        }
    }
}