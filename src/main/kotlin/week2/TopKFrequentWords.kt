package week2

import java.util.*

class TopKFrequentWords {
    fun topKFrequent(words: Array<String>, k: Int): List<String> {
        val map = mutableMapOf<String, Int>()
        val maxHeap = PriorityQueue<String> { a, b ->
            if (map[b] != map[a]) map[b]!! - map[a]!!
            else a.compareTo(b)
        }
        for (word in words) {
            val count = map.getOrDefault(word, 0)
            map[word] = count + 1
        }

        maxHeap.addAll(map.keys)
        val top = mutableListOf<String>()
        for (i in 1..k) {
            top.add(maxHeap.poll())
        }

        return top
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val strings = arrayOf(
                arrayOf("i", "love", "leetcode", "i", "love", "coding"),
                arrayOf("the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"),
                arrayOf("dog", "cat", "monkey", "dog", "cat", "monkey")
            )
            val ks = arrayOf(2, 4, 2)
            for (i in strings.indices) {
                val top = TopKFrequentWords().topKFrequent(strings[i], ks[i])
                println("words: ${strings[i].contentToString()}, k: ${ks[i]}, top: $top")
            }
        }
    }
}