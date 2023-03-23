package week4

import TreeNode

class PathSum {
    fun hasPathSum(root: TreeNode?, targetSum: Int): Boolean {
        return if (root == null)
            false
        else if (root.left == null && root.right == null)
            root.value == targetSum
        else
            hasPathSum(root.left, targetSum - root.value)
                    || hasPathSum(root.right, targetSum - root.value)
    }

    /*// Recursively checks each root-to-leaf path
    // Visits each node once: O(n) time, O(h) space
    fun codepath(root: TreeNode?, sum: Int): Boolean {
        var sum = sum
        if (root == null) return false

        // Find remaining sum
        sum -= root.`val`

        // If the current root is a leaf
        return if (root.left == null && root.right == null)
            sum == 0
        else
            codepath(root.left, remaining) || codepath(root.right, remaining)

        // Visit children otherwise
    }*/

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(arrayOf(5,4,8,11,null,13,4,7,2,null,null,null,1), arrayOf(1,2,3,null), arrayOf(1,2,null))
            val targets = arrayOf(22, 5, 0)
            for (i in arrays.indices) {
                print("array: ${arrays[i].contentToString()}, target: ${targets[i]}")
                val tree = TreeNode.build(arrays[i])
                TreeNode.print(tree, ", tree")
                val hasPath = PathSum().hasPathSum(tree, targets[i])
                println("has path sum? $hasPath")
            }
        }
    }
}

