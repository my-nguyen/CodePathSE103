package week4

import TreeNode
import java.util.*

class ValidateBinarySearchTree {
    fun isValidBST(root: TreeNode?): Boolean {
        // return mine(root)
        // return leetcodeTopDownDFS(root)
        return leetcodeInorderTraversal(root)
    }

    private fun leetcodeTopDownDFS(root: TreeNode?): Boolean {
        return validate(root, Int.MIN_VALUE, Int.MAX_VALUE)
    }

    private val nodes = LinkedList<TreeNode?>()
    private val upper = LinkedList<Int>()
    private val lower = LinkedList<Int>()
    private fun leetcodeTopDownIterative(root: TreeNode?): Boolean {
        var low = Int.MIN_VALUE
        var high = Int.MAX_VALUE
        update(root, low, high)

        while (nodes.isNotEmpty()) {
            val node = nodes.poll() ?: continue

            low = lower.poll()
            if (node.value <= low)
                return false
            high = upper.poll()
            if (node.value >= high)
                return false

            update(node.right, node.value, high)
            update(node.left, low, node.value)
        }
        return true
    }

    private var previous = Int.MIN_VALUE
    private fun leetcodeInorderTraversal(root: TreeNode?): Boolean {
        return inorder(root)
    }

    private fun leetcodeInorderIterative(root: TreeNode?): Boolean {
        val stack = Stack<TreeNode>()
        var previous = Int.MIN_VALUE
        var node = root
        while (stack.isNotEmpty() && node != null) {
            while (node != null) {
                stack.push(node)
                node = node.left
            }

            node = stack.pop()
            // If next element in inorder traversal is smaller than the previous one, then that's not BST.
            if (node.value <= previous)
                return false

            previous = node.value
            node = node.right
        }
        return true
    }

    // incorrect solution
    private fun mine(root: TreeNode?): Boolean {
        return when {
            root == null -> true
            root.left == null && root.right == null -> true
            root.left != null && root.left!!.value >= root.value -> false
            root.right != null && root.value >= root.right!!.value -> false
            else -> mine(root.left) && mine(root.right)
        }
    }

    // similar to that of Gayle McDowell
    // time: O(n), to traverse all nodes in tree
    // space: O(n)
    private fun validate(node: TreeNode?, low: Int, high: Int): Boolean {
        return when {
            // a null node is a valid BST
            node == null -> true
            // node value is not within [low, high] range: not a valid BST
            node.value <= low || node.value >= high -> false
            // recursive call to left subtree and right subtree with updated low and high:
            // * going left, low stays the same, and high takes on the current node value
            // * going right, high stays the same, and low takes on the current node value
            else -> validate(node.left, low, node.value) && validate(node.right, node.value, high)
        }
    }

    private fun update(root: TreeNode?, low: Int, high: Int) {
        nodes.add(root)
        lower.add(low)
        upper.add(high)
    }

    private fun inorder(root: TreeNode?): Boolean {
        return when {
            root == null -> true
            !inorder(root.left) -> false
            root.value <= previous -> false
            else -> {
                previous = root.value
                inorder(root.right)
            }
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(arrayOf<Int?>(2,1,3), arrayOf(5,1,4,null,null,3,6),
                arrayOf<Int?>(0, -1), arrayOf(5,4,6,null,null,3,7)
            )
            for (array in arrays) {
                print("array: ${array.contentToString()}")
                val tree = TreeNode.build(array)
                TreeNode.print(tree, ", tree")
                val isValidBST = ValidateBinarySearchTree().isValidBST(tree)
                println(", is valid BST? $isValidBST")
            }
        }
    }
}