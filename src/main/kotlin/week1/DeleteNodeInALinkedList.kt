class DeleteNodeInALinkedList {
    fun deleteNode(node: ListNode?) {
        /*if (head == null) return

        if (head.`val` == node.`val`) {
            head = head.next
        } else {
            var previous = head
            var current = head.next
            while (current != null) {
                if (current.`val` == node.`val`) {
                    current = current.next
                    break
                } else {
                    previous = current
                    current = current.next
                }
            }
        }*/
        node?.let {
            it.value = it.next!!.value
            it.next = it.next!!.next
        }
    }
}