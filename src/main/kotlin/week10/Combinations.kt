package week10

class Combinations {
    fun combine(n: Int, k: Int): List<List<Int>> {
        return listOf()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val n = arrayOf(4, 1)
            val k = arrayOf(2, 1)
            for (i in n.indices) {
                val combinations = Combinations().combine(n[i], k[i])
                print("combinations: ")
                for (combination in combinations) {
                    print("$combination, ")
                }
                println()
            }
        }
    }
}