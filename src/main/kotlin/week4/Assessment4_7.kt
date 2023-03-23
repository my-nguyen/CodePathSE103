package week4

import TreeNode
import java.util.*

class Assessment4_7 {
    fun averageOfLevels(root: TreeNode?): DoubleArray {
        val averages = mutableListOf<Double>()
        if (root == null)
            return averages.toDoubleArray()

        val queue = LinkedList<TreeNode>()
        queue.add(root)
        while (queue.isNotEmpty()) {
            val size = queue.size
            var sum = 0
            for (i in 1..size) {
                val node = queue.poll()
                sum += node.value

                if (node.left != null)
                    queue.add(node.left!!)
                if (node.right != null)
                    queue.add(node.right!!)
            }
            averages.add(sum / size.toDouble())
        }
        return averages.toDoubleArray()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(arrayOf(3,9,20,null,15,7), arrayOf<Int?>(3,9,20,15,7))
            for (array in arrays) {
                print("array: ${array.contentToString()}")
                val tree = TreeNode.build(array)
                print(", tree: ")
                tree?.print()
                val averages = Assessment4_7().averageOfLevels(tree)
                println(", averages: ${averages.contentToString()}")
            }
        }
    }
}