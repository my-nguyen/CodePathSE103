package week3

import java.util.*

// this is leetcode Sliding Window Maximum
class Assessment8 {
    fun maxSlidingWindow(array: IntArray, k: Int): IntArray {
        val maxHeap = PriorityQueue<Int> { a, b -> b - a }
        if (array.size < k) {
            for (number in array)
                maxHeap.offer(number)
            return intArrayOf(maxHeap.peek())
        }

        for (i in 0 until k)
            maxHeap.offer(array[i])
        val max = IntArray(array.size - k  + 1)
        max[0] = maxHeap.peek()
        for (i in k until array.size) {
            maxHeap.remove(array[i-k])
            maxHeap.offer(array[i])
            max[i-k+1] = maxHeap.peek()
        }
        return max
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(
                intArrayOf(1,3,-1,-3,5,3,6,7), intArrayOf(1), intArrayOf(1,-1), intArrayOf(9,11), intArrayOf(4,-2)
            )
            val ks = arrayOf(3, 1, 1, 2, 2)
            for (i in arrays.indices) {
                val maxes = Assessment8().maxSlidingWindow(arrays[i], ks[i])
                println("array: ${arrays[i].contentToString()}, k: ${ks[i]}, maxes: ${maxes.contentToString()}")
            }
        }
    }
}