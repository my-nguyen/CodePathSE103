package week9

class LongestPalindromicSubstring {
    fun longestPalindrome(s: String): String {
        return codepath(s)
    }

    private fun solutionBruteForce(string: String): String {
        if (string.isEmpty())
            return ""

        var longestPalindrome = ""
        for (i in string.indices) {
            for (j in i until string.length) {
                // check if substring from i to j is a palindrome
                val substring = string.substring(i, j + 1)
                if (isPalindrome(substring) && substring.length > longestPalindrome.length)
                    longestPalindrome = substring
            }
        }
        return longestPalindrome
    }

    private fun isPalindrome(s: String): Boolean {
        return s == s.reversed()
    }

    private fun NickWhite(string: String?): String {
        if (string.isNullOrEmpty())
            return ""

        var start = 0
        var end = 0
        for (i in string.indices) {
            // expand from one character where 'i' is, e.g. the 'e' in "racecar"
            val len1 = expandFromMiddle(string, i, i)
            // expand from 2 adjacent characters, e.g. the "bb" in "abba"
            val len2 = expandFromMiddle(string, i, i + 1)
            val len = maxOf(len1, len2)
            // found a longer substring
            if (len > end - start) {
                // i is the center of the newfound palindrome, so start is halfway back and end is halfway forward
                // from the center
                start = i - (len - 1) / 2
                end = i + len / 2
            }
        }
        return string.substring(start, end + 1)
    }

    private fun expandFromMiddle(string: String?, left: Int, right: Int): Int {
        var left = left
        var right = right
        return if (string == null || left > right) {
            0
        } else {
            while (left >= 0 && right < string.length && string[left] == string[right]) {
                left--
                right++
            }
            right - left - 1
        }
    }

    private fun codepath(string: String): String {
        if (string.isNullOrEmpty() || string.length == 1)
            return string

        val dp = Array(string.length) { BooleanArray(string.length) { true } }
        var longest = 0..0

        for (k in 1..string.lastIndex) {
            for (i in 0 until string.length-k) {
                val j = i + k
                val isPalindrome = if (k == 1)
                    string[i] == string[j]
                else
                    // leftmost and rightmost characters are the same, and the characters in between are the same.
                    string[i] == string[j] && dp[i+1][j-1]
                if (isPalindrome)
                    longest = i..j
                dp[i][j] = isPalindrome
            }
        }

        return string.substring(longest)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val strings = arrayOf("babad", "cbbd", "a", "ac")
            for (string in strings) {
                print("string: $string, ")
                val palindrome = LongestPalindromicSubstring().longestPalindrome(string)
                println("palindrome: $palindrome")
            }
        }
    }
}
