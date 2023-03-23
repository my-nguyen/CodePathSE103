package week8

import TreeNode
import java.util.*

class HouseRobberIII {
    fun rob(root: TreeNode?): Int {
        if (root == null)
            return 0

        val levelSums = mutableListOf<Int>()
        val queue = LinkedList<TreeNode>()
        queue.offer(root)
        while (queue.isNotEmpty()) {
            val size = queue.size
            var sum = 0
            // collect the sum of all node values at each level
            for (i in 1..size) {
                val node = queue.poll()
                sum += node.value
                // println("node: ${node.value}, sum: $sum")
                if (node.left != null)
                    queue.offer(node.left)
                if (node.right != null)
                    queue.offer(node.right)
            }
            levelSums.add(sum)
            println("level: $sum")
        }

        val subSums = IntArray(2)
        // calculate the odd sums and the even sums
        for (i in levelSums.indices) {
            subSums[i % 2] += levelSums[i]
        }
        println("sums: ${subSums.contentToString()}")

        return maxOf(subSums[0], subSums[1])
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(
                arrayOf(4,1,null,2,null,3),
                // arrayOf(3,2,3,null,3,null,1), arrayOf(3,4,5,1,3,null,1)
            )
            for (array in arrays) {
                val tree = TreeNode.build(array)
                val robbed = HouseRobberIII().rob(tree)
                println("rob: $robbed")
            }
        }
    }
}