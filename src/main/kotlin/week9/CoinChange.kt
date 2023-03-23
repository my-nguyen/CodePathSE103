package week9

class CoinChange {
    fun coinChange(coins: IntArray, amount: Int): Int {
        // return bruteForce(coins, amount)
        // return memoized(coins, amount)
        return tabulized(coins, amount)
    }

    private fun bruteForce(coins: IntArray, amount: Int): Int {
        if (amount == 0)
            return 0
        if (amount < 0)
            return -1

        var min = Int.MAX_VALUE
        for (coin in coins) {
            val count = bruteForce(coins, amount-coin)
            if (count != -1)
                min = minOf(min, count+1)
        }
        return if (min == Int.MAX_VALUE) -1 else min
    }

    private fun memoized(coins: IntArray, amount: Int, memo: MutableMap<Int, Int> = mutableMapOf()): Int {
        if (amount == 0)
            return 0
        if (amount < 0)
            return -1
        if (memo.containsKey(amount))
            return memo[amount]!!

        var min = Int.MAX_VALUE
        for (coin in coins) {
            val count = memoized(coins, amount-coin, memo)
            if (count != -1)
                min = minOf(min, count+1)
        }

        memo[amount] = if (min == Int.MAX_VALUE) -1 else min
        return memo[amount]!!
    }

    private fun tabulized(coins: IntArray, amount: Int): Int {
        if (amount == 0)
            return 0
        if (amount < 0)
            return -1
        val table = IntArray(amount + 1)
        var min = Int.MAX_VALUE
        for (coin in coins) {
            val count = tabulized(coins, amount-coin)
            if (count != -1) {
                min = minOf(min, count + 1)
            }
        }
        table[amount] = if (min == Int.MAX_VALUE) -1 else min
        return table[amount]
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val coins = arrayOf(
                intArrayOf(1,2,5), intArrayOf(2), intArrayOf(1), intArrayOf(1), intArrayOf(1)
            )
            val amounts = arrayOf(11, 3, 0, 1, 2)
            for (i in coins.indices) {
                print("coins: ${coins[i].contentToString()}, amount: ${amounts[i]}, ")
                val minCoins = CoinChange().coinChange(coins[i], amounts[i])
                println("fewest number of coins: $minCoins")
            }
        }
    }
}