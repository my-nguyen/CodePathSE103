package week1

import week6.SinglyLinkedListNode

class Assessment1_8 {
    fun plusOne(head: SinglyLinkedListNode?): SinglyLinkedListNode? {
        val reversed = reverse(head)
        var current = reversed
        var previous: SinglyLinkedListNode? = null
        while (current != null) {
            if (current.data != 9) {
                current.data++
                break
            } else {
                current.data = 0
                previous = current
                current = current.next
            }
        }

        if (current == null) {
            val node = SinglyLinkedListNode(1)
            previous!!.next = node
        }

        return reverse(reversed)
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