package week7

class GroupAnagrams {
    fun groupAnagrams(strings: Array<String>): List<List<String>> {
        // return mine1(strings)
        return mine2(strings)
    }

    // change the map's key type from IntArray to String for faster lookup. result: faster than 91%
    private fun mine2(strings: Array<String>): List<List<String>> {
        val map = mutableMapOf<String, MutableList<String>>()
        for (string in strings) {
            val anagram = makeAnagram2(string)
            if (map.containsKey(anagram)) {
                map[anagram]!!.add(string)
            } else {
                val list = mutableListOf<String>()
                list.add(string)
                map[anagram] = list
            }
        }
        return map.values.toList()
    }

    private fun makeAnagram2(string: String): String {
        // collect count of each character in string
        val counts = IntArray(26)
        for (c in string) {
            val index = c - 'a'
            counts[index]++
        }

        // convert the counts array into a String
        val sb = StringBuilder()
        for (i in counts.indices) {
            if (counts[i] != 0) {
                sb.append('a' + i)
                sb.append(counts[i])
            }
        }
        return sb.toString()
    }

    // result: faster than 6%
    private fun mine1(strings: Array<String>): List<List<String>> {
        val map = mutableMapOf<IntArray, MutableList<String>>()
        for (string in strings) {
            val anagram = makeAnagram1(string)
            var found = false
            for (key in map.keys) {
                if (key contentEquals anagram) {
                    found = true
                    map[key]!!.add(string)
                    break
                }
            }
            if (!found) {
                val list = mutableListOf<String>()
                list.add(string)
                map[anagram] = list
            }
        }
        return map.values.toList()
    }

    private fun makeAnagram1(string: String): IntArray {
        val anagram = IntArray(26)
        for (c in string) {
            val index = c - 'a'
            anagram[index]++
        }
        return anagram
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val data = arrayOf(
                arrayOf("eat","tea","tan","ate","nat","bat"),
                arrayOf(""),
                arrayOf("a")
            )
            for (strings in data) {
                val anagrams = GroupAnagrams().groupAnagrams(strings)
                for (anagram in anagrams) {
                    print("{ $anagram }, ")
                }
                println()
            }
        }
    }
}