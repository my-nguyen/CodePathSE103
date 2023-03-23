package week7

class LengthOfLongestSubstringTwoDistinct {
    fun lengthOfLongestSubstringTwoDistinct(s: String): Int {
        return codepath(s)
    }

    private fun codepath(s: String): Int {
        var maxSubstringLength = 0
        // Start and end pointers to track the sliding window
        var startIndex = 0
        var endIndex = 0
        val charToCounts: MutableMap<Char, Int> = HashMap()
        while (endIndex < s.length) {
            val endChar = s[endIndex]
            charToCounts.putIfAbsent(endChar, 0)
            charToCounts[endChar] = charToCounts[endChar]!! + 1

            // Too many distinct characters in the window, we need to shrink the window
            while (charToCounts.size > 2) {
                // Decrement the count of the character at the start of the window
                val startChar = s[startIndex]
                charToCounts[startChar] = charToCounts[startChar]!! - 1
                if (charToCounts[startChar] == 0) {
                    charToCounts.remove(startChar)
                }
                // Decrease the sliding window by moving the start pointer
                startIndex++
            }
            maxSubstringLength = Math.max(maxSubstringLength, endIndex - startIndex + 1)
            endIndex++
        }
        return maxSubstringLength
    }
}