import java.util.*

data class TreeNode(var value: Int, var left: TreeNode? = null, var right: TreeNode? = null) {
    override fun toString() = value.toString()

    // pre-order print
    fun print() {
        print("$value ")
        print(left)
        print(right)
    }

    companion object {
        private fun insert(array: Array<Int?>, node: TreeNode?, i: Int): TreeNode? {
            // print("i: $i, ")
            return when {
                i >= array.size -> node
                array[i] == null -> {
                    // println("null node")
                    null
                }
                else -> {
                    val root = TreeNode(array[i]!!)
                    // println("root: ${array[i]}, left-index: ${2*i + 1}, right-index: ${2*i + 2}")
                    root.left = insert(array, null, 2 * i + 1)
                    root.right = insert(array, null, 2 * i + 2)
                    root
                }
            }
        }

        fun build(array: Array<Int?>): TreeNode? {
            return build2(array)
        }

        private fun build1(array: Array<Int?>): TreeNode? {
            return if (array.isEmpty() || array[0] == null)
                null
            else
                insert(array, null, 0)
        }

        private fun build2(array: Array<Int?>): TreeNode? {
            if (array.isEmpty() || array[0] == null)
                return null

            val queue = LinkedList<TreeNode>()
            val root = TreeNode(array[0]!!)
            queue.add(root)
            // println("root: $root")
            var i = 1
            while (i < array.size) {
                val size = queue.size
                for (j in 1..size) {
                    if (i == array.size)
                        break
                    val node = queue.poll()
                    // print("i: $i, array-i: ${array[i]}")
                    if (array[i] != null) {
                        // print(" is left")
                        node.left = TreeNode(array[i]!!)
                        queue.add(node.left!!)
                    }
                    // println()
                    i++
                    if (i == array.size)
                        break
                    // print("i: $i, array-i: ${array[i]}")
                    if (array[i] != null) {
                        // print(" is right")
                        node.right = TreeNode(array[i]!!)
                        queue.add(node.right!!)
                    }
                    // println()
                    i++
                }
            }
            return root
        }

        fun print(root: TreeNode?, caption: String) {
            print("$caption: ")
            print(root)
            // println()
        }

        private fun print(root: TreeNode?) {
            if (root != null) {
                print(root.left)
                print("${root.value} ")
                print(root.right)
            }
        }
    }
}
