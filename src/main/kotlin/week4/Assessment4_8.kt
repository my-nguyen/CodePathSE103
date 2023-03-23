package week4

import TreeNode
import java.util.*

class Assessment4_8 {
    class Codec() {
        // Encodes a URL to a shortened URL.
        fun serialize(root: TreeNode?): String {
            if (root == null)
                return "*"

            val queue = LinkedList<TreeNode?>()
            queue.add(root)
            val sb = StringBuilder()
            while (queue.isNotEmpty()) {
                val size = queue.size
                for (i in 1..size) {
                    val node = queue.poll()
                    if (node == null)
                        sb.append("*,")
                    else {
                        sb.append("${node.value},")
                        queue.add(node.left)
                        queue.add(node.right)
                    }
                }
            }
            return sb.toString()
        }

        // Decodes your encoded data to tree.
        fun deserialize(data: String): TreeNode? {
            if (data.isNullOrEmpty() || data == "*")
                return null

            val values = data.split(",")
            val queue = LinkedList<TreeNode>()
            val root = TreeNode(values[0].toInt())
            queue.add(root)
            var i = 1
            // since the serialized string has a trailing comma, the last element of the 'values' array will be the empty string
            while (values[i] != "") {
                val size = queue.size
                // println("i: $i, queue size: $size")
                for (j in 1..size) {
                    val node = queue.poll()

                    if (i == values.size)
                        break
                    if (values[i] != "*") {
                        node.left = TreeNode(values[i].toInt())
                        queue.add(node.left!!)
                    }
                    i++

                    if (i == values.size)
                        break
                    if (values[i] != "*") {
                        node.right = TreeNode(values[i].toInt())
                        queue.add(node.right!!)
                    }
                    i++
                }
            }
            return root
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(arrayOf<Int?>(2,1,3), arrayOf(), arrayOf(1,2,3,null,null,4,5))
            for (array in arrays) {
                print("array: ${array.contentToString()}")
                val tree = TreeNode.build(array)
                print(", tree: ")
                tree?.print()
                val codec = Codec()
                val serialized = codec.serialize(tree)
                print(", serialized: $serialized")
                val deserialized = codec.deserialize(serialized)
                print(", deserialized: ")
                deserialized?.print()
                println()
            }
        }
    }
}