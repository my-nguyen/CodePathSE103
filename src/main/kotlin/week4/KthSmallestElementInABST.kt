package week4

import TreeNode
import java.util.*

class KthSmallestElementInABST {
    fun kthSmallest(root: TreeNode, k: Int): Int {
        // return mine(node, k, Stack<Int>())
        // return leetcodeInorderRecursive(root, k)
        return leetcodeInorderIterative(root, k)
    }

    // It's a very straightforward approach with O(N) time complexity. The idea is to build an inorder traversal of BST
    // which is an array sorted in the ascending order. Now the answer is the (k-1)th element of this array.
    private fun leetcodeInorderRecursive(root: TreeNode, k: Int): Int {
        val list = mutableListOf<Int>()
        inorder(root, list)
        return list[k - 1]
    }

    // The above recursion could be converted into iteration, with the help of stack. This way one could speed up
    // the solution because there is no need to build the entire inorder traversal, and one could stop after the kth element.
    private fun leetcodeInorderIterative(root: TreeNode, k: Int): Int {
        val stack = LinkedList<TreeNode>()
        var node: TreeNode? = root
        var k = k
        while (true) {
            while (node != null) {
                // keep moving left and pushing node onto stack
                stack.add(node)
                node = node.left
            }

            // this is the leftmost node in the current branch. pop it and check if it's at kth position
            node = stack.removeLast()
            if (--k == 0)
                return node.value
            // the popped node is not at kth position: try moving right from the popped node
            node = node.right
        }
    }

    // incorrect solution
    private fun mine(node: TreeNode, k: Int, stack: Stack<Int>): Int {
        if (node.left == null && node.right != null) {
            // this is the left-most element (aka smallest) and it has a right child: find the smallest element
            // in its right child
            getSmallest(node.right!!)
        }
        if (node.left != null && node.left!!.left == null && node.left!!.right == null) {
            // this is the parent of the smallest element and the latter has no children: save this parent, and
            stack.push(node.value)
            // if the number of parents is k, then return the parent's value
            if (stack.size == k) {
                return stack.peek()
            }
        } else {
            // otherwise, recurse on the left child until we match one of the cases above
            return mine(node.left!!, k, stack)
        }
        // return just to pass compiler error
        return 0
    }

    private fun getSmallest(node: TreeNode): TreeNode {
        return if (node.left == null)
            node
        else
            getSmallest(node.left!!)
    }

    private fun inorder(root: TreeNode?, list: MutableList<Int>) {
        if (root == null)
            return

        inorder(root.left, list)
        list.add(root.value)
        inorder(root.right, list)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(
                arrayOf(3,1,4,null,2), arrayOf(5,3,6,2,4,null,null,1),
                arrayOf(5,3,6,2,4,null,7),
                // arrayOf(100,95,null,2,97,1,4,null,null,null,null,null,90,5,null,null,80,10,null,7,70,null,null,12,null,null,15,null,20,17,25,null,null,null,30,27)
            )
            val ks = arrayOf(1, 3, 3, 3)
            for (i in arrays.indices) {
                print("array: ${arrays[i].contentToString()}")
                val tree = TreeNode.build(arrays[i])
                TreeNode.print(tree, ", tree")
                val smallest = KthSmallestElementInABST().kthSmallest(tree!!, ks[i])
                println(", k: ${ks[i]}, kth smallest: $smallest")
            }
        }
    }
}