package week5

class NumberOfSinkNodesInAGraph {
    fun countSink(nodes: Int, edges: Int, from: IntArray, to: IntArray?): Int {
        val sinkNodes = IntArray(nodes + 1)
        // mark non-sink nodes, which have edges coming out of
        for (i in 0 until edges) {
            sinkNodes[from[i]] = 1
        }

        // count sink nodes
        var count = 0
        for (i in 1..nodes) {
            if (sinkNodes[i] == 0)
                count++
        }
        return count
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val nodes = arrayOf(4, 4)
            val edges = arrayOf(2, 2)
            val from = arrayOf(intArrayOf(2,4), intArrayOf(3,3))
            val to = arrayOf(intArrayOf(3,3), intArrayOf(2,4))
            for (i in nodes.indices) {
                val countSink = NumberOfSinkNodesInAGraph().countSink(nodes[i], edges[i], from[i], to[i])
                println("number of sink nodes: $countSink")
            }
        }
    }
}