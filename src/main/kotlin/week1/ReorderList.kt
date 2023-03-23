package week1

import ListNode

class ReorderList {
    fun reorderList(head: ListNode?) {
        if (head == null)
            return

        val middle = findMiddle(head!!)
        val reversed = reverse(middle)
        var a = head
        var b = reversed
        while (a != null && b != null) {
            var tmp = a.next
            a.next = b
            a = tmp

            tmp = b.next
            b.next = a
            b = tmp!!
        }
    }

    fun findMiddle(head: ListNode): ListNode {
        var fast = head
        var slow = head
        while (fast.next != null && fast.next!!.next != null) {
            fast = fast.next!!.next!!
            slow = slow.next!!
        }
        return slow
    }

    fun reverse(head: ListNode): ListNode {
        var previous: ListNode? = null
        var current: ListNode? = head
        while (current != null) {
            val tmp = current.next
            current.next = previous
            previous = current
            current = tmp
        }
        return previous!!
    }
}