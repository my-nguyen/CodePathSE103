package week1

import ListNode

class SwapNodesInPairs {
    fun swapPairs(head: ListNode?): ListNode? {
        var previous: ListNode? = null
        var current = head
        var result = head
        while (current != null) {
            val pre = previous
            val end = current

            for (i in 1..2) {
                if (current == null) {
                    break
                } else {
                    val tmp = current.next
                    current.next = previous
                    previous = current
                    current = tmp
                }
            }

            end.next = current
            if (pre == null) {
                result = previous
            } else {
                pre.next = previous
            }

            previous = end
        }
        return result
    }
}