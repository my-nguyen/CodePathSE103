package week10

import java.util.*
import java.util.ArrayList

class Assessment10_7 {
    fun lengthOfLIS(nums: IntArray): Int {
        return solutionDP(nums)
    }

    private fun solutionDP(nums: IntArray): Int {
        val dp = IntArray(nums.size)
        Arrays.fill(dp, 1)
        for (i in 1 until nums.size) {
            for (j in 0 until i) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1)
                }
            }
        }
        var longest = 0
        for (c in dp) {
            longest = Math.max(longest, c)
        }
        return longest
    }

    private fun solutionSequence(nums: IntArray): Int {
        val sub = ArrayList<Int>()
        sub.add(nums[0])
        for (i in 1 until nums.size) {
            val num = nums[i]
            if (num > sub[sub.size - 1]) {
                sub.add(num)
            } else {
                // Find the first element in sub that is greater than or equal to num
                var j = 0
                while (num > sub[j]) {
                    j += 1
                }
                sub[j] = num
            }
        }
        return sub.size
    }

    private fun solutionBinarySearch(nums: IntArray): Int {
        val sub = ArrayList<Int>()
        sub.add(nums[0])
        for (i in 1 until nums.size) {
            val num = nums[i]
            if (num > sub[sub.size - 1]) {
                sub.add(num)
            } else {
                val j = binarySearch(sub, num)
                sub[j] = num
            }
        }
        return sub.size
    }

    private fun binarySearch(sub: ArrayList<Int>, num: Int): Int {
        var left = 0
        var right = sub.size - 1
        var mid = (left + right) / 2
        while (left < right) {
            mid = (left + right) / 2
            if (sub[mid] == num) {
                return mid
            }
            if (sub[mid] < num) {
                left = mid + 1
            } else {
                right = mid
            }
        }
        return left
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(
                intArrayOf(10,9,2,5,3,7,101,18), intArrayOf(0,1,0,3,2,3), intArrayOf(7,7,7,7,7,7,7),
                intArrayOf(1,4,3), intArrayOf(1,4,5,2,6), intArrayOf(2,3,3,5)
            )
            for (numbers in arrays) {
                print("numbers: ${numbers.contentToString()}, ")
                val length = Assessment10_7().lengthOfLIS(numbers)
                println("longest increasing subsequence: $length")
            }
        }
    }
}

