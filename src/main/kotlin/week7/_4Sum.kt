package week7

class _4Sum {
    fun fourSum(array: IntArray, target: Int): List<List<Int>> {
        // return mine1(array, target)
        return mine2(array, target)
    }

    // faster than 66%
    private fun mine1(array: IntArray, target: Int): List<List<Int>> {
        array.sort()
        val result = mutableSetOf<List<Int>>()
        for (i in 0 until array.size-3) {
            for (j in i+1 until array.size-2) {
                var k = j + 1
                var l = array.lastIndex
                while (k < l) {
                    val sum = array[i] + array[j] + array[k] + array[l]
                    when {
                        sum < target -> k++
                        sum > target -> l--
                        else -> {
                            val list = listOf(array[i], array[j], array[k], array[l])
                            result.add(list)
                            k++
                            l--
                        }
                    }
                }
            }
        }
        return result.toList()
    }

    // faster than 55%: no improvement
    private fun mine2(array: IntArray, target: Int): List<List<Int>> {
        array.sort()
        val result = mutableSetOf<List<Int>>()
        for (i in 0 until array.size-3) {
            val target1 = target - array[i]
            for (j in i+1 until array.size-2) {
                val target2 = target1 - array[j]
                var k = j + 1
                var l = array.lastIndex
                while (k < l) {
                    when {
                        array[k] + array[l] < target2 -> k++
                        array[k] + array[l] > target2 -> l--
                        else -> {
                            val list = listOf(array[i], array[j], array[k], array[l])
                            result.add(list)
                            k++
                            l--
                        }
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
                intArrayOf(1,0,-1,0,-2,2), intArrayOf(2,2,2,2,2)
            )
            val targets = arrayOf(0, 8)
            for (i in arrays.indices) {
                print("array: ${arrays[i].contentToString()}, target: ${targets[i]}")
                val sums = _4Sum().fourSum(arrays[i], targets[i])
                print(", result: ")
                for (sum in sums) {
                    print("${sum}, ")
                }
                println()
            }
        }
    }
}