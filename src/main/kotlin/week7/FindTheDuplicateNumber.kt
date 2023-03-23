package week7


class FindTheDuplicateNumber {
    fun findDuplicate(array: IntArray): Int {
        // return solution1(array)
        return solution2(array)
        // return solution3(array)
    }

    private fun solution1(array: IntArray): Int {
        array.sort()
        for (i in 1..array.lastIndex) {
            if (array[i] == array[i - 1]) {
                return array[i]
            }
        }
        return -1
    }

    private fun solution2(array: IntArray): Int {
        val seen = mutableSetOf<Int>()
        for (num in array) {
            if (seen.contains(num)) {
                return num
            }
            seen.add(num)
        }
        return -1
    }

    private fun solution3(array: IntArray): Int {
        // Find the intersection point of the two runners.
        var slow = array[0]
        var fast = array[0]
        do {
            slow = array[slow]
            fast = array[array[fast]]
        } while (slow != fast)

        // Find the "entrance" to the cycle.
        slow = array[0]
        while (slow != fast) {
            slow = array[slow]
            fast = array[fast]
        }
        return fast
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(
                intArrayOf(1,3,4,2,2), intArrayOf(3,1,3,4,2), intArrayOf(1,1), intArrayOf(1,1,2)
            )
            for (array in arrays) {
                print("array: ${array.contentToString()}, ")
                val duplicate = FindTheDuplicateNumber().findDuplicate(array)
                println("duplicate: $duplicate")
            }
        }
    }
}
