package week4

import TreeNode

class SymmetricTree {
    fun isSymmetric(root: TreeNode?): Boolean {
        return isSymmetric(root, root)
    }

    // Visits each node once: O(n) time, O(h) space
    private fun codepath(root: TreeNode?): Boolean {
        return if (root == null) true else isMirror(root.left, root.right)
    }

    // Recursively determines if two branches are mirrors of each other
    fun isMirror(t1: TreeNode?, t2: TreeNode?): Boolean {
        // Base case 1: Both branches are null
        if (t1 == null && t2 == null) return true

        // Base case 2: One branch is null and the other is not
        if (t1 == null || t2 == null) return false

        // Base case 3: The values of two roots are different
        return if (t1.value !== t2.value) false else isMirror(t1.right, t2.left) && isMirror(t1.left, t2.right)
    }

    private fun isSymmetric(left: TreeNode?, right: TreeNode?): Boolean {
        return when {
            // if both left and right subtrees are null, then it's symmetric
            left == null && right == null -> true
            // if one subtree is null and the other one is not, or if both subtrees are not null but their values are
            // different, then it's not symmetric
            left == null || right == null || left.value != right.value -> false
            // otherwise, make recursive calls to left and right subtrees
            else -> isSymmetric(left.left, right.right) && isSymmetric(left.right, right.left)
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(arrayOf<Int?>(1,2,2,3,4,4,3), arrayOf(1,2,2,null,3,null,3))
            for (array in arrays) {
                print("array: ${array.contentToString()}")
                val tree = TreeNode.build(array)
                TreeNode.print(tree, ", tree")
                val isSymmetric = SymmetricTree().isSymmetric(tree)
                println(", is symmetric? $isSymmetric")
            }
        }
    }
}
