package week12

class ImplementTrie {
    class Trie() {

        /** Initialize your data structure here. */


        /** Inserts a word into the trie. */
        fun insert(word: String) {
        }

        /** Returns if the word is in the trie. */
        fun search(word: String): Boolean {
            return true
        }

        /** Returns if there is any word in the trie that starts with the given prefix. */
        fun startsWith(prefix: String): Boolean {
            return true
        }
    }

    /**
     * Your Trie object will be instantiated and called as such:
     * var obj = Trie()
     * obj.insert(word)
     * var param_2 = obj.search(word)
     * var param_3 = obj.startsWith(prefix)
     */

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val methods = arrayOf("Trie", "insert", "search", "search", "startsWith", "insert", "search")
            val params = arrayOf(null, "apple", "apple", "app", "app", "app", "app")
        }
    }
}