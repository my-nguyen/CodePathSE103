package week8

class ContainsDuplicate {
    fun containsDuplicate(array: IntArray): Boolean {
        val set = mutableSetOf<Int>()
        for (number in array) {
            if (set.contains(number))
                return true
            else
                set.add(number)
        }
        return false
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(intArrayOf(1,2,3,1), intArrayOf(1,2,3,4), intArrayOf(1,1,1,3,3,4,3,2,4,2))
            for (array in arrays) {
                val duplicate = ContainsDuplicate().containsDuplicate(array)
                println("contains duplicate? $duplicate")
            }
        }
    }
}