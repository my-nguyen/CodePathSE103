package week4

import TreeNode

class BinaryTrees2ndLargestNode {
    fun getSecondLargest(node: TreeNode): TreeNode {
        return when {
            // this is the right-most element (aka largest) and it has a left child: return the largest element
            // in its left child
            node.right == null && node.left != null -> getLargest(node.left!!)
            // this is the parent of the largest element and the latter has no children: return this parent
            node.right != null && node.right!!.left == null && node.right!!.right == null -> node
            // otherwise, recurse on the right child until we match one of the cases above
            else -> getSecondLargest(node.right!!)
        }
    }

    private fun getLargest(node: TreeNode): TreeNode {
        return if (node!!.right != null)
            getLargest(node.right!!)
        else
            node
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(
                arrayOf<Int?>(5,2,9,1,3,6,12),
                arrayOf(5,2,12,1,3,7,null,null,null,null,null,6,9),
                arrayOf(5,2,12,1,3,9,null,null,null,null,null,7,null,6,8),
                arrayOf(5,2,12,1,3,9,null,null,null,null,null,7,11,6),
                arrayOf(5,2,12,1,3,9,null,null,null,null,null,7,11,6,10),
            )
            for (array in arrays) {
                print("array: ${array.contentToString()}")
                val tree = TreeNode.build(array)
                TreeNode.print(tree, ", tree")
                val second = BinaryTrees2ndLargestNode().getSecondLargest(tree!!)
                println(", 2nd largest: ${second?.value}")
            }
        }
    }
}