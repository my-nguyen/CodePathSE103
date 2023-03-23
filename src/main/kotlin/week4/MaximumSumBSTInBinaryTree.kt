package week4

import TreeNode

class MaximumSumBSTInBinaryTree {
    // Mari Batilando's solution: post-order traversal
    // * if node is a leaf, then pass back its value, minVal, maxVal, isBST
    // * if not, then traverse left subtree then right subtree to figure out whether or not they are BSTs
    // * once you have those values, figure out whether the current node is also a BST by checking if isBST is true
    //   for both children and all keys of left subtree are less than the current node, and all keys of right subtree
    //   are greater than the current node
    // * update maxSumBST if needed whenever you find a BST node
    data class Result(val isBST: Boolean, val sum: Int, val min: Int, val max: Int)

    var maxSum = 0

    fun maxSumBST(root: TreeNode?): Int {
        maxSumBSTHelper(root)
        return maxSum
    }

    private fun maxSumBSTHelper(node: TreeNode?): Result {
        if (node == null)
            return Result(true, 0, Int.MAX_VALUE, Int.MIN_VALUE)

        val left = maxSumBSTHelper(node.left)
        val right = maxSumBSTHelper(node.right)
        if (left.max >= node.value || right.min <= node.value || !left.isBST || !right.isBST) {
            return Result(false, 0, Int.MAX_VALUE, Int.MIN_VALUE)
        }

        val sum = node.value + left.sum + right.sum
        maxSum = maxOf(maxSum, sum)
        val min = minOf(node.value, left.min)
        val max = maxOf(node.value, right.max)
        return Result(true, sum, min, max)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(
                arrayOf(1,4,3,2,4,2,5,null,null,null,null,null,null,4,6), arrayOf(4,3,null,1,2),
                arrayOf<Int?>(-4,-2,-5), arrayOf<Int?>(2,1,3), arrayOf(5,4,8,3,null,6,3)
            )
            for (array in arrays) {
                print("array: ${array.contentToString()}")
                val tree = TreeNode.build(array)
                TreeNode.print(tree, ", tree")
                val maxSum = MaximumSumBSTInBinaryTree().maxSumBST(tree)
                println(", max sum: $maxSum")
            }
        }
    }
}