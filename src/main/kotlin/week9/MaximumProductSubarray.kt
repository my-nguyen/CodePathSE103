package week9

class MaximumProductSubarray {
    fun maxProduct(array: IntArray): Int {
        // return mine(array)
        return solution(array)
    }

    // example to illustrate the use of maxSoFar and minSoFar:
    // 2, -5, 3, 1, -4, 0, -10, 2, 8
    // initial: maxSF = 2, minSF = 2, result = 2
    // current = -5: maxSF = max(-5, 2*-5, 2*-5) = -5, minSF = min(-5, 2*-5, 2*-5) = -10, result = 2
    // current = 3: maxSF = max(3, -5*3, -10*3) = 3, minSF = min(3, -5*3, -10*3) = -30, result = 3
    // current = 1: maxSF = max(1, 3*1, -30*1) = 3, minSF = min(1, 3*1, -30*1) = -30, result = 3
    // current = -4: maxSF = max(-4, 3*-4, -30*-4) = 120, minSF = min(-4, 3*-4, -30*-4) = -12, result = 120
    // current = 0: maxSF = max(0, 120*0, -12*0) = 0, minSF = min(0, 120*0, -12*0) = 0, result = 120
    // current = -10: maxSF = max(-10, 0*-10, 0*-10) = 0, minSF = min(-10, 0*-10, 0*-10) = -10, result = 120
    // current = 2: maxSF = max(2, 0*2, -10*2) = 2, minSF = min(2, 0*2, -10*2) = -20, result = 120
    // current = 8: maxSF = max(8, 2*8, -20*8) = 16, minSF = min(8, 2*8, -20*8) = -160, result = 120
    // result = 120!!!
    private fun solution(nums: IntArray): Int {
        if (nums.isEmpty())
            return 0

        // the maximum product so far, to keep track of positive numbers
        var maxSoFar = nums[0]
        // to minimum product so far, to handle negative numbers
        var minSoFar = nums[0]
        var result = maxSoFar
        for (i in 1..nums.lastIndex) {
            val curr = nums[i]
            // maxSoFar is the MAXIMUM of (1) the product of maxSoFar and the current number;
            // (2) the product of minSoFar and the current number; and (3) the current number
            val tempMax = maxOf(curr, maxOf(maxSoFar * curr, minSoFar * curr))
            // minSoFar is the MINIMUM of (1) the product of maxSoFar and the current number;
            // (2) the product of minSoFar and the current number; and (3) the current number
            minSoFar = minOf(curr, minOf(maxSoFar * curr, minSoFar * curr))
            maxSoFar = tempMax
            result = maxOf(maxSoFar, result)
        }
        return result
    }

    private fun mine(array: IntArray): Int {
        if (array.isEmpty())
            return 0
        if (array.size == 1)
            return array[0]

        var max = Int.MIN_VALUE
        for (i in array.indices) {
            var product = 1
            for (j in i..array.lastIndex) {
                product *= array[j]
                if (product > max)
                    max = product
            }
        }
        return max
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(
                intArrayOf(0,2),
                intArrayOf(-2,3,-4),
                intArrayOf(2,3,-2,4), intArrayOf(-2,0,-1)
            )
            for (array in arrays) {
                val product = MaximumProductSubarray().maxProduct(array)
                println("product: $product")
            }
        }
    }
}
