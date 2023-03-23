package week2

class HashTableWordCount {
    fun wordCount(s: String): HashMap<String?, Int?>? {
        var s = s
        s = s.replace("^[a-zA-Z]".toRegex(), " ").toLowerCase()
        val split = s.split(" ".toRegex()).toTypedArray()
        val map = HashMap<String?, Int?>()
        for (i in split.indices) {
            val word = split[i]
            if (word != "") {
                if (map.containsKey(word)) {
                    map[word] = map[word]!! + 1
                } else {
                    map[word] = 1
                }
            }
        }
        return map
    }
}