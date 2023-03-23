package week10

class DeleteAndEarn {
    fun deleteAndEarn(nums: IntArray): Int {
        return solutionDP(nums)
    }

    fun solutionDP(nums: IntArray): Int {
        val count = IntArray(10001)
        for (x in nums) count[x]++
        var avoid = 0
        var using = 0
        var prev = -1
        for (k in 0..10000) if (count[k] > 0) {
            val m = Math.max(avoid, using)
            if (k - 1 != prev) {
                using = k * count[k] + m
                avoid = m
            } else {
                using = k * count[k] + avoid
                avoid = m
            }
            prev = k
        }
        return Math.max(avoid, using)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(intArrayOf(3,4,2), intArrayOf(2,2,3,3,3,4))
            for (array in arrays) {
                print("array: ${array.contentToString()}, ")
                val number = DeleteAndEarn().deleteAndEarn(array)
                println("number: $number")
            }
        }
    }
}
