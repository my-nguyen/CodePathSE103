package week4

import TreeNode
import java.util.*

class MinimumDepthOfBinaryTree {
    fun minDepth(root: TreeNode?): Int {
        // return mine(root)
        return codepath(root)
    }

    private fun codepath(root: TreeNode?): Int {
        if (root == null)
            return 0

        val queue = LinkedList<TreeNode>()
        queue.add(root)
        var depth = 1
        while (true) {
            val size = queue.size
            for (i in 1..size) {
                val node = queue.remove()
                // a leaf node is reached: return right away, since that's the minimum depth
                if (node.left == null && node.right == null)
                    return depth

                // it's not a leaf node, so keep pushing any left and right nodes
                if (node.left != null)
                    queue.add(node.left!!)
                if (node.right != null)
                    queue.add(node.right!!)
            }
            depth++
        }
    }

    private fun mine(root: TreeNode?): Int {
        if (root == null)
            return 0
        return minDepth(root, 1)
    }

    private fun minDepth(root: TreeNode?, level: Int): Int {
        if (root == null)
            return level
        if (root.left == null && root.right == null)
            return level + 1
        val left = minDepth(root.left, level)
        val right = minDepth(root.right, level)
        return minOf(left, right)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(arrayOf(3,9,20,null,null,15,7), arrayOf(2,null,3,null,4,null,5,null,6))
            for (array in arrays) {
                print("array: ${array.contentToString()}")
                // TreeNode.build() is incorrect with the 2nd array above
                val tree = TreeNode.build(array)
                TreeNode.print(tree, ", tree")
                val depth = MinimumDepthOfBinaryTree().minDepth(tree)
                println(", depth: $depth")
            }
        }
    }
}