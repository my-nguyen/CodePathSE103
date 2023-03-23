package week4

import TreeNode
import java.util.*

class FindLargestValueInEachTreeRow {
    fun largestValues(root: TreeNode?): List<Int> {
        if (root == null)
            return mutableListOf()

        val queue = LinkedList<TreeNode>()
        queue.add(root)
        val result = mutableListOf<Int>()
        while (queue.isNotEmpty()) {
            val size = queue.size
            val list = mutableListOf<Int>()
            for (i in 1..size) {
                val node = queue.poll()
                list.add(node.value)
                if (node.left != null) {
                    queue.add(node.left!!)
                }
                if (node.right != null) {
                    queue.add(node.right!!)
                }
            }
            result.add(list.maxOrNull()!!)
        }
        return result
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(arrayOf(1,3,2,5,3,null,9), arrayOf<Int?>(1,2,3), arrayOf<Int?>(1), arrayOf(1,null,2), arrayOf())
            for (array in arrays) {
                print("array: ${array.contentToString()}")
                val tree = TreeNode.build(array)
                TreeNode.print(tree, ", tree")
                val maxes = FindLargestValueInEachTreeRow().largestValues(tree)
                println("maxes: $maxes")
            }
        }
    }
}