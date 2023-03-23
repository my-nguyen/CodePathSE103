package week9


class MinimumCostTreeFromLeafValues {
    fun mctFromLeafValues(array: IntArray): Int {
        return allenlipeng47(array)
    }

    private fun allenlipeng47(arr: IntArray): Int {
        val st = SegmentTree(arr)
        val memo = Array(arr.size) { IntArray(arr.size) { -1 } }
        return dfs(arr, 0, arr.size - 1, memo, st)
    }

    private fun dfs(arr: IntArray, lo: Int, hi: Int, memo: Array<IntArray>, st: SegmentTree): Int {
        if (lo == hi) {
            memo[lo][hi] = 0
        }
        if (memo[lo][hi] != -1) {
            return memo[lo][hi]
        }
        memo[lo][hi] = Int.MAX_VALUE
        for (i in lo until hi) {
            // [lo, i], [i + 1, hi]
            val a1 = dfs(arr, lo, i, memo, st)
            val a2 = dfs(arr, i + 1, hi, memo, st)
            val a3 = st.findMax(lo, i) * st.findMax(i + 1, hi)
            val currAns = a1 + a2 + a3
            memo[lo][hi] = minOf(memo[lo][hi], currAns)
        }
        return memo[lo][hi]
    }

    internal class SegmentTree(val array: IntArray) {
        var root: Node?
        private fun build(start: Int, end: Int): Node? {
            if (start > end) {
                return null
            }
            val node = Node(start, end)
            if (start == end) {
                node.max = array[start]
            } else {
                val mid = start + (end - start) / 2
                node.left = build(start, mid)
                node.right = build(mid + 1, end)
                val leftMax = if (node.left == null) Int.MIN_VALUE else node.left!!.max
                val rightMax = if (node.right == null) Int.MIN_VALUE else node.right!!.max
                node.max = maxOf(leftMax, rightMax)
            }
            return node
        }

        fun findMax(start: Int, end: Int): Int {
            return findMax(root, start, end)
        }

        private fun findMax(node: Node?, start: Int, end: Int): Int {
            return if (start > node!!.end || end < node.start)
                Int.MIN_VALUE
            else if (start <= node.start && node.end <= end)
                node.max
            else
                maxOf(findMax(node.left, start, end), findMax(node.right, start, end))
        }

        init {
            this.root = build(0, array.size - 1)
        }
    }

    internal class Node(var start: Int, var end: Int) {
        var max = Int.MIN_VALUE
        var left: Node? = null
        var right: Node? = null
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(intArrayOf(6,2,4))
            for (array in arrays) {
                print("array: ${array.contentToString()}, ")
                val cost = MinimumCostTreeFromLeafValues().mctFromLeafValues(array)
                println("minimum cost tree: $cost")
            }
        }
    }
}