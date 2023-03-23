package week3

import ListNode

// this is leetcode problem Insertion Sort List
class Assessment6 {
    fun insertionSortList(head: ListNode?): ListNode? {
        return leetcode(head)
    }

    private fun leetcode(head: ListNode?): ListNode? {
        val dummy = ListNode()
        var curr = head
        while (curr != null) {
            // At each iteration, we insert an element into the resulting list.
            var prev: ListNode? = dummy

            // find the position to insert the current node
            while (prev!!.next != null && prev.next!!.value < curr.value) {
                prev = prev.next
            }
            val next = curr.next
            // insert the current node to the new list
            curr.next = prev.next
            prev.next = curr

            // moving on to the next iteration
            curr = next
        }
        return dummy.next
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(intArrayOf(4,2,1,3), intArrayOf(-1,5,3,4,0), intArrayOf(6,5,3,1,8,7,2,4))
            for (array in arrays) {
                val head = ListNode.build(array)
                head.print()
                val sorted = Assessment6().insertionSortList(head)
                sorted?.print()
            }
        }
    }
}