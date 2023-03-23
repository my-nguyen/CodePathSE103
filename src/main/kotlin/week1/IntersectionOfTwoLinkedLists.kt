package week1

import ListNode

class IntersectionOfTwoLinkedLists {
    fun getIntersectionNode(headA: ListNode?, headB: ListNode?): ListNode? {
        var nA = headA
        var nB = headB
        var endA = false
        var endB = false
        while (nA != nB) {
            if (nA == null) {
                if (!endA) {
                    endA = true
                    nA = headB
                } else
                    return null
            } else
                nA = nA.next
            if (nB == null) {
                if (!endB) {
                    endB = true
                    nB = headA
                } else
                    return null
            } else
                nB = nB.next
        }
        return nA
    }
}