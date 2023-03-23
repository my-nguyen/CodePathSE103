package week2

class Multimap {
    fun multiMap(wordCounts: Map<String, Int>): Map<Int, MutableSet<String>>? {
        val multiMap: MutableMap<Int, MutableSet<String>> = HashMap()
        for ((word, count) in wordCounts) {
            var words: MutableSet<String>? = null
            if (!multiMap.containsKey(count)) {
                words = HashSet()
                multiMap[count] = words
            } else {
                words = multiMap[count]
            }
            words!!.add(word)
        }
        return multiMap
    }
}