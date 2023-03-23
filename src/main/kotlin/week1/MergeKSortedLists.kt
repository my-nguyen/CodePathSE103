package week1

import ListNode
import java.util.*

class MergeKSortedLists {
    fun mergeKLists(lists: Array<ListNode?>): ListNode? {
        if (lists.isNullOrEmpty())
            return null

        val minHeap = PriorityQueue<ListNode>() { a, b -> a.value - b.value }
        for (list in lists) {
            if (list != null)
                minHeap.add(list)
        }
        if (minHeap.size == 0)
            return null

        val dummy = ListNode(-1)
        var current = dummy
        while (minHeap.isNotEmpty()) {
            val node = minHeap.poll()
            current.next = node
            current = node
            if (node.next != null)
                minHeap.add(node.next)
        }
        return dummy.next
    }
}