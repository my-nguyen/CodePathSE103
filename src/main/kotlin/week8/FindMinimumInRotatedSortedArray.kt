package week8

class FindMinimumInRotatedSortedArray {
    fun findMin(array: IntArray): Int {
        var left = 0
        var right = array.lastIndex
        while (left <= right) {
            val mid = left + (right-left)/2
            if (mid < right && array[mid] > array[mid+1])
                return array[mid+1]
            if (left < mid && array[mid-1] > array[mid])
                return array[mid]

            if (array[mid] >= array[left]) {
                left = mid + 1
            } else {
                right = mid - 1
            }
        }
        return array[0]
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(intArrayOf(3,4,5,1,2), intArrayOf(4,5,6,7,0,1,2), intArrayOf(11,13,15,17))
            for (array in arrays) {
                print("array: ${array.contentToString()}, ")
                val min = FindMinimumInRotatedSortedArray().findMin(array)
                println("min: $min")
            }
        }
    }
}