package week11

class UniquePaths {
    fun uniquePaths(m: Int, n: Int): Int {
        // return mine(m, n, 0, 0)
        return mine(m, n)
    }

    private fun mine(m: Int, n: Int): Int {
        val numbers = Array(m) { IntArray(n) }
        for (j in 0 until n) {
            numbers[m-1][j] = 1
        }
        for (i in 0 until m) {
            numbers[i][n-1] = 1
        }

        for (i in m-2 downTo 0) {
            for (j in n-2 downTo 0) {
                numbers[i][j] = numbers[i+1][j] + numbers[i][j+1]
            }
        }
        return numbers[0][0]
    }

    private fun mine(m: Int, n: Int, i: Int, j: Int): Int {
        return if (i == m-1 || j == n-1)
            1
        else
            mine(m, n, i+1, j) + mine(m, n, i, j+1)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val m = arrayOf(3, 3, 7, 3)
            val n = arrayOf(7, 2, 3, 3)
            for (i in m.indices) {
                print("m: ${m[i]}, n: ${n[i]}, ")
                val path = UniquePaths().uniquePaths(m[i], n[i])
                println("unique paths: $path")
            }
        }
    }
}