package week4

import TreeNode
import kotlin.math.abs

class BalancedBinaryTree {
    // incorrect solution with the last input according to leetcode; i'm not sure why
    fun isBalanced(root: TreeNode?): Boolean {
        return if (root == null)
            true
        else {
            val left = height(root.left)
            val right = height(root.right)
            abs(left - right) <= 1 && isBalanced(root.left) && isBalanced(root.right)
        }
    }

    // Recursively determine if a binary tree is balanced
    // Visits each node once: O(n) time, O(h) space
    private fun codepath(root: TreeNode?): Boolean {
        return height2(root) != -1
    }

    // Find the height of a tree given its root
    // return -1 if unbalanced.
    private fun height2(root: TreeNode?): Int {
        // Base case
        if (root == null) return 0
        val leftHeight = height2(root.left)
        val rightHeight = height2(root.right)

        // If tree is unbalanced, return -1
        return if (leftHeight == -1 || rightHeight == -1 || Math.abs(leftHeight - rightHeight) > 1
        ) -1 else 1 + Math.max(leftHeight, rightHeight)

        // Otherwise, return height of tree
    }

    private fun height(root: TreeNode?): Int {
        return if (root == null)
            -1
        else
            1 + maxOf(height(root.left), height(root.right))
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(
                arrayOf(3,9,20,null,null,15,7), arrayOf(1,2,2,3,3,null,null,4,4), arrayOf(),
                arrayOf<Int?>(1), arrayOf(1,2,2,3,null,null,3,4,null,null,4)
            )
            for (array in arrays) {
                print("array: ${array.contentToString()}")
                val tree = TreeNode.build(array)
                TreeNode.print(tree, ", tree")
                val isBalanced = BalancedBinaryTree().isBalanced(tree)
                println(", is balanced? $isBalanced")
            }
        }
    }
}
