package week8

import TreeNode
import java.util.*

class SameTree {
    fun isSameTree(tree1: TreeNode?, tree2: TreeNode?): Boolean {
        // both trees are null
        if (tree1 == null && tree2 == null)
            return true
        // one tree is null but the other is not
        if (tree1 == null || tree2 == null)
            return false

        // queue the root node of each tree
        val queue = LinkedList<TreeNode>()
        queue.offer(tree1)
        queue.offer(tree2)
        while (queue.isNotEmpty()) {
            // extract the top 2 queue entries
            val node1 = queue.poll()
            val node2 = queue.poll()

            // check the value field of the 2 nodes
            if (node1.value != node2.value)
                return false

            if (node1.left != null && node2.left != null) {
                // both nodes have a left tree: queue the 2 left trees
                queue.offer(node1.left)
                queue.offer(node2.left)
            } else if (node1.left != null || node2.left != null) {
                // one node has a left tree but the other one doesn't
                return false
            }

            if (node1.right != null && node2.right != null) {
                // both nodes have a right tree: queue the 2 right trees
                queue.offer(node1.right)
                queue.offer(node2.right)
            } else if (node1.right != null || node2.right != null) {
                // one node has a right tree but the other one doesn't
                return false
            }
        }
        return true
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val ps = arrayOf(arrayOf<Int?>(1,2,3), arrayOf<Int?>(1,2), arrayOf<Int?>(1,2,1))
            val qs = arrayOf(arrayOf<Int?>(1,2,3), arrayOf<Int?>(1,null,2), arrayOf<Int?>(1,1,2))
            for (i in ps.indices) {
                val p = TreeNode.build(ps[i])
                val q = TreeNode.build(qs[i])
                val same = SameTree().isSameTree(p, q)
                println("is same tree? $same")
            }
        }
    }
}