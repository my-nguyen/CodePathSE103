package week9

class MaximumSubarray {
    fun maxSubArray(array: IntArray): Int {
        // return mine(array)
        return dp(array)
    }

    private fun dp(array: IntArray): Int {
        var max = array[0]
        val dp = IntArray(array.size)
        dp[0] = array[0]
        for (i in 1..array.lastIndex) {
            dp[i] = dp[i-1] + array[i]
            max = maxOf(max, dp[i])
        }
        println("dp: ${dp.contentToString()}")
        return max
    }

    private fun mine(array: IntArray): Int {
        // take the first array entry instead of Int.MIN_VALUE
        var global = array[0]
        var local = array[0]
        for (i in 1..array.lastIndex) {
            val tmp = local + array[i]
            // choose between the current entry and the sum including the current entry
            local = maxOf(array[i], tmp)
            // update the global max as well
            global = maxOf(global, local)
            // println("i: $i, arr: ${array[i]}, local: $local, global: $global")
        }
        return global
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(intArrayOf(-2,1,-3,4,-1,2,1,-5,4), /*intArrayOf(1,3,-1,8,-20,5), intArrayOf(1), intArrayOf(5,4,-1,7,8)*/)
            for (array in arrays) {
                val max = MaximumSubarray().maxSubArray(array)
                println("max subarray: $max")
            }
        }
    }
}