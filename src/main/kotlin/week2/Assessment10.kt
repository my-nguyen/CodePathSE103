package week2

import java.util.*

class Assessment10 {
    fun getSkyline(buildings: Array<IntArray>): List<List<Int>> {
        // return mine(buildings)
        return leetcode(buildings)
    }

    private fun leetcode(buildings: Array<IntArray>): List<List<Int>> {
        val n = buildings.size
        val output = mutableListOf<List<Int>>()

        // The base cases
        if (n == 0)
            return output
        if (n == 1) {
            val xStart = buildings[0][0]
            val xEnd = buildings[0][1]
            val y = buildings[0][2]
            output.add(listOf(xStart, y))
            output.add(listOf(xEnd, 0))
            return output
        }

        // If there is more than one building, recursively divide the input into two subproblems.
        val leftSkyline = getSkyline(Arrays.copyOfRange(buildings, 0, n / 2))
        val rightSkyline = getSkyline(Arrays.copyOfRange(buildings, n / 2, n))

        // Merge the results of subproblem together.
        return mergeSkylines(leftSkyline, rightSkyline)
    }

    private fun mergeSkylines(left: List<List<Int>>, right: List<List<Int>>): List<List<Int>> {
        val nL = left.size
        val nR = right.size
        var pL = 0
        var pR = 0
        var currY = 0
        var leftY = 0
        var rightY = 0
        var x: Int
        var maxY: Int
        val output: MutableList<MutableList<Int>> = ArrayList()

        // while we're in the region where both skylines are present
        while (pL < nL && pR < nR) {
            val pointL = left[pL]
            val pointR = right[pR]
            // pick up the smallest x
            if (pointL[0] < pointR[0]) {
                x = pointL[0]
                leftY = pointL[1]
                pL++
            } else {
                x = pointR[0]
                rightY = pointR[1]
                pR++
            }
            // max height (i.e. y) between both skylines
            maxY = maxOf(leftY, rightY)
            // update output if there is a skyline change
            if (currY != maxY) {
                updateOutput(output, x, maxY)
                currY = maxY
            }
        }

        // there is only left skyline
        appendSkyline(output, left, pL, nL, currY)

        // there is only right skyline
        appendSkyline(output, right, pR, nR, currY)
        return output
    }

    private fun updateOutput(output: MutableList<MutableList<Int>>, x: Int, y: Int) {
        // if skyline change is not vertical - add the new point
        if (output.isEmpty() || output[output.size - 1][0] != x) {
            output.add(mutableListOf(x, y))
        } else {
            output[output.size - 1][1] = y
        }
    }

    private fun appendSkyline(output: MutableList<MutableList<Int>>, skyline: List<List<Int>>, p: Int, n: Int, currY: Int) {
        var p = p
        var currY = currY
        while (p < n) {
            val point = skyline[p]
            val x = point[0]
            val y = point[1]
            p++

            // update output if there is a skyline change
            if (currY != y) {
                updateOutput(output, x, y)
                currY = y
            }
        }
    }

    data class Building(val height: Int, val isLeft: Boolean)
    private fun mine(buildings: Array<IntArray>): List<List<Int>> {
        val maxHeap = PriorityQueue<IntArray> { a, b -> b[2] - a[2] }
        val map = TreeMap<Int, MutableSet<IntArray>>()
        for (building in buildings) {
            val left = building[0]
            if (!map.containsKey(left))
                map[left] = mutableSetOf()
            map[left]!!.add(building)

            val right = building[1]
            if (!map.containsKey(right))
                map[right] = mutableSetOf()
            map[right]!!.add(building)
        }

        val result = mutableListOf<IntArray>()
        for ((k, list) in map) {
            if (result.isEmpty()) {
            }
        }
        return mutableListOf()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val data = arrayOf(
                arrayOf(intArrayOf(2,9,10), intArrayOf(3,7,15), intArrayOf(5,12,12), intArrayOf(15,20,10), intArrayOf(19,24,8)),
                arrayOf(intArrayOf(0,2,3), intArrayOf(2,5,3))
            )
            for (buildings in data) {
                print("buildings: ")
                for (building in buildings) {
                    print("${building.contentToString()} ")
                }
                println()
                val skyline = Assessment10().getSkyline(buildings)
                print("skyline: ")
                for (building in skyline) {
                    print("$building ")
                }
                println()
            }
        }
    }
}