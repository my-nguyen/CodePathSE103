package week2

import java.util.*

class FindKPairsWithSmallestSums {
    data class Entry(val array: IntArray, val index: Int) {
        override fun toString() = "${array.contentToString()} at $index"
    }

    fun kSmallestPairs(nums1: IntArray, nums2: IntArray, k: Int): List<List<Int>> {
        val pairs = mutableListOf<List<Int>>()
        if (nums1.isEmpty() || nums2.isEmpty())
            return pairs

        val minHeap = PriorityQueue<Entry>() { a, b -> a.array[a.index] - b.array[b.index] }
        minHeap.add(Entry(nums1, 0))
        minHeap.add(Entry(nums2, 0))

        val map = mutableMapOf<IntArray, Int>()

        while (pairs.size < k && minHeap.isNotEmpty()) {
            val top = minHeap.poll()

            map[top.array] = top.array[top.index]
            if (map.size == 2) {
                val pair = listOf(map[nums1]!!, map[nums2]!!)
                pairs.add(pair)
            }
            if (top.index < top.array.lastIndex) {
                val entry = Entry(top.array, top.index+1)
                minHeap.add(entry)
            } else {
                map.remove(top.array)
            }
        }

        return pairs
    }
}