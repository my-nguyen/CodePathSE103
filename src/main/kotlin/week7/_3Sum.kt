package week7

class _3Sum {
    fun threeSum(array: IntArray): List<List<Int>> {
        if (array.size < 3)
            return listOf()

        array.sort()
        val result = mutableSetOf<List<Int>>()
        for (i in 0 until array.size-2) {
            var j = i + 1
            var k = array.lastIndex
            while (j < k) {
                val sum = array[i] + array[j] + array[k]
                when {
                    sum < 0 -> j++
                    sum > 0 -> k--
                    else -> {
                        val list = listOf(array[i], array[j], array[k])
                        result.add(list)
                        j++
                        k--
                    }
                }
            }
        }
        return result.toList()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(
                intArrayOf(-1,0,1,2,-1,-4), intArrayOf(), intArrayOf(0)
            )
            for (array in arrays) {
                val sums = _3Sum().threeSum(array)
                for (sum in sums) {
                    print("$sum, ")
                }
                println()
            }
        }
    }
}