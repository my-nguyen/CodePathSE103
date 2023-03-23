package week10

class Subsets {
    fun subsets(array: IntArray): List<List<Int>> {
        return listOf()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(intArrayOf(1,2,3), intArrayOf(0))
            for (array in arrays) {
                val subsets = Subsets().subsets(array)
                for (subset in subsets) {
                    print("$subset, ")
                }
                println()
            }
        }
    }
}