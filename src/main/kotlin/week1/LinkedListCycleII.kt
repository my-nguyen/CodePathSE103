package week1

import ListNode

class LinkedListCycleII {
    fun detectCycle(head: ListNode?): ListNode? {
        if (head == null)
            return null
        val cycle = findCycle(head)
        if (cycle == null)
            return null
        val length = getLength(cycle)
        return findCycleHead(head, length)
    }

    private fun findCycle(head: ListNode): ListNode? {
        var fast = head
        var slow = head
        while (fast.next != null && fast.next!!.next != null) {
            fast = fast.next!!.next!!
            slow = slow.next!!
            if (slow == fast) {
                println("cycle: ${slow.value}")
                return slow
            }
        }
        return null
    }

    private fun getLength(head: ListNode): Int {
        var current = head.next
        var length = 1
        while (current != head) {
            current = current!!.next
            length++
        }
        println("length: $length")
        return length
    }

    private fun findCycleHead(head: ListNode, length: Int): ListNode {
        var fast = head
        for (i in 1..length)
            fast = fast.next!!
        println("fast: ${fast.value}")
        var slow = head
        while (slow != fast) {
            slow = slow.next!!
            fast = fast.next!!
        }
        println("cycle head: ${slow.value}")
        return slow
    }
}