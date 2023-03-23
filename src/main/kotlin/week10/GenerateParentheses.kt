package week10


class GenerateParentheses {
    fun generateParenthesis(count: Int): List<String> {
        return listOf()
    }

    fun solutionBruteForce(n: Int): List<String> {
        val combinations = mutableListOf<String>()
        generateAll(CharArray(2 * n), 0, combinations)
        return combinations
    }

    fun generateAll(current: CharArray, pos: Int, result: MutableList<String>) {
        if (pos == current.size) {
            if (valid(current))
                result.add(String(current))
        } else {
            current[pos] = '('
            generateAll(current, pos + 1, result)
            current[pos] = ')'
            generateAll(current, pos + 1, result)
        }
    }

    fun valid(current: CharArray): Boolean {
        var balance = 0
        for (c in current) {
            if (c == '(')
                balance++
            else
                balance--
            if (balance < 0)
                return false
        }
        return balance == 0
    }

    fun solutionBacktracking(n: Int): List<String?> {
        val ans = mutableListOf<String>()
        backtrack(ans, StringBuilder(), 0, 0, n)
        return ans
    }

    fun backtrack(ans: MutableList<String>, cur: StringBuilder, open: Int, close: Int, max: Int) {
        if (cur.length == max * 2) {
            ans.add(cur.toString())
            return
        }
        if (open < max) {
            cur.append("(")
            backtrack(ans, cur, open + 1, close, max)
            cur.deleteCharAt(cur.length - 1)
        }
        if (close < open) {
            cur.append(")")
            backtrack(ans, cur, open, close + 1, max)
            cur.deleteCharAt(cur.length - 1)
        }
    }

    fun solutionClosureNumber(n: Int): List<String> {
        val ans = mutableListOf<String>()
        if (n == 0) {
            ans.add("")
        } else {
            for (c in 0 until n) {
                for (left in solutionClosureNumber(c)) {
                    for (right in solutionClosureNumber(n - 1 - c))
                        ans.add("($left)$right")
                }
            }
        }
        return ans
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val counts = arrayOf(3, 1)
            for (count in counts) {
                val parentheses = GenerateParentheses().generateParenthesis(count)
                println("parentheses: $parentheses")
            }
        }
    }
}

