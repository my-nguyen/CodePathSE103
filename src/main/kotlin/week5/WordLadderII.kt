package week5

import java.util.*


class WordLadderII {
    fun findLadders(beginWord: String, endWord: String, wordList: List<String>): List<List<String>> {
        return solution1(beginWord, endWord, wordList)
        // return solution2(beginWord, endWord, wordList)
    }

    private var adjList = mutableMapOf<String, MutableList<String>>()
    private var currPath = mutableListOf<String>()
    private var shortestPaths = mutableListOf<List<String>>()

    private fun solution1(beginWord: String, endWord: String, wordList: List<String>): List<List<String>> {
        // copying the words into the set for efficient deletion in BFS
        val copiedWordList = HashSet(wordList)
        // build the DAG using BFS
        bfs1(beginWord, endWord, copiedWordList)

        // every path will start from the beginWord
        currPath.add(beginWord)
        // traverse the DAG to find all the paths between beginWord and endWord
        backtrack1(beginWord, endWord)
        return shortestPaths
    }

    private fun findNeighbors1(word: String, wordList: Set<String>): List<String> {
        val neighbors = mutableListOf<String>()
        val charList = word.toCharArray()
        for (i in word.indices) {
            val oldChar = charList[i]

            // replace the i-th character with all letters from a to z except the original character
            var c = 'a'
            while (c <= 'z') {
                charList[i] = c

                // skip if the character is same as original or if the word is not present in the wordList
                if (c == oldChar || !wordList.contains(String(charList))) {
                    c++
                    continue
                }
                neighbors.add(String(charList))
                c++
            }
            charList[i] = oldChar
        }
        return neighbors
    }

    private fun backtrack1(source: String, destination: String) {
        // store the path if we reached the endWord
        if (source == destination) {
            val tempPath = currPath.toList()
            shortestPaths.add(tempPath)
        }
        if (!adjList.containsKey(source)) {
            return
        }

        for (i in adjList[source]!!.indices) {
            currPath.add(adjList[source]!![i])
            backtrack1(adjList[source]!![i], destination)
            currPath.removeAt(currPath.size - 1)
        }
    }

    private fun bfs1(beginWord: String, endWord: String, wordList: MutableSet<String>) {
        val q = LinkedList<String>()
        q.add(beginWord)

        // remove the root word which is the first layer in the BFS
        if (wordList.contains(beginWord)) {
            wordList.remove(beginWord)
        }
        val isEnqueued = mutableMapOf<String, Int>()
        isEnqueued[beginWord] = 1
        while (q.size > 0) {
            // visited will store the words of current layer
            val visited = mutableListOf<String>()
            for (i in q.indices.reversed()) {
                val currWord = q.peek()
                q.remove()

                // findNeighbors will have the adjacent words of the currWord
                val neighbors = findNeighbors1(currWord, wordList)
                for (word in neighbors) {
                    visited.add(word)
                    if (!adjList.containsKey(currWord)) {
                        adjList[currWord] = ArrayList()
                    }

                    // add the edge from currWord to word in the list
                    adjList[currWord]!!.add(word)
                    if (!isEnqueued.containsKey(word)) {
                        q.add(word)
                        isEnqueued[word] = 1
                    }
                }
            }
            // removing the words of the previous layer
            for (i in visited.indices) {
                if (wordList.contains(visited[i])) {
                    wordList.remove(visited[i])
                }
            }
        }
    }

    private fun solution2(beginWord: String, endWord: String, wordList: List<String>?): List<List<String>> {
        // copying the words into the set for efficient deletion in BFS
        val copiedWordList = HashSet(wordList)
        // build the DAG using BFS
        val sequenceFound = bfs2(beginWord, endWord, copiedWordList)

        // There is no valid sequence that connects `beginWord` to `endWord`
        if (!sequenceFound) {
            return shortestPaths
        }
        // every path will start from the beginWord
        currPath.add(beginWord)
        // traverse the DAG to find all the paths between beginWord and endWord
        backtrack2(beginWord, endWord)
        return shortestPaths
    }

    private fun findNeighbors2(word: String, wordList: Set<String>): List<String> {
        val neighbors = mutableListOf<String>()
        val charList = word.toCharArray()
        for (i in word.indices) {
            val oldChar = charList[i]

            // replace the i-th character with all letters from a to z except the original character
            var c = 'a'
            while (c <= 'z') {
                charList[i] = c

                // skip if the character is same as original or if the word is not present in the wordList
                if (c == oldChar || !wordList.contains(String(charList))) {
                    c++
                    continue
                }
                neighbors.add(String(charList))
                c++
            }
            charList[i] = oldChar
        }
        return neighbors
    }

    private fun backtrack2(source: String, destination: String) {
        // store the path if we reached the endWord
        if (source == destination) {
            val tempPath = currPath.toList()
            // val tempPath: List<String> = ArrayList(currPath)
            shortestPaths.add(tempPath)
        }
        if (!adjList.containsKey(source)) {
            return
        }

        for (i in adjList[source]!!.indices) {
            currPath.add(adjList[source]!![i])
            backtrack2(adjList[source]!![i], destination)
            currPath.removeAt(currPath.size - 1)
        }
    }

    private fun addEdge(word1: String, word2: String, direction: Int) {
        if (direction == 1) {
            if (!adjList.containsKey(word1)) {
                adjList[word1] = ArrayList()
            }
            adjList[word1]!!.add(word2)
        } else {
            if (!adjList.containsKey(word2)) {
                adjList[word2] = ArrayList()
            }
            adjList[word2]!!.add(word1)
        }
    }

    private fun bfs2(beginWord: String, endWord: String, wordList: MutableSet<String>): Boolean {
        if (!wordList.contains(endWord)) {
            return false
        }

        // remove the root word which is the first layer
        if (wordList.contains(beginWord)) {
            wordList.remove(beginWord)
        }
        var forwardQueue = mutableSetOf<String>()
        var backwardQueue = mutableSetOf<String>()
        forwardQueue.add(beginWord)
        backwardQueue.add(endWord)
        var found = false
        var direction = 1
        while (forwardQueue.isNotEmpty()) {
            // visited will store the words of current layer
            val visited = mutableSetOf<String>()

            // swap the queues because we are always extending the forwardQueue
            if (forwardQueue.size > backwardQueue.size) {
                val temp = forwardQueue
                forwardQueue = backwardQueue
                backwardQueue = temp
                direction = direction xor 1
            }
            for (currWord in forwardQueue) {
                val neighbors = findNeighbors2(currWord, wordList)
                for (word in neighbors) {

                    // if the backwardQueue already contains it we can stop after completing this level
                    if (backwardQueue.contains(word)) {
                        found = true
                        addEdge(currWord, word, direction)
                    } else if (!found && wordList.contains(word) && !forwardQueue.contains(word)) {
                        visited.add(word)
                        addEdge(currWord, word, direction)
                    }
                }
            }

            // removing the words of the previous layer
            for (currWord in forwardQueue) {
                if (wordList.contains(currWord)) {
                    wordList.remove(currWord)
                }
            }
            if (found) {
                break
            }
            forwardQueue = visited
        }
        return found
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val begins = arrayOf("hit", "hit")
            val ends = arrayOf("cog", "cog")
            val words = arrayOf(
                listOf("hot","dot","dog","lot","log","cog"),
                listOf("hot","dot","dog","lot","log")
            )
            for (i in begins.indices) {
                val ladders = WordLadderII().findLadders(begins[i], ends[i], words[i])
                print("ladders: ")
                for (ladder in ladders) {
                    print("${ladder}, ")
                }
                println()
            }
        }
    }
}