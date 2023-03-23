package week1

import ListNode

class OddEvenLinkedList {
    fun oddEvenList(head: ListNode?): ListNode? {
        if (head?.next == null)
            return head

        val oddHead = head
        val evenHead = head.next
        var odd = oddHead
        var even = evenHead
        var current = head.next!!.next
        var count = 1
        while (current != null) {
            if (count % 2 == 1) {
                // println("odd before: ${odd!!.`val`}, after: ${current.`val`}")
                odd!!.next = current
                odd = current
            } else {
                // println("even before: ${even.`val`}, after: ${current.`val`}")
                even!!.next = current
                even = current
            }
            // println("current : ${current.`val`}, count: $count")
            current = current.next
            count++
        }
        // println("odd: ${odd!!.`val`}, evenHead: ${evenHead.`val`}, oddHead: ${oddHead.`val`}")
        even!!.next = null
        odd!!.next = evenHead
        var tmp = head
        print("list: ")
        while (tmp != null) {
            print("${tmp.value} ")
            tmp = tmp.next
        }
        println()
        return head
    }
}