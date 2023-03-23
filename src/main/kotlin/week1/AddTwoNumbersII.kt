package week1

import ListNode

class AddTwoNumbersII {
    fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
        var r1: ListNode? = reverse(l1)
        var r2: ListNode? = reverse(l2)
        val dummy = ListNode(-1)
        var r3 = dummy
        var carry = 0
        while (r1 != null || r2 != null) {
            var sum = carry
            if (r1 != null) {
                sum += r1.value
                r1 = r1.next
            }
            if (r2 != null) {
                sum += r2.value
                r2 = r2.next
            }

            r3.next = ListNode(sum % 10)
            r3 = r3.next!!
            carry = sum / 10
        }
        if (carry != 0)
            r3.next = ListNode(carry)

        return reverse(dummy.next)
    }

    private fun reverse(l: ListNode?): ListNode? {
        var previous: ListNode? = null
        var current: ListNode? = l
        while (current != null) {
            val tmp: ListNode? = current.next
            current.next = previous
            previous = current
            current = tmp
        }
        return previous
    }
}