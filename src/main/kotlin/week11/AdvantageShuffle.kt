package week11

import java.util.*

class AdvantageShuffle {
    fun advantageCount(nums1: IntArray, nums2: IntArray): IntArray {
        return solutionGreedy(nums1, nums2)
    }

    private fun solutionGreedy(A: IntArray, B: IntArray): IntArray {
        val sortedA = A.sorted()
        val sortedB = B.sorted()
        println("sortedA: $sortedA, sortedB: $sortedB")

        // assigned[b] = list of a that are assigned to beat b
        val assigned = mutableMapOf<Int, Queue<Int>>()
        for (b in B)
            assigned[b] = LinkedList()

        // remaining = list of a that are not assigned to any b
        val remaining = LinkedList<Int>()

        // populate (assigned, remaining) appropriately
        // sortedB[j] is always the smallest unassigned element in B
        var j = 0
        for (a in sortedA) {
            if (a > sortedB[j]) {
                assigned[sortedB[j++]]!!.add(a)
            } else {
                remaining.add(a)
            }
        }
        for ((k, v) in assigned) {
            println("b: $k, assigned: $v")
        }
        println("remaining: $remaining")

        // Reconstruct the answer from annotations (assigned, remaining)
        val ans = IntArray(B.size)
        for (i in B.indices) {
            println("i: $i, B[i]: ${B[i]}, assigned: ${assigned[B[i]]}")
            // if there is some a assigned to b...
            ans[i] = if (assigned[B[i]]!!.size > 0)
                assigned[B[i]]!!.poll()
            else
                remaining.poll()!!
        }
        return ans
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val nums1 = arrayOf(intArrayOf(2,7,11,15), /*intArrayOf(12,24,8,32)*/)
            val nums2 = arrayOf(intArrayOf(1,10,4,11), intArrayOf(13,25,32,11))
            for (i in nums1.indices) {
                print("nums1: ${nums1[i].contentToString()}, nums2: ${nums2[i].contentToString()}, ")
                val count = AdvantageShuffle().advantageCount(nums1[i], nums2[i])
                println("advantage count: ${count.contentToString()}")
            }
        }
    }
}

