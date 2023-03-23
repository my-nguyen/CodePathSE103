package week9

class Assessment9_7 {
    fun maxProfit(prices: IntArray): Int {
        return solutionDP1(prices)
        // return solutionDP2 (prices)
    }

    private fun solutionDP1(prices: IntArray): Int {
        var sold = Int.MIN_VALUE
        var held = Int.MIN_VALUE
        var reset = 0
        for (price in prices) {
            val preSold = sold
            sold = held + price
            held = Math.max(held, reset - price)
            reset = Math.max(reset, preSold)
        }
        return Math.max(sold, reset)
    }

    private fun solutionDP2(prices: IntArray): Int {
        val MP = IntArray(prices.size + 2)
        for (i in prices.indices.reversed()) {
            var C1 = 0
            // Case 1). buy and sell the stock
            for (sell in i + 1 until prices.size) {
                val profit = prices[sell] - prices[i] + MP[sell + 2]
                C1 = Math.max(profit, C1)
            }

            // Case 2). do no transaction with the stock p[i]
            val C2 = MP[i + 1]

            // wrap up the two cases
            MP[i] = Math.max(C1, C2)
        }
        return MP[0]
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(intArrayOf(1,2,3,0,2), intArrayOf(1))
            for (prices in arrays) {
                print("prices: ${prices.contentToString()}, ")
                val profit = Assessment9_7().maxProfit(prices)
                println("max profit: $profit")
            }
        }
    }
}
