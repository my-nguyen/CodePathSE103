package week10


class PalindromePartitioning {
    fun partition(string: String): List<List<String>> {
        return solutionBacktracking(string)
    }

    private fun solutionBacktracking(string: String): List<List<String>> {
        val result = mutableListOf<List<String>>()
        dfs1(0, result, mutableListOf(), string)
        return result
    }

    private fun solutionBacktrackingDP(string: String): List<List<String>> {
        val len = string.length
        val dp = Array(len) { BooleanArray(len) }
        val result = mutableListOf<List<String>>()
        dfs(result, string, 0, ArrayList(), dp)
        return result
    }

    private fun dfs(result: MutableList<List<String>>, string: String, start: Int, currentList: MutableList<String>, dp: Array<BooleanArray>) {
        if (start >= string.length)
            result.add(ArrayList(currentList))
        for (end in start until string.length) {
            if (string[start] == string[end] && (end - start <= 2 || dp[start + 1][end - 1])) {
                dp[start][end] = true
                currentList.add(string.substring(start, end + 1))
                dfs(result, string, end + 1, currentList, dp)
                currentList.removeAt(currentList.size - 1)
            }
        }
    }

    private fun dfs1(start: Int, result: MutableList<List<String>>, current: MutableList<String>, string: String) {
        if (start >= string.length)
            result.add(ArrayList(current))
        for (end in start until string.length) {
            if (isPalindrome(string, start, end)) {
                // add current substring in the currentList
                current.add(string.substring(start, end + 1))
                dfs1(end + 1, result, current, string)
                // backtrack and remove the current substring from currentList
                current.removeAt(current.size - 1)
            }
        }
    }

    private fun isPalindrome(s: String, low: Int, high: Int): Boolean {
        var low = low
        var high = high
        while (low < high) {
            if (s[low++] != s[high--])
                return false
        }
        return true
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val strings = arrayOf("aab", "a")
            for (string in strings) {
                val partitions = PalindromePartitioning().partition(string)
                for (partition in partitions) {
                    print("$partition, ")
                }
                println()
            }
        }
    }
}

