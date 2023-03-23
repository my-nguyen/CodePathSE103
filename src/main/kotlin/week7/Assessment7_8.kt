package week7

import java.util.*


class Assessment7_8 {
    fun threeSumClosest(nums: IntArray, target: Int): Int {
        Arrays.sort(nums)
        var min = Int.MAX_VALUE
        for (i in 0 until nums.size - 2) {
            var left = i + 1
            var right = nums.size - 1
            while (left < right) {
                val sum = nums[i] + nums[left] + nums[right]
                if (sum < target)
                    left++
                else if (sum > target)
                    right--
                else
                    return sum

                if (Math.abs(sum - target) < Math.abs(min - target))
                    min = sum
            }
        }
        return min
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(
                intArrayOf(-1,2,1,-4),
            )
            val targets = arrayOf(1)
            for (i in arrays.indices) {
                print("array: ${arrays[i].contentToString()}, target: ${targets[i]}, ")
                val sum = Assessment7_8().threeSumClosest(arrays[i], targets[i])
                println("sum: $sum")
            }
        }
    }
}