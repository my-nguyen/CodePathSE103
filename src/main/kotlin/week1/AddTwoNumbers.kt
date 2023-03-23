package week1

import ListNode

class AddTwoNumbers {
    fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
        var n1 = l1
        var n2 = l2
        var carry = 0
        val dummy = ListNode(0)
        var current = dummy
        while (n1 != null || n2 != null) {
            var sum = carry
            if (n1 != null) {
                sum += n1.value
                n1 = n1.next
            }
            if (n2 != null) {
                sum += n2.value
                n2 = n2.next
            }
            current.next = ListNode(sum % 10)
            current = current.next!!
            carry = sum / 10
        }
        if (carry != 0) {
            current.next = ListNode(1)
        }
        return dummy.next
    }
}