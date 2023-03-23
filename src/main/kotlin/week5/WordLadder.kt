package week5

import java.util.*
import java.util.function.Consumer

class WordLadder {
    fun ladderLength(beginWord: String, endWord: String, wordList: List<String>): Int {
        // return solution1(beginWord, endWord, wordList)
        return solution2(beginWord, endWord, wordList)
    }

    private fun solution1(beginWord: String, endWord: String, wordList: List<String>): Int {
        // Since all words are of same length.
        val L = beginWord.length

        // Dictionary to hold combination of words that can be formed, from any given word. By changing one letter at a time.
        val allComboDict: MutableMap<String, MutableList<String>> = HashMap()
        wordList.forEach(
            Consumer { word: String ->
                for (i in 0 until L) {
                    // Key is the generic word; Value is a list of words which have the same intermediate generic word.
                    val newWord = word.substring(0, i) + '*' + word.substring(i + 1, L)
                    val transformations = allComboDict.getOrDefault(newWord, ArrayList())
                    transformations.add(word)
                    allComboDict[newWord] = transformations
                }
            })

        // Queue for BFS
        val Q = LinkedList<Pair<String, Int>>()
        Q.add(Pair(beginWord, 1))

        // Visited to make sure we don't repeat processing same word.
        val visited: MutableMap<String, Boolean> = HashMap()
        visited[beginWord] = true
        while (Q.isNotEmpty()) {
            val node = Q.remove()
            val word = node.first
            val level = node.second
            for (i in 0 until L) {
                // Intermediate words for current word
                val newWord = word.substring(0, i) + '*' + word.substring(i + 1, L)

                // Next states are all the words which share the same intermediate state.
                for (adjacentWord in allComboDict.getOrDefault(newWord, ArrayList())) {
                    // If at any point if we find what we are looking for i.e. the end word - we can return with the answer.
                    if (adjacentWord == endWord) {
                        return level + 1
                    }
                    // Otherwise, add it to the BFS Queue. Also mark it visited
                    if (!visited.containsKey(adjacentWord)) {
                        visited[adjacentWord] = true
                        Q.add(Pair(adjacentWord, level + 1))
                    }
                }
            }
        }
        return 0
    }

    private var L = 0
    // Dictionary to hold combination of words that can be formed, from any given word. By changing one letter at a time.
    private val allComboDict = mutableMapOf<String, MutableList<String>>()

    private fun visitWordNode(Q: Queue<Pair<String, Int>>, visited: MutableMap<String, Int>, othersVisited: Map<String, Int>): Int {
        val node = Q.remove()
        val word = node.first
        val level = node.second
        for (i in 0 until L) {
            // Intermediate words for current word
            val newWord = word.substring(0, i) + '*' + word.substring(i + 1, L)

            // Next states are all the words which share the same intermediate state.
            for (adjacentWord in allComboDict.getOrDefault(newWord, ArrayList())) {
                // If at any point if we find what we are looking for i.e. the end word - we can return with the answer.
                if (othersVisited.containsKey(adjacentWord)) {
                    return level + othersVisited[adjacentWord]!!
                }
                if (!visited.containsKey(adjacentWord)) {
                    // Save the level as the value of the dictionary, to save number of hops.
                    visited[adjacentWord] = level + 1
                    Q.add(Pair(adjacentWord, level + 1))
                }
            }
        }
        return -1
    }

    private fun solution2(beginWord: String, endWord: String, wordList: List<String>): Int {
        if (!wordList.contains(endWord)) {
            return 0
        }

        // Since all words are of same length.
        L = beginWord.length
        wordList.forEach(
            Consumer { word: String ->
                for (i in 0 until L) {
                    // Key is the generic word; Value is a list of words which have the same intermediate generic word.
                    val newWord = word.substring(0, i) + '*' + word.substring(i + 1, L)
                    val transformations = allComboDict.getOrDefault(newWord, ArrayList())
                    transformations.add(word)
                    allComboDict[newWord] = transformations
                }
            })

        // Queues for birdirectional BFS; BFS starting from beginWord
        val Q_begin = LinkedList<Pair<String, Int>>()
        // BFS starting from endWord
        val Q_end = LinkedList<Pair<String, Int>>()
        Q_begin.add(Pair(beginWord, 1))
        Q_end.add(Pair(endWord, 1))

        // Visited to make sure we don't repeat processing same word.
        val visitedBegin = mutableMapOf<String, Int>()
        val visitedEnd = mutableMapOf<String, Int>()
        visitedBegin[beginWord] = 1
        visitedEnd[endWord] = 1
        while (!Q_begin.isEmpty() && !Q_end.isEmpty()) {
            // One hop from begin word
            var ans = visitWordNode(Q_begin, visitedBegin, visitedEnd)
            if (ans > -1) {
                return ans
            }

            // One hop from end word
            ans = visitWordNode(Q_end, visitedEnd, visitedBegin)
            if (ans > -1) {
                return ans
            }
        }
        return 0
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val beginWords = arrayOf("hit", "hit")
            val endWords = arrayOf("cog", "cog")
            val wordLists = arrayOf(
                listOf("hot","dot","dog","lot","log","cog"),
                listOf("hot","dot","dog","lot","log")
            )
            for (i in beginWords.indices) {
                val length = WordLadder().ladderLength(beginWords[i], endWords[i], wordLists[i])
                println(length)
            }
        }
    }
}