package week10

class Permutations {
    fun permute(array: IntArray): List<List<Int>> {
        return listOf()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(intArrayOf(1,2,3), intArrayOf(0,1), intArrayOf(1))
            for (array in arrays) {
                val permutations = Permutations().permute(array)
                print("permutations: ")
                for (permutation in permutations) {
                    print("$permutation")
                }
                println()
            }
        }
    }
}