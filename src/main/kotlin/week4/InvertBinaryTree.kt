package week4

import TreeNode
import java.util.*

class InvertBinaryTree {
    fun invertTree(root: TreeNode?): TreeNode? {
        return recursive(root)
    }

    // Recursively invert
    // Visits each node once: O(n) time, O(h) space
    private fun codepath(root: TreeNode?): TreeNode? {
        if (root == null) return null
        val right = codepath(root.right)
        val left = codepath(root.left)
        root.left = right
        root.right = left
        return root
    }

    private fun recursive(root: TreeNode?): TreeNode? {
        if (root != null) {
            val tmp = root!!.left
            root!!.left = root!!.right
            root!!.right = tmp

            invertTree(root!!.left)
            invertTree(root!!.right)
        }

        return root
    }

    private fun iterative(root: TreeNode?): TreeNode? {
        if (root == null)
            return null

        val queue = LinkedList<TreeNode>()
        queue.add(root)
        while (queue.isNotEmpty()) {
            val node = queue.poll()
            val tmp = node.left
            node.left = node.right
            node.right = tmp

            if (node.left != null)
                queue.add(node.left!!)
            if (node.right != null)
                queue.add(node.right!!)
        }
        return root
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(arrayOf<Int?>(4,2,7,1,3,6,9), arrayOf<Int?>(2,1,3), arrayOf<Int?>())
            for (array in arrays) {
                print("array: ${array.contentToString()}")
                val tree = TreeNode.build(array)
                TreeNode.print(tree, ", tree")
                val inverted = InvertBinaryTree().invertTree(tree)
                TreeNode.print(inverted, ", inverted tree")
                println()
            }
        }
    }
}
