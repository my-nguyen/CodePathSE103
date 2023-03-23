package week1

import ListNode

class LinkedListCycle {
    fun hasCycle(head: ListNode?): Boolean {
        var fast = head
        var slow = head
        while (fast?.next != null) {
            fast = fast.next!!.next
            slow = slow!!.next
            if (slow == fast)
                return true
        }
        return false
    }
}