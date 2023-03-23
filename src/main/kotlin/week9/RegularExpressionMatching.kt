package week9

class RegularExpressionMatching {
    fun isMatch(s: String, p: String): Boolean {
        return solutionRecursion(s, p)
    }

    private fun solutionRecursion(text: String, pattern: String): Boolean {
        return if (pattern.isEmpty())
            text.isEmpty()
        else {
            // whether the first character in pattern is a match (either an exact match or a period '.')
            val firstMatch = text.isNotEmpty() && (pattern[0] == text[0] || pattern[0] == '.')
            if (pattern.length >= 2 && pattern[1] == '*') {
                // if the 2nd character is an asterisk '*', either remove the asterisk from consideration while checking
                // pattern against text, or keep the pattern intact and check against text starting from 2nd character
                solutionRecursion(text, pattern.substring(2)) || (firstMatch && solutionRecursion(text.substring(1), pattern))
            } else {
                // without an asterisk '*', keep checking for match for the remainder of text and pattern
                firstMatch && solutionRecursion(text.substring(1), pattern.substring(1))
            }
        }
    }

    lateinit var memo: Array<Array<Boolean?>>
    private fun solutionTopDown(text: String, pattern: String): Boolean {
        memo = Array(text.length + 1) { arrayOfNulls(pattern.length + 1) }
        return dp(0, 0, text, pattern)
    }

    fun dp(i: Int, j: Int, text: String, pattern: String): Boolean {
        if (memo[i][j] != null)
            return memo[i][j] == true

        val ans = if (j == pattern.length) {
            i == text.length
        } else {
            val firstMatch = i < text.length && (pattern[j] == text[i] || pattern[j] == '.')
            if (j + 1 < pattern.length && pattern[j + 1] == '*') {
                dp(i, j + 2, text, pattern) || firstMatch && dp(i + 1, j, text, pattern)
            } else {
                firstMatch && dp(i + 1, j + 1, text, pattern)
            }
        }
        memo[i][j] = ans
        return ans
    }

    private fun solutionBottomUp(text: String, pattern: String): Boolean {
        val dp = Array(text.length + 1) { BooleanArray(pattern.length + 1) }
        dp[text.length][pattern.length] = true
        for (i in text.length downTo 0) {
            for (j in pattern.length - 1 downTo 0) {
                val firstMatch = i < text.length && (pattern[j] == text[i] || pattern[j] == '.')
                if (j + 1 < pattern.length && pattern[j + 1] == '*') {
                    dp[i][j] = dp[i][j + 2] || firstMatch && dp[i + 1][j]
                } else {
                    dp[i][j] = firstMatch && dp[i + 1][j + 1]
                }
            }
        }
        return dp[0][0]
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val strings = arrayOf("aa", "aa", "ab", "aab", "mississippi")
            val patterns = arrayOf("a", "a*", ".*", "c*a*b", "mis*is*p*.")
            for (i in strings.indices) {
                print("string: ${strings[i]}, pattern: ${patterns[i]}, ")
                val matched = RegularExpressionMatching().isMatch(strings[i], patterns[i])
                println("matched? $matched")
            }
        }
    }
}