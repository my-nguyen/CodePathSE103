package week9

class BestTimeToBuyAndSellStock {
    fun maxProfit(prices: IntArray): Int {
        // return mine(prices)
        return solution(prices)
    }

    private fun solution(prices: IntArray): Int {
        var minPrice = Integer.MAX_VALUE
        var profit = 0
        for (i in prices.indices) {
            if (prices[i] < minPrice) {
                // check for min price first before checking profit
                minPrice = prices[i]
            } else if (profit < prices[i] - minPrice) {
                // once min price has been set, try to maximize profit which is the difference between the current price
                // and the min price
                profit = prices[i] - minPrice
            }
        }
        return profit
    }

    private fun mine(prices: IntArray): Int {
        var min = Int.MAX_VALUE
        var max = Int.MIN_VALUE
        var profit = 0
        for (i in prices.indices) {
            if (prices[i] < min) {
                min = prices[i]
                max = Int.MIN_VALUE
                // println("i: $i, min: $min")
            } else if (prices[i] > max) {
                max = prices[i]
                profit = max - min
                // println("i: $i, max: $max, profit: $profit")
            }
        }
        return profit
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val data = arrayOf(intArrayOf(3,2,6,5,0,3), /*intArrayOf(2,1,2,1,0,1,2), intArrayOf(2,4,1), intArrayOf(7,1,5,3,6,4), intArrayOf(7,6,4,3,1)*/)
            for (prices in data) {
                print("prices: ${prices.contentToString()}, ")
                val profit = BestTimeToBuyAndSellStock().maxProfit(prices)
                println("profit: $profit")
            }
        }
    }
}