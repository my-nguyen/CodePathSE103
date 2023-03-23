package week8

import TreeNode

class ConstructStringFromBinaryTree {
    val sb = StringBuilder()

    fun tree2str(root: TreeNode?): String {
        preorder(root, true)
        return sb.toString()
    }

    fun preorder(node: TreeNode?, isRoot: Boolean = false) {
        node?.let {
            if (!isRoot)
                sb.append("(")
            sb.append(it.value)
            it.left?.let { left ->
                preorder(left)
            }
            it.right?.let { right ->
                if (it.left == null)
                    sb.append("()")
                preorder(right)
            }
            if (!isRoot)
                sb.append(")")
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(arrayOf<Int?>(1,2,3,4), arrayOf<Int?>(1,2,3,null,4))
            for (array in arrays) {
                val tree = TreeNode.build(array)
                val string = ConstructStringFromBinaryTree().tree2str(tree)
                println("string: $string")
            }
        }
    }
}