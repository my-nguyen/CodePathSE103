package week3


class SortCharactersByFrequency {
    fun frequencySort(string: String): String {
        return mine(string)
    }

    /*
     Map each unique character to the number of times it appears in the string.
     Create an array where the index of the array represents how many times that
     character occurred in the String. Iterate from the end of the array to the
     beginning – at each index, append each character to the result string that
     number of times.
    */
    private fun codepath(s: String?): String? {
        if (s == null) {
            return null
        }
        val map = mutableMapOf<Char, Int>()
        val charArray = s.toCharArray()
        var max = 0
        for (c in charArray) {
            if (!map.containsKey(c)) {
                map[c] = 0
            }
            map[c] = map[c]!! + 1
            max = Math.max(max, map[c]!!)
        }
        val array = buildArray(map, max)
        return buildString(array)
    }

    private fun buildArray(map: Map<Char, Int>, maxCount: Int): Array<MutableList<Char>> {
        val array = Array(maxCount+1) { mutableListOf<Char>() }
        for (c in map.keys) {
            val count = map[c]!!
            if (array[count] == null) {
                array[count] = mutableListOf()
            }
            array[count]!!.add(c)
        }
        return array
    }

    private fun buildString(array: Array<MutableList<Char>>): String {
        val sb = StringBuilder()
        for (i in array.size - 1 downTo 1) {
            val list: List<Char?>? = array[i]
            if (list != null) {
                for (c in list) {
                    for (j in 0 until i) {
                        sb.append(c)
                    }
                }
            }
        }
        return sb.toString()
    }

    private fun mine(string: String): String {
        val map = mutableMapOf<Char, Int>()
        for (c in string) {
            map[c] = map.getOrDefault(c, 0) + 1
        }
        val sorted = map.toList().sortedByDescending { (_, value) -> value}.toMap()
        // val sorted = map.toSortedMap(compareByDescending { it })

        val result = StringBuilder()
        for ((k, v) in sorted) {
            val tmp = k.toString().repeat(v)
            result.append(tmp)
        }
        return result.toString()
    }

    /*fun hackmd(s: String?): String? {
        if (s == null) {
            return null
        }
        val map = mutableMapOf<Char, Int>()
        val charArray = s.toCharArray()
        var max = 0
        for (c in charArray) {
            if (!map.containsKey(c)) {
                map[c] = 0
            }
            map[c] = map[c]!! + 1
            max = Math.max(max, map[c]!!)
        }
        val array = buildArray(map, max)
        return buildString(array)
    }

    private fun buildArray(map: Map<Char, Int>, maxCount: Int): Array<MutableList<Char>> {
        val array = Array(maxCount + 1) { MutableList<Char>() }
        // val array: Array<MutableList<Char?>?> = arrayOfNulls<MutableList<*>?>(maxCount + 1)
        for (c in map.keys) {
            val count = map[c]!!
            if (array[count] == null) {
                // array[count] = ArrayList<Any?>()
                array[count] = mutableListOf()
            }
            array[count]!!.add(c)
        }
        return array
    }

    private fun buildString(array: Array<MutableList<Char>>): String? {
        val sb = StringBuilder()
        for (i in array.size - 1 downTo 1) {
            val list = array[i]
            if (list != null) {
                for (c in list) {
                    for (j in 0 until i) {
                        sb.append(c)
                    }
                }
            }
        }
        return sb.toString()
    }*/

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val strings = arrayOf("tree", "cccaaa", "Aabb")
            for (string in strings) {
                val sorted = SortCharactersByFrequency().frequencySort(string)
                println("string: $string, sorted: $sorted")
            }
        }
    }
}
