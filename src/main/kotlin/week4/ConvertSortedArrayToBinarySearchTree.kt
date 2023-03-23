package week4

import TreeNode

class ConvertSortedArrayToBinarySearchTree {
    fun sortedArrayToBST(nums: IntArray): TreeNode? {
        return sortedArrayToBST(nums, 0, nums.lastIndex)
    }

    private fun sortedArrayToBST(array: IntArray, left: Int, right: Int): TreeNode {
        // there's only one element in the current array partition
        if (left == right)
            return TreeNode(array[left])

        val mid = left + (right-left)/2
        // the middle element is the root of the next 2 partitions
        val node = TreeNode(array[mid])
        // break array into 2 partitions: [left, mid-1], and [mid+1, right]
        if (left < mid)
            node.left = sortedArrayToBST(array, left, mid-1)
        node.right = sortedArrayToBST(array, mid+1, right)
        return node
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(intArrayOf(-10,-3,0,5,9), intArrayOf(1,3))
            for (array in arrays) {
                print("array: ${array.contentToString()}")
                val tree = ConvertSortedArrayToBinarySearchTree().sortedArrayToBST(array)
                TreeNode.print(tree, ", tree")
                println()
            }
        }
    }
}