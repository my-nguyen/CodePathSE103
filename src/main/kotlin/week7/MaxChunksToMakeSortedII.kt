package week7

class MaxChunksToMakeSortedII {
    fun maxChunksToSorted(arr: IntArray): Int {
        return codepath(arr)
        // return leetcode1(arr)
        // return mine(arr)
    }

    private fun codepath(arr: IntArray): Int {
        val arrLength = arr.size
        val runningMin = IntArray(arrLength)

        // keep track of running mins
        var currMin = Int.MAX_VALUE
        for (i in arr.indices.reversed()) {
            currMin = minOf(currMin, arr[i])
            runningMin[i] = currMin
        }

        var chunks = 1
        var currMax = 0
        for (i in 0 until arr.lastIndex) {
            currMax = maxOf(arr[i], currMax)
            // as long as running max of current element is smaller than running min of next element, we can count chunks (???)
            if (currMax <= runningMin[i + 1])
                chunks++
            println("currMax: $currMax, runningMin: ${runningMin[i+1]}, chunks: $chunks")
        }
        return chunks
    }

    private fun leetcode1(array: IntArray): Int {
        val lmin = IntArray(array.size + 1)
        var value = Int.MAX_VALUE
        lmin[array.size] = value
        // this array traverse from last element of array and check for the min element
        for (i in array.indices.reversed()) {
            value = minOf(array[i], value)
            lmin[i] = value
        }
        var lmax = array[0]
        var count = 0
        for (i in array.indices) {
            lmax = maxOf(array[i], lmax)
            if (lmax <= lmin[i + 1]) {
                count++
            }
        }
        return count
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(
                intArrayOf(0,0,1,1,1),
                /*intArrayOf(5,4,3,2,1),
                intArrayOf(2,1,3,4,4)*/
            )
            for (array in arrays) {
                print("array: ${array.contentToString()}, ")
                val chunks = MaxChunksToMakeSortedII().maxChunksToSorted(array)
                println("chunks: $chunks")
            }
        }
    }
}