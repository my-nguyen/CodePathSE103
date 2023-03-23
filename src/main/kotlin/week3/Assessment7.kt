package week3

// this is leetcode 132 Pattern
class Assessment7 {
    fun find132pattern(nums: IntArray): Boolean {
        if (nums.size < 3)
            return false

        for (i in 0 until nums.size-2) {
            for (j in i+1 until nums.size-1) {
                for (k in j+1 until nums.size) {
                    if (nums[i] < nums[k] && nums[k] < nums[j])
                        return true
                }
            }
        }
        return false
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(intArrayOf(1,2,3,4), intArrayOf(3,1,4,2), intArrayOf(-1,3,2,0))
            for (array in arrays) {
                val pattern = Assessment7().find132pattern(array)
                println("array: ${array.contentToString()}, is 132 pattern? $pattern")
            }
        }
    }
}