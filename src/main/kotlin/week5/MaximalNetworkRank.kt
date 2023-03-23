package week5


class MaximalNetworkRank {
    fun maximalNetworkRank(n: Int, roads: Array<IntArray>): Int {
        // return mine(n, roads)
        return leetcode(n, roads)
    }

    // https://leetcode.com/problems/maximal-network-rank/discuss/1264122/Java-Simple-Solution
    private fun leetcode(n: Int, roads: Array<IntArray>): Int {
        // a matrix of boolean
        val connected = Array(n) { BooleanArray(n) }
        // map each city to the number of connections
        val connections = mutableMapOf<Int, Int>()
        createGraph(roads, connected, connections)

        var max = 0
        // this uses double loops instead of my solution which uses triple loops
        for (city1 in 0 until n) {
            val conn1 = connections.getOrDefault(city1, 0)
            println("city1: $city1, count: $conn1")
            for (city2 in city1+1 until n) {
                val conn2 = connections.getOrDefault(city2, 0)
                var rank = conn1 + conn2
                if (connected[city1][city2]) rank--
                max = maxOf(max, rank)
                println("city2: $city2, count: $conn2, rank: $rank, max: $max")
            }
        }
        return max
    }

    private fun createGraph(roads: Array<IntArray>, connected: Array<BooleanArray>, map: MutableMap<Int, Int>) {
        for (road in roads) {
            val a = road[0]
            val b = road[1]
            map[a] = map.getOrDefault(a, 0) + 1
            map[b] = map.getOrDefault(b, 0) + 1
            connected[a][b] = true
            connected[b][a] = true
        }
    }

    private fun mine(n: Int, roads: Array<IntArray>): Int {
        val matrix = Array(n) { IntArray(n) }
        // create a matrix of integers where if 2 nodes are connected, then the 2 matrix elements at (node1, node2) and
        // (node2, node1) are set to 1; otherwise the elements are set to 0
        for (road in roads) {
            val row = road[0]
            val col = road[1]
            matrix[row][col] = 1
            matrix[col][row] = 1
        }

        // count the number of 1's in each matrix row
        val counts = IntArray(n)
        for (i in matrix.indices) {
            counts[i] = matrix[i].sum()
        }

        var max = 0
        // iterate by row
        for (i in matrix.indices) {
            // for each row, check each column
            for (j in 0 until matrix[i].lastIndex) {
                // check all pairs (j, k) within each row
                for (k in j+1..matrix[i].lastIndex) {
                    var sum = counts[j] + counts[k]
                    // discount by 1 if j and k are connected
                    if (matrix[j][k] == 1)
                        sum--
                    max = maxOf(max, sum)
                }
            }
        }
        return max
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val sizes = arrayOf(/*4,*/ 5, /*8*/)
            val roads = arrayOf(
                // arrayOf(intArrayOf(0,1), intArrayOf(0,3), intArrayOf(1,2), intArrayOf(1,3)),
                arrayOf(intArrayOf(0,1), intArrayOf(0,3), intArrayOf(1,2), intArrayOf(1,3), intArrayOf(2,3), intArrayOf(2,4)),
                // arrayOf(intArrayOf(0,1), intArrayOf(1,2), intArrayOf(2,3), intArrayOf(2,4), intArrayOf(5,6), intArrayOf(5,7))
            )
            for (i in sizes.indices) {
                val max = MaximalNetworkRank().maximalNetworkRank(sizes[i], roads[i])
                println("max: $max")
            }
        }
    }
}