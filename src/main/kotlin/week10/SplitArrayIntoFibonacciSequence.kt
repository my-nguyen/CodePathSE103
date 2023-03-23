package week10


class SplitArrayIntoFibonacciSequence {
    fun splitIntoFibonacci(string: String): List<Int> {
        return solutionBruteForce(string)
    }

    fun solutionBruteForce(string: String): List<Int> {
        val N = string.length
        for (i in 0 until minOf(10, N)) {
            if (string[0] == '0' && i > 0)
                break
            val a = java.lang.Long.valueOf(string.substring(0, i + 1))
            if (a >= Int.MAX_VALUE)
                break

            search@ for (j in i + 1 until minOf(i + 10, N)) {
                if (string[i + 1] == '0' && j > i + 1)
                    break
                val b = string.substring(i + 1, j + 1).toLong()
                if (b >= Int.MAX_VALUE)
                    break
                val fib = mutableListOf<Int>()
                fib.add(a.toInt())
                fib.add(b.toInt())
                var k = j + 1
                while (k < N) {
                    val nxt = (fib[fib.size - 2]!! + fib[fib.size - 1]!!).toLong()
                    val nxtS = nxt.toString()
                    if (nxt <= Int.MAX_VALUE && string.substring(k).startsWith(nxtS)) {
                        k += nxtS.length
                        fib.add(nxt.toInt())
                    } else
                        continue@search
                }
                if (fib.size >= 3)
                    return fib
            }
        }
        return ArrayList()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val strings = arrayOf("123456579", "11235813", "112358130", "0123", "1101111")
            for (string in strings) {
                print("string: $string")
                val fibonacci = SplitArrayIntoFibonacciSequence().splitIntoFibonacci(string)
                println("fibonacci: $fibonacci")
            }
        }
    }
}
