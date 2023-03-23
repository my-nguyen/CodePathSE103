package week1

import week6.SinglyLinkedListNode

class Assessment1_7 {
    fun isPalindrome(head: SinglyLinkedListNode?): Boolean {
        val middle = findMiddle(head)
        var reversed = reverse(middle)
        var current = head
        while (current != null && reversed != null) {
            if (current.data != reversed.data)
                return false
            else {
                current = current.next
                reversed = reversed.next
            }
        }
        return true
    }

    private fun findMiddle(head: SinglyLinkedListNode?): SinglyLinkedListNode? {
        var slow = head
        var fast = head
        while (fast != null && fast.next != null) {
            fast = fast.next!!.next
            slow = slow!!.next
        }
        return slow
    }

    private fun reverse(head: SinglyLinkedListNode?): SinglyLinkedListNode? {
        var current = head
        var previous: SinglyLinkedListNode? = null
        while (current != null) {
            val tmp = current.next
            current.next = previous
            previous = current
            current = tmp
        }
        return previous
    }
}