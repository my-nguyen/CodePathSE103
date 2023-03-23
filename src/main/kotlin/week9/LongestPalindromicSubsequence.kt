package week9

class LongestPalindromicSubsequence {
    fun longestPalindromeSubseq(string: String): Int {
        return 0
    }

    fun SaurabhPotdar(s: String): Int {
        return lcs(s, s.reversed())
    }

    fun lcs(s1: String, s2: String): Int {
        val m = s1.length
        val n = s2.length
        val dp = Array(m + 1) { IntArray(n + 1) }
        for (i in 0..m) {
            for (j in 0..n) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 0
                } else if (s1[i - 1] == s2[j - 1]) {
                    dp[i][j] = 1 + dp[i - 1][j - 1]
                } else {
                    dp[i][j] = maxOf(dp[i][j - 1], dp[i - 1][j])
                }
            }
        }
        return dp[m][n]
    }

    // If we start from both ends of the string and compare two chars and they are the same, we can increase our count
    // by 2. If they are not the same, we cannot increase our count in this iteration.. BUT we might be able to increase
    // our count later by moving our two pointers closer together.
    // There might be a character that matches our i pointer deeper in the string from the right side, so we need to
    // check that by making a recursive call decrementing our j pointer.
    // There might also be a character that matches our j pointer deeper in the string from the left side, so we need to
    // check that by making a recursive call incrementing our i pointer.
    // If our pointers ever pass each other, that means our string had an even amount of characters. Something like aaaa,
    // our pointers would pass each other at i = 2 and j = 1. We return 0 in this base case because it means there cannot
    // be a palindrome with a center character. If i and j are ever equal, that means there is a center character in
    // our possible palindrome eg. aabaa, so we can return 1 for that character.
    fun kdaahri1Brute(s: String): Int {
        return explore(0, s.lastIndex, s)
    }

    fun explore(i: Int, j: Int, s: String): Int {
        if (i > j)
            return 0
        if (i == j)
            return 1
        if (s[i] == s[j])
            return 2 + explore(i + 1, j - 1, s)

        val goRight = explore(i + 1, j, s)
        val goLeft = explore(i, j - 1, s)
        return maxOf(goRight, goLeft)
    }

    fun kdaahri2Optimized(s: String): Int {
        val n = s.length
        val dp = Array(n) { Array<Int?>(n) { 0 } }
        return explore(0, n - 1, s, dp)
    }

    fun explore(i: Int, j: Int, s: String, dp: Array<Array<Int?>>): Int {
        if (i > j)
            return 0
        if (i == j)
            return 1
        if (dp[i][j] != null)
            return dp[i][j]!!

        if (s[i] == s[j]) {
            val res = 2 + explore(i + 1, j - 1, s, dp)
            dp[i][j] = res
            return res
        }

        val res1 = explore(i + 1, j, s, dp)
        val res2 = explore(i, j - 1, s, dp)
        val max = Math.max(res1, res2)
        dp[i][j] = max
        return max
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val strings = arrayOf("bbbab", "cbbd")
            for (string in strings) {
                val sub = LongestPalindromicSubsequence().longestPalindromeSubseq(string)
                println("sub: $sub")
            }
        }
    }
}
