package week9

class ClimbingStairs {
    fun climbStairs(n: Int): Int {
        // return dpTopDown(n)
        // return dpBottomUp(n)
        return dpNoMemory(n)
    }

    private fun dpTopDown(n: Int): Int {
        return if (n == 1 || n == 2)
            n
        else {
            val dp = mutableListOf<Int>()
            dp.add(1)
            dp.add(2)
            countRecursive(n, dp)
        }
    }

    private fun dpBottomUp(n: Int): Int {
        return if (n == 1 || n == 2)
            n
        else {
            val dp = mutableListOf<Int>()
            dp.add(1)
            dp.add(2)
            for (i in 2 until n)
                dp.add(dp[i-2] + dp[i-1])
            dp[n-1]
        }
    }

    private fun dpNoMemory(n: Int): Int {
        return if (n == 1 || n == 2)
            n
        else {
            var previous = 1
            var next = 2
            for (i in 2 until n) {
                val sum = previous + next
                previous = next
                next = sum
            }
            return next
        }
    }

    private fun countRecursive(n: Int, dp: MutableList<Int>): Int {
        // if n is not pre-calculated, then recursively calculate it, save the calculation and return it
        if (n > dp.size) {
            val stairs = countRecursive(n-1, dp) + countRecursive(n-2, dp)
            dp.add(stairs)
        }
        return dp[n-1]
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val data = arrayOf(1, 2, 3, 4, 5, 6, 7)
            for (steps in data) {
                val count = ClimbingStairs().climbStairs(steps)
                println("count: $count")
            }
        }
    }
}