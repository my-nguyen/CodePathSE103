package week8

class MergeIntervals {
    fun merge(intervals: Array<IntArray>): Array<IntArray> {
        // return merge1(intervals)
        return merge2(intervals)
    }

    private fun merge2(intervals: Array<IntArray>): Array<IntArray> {
        // intervals.sortWith(compareBy({it[0]}))
        intervals.sortWith { a, b -> a[0] - b[0] }

        val result = mutableListOf<IntArray>()
        result.add(intervals[0])
        for (i in 1..intervals.lastIndex) {
            if (intervals[i][0] <= result.last()[1])
                result.last()[1] = maxOf(result.last()[1], intervals[i][1])
            else
                result.add(intervals[i])
        }
        return result.toTypedArray()
    }

    private fun merge1(intervals: Array<IntArray>): Array<IntArray> {
        // intervals.sortWith(compareBy({it[0]}))
        intervals.sortWith { a, b -> a[0] - b[0] }

        val result = mutableListOf<IntArray>()
        var interval = intervals[0].copyOf()
        for (i in 1..intervals.lastIndex) {
            if (intervals[i][0] <= interval[1])
                interval[1] = maxOf(interval[1], intervals[i][1])
            else {
                result.add(interval)
                interval = intervals[i].copyOf()
            }
        }
        result.add(interval)
        return result.toTypedArray()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val data = arrayOf(
                arrayOf(intArrayOf(1,4), intArrayOf(0,2), intArrayOf(3,5)),
                arrayOf(intArrayOf(1,3), intArrayOf(2,6), intArrayOf(8,10), intArrayOf(15,18)),
                arrayOf(intArrayOf(1,4), intArrayOf(4,5))
            )
            for (intervals in data) {
                val merged = MergeIntervals().merge(intervals)
                for (interval in merged) {
                    print("${interval.contentToString()}, ")
                }
                println()
            }
        }
    }
}