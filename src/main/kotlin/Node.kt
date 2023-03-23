data class Node(var value: Int, var next: Node? = null, var random: Node? = null) {
    override fun toString() = "$value"

    fun print() {
        var current: Node? = this
        while (current != null) {
            print("${current.value} ")
            current = current.next
        }
        println()
    }

    companion object {
        fun build(array: IntArray): Node {
            val dummy = Node(-1)
            var current = dummy
            for (number in array) {
                current.next = Node(number)
                current = current.next!!
            }
            return dummy.next!!
        }
    }
}
