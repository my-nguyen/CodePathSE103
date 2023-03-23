package week7


class LongestRepeatingCharacterReplacement {
    fun characterReplacement(string: String, add: Int): Int {
        return educative(string, add)
    }

    private fun educative(string: String, add: Int): Int {
        var i = 0
        val map = mutableMapOf<Char, Int>()
        var maxCount = 0
        var maxLength = 0
        for (j in string.indices) {
            val count = map.getOrDefault(string[j], 0)
            map[string[j]] = count + 1
            maxCount = maxOf(maxCount, count+1)
            if (j-i+1 > maxCount+add) {
                map[string[i]] = map[string[i]]!! - 1
                i++
            }
            maxLength = maxOf(maxLength, j-i+1)
        }
        return maxLength
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val strings = arrayOf("ABAB", "AABABBA")
            val adds = arrayOf(2, 1)
            for (i in strings.indices) {
                print("string: ${strings[i]}, limit: ${adds[i]}, ")
                val replaced = LongestRepeatingCharacterReplacement().characterReplacement(strings[i], adds[i])
                println("max length: $replaced")
            }
        }
    }
}