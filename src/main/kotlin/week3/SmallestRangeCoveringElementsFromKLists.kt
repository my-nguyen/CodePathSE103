package week3

import java.util.*

class SmallestRangeCoveringElementsFromKLists {
    fun smallestRange(nums: List<List<Int>>): IntArray {
        return leetcodeBrute1(nums)
    }

    // The naive approach is to consider every pair of elements, nums[i][j] and nums[k][l] from amongst the given lists
    // and consider the range formed by these elements. For every range currently considered, we can traverse over
    // all the lists to find if at least one element from these lists can be included in the current range. If so,
    // we store the end-points of the current range and compare it with the previous minimum range found, if any,
    // satisfying the required criteria, to find the smaller range from among them.
    // Once all the element pairs have been considered as the ranges, we can obtain the required minimum range.
    // Time complexity: O(n^3)
    // Space complexity: O(1)
    fun leetcodeBrute1(nums: List<List<Int>>): IntArray {
        var minx = 0
        var miny = Int.MAX_VALUE
        for (i in nums.indices) {
            for (j in nums[i].indices) {
                for (k in i until nums.size) {
                    for (l in (if (k == i) j else 0) until nums[k].size) {
                        val min = minOf(nums[i][j], nums[k][l])
                        val max = maxOf(nums[i][j], nums[k][l])
                        var n: Int
                        var m = 0
                        while (m < nums.size) {
                            n = 0
                            while (n < nums[m].size) {
                                if (nums[m][n] in min..max) break
                                n++
                            }
                            if (n == nums[m].size) break
                            m++
                        }
                        if (m == nums.size) {
                            if (miny - minx > max - min || miny - minx == max - min && minx > min) {
                                miny = max
                                minx = min
                            }
                        }
                    }
                }
            }
        }
        return intArrayOf(minx, miny)
    }

    // In the last approach, we consider every possible range and then traverse over every list to check if at least
    // one of the elements from these lists lies in the required range. Instead of doing this traversal for every range,
    // we can make use of Binary Search to find the index of the element just larger than(or equal to) the lower limit
    // of the range currently considered.
    // If all the elements in the current list are lesser than this lower limit, we'll get the index as nums[k].length
    // for the kth list being currently checked. In this case, none of the elements of the current list lies in
    // the current range.
    // On the other hand, if all the elements in this list are larger than this lower limit, we'll get the index of
    // the first element(minimum) in the current list. If this element happens to be larger than the upper limit of
    // the range currently considered, then also, none of the elements of the current list lies within the current range.
    // Whenever a range is found which satisfies the required criteria, we can compare it with the minimum range found
    // so far to determine the required minimum range.
    // Time complexity: O(n^2 log(k)); k refers to the average length of each list
    // Space complexity: O(1)
    fun leetcode2Brute(nums: Array<IntArray>): IntArray {
        var minx = 0
        var miny = Int.MAX_VALUE
        for (i in nums.indices) {
            for (j in nums[i].indices) {
                for (k in i until nums.size) {
                    for (l in (if (k == i) j else 0) until nums[k].size) {
                        val min = Math.min(nums[i][j], nums[k][l])
                        val max = Math.max(nums[i][j], nums[k][l])
                        var n: Int
                        var m = 0
                        while (m < nums.size) {
                            n = Arrays.binarySearch(nums[m], min)
                            if (n < 0) n = -1 - n
                            if (n == nums[m].size || nums[m][n] < min || nums[m][n] > max) break
                            m++
                        }
                        if (m == nums.size) {
                            if (miny - minx > max - min || miny - minx == max - min && minx > min) {
                                miny = max
                                minx = min
                            }
                        }
                    }
                }
            }
        }
        return intArrayOf(minx, miny)
    }

    // We'll discuss about the implementation used in the current approach along with the idea behind it.
    // This approach makes use of an array of pointers, next, whose length is equal to the number of given lists.
    // In this array, next[i] refers to the element which needs to be considered next in the (i - 1)th list. The meaning
    // of this will become more clearer when we look at the process.
    // We start by initializing all the elements of next to 0. Thus, currently, we are considering the first (minimum)
    // element among all the lists. Now, we find out the index of the list containing the maximum(maxi) and minimum(mini)
    // elements from amongst the elements currently pointed by next. The range formed by these maximum and minimum
    // elements surely contains atleast one element from each list.
    // But, now our objective is to minimize this range. To do so, there are two options: Either decrease the maximum
    // value or increase the minimum value.
    // Now, the maximum value can't be reduced any further, since it already corresponds to the minimum value in one of
    // the lists. Reducing it any further will lead to the exclusion of all the elements of this list(containing the last
    // maximum value) from the new range.
    // Thus, the only option left in our hand is to try to increase the minimum value. To do so, we now need to consider
    // the next element in the list containing the last minimum value. Thus, we increment the entry at the corresponding
    // index in next, to indicate that the next element in this list now needs to be considered.
    // Thus, at every step, we find the maximum and minimum values being pointed currently, update the next values
    // appropriately, and also find out the range formed by these maximum and minimum values to find out the smallest
    // range satisfying the given criteria.
    // While doing this process, if any of the lists gets completely exhausted, it means that the minimum value being
    // increased for minimizing the range being considered can't be increased any further, without causing the exclusion
    // of all the elements in atleast one of the lists. Thus, we can stop the search process whenever any list gets
    // completely exhausted.
    // We can also stop the process, when all the elements of the given lists have been exhausted.
    // Summarizing the statements above, the process becomes:
    // 1. Initialize next array(pointers) with all 0's.
    // 2. Find the indices of the lists containing the minimum(mini) and the maximum(maxi) elements amongst the elements
    //    pointed by the next array.
    // 3. If the range formed by the maximum and minimum elements found above is larger than the previous maximum range,
    //    update the boundary values used for the maximum range.
    // 4. Increment the pointer nums[mini].
    // 5. Repeat steps 2 to 4 till any of the lists gets exhausted
    // Time complexity: O(n * m); m refers to the total number of lists
    // Space complexity: O(m)
    fun leetcodePointers(nums: Array<IntArray>): IntArray {
        var minx = 0
        var miny = Int.MAX_VALUE
        val next = IntArray(nums.size)
        var flag = true
        var i = 0
        while (i < nums.size && flag) {
            var j = 0
            while (j < nums[i].size && flag) {
                var min_i = 0
                var max_i = 0
                for (k in nums.indices) {
                    if (nums[min_i][next[min_i]] > nums[k][next[k]])
                        min_i = k
                    if (nums[max_i][next[max_i]] < nums[k][next[k]])
                        max_i = k
                }
                if (miny - minx > nums[max_i][next[max_i]] - nums[min_i][next[min_i]]) {
                    miny = nums[max_i][next[max_i]]
                    minx = nums[min_i][next[min_i]]
                }
                next[min_i]++
                if (next[min_i] == nums[min_i].size) {
                    flag = false
                }
                j++
            }
            i++
        }
        return intArrayOf(minx, miny)
    }

    // In the last approach, at each step, we update the pointer corresponding to the current minimum element and
    // traverse over the whole next array to determine the new maximum and minimum values. We can do some optimization
    // here, by making use of a simple observation.
    // Whenever we update a single entry of next to consider the new maximum and minimum values (if we already know
    // the last maximum and minimum values), all the elements to be considered for finding the maximum and minimum values
    // remain the same except the new element being pointed by a single updated entry in next. This new entry is
    // certainly larger than the last minimum value(since that was the reasoning behind the updation).
    // Thus, we can't be sure whether this is the new minimum element or not. But, since it is larger than the last value
    // being considered, it could be a potential competitor for the new maximum value. Thus, we can directly compare it
    // with the last maximum value to determine the current maximum value.
    // Now, we're left with finding the minimum value iteratively at every step. To avoid this iterative process,
    // a better idea is to make use of a Min-Heap, which stores the values being pointed currently by the next array.
    // Thus, the minimum value always lies at the top of this heap, and we need not do the iterative search process.
    // At every step, we remove the minimum element from this heap and find out the range formed by the current maximum
    // and minimum values, and compare it with the minimum range found so far to determine the required minimum range.
    // We also update the increment the index in next corresponding to the list containing this minimum entry and add
    // this element to the heap as well
    // Time complexity: O(n log(m)); m refers to the total number of lists.
    // Space complexity: O(m)
    fun leetcodePriorityQueue(nums: Array<IntArray>): IntArray {
        var minx = 0
        var miny = Int.MAX_VALUE
        var max = Int.MIN_VALUE
        val next = IntArray(nums.size)
        var flag = true
        val minQueue = PriorityQueue<Int> { i, j -> nums[i][next[i]] - nums[j][next[j]]
        }
        for (i in nums.indices) {
            minQueue.offer(i)
            max = Math.max(max, nums[i][0])
        }
        var i = 0
        while (i < nums.size && flag) {
            var j = 0
            while (j < nums[i].size && flag) {
                val min_i = minQueue.poll()
                if (miny - minx > max - nums[min_i][next[min_i]]) {
                    minx = nums[min_i][next[min_i]]
                    miny = max
                }
                next[min_i]++
                if (next[min_i] == nums[min_i].size) {
                    flag = false
                    break
                }
                minQueue.offer(min_i)
                max = Math.max(max, nums[min_i][next[min_i]])
                j++
            }
            i++
        }
        return intArrayOf(minx, miny)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val data = arrayOf(
                arrayOf(intArrayOf(4,10,15,24,26), intArrayOf(0,9,12,20), intArrayOf(5,18,22,30))
            )
            for (arrays in data) {
                val nums = mutableListOf<List<Int>>()
                for (array in arrays) {
                    nums.add(array.toList())
                }
                val range = SmallestRangeCoveringElementsFromKLists().smallestRange(nums)
                println("range: $range")
            }
        }
    }
}