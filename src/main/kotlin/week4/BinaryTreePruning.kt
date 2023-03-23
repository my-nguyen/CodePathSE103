package week4

import TreeNode

class BinaryTreePruning {
    fun pruneTree(root: TreeNode?): TreeNode? {
        return postorder(root)
    }

    private fun postorder(node: TreeNode?): TreeNode? {
        if (node == null)
            return null

        node.left = pruneTree(node.left)
        node.right = pruneTree(node.right)
        return if (node.left == null && node.right == null && node.value == 0)
            // remove node if it's a leaf and its value is 0
            null
        else
            node
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(
                arrayOf(1, null, 0, 0, 1),
                arrayOf<Int?>(1, 0, 1, 0, 0, 0, 1),
                arrayOf<Int?>(1, 1, 0, 1, 1, 0, 1, 0)
            )
            for (array in arrays) {
                print("array: ${array.contentToString()}")
                val tree = TreeNode.build(array)
                TreeNode.print(tree, ", tree")
                val pruned = BinaryTreePruning().pruneTree(tree)
                TreeNode.print(pruned, ", pruned")
                println()
            }
        }
    }
}