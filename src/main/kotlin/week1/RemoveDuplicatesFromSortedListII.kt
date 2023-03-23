package week1

import ListNode

class RemoveDuplicatesFromSortedListII {
    fun deleteDuplicates(head: ListNode?): ListNode? {
        if (head == null)
            return null

        val dummy = ListNode(-1)
        dummy.next = head
        var slow = dummy
        var fast = head
        while (fast?.next != null) {
            if (fast.value == fast.next!!.value) {
                // as long as nodes next to each other have the same value, skip
                while (fast!!.next != null && fast.value == fast.next!!.value)
                    fast = fast.next
                // skip to the node with a different value
                fast = fast.next
                // discard all nodes with identical values
                slow.next = fast
            } else {
                // proceed as normal
                fast = fast.next
                slow = slow.next!!
            }
        }
        return dummy.next
    }
}