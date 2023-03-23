package week9

class Assessment9_4 {
    fun minCost(cost: IntArray?): Int {
        if (cost == null || cost.size == 1)
            return 0

        val table = IntArray(cost.size + 1)
        // start from 2, since we don't have to worry about costs for entries at 0 and 1.
        for (i in 2 until table.size) {
            val one = table[i - 1] + cost[i - 1]
            val two = table[i - 2] + cost[i - 2]
            table[i] = minOf(one, two)
        }
        return table[table.size - 1]
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val costs = arrayOf(
                intArrayOf(10,15,20), intArrayOf(1,100,1,1,1,100,1,1,100,1)
            )
            for (cost in costs) {
                print("costs: ${cost.contentToString()}, ")
                val cost = Assessment9_4().minCost(cost)
                println("minimum cost: $cost")
            }
        }
    }
}