package week7

class SearchInsertPosition {
    fun searchInsert(numbers: IntArray, target: Int): Int {
        if (target < numbers.first())
            return 0
        if (target > numbers.last())
            return numbers.size

        var low = 0
        var high = numbers.lastIndex
        while (low <= high) {
            val mid = low + (high-low)/2
            // println("target: $target, low: $low, mid: $mid, high: $high, at mid: ${numbers[mid]}")
            when {
                numbers[mid] < target -> low = mid + 1
                numbers[mid] > target -> high = mid - 1
                else -> return mid
            }
        }
        // println("returning $low")
        return low
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val numbers = arrayOf(
                intArrayOf(1,3,5),
                intArrayOf(1,3,5,6), intArrayOf(1,3,5,6), intArrayOf(1,3,5,6), intArrayOf(1)
            )
            val targets = arrayOf(1, 2, 7, 0, 0)
            for (i in numbers.indices) {
                val position = SearchInsertPosition().searchInsert(numbers[i], targets[i])
                println("numbers: ${numbers[i].contentToString()}, target: ${targets[i]}, position: $position")
            }
        }
    }
}