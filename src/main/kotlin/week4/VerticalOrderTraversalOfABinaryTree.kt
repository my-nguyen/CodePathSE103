package week4

import TreeNode
import java.util.*

class VerticalOrderTraversalOfABinaryTree {
    fun verticalTraversal(root: TreeNode?): List<List<Int>> {
        // return mine(root)
        return leetcode1(root)
    }

    // IntArray = { 0: col, 1: row, 2: value }
    val minHeap1 = PriorityQueue<IntArray>() { a, b ->
        when {
            a[0] != b[0] -> a[0] - b[0]
            a[1] != b[1] -> a[1] - b[1]
            else -> a[2] - b[2]
        }
    }

    // time complexity: O(N logN)
    // space complexity: O(N)
    private fun leetcode1(root: TreeNode?): List<List<Int>> {
        dfs1(root, 0, 0)
        // a list of sublists; the latter containing all entries within the same column,
        // and the former is indexed by column position
        val result = mutableListOf<MutableList<Int>>()
        var col = Int.MIN_VALUE
        while (minHeap1.isNotEmpty()) {
            // take top entry from minHeap
            val entry = minHeap1.poll()
            // if the entry column is a new column, add a new list into result
            if (entry[0] != col) {
                col = entry[0]
                result.add(mutableListOf())
            }
            // save entry value in list, which contains all entries within the same column
            result.last().add(entry[2])
        }
        return result
    }

    val map = mutableMapOf<Int, Queue<IntArray>>()
    private fun leetcode2(root: TreeNode?): List<List<Int>> {
        dfs2(root, 0, 0)

        val minCol = map.keys.minOrNull()!!
        val maxCol = map.keys.maxOrNull()!!
        val result = mutableListOf<MutableList<Int>>()
        for (col in minCol..maxCol) {
            // add an empty list to result to contain all entries in the current column
            result.add(mutableListOf())
            // extract all entries from pQueue and save it into the list just created
            while (map[col]!!.isNotEmpty()) {
                val entry = map[col]!!.remove()
                result.last().add(entry[1])
            }
        }
        return result
    }

    private fun dfs1(node: TreeNode?, col: Int, row: Int) {
        if (node == null)
            return

        // save current entry info into minHeap
        val entry = intArrayOf(col, row, node.value)
        minHeap1.add(entry)

        dfs1(node.left, col - 1, row + 1)
        dfs1(node.right, col + 1, row + 1)
    }

    private fun dfs2(node: TreeNode?, col: Int, row: Int) {
        if (node == null)
            return

        // IntArray = { 0: row, 1: value }
        val minHeap2 = PriorityQueue<IntArray> { a, b ->
            if (a[0] != b[0]) a[0] - b[0]
            else a[1] - b[1]
        }
        map.putIfAbsent(col, minHeap2)
        map[col]!!.add(intArrayOf(row, node.value))

        dfs2(node.left, col-1, row+1)
        dfs2(node.right, col+1, row+1)
    }

    private fun mine(root: TreeNode?): List<List<Int>> {
        val lists = mutableListOf<MutableList<Int>>()
        if (root == null)
            return lists

        var row = 0
        var col = 0
        recurse(root.left, row + 1, col - 1, lists)
        recurse(root.right, row + 1, col + 1, lists)
        return lists
    }

    private fun recurse(node: TreeNode?, row: Int, col: Int, lists: MutableList<MutableList<Int>>) {
        if (node == null)
            return
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(
                arrayOf(3,9,20,null,null,15,7), arrayOf<Int?>(1,2,3,4,6,5,7),
                arrayOf<Int?>(1,2,3,4,6,5,7), arrayOf(2,9,8,4,null,1,7,3,11,null,5,null,null,null,null,null,6),
                arrayOf(2,null,8,1,null,null,4,6,null,null,7)
            )
            for (array in arrays) {
                print("array: ${array.contentToString()}")
                val tree = TreeNode.build(array)
                TreeNode.print(tree, ", tree")
                val vertical = VerticalOrderTraversalOfABinaryTree().verticalTraversal(tree)
                print(", column traversal: ")
                for (list in vertical) {
                    print("$list ")
                }
                println()
            }
        }
    }
}