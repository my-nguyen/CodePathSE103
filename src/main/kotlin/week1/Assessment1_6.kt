package week1

import week6.SinglyLinkedListNode

class Assessment1_6 {
    fun getLength(A: SinglyLinkedListNode): Int {
        var count = 0
        var current: SinglyLinkedListNode? = A
        while (current != null) {
            count++
            current = current.next
        }
        return count
    }
}