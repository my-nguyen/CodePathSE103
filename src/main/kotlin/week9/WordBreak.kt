package week9

class WordBreak {
    fun wordBreak(string: String, wordDict: List<String>): Boolean {
        // return tabulized(string, wordDict)
        return memoized(string, wordDict)
        // return bruteForce(string, wordDict)
    }

    private fun bruteForce(string: String, wordDict: List<String>): Boolean {
        if (string == "")
            return true
        for (i in string.indices) {
            for (word in wordDict) {
                if (string.startsWith(word)) {
                    if (bruteForce(string.removePrefix(word), wordDict))
                        return true
                }
            }
        }
        return false
    }

    private fun memoized(string: String, wordDict: List<String>, memo: MutableMap<String, Boolean> = mutableMapOf()): Boolean {
        if (string == "")
            return true
        if (memo.containsKey(string))
            return memo[string]!!

        for (i in string.indices) {
            for (word in wordDict) {
                if (string.startsWith(word)) {
                    if (memoized(string.removePrefix(word), wordDict, memo)) {
                        memo[string] = true
                        return true
                    }
                }
            }
        }
        memo[string] = false
        return false
    }

    private fun tabulized(string: String, wordDict: List<String>): Boolean {
        val set = wordDict.toSet()
        val table = BooleanArray(string.length + 1)
        // set the first index to true, meaning there's a valid string up until but not including this index
        table[0] = true
        // iterate the whole string
        for (i in string.indices) {
            if (table[i]) {
                for (j in i+1..string.length) {
                    // check if any substring from i to j is in the word list
                    if (set.contains(string.substring(i, j)))
                        // mark dp
                        table[j] = true
                }
            }
        }
        return table.last()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val strings = arrayOf("catsandog", /*"cars", "leetcode", "applepenapple", "catsandog"*/)
            val dictionaries = arrayOf(
                listOf("cats","dog","sand","and","cat"), listOf("car","ca","rs"), listOf("leet","code"), listOf("apple","pen"), listOf("cats","dog","sand","and","cat")
            )
            for (i in strings.indices) {
                print("string: ${strings[i]}, dictionary: ${dictionaries[i]}, ")
                val word = WordBreak().wordBreak(strings[i], dictionaries[i])
                println("word break? $word")
            }
        }
    }
}