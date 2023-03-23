package week4

import TreeNode
import java.util.*

class Assessment4_9 {
    class BSTIterator(root: TreeNode?) {

        // private val iterator = FlatteningTheBST(root)
        private val iterator = ControlledRecursion(root)

        fun next() = iterator.next()

        fun hasNext() = iterator.hasNext()
    }

    class ControlledRecursion(root: TreeNode?) {
        // for the recursion simulation
        val stack = Stack<TreeNode>()

        init {
            goLeft(root)
        }

        private fun goLeft(root: TreeNode?) {
            var node = root
            // keep going left, adding left children along the way onto the stack
            while (node != null) {
                stack.push(node)
                node = node.left
            }
        }

        fun next(): Int {
            // get the node at stack top
            val node = stack.pop()

            // if node is not a leaf, which means it has a right child, then the next smallest element is the leftmost
            // child of the right child
            if (node.right != null) {
                goLeft(node.right)
            }
            // otherwise if node is a leaf, then it's the next smallest element: do nothing

            return node.value
        }

        fun hasNext() = stack.isNotEmpty()
    }

    class FlatteningTheBST(root: TreeNode?) {

        private val nodes = mutableListOf<TreeNode>()
        private var index = 0

        init {
            inorder(root)
        }

        fun next() = nodes[index++].value

        fun hasNext() = index < nodes.size
        
        private fun inorder(node: TreeNode?) {
            if (node == null)
                return

            inorder(node.left)
            nodes.add(node)
            inorder(node.right)
        }
    }

    /**
     * Your BSTIterator object will be instantiated and called as such:
     * var obj = BSTIterator(root)
     * var param_1 = obj.next()
     * var param_2 = obj.hasNext()
     */

    interface Command {
        fun run(arg: TreeNode?): Int?
    }

    lateinit var iterator: BSTIterator

    fun buildMap(): Map<String, Command> {
        val map = mutableMapOf<String, Command>()

        map["BSTIterator"] = object: Command {
            override fun run(arg: TreeNode?): Int? {
                iterator = BSTIterator(arg)
                return null
            }
        }

        map["next"] = object: Command {
            override fun run(arg: TreeNode?) = iterator.next()
        }

        map["hasNext"] = object: Command {
            override fun run(arg: TreeNode?) = if (iterator.hasNext()) 1 else 0
        }

        return map
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val methods = arrayOf(
                arrayOf("next", "next", "hasNext", "next", "hasNext", "next", "hasNext", "next", "hasNext"),
                arrayOf("next", "next", "hasNext", "next", "hasNext", "next", "hasNext", "next", "hasNext"),
            )
            val arrays = arrayOf(
                arrayOf(7,3,15,null,null,9,20),
                arrayOf<Int?>(6,4,10,2,5,8,12)
            )
            val map = Assessment4_9().buildMap()
            for (i in methods.indices) {
                val tree = TreeNode.build(arrays[i])
                print("${map["BSTIterator"]!!.run(tree)}, ")
                for (j in methods[i].indices) {
                    print("${map[methods[i][j]]!!.run(null)}, ")
                }
                println()
            }
        }
    }
}