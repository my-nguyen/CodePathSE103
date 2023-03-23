package week9

class Fibonacci {
    // private val fib = Fib()
    // private val fib = FibMemo()
    // private val fib = FibBottomUp()
    private val fib = FibNoCache()
    fun results(n: Int): String {
        return "The ${n}th Fibonacci number is ${fib.f(n)} and requires ${fib.work} computations"
    }

    class Fib {
        var work = 0
        fun f(n: Int): Int {
            work++
            return if (n < 2)
                n
            else
                f(n-1) + f(n-2)
        }
    }

    class FibMemo {
        private val cache = mutableListOf<Int>()
        var work = 2
        fun f(n: Int): Int {
            return if (n < cache.size)
                cache[n]
            else {
                work++
                if (n < 2)
                    n
                else {
                    val sum = f(n - 1) + f(n - 2)
                    cache.add(sum)
                    // println("added $sum, cache size: ${cache.size}")
                    cache[n]
                }
            }
        }

        init {
            cache.add(0)
            cache.add(1)
        }
    }

    class FibBottomUp {
        private val cache = mutableListOf<Int>()
        var work = 1
        fun f(n: Int): Int {
            for (i in 2..n+1) {
                work++
                val sum = cache[i-1] + cache[i-2]
                cache.add(sum)
            }
            return cache[n]
        }

        init {
            cache.add(0)
            cache.add(1)
        }
    }

    class FibNoCache {
        var work = 0
        fun f(n: Int): Int {
            return if (n < 2)
                n
            else {
                work = 2
                var previous = 0
                var next = 1
                for (i in 2..n) {
                    work++
                    val sum = next + previous
                    previous = next
                    next = sum
                }
                next
            }
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            println(Fibonacci().results(6))
        }
    }
}
