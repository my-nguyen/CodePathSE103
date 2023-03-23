package week7


class Assessment7_5 {
    data class Interval(val start: Int, val end: Int) {
        override fun toString() = "[$start, $end]"
    }

    fun addIntervalBuggy(intervals: List<Interval>, newInterval: Interval): List<Interval> {
        val result = mutableListOf<Interval>()
        var newInterval = newInterval
        for (interval in intervals) {
            if (interval.end < newInterval.start) {
                result.add(interval)
            } else if (interval.start > newInterval.end) {
                result.add(newInterval)
            } else if (interval.end >= newInterval.start || interval.start <= newInterval.end) {
                val start = minOf(interval.start, newInterval.start)
                val end = maxOf(interval.end, newInterval.end)
                newInterval = Interval(start, end)
            }
        }
        result.add(newInterval)
        return result
    }

    fun addIntervalFixed(intervals: List<Interval>, newInterval: Interval): List<Interval> {
        val result = mutableListOf<Interval>()
        var i = 0
        while (i < intervals.size && intervals[i].end < newInterval.start) {
            result.add(intervals[i])
            i++
        }
        var start = 0
        var end = 0
        while (i < intervals.size && intervals[i].start <= newInterval.end) {
            start = minOf(newInterval.start, intervals[i].start)
            end = maxOf(newInterval.end, intervals[i].end)
            i++
        }
        val tmpInterval = Interval(start, end)
        result.add(tmpInterval)
        while (i < intervals.size) {
            result.add(intervals[i])
            i++
        }
        return result
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val data1 = arrayOf(
                listOf(intArrayOf(1,5), intArrayOf(6,12), intArrayOf(14,15)),
            )
            val data2 = arrayOf(
                intArrayOf(3,6),
            )
            for (i in data1.indices) {
                val intervals = mutableListOf<Interval>()
                for (j in data1[i].indices) {
                    val interval = Interval(data1[i][j][0], data1[i][j][1])
                    intervals.add(interval)
                }
                print("intervals: ")
                for (interval in intervals) {
                    print("$interval, ")
                }
                val newInterval = Interval(data2[i][0], data2[i][1])
                print("newInterval: $newInterval, ")
                val merged = Assessment7_5().addIntervalFixed(intervals, newInterval)
                print("merged: ")
                for (interval in merged) {
                    print("$interval, ")
                }
                println()
            }
        }
    }
}