package week4

import TreeNode

class MostFrequentSubtreeSum {
    // Since you need to get the most frequent sums, anything relating to frequency hints you need to use a
    // Map<PossibleSum, Frequency>
    // Second you have a tree and you need to iterate it and at every node you need to find the sum of subtree i.e root,
    // left subtree, right subtree. In order to iterate you can use a dfs
    // Keep a global maxFrequency variable just to keep track of high frequency whenever you do a put operation on hashMap
    // https://leetcode.com/problems/most-frequent-subtree-sum/discuss/1288485/Java-Solution-and-Explanation-for-Beginners
    private var maxFrequency = 0

    fun findFrequentTreeSum(root: TreeNode?): IntArray? {
        val frequencies = mutableMapOf<Int, Int>()
        // calculate the subtree sum and frequency of the root node
        dfs(root, frequencies)

        val list = mutableListOf<Int>()
        for ((key, value) in frequencies) {
            // collect all sums with max frequency into a list
            if (value == maxFrequency) {
                list.add(key)
            }
        }
        // convert list to array and return
        return list.toIntArray()
    }

    fun dfs(root: TreeNode?, frequencies: MutableMap<Int, Int>): Int {
        if (root == null)
            return 0

        // a subtree sum is the sum of (1) current node; (2) sum of left subtree; and (3) sum of right subtree
        var sum = root.value
        sum += dfs(root.left, frequencies)
        sum += dfs(root.right, frequencies)

        // record the frequency of the current sum
        frequencies[sum] = frequencies.getOrDefault(sum, 0) + 1
        // update max frequency if any
        maxFrequency = maxOf(maxFrequency, frequencies[sum]!!)
        return sum
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(arrayOf<Int?>(5,2,-3), arrayOf<Int?>(5,2,-5))
            for (array in arrays) {
                print("array: ${array.contentToString()}")
                val tree = TreeNode.build(array)
                TreeNode.print(tree, ", tree")
                val frequencies = MostFrequentSubtreeSum().findFrequentTreeSum(tree)
                println(", frequencies: ${frequencies.contentToString()}")
            }
        }
    }
}