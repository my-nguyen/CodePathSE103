package week9

class Assessment9_6 {
    fun numTrees(n: Int): Int {
        // return solutionDP(n)
        return solutionMath(n)
    }

    private fun solutionDP(n: Int): Int {
        val table = IntArray(n + 1)
        table[0] = 1
        table[1] = 1
        for (i in 2..n) {
            for (j in 1..i) {
                table[i] += table[j - 1] * table[i - j]
            }
        }
        return table[n]
    }

    private fun solutionMath(n: Int): Int {
        // use long instead of int, to avoid overflow
        var C = 1L
        for (i in 0 until n) {
            C = C * 2 * (2 * i + 1) / (i + 2)
        }
        return C.toInt()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val numbers = arrayOf(3, 1)
            for (n in numbers) {
                print("n: $n, ")
                val count = Assessment9_6().numTrees(n)
                println("number of trees: $count")
            }
        }
    }
}
