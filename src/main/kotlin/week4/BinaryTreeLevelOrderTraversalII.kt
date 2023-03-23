package week4

import TreeNode
import java.util.*

class BinaryTreeLevelOrderTraversalII {
    fun levelOrderBottom(root: TreeNode?): List<List<Int>> {
        val result = mutableListOf<MutableList<Int>>()
        if (root == null)
            return result

        val queue = LinkedList<TreeNode>()
        queue.add(root)
        while (queue.isNotEmpty()) {
            val size = queue.size
            val list = mutableListOf<Int>()
            result.add(0, list)
            for (i in 1..size) {
                val node = queue.poll()
                list.add(node.value)

                if (node.left != null)
                    queue.add(node.left!!)
                if (node.right != null)
                    queue.add(node.right!!)
            }
        }
        return result
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(arrayOf(3,9,20,null,null,15,7), arrayOf<Int?>(1), arrayOf<Int?>())
            for (array in arrays) {
                print("array: ${array.contentToString()}")
                val tree = TreeNode.build(array)
                print(", tree: ")
                tree?.print()
                val levels = BinaryTreeLevelOrderTraversalII().levelOrderBottom(tree)
                print(", levels: ")
                for (level in levels)
                    print("$level, ")
                println()
            }
        }
    }
}