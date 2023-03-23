package week2

import java.util.*

class KClosestPointsToOrigin {
    fun kClosest(points: Array<IntArray>, k: Int): Array<IntArray> {
        val minHeap = PriorityQueue<IntArray>() { a, b -> distance(a) - distance(b) }
        for (point in points) {
            minHeap.add(point)
        }

        val result = Array(k) { IntArray(2) }
        for (i in 0 until k) {
            result[i] = minHeap.poll()
        }

        return result
    }

    private fun distance(point: IntArray) = point[0]*point[0] + point[1]*point[1]

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val points = arrayOf(
                arrayOf(intArrayOf(1,3), intArrayOf(-2,2)),
                arrayOf(intArrayOf(3,3), intArrayOf(5,-1), intArrayOf(-2,4))
            )
            val ks = arrayOf(1, 2)
            for (i in points.indices) {
                val result = KClosestPointsToOrigin().kClosest(points[i], ks[i])
                for (point in result) {
                    print("${point.contentToString()}, ")
                }
                println()
            }
        }
    }
}