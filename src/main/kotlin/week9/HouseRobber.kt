package week9

class HouseRobber {
    fun rob(array: IntArray): Int {
        // return mine(array)
        return robIterative(array)
    }

    private fun robIterative(nums: IntArray): Int {
        return when (val length = nums.size) {
            0 -> 0
            1 -> nums[0]
            else -> {
                val total = IntArray(length)
                total[length - 1] = nums[length - 1]
                total[length - 2] = nums[length - 2]
                if (length > 2) {
                    // iterating from the end to the start of the array, use a moving total to keep track of the total
                    // max so far
                    total[length - 3] = nums[length - 3] + nums[length - 1]
                    var i = length - 4
                    while (i >= 0) {
                        // the total max is the TOTAL of the current element AND the MAX of either the 2nd or the 3rd
                        // element away from the current element
                        total[i] = nums[i] + maxOf(total[i + 2], total[i + 3])
                        i--
                    }
                }
                // eventually it's the max of the first 2 array elements
                maxOf(total[0], total[1])
            }
        }
    }

    private fun mine(array: IntArray): Int {
        // base case when there's only 1 or 2 elements
        if (array.size == 1 || array.size == 2)
            return array.maxOrNull()!!

        val dp = IntArray(array.size)
        // the first 2 sums are just the first 2 array elements
        dp[0] = array[0]
        dp[1] = array[1]
        // the 3rd sum is the sum of the 1st and 3rd array elements
        dp[2] = array[2] + array[0]
        var max = maxOf(dp[2], dp[1])

        for (i in 3..array.lastIndex) {
            // consider between 2 indices before or 3 indices before
            val tmp = maxOf(dp[i-2], dp[i-3])
            // add that max with the current array element
            dp[i] = array[i] + tmp
            // update max if any
            max = maxOf(max, dp[i])
            // println("i: $i, array: ${array[i]}, tmp: $tmp, dp: ${dp[i]}, max: $max")
        }
        return max
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(intArrayOf(1,3,1), intArrayOf(2,1), intArrayOf(1,2,3,1), intArrayOf(2,7,9,3,1))
            for (array in arrays) {
                print("array: ${array.contentToString()}, ")
                val rob = HouseRobber().rob(array)
                println("rob amount: $rob")
            }
        }
    }
}