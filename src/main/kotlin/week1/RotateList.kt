package week1

import ListNode

class RotateList {
    fun rotateRight(head: ListNode?, k: Int): ListNode? {
        if (head == null)
            return null

        var current = head
        var count = 1
        while (current!!.next != null) {
            current = current.next
            count++
        }
        current.next = head

        val rotate = k % count
        val skip = count - rotate
        current = head
        for (i in 1 until skip) {
            current = current!!.next
        }

        val result = current!!.next
        current.next = null
        return result
    }
}