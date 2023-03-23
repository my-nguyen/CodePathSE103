package week1

class Assessment1_9 {
    class LRUCache(private val capacity: Int) {
        private val map = LinkedHashMap<Int, Int>()

        fun get(key: Int): Int {
            return if (map.containsKey(key)) {
                val value = map[key]!!
                map.remove(key)
                map[key] = value
                value
            } else {
                -1
            }
        }

        fun put(key: Int, value: Int) {
            if (map.containsKey(key)) {
                map.remove(key)
            } else {
                if (map.size >= capacity) {
                    val k = map.keys.iterator().next()
                    map.remove(k)
                }
            }
            map[key] = value
        }
    }
}