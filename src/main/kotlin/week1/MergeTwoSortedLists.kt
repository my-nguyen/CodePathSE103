package week1

import ListNode

class MergeTwoSortedLists {
    fun mergeTwoLists(l1: ListNode?, l2: ListNode?): ListNode? {
        if (l1 == null || l2 == null) {
            return if (l1 == null && l2 == null)
                null
            else l1 ?: l2
        }

        var n1 = l1
        var n2 = l2
        val dummy = ListNode(-1)
        var n3 = dummy
        while (n1 != null && n2 != null) {
            if (n1.value <= n2.value) {
                n3.next = n1
                n3 = n1
                n1 = n1.next
            } else {
                n3.next = n2
                n3 = n2
                n2 = n2.next
            }
        }

        n3.next = n1 ?: n2
        /*if (n1 != null)
            n3.next = n1
        else
            n3.next = n2*/

        return dummy.next
    }
}