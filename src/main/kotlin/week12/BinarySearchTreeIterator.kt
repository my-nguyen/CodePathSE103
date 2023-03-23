package week12

import TreeNode

class BinarySearchTreeIterator {
    /**
     * Example:
     * var ti = TreeNode(5)
     * var v = ti.`val`
     * Definition for a binary tree node.
     * class TreeNode(var `val`: Int) {
     *     var left: TreeNode? = null
     *     var right: TreeNode? = null
     * }
     */
    class BSTIterator(root: TreeNode?) {

        fun next(): Int {
            return 0
        }

        fun hasNext(): Boolean {
            return true
        }
    }

    /**
     * Your BSTIterator object will be instantiated and called as such:
     * var obj = BSTIterator(root)
     * var param_1 = obj.next()
     * var param_2 = obj.hasNext()
     */

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val methods = arrayOf("BSTIterator", "next", "next", "hasNext", "next", "hasNext", "next", "hasNext", "next", "hasNext")
            val params = arrayOf(arrayOf(7,3,15,null,null,9,20), null, null, null, null, null, null, null, null, null)
        }
    }
}