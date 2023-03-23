package week9

import java.util.*


class LongestValidParentheses {
    fun longestValidParentheses(s: String): Int {
        return solution1Brute(s)
    }

    private fun solution1Brute(string: String): Int {
        var maxlen = 0
        for (i in string.indices) {
            // check in pairs: "()" is valid; "((" is not but "(())" is valid
            for (j in i+2..string.length step 2) {
                if (isValid(string.substring(i, j))) {
                    maxlen = maxOf(maxlen, j - i)
                }
            }
        }
        return maxlen
    }

    private fun isValid(string: String): Boolean {
        val stack = Stack<Char>()
        for (c in string) {
            // c can only be '(' or ')'
            if (c == '(') {
                // if it's a ‘(’, push it onto the stack
                stack.push('(')
            } else {
                // otherwise, it's a ‘)’
                if (stack.isNotEmpty() && stack.peek() == '(') {
                    // if the stack top is a '(', then pop it
                    stack.pop()
                } else {
                    // otherwise string is invalid
                    return false
                }
            }
        }
        return stack.empty()
    }

    private fun solution2DP(s: String): Int {
        var maxans = 0
        val dp = IntArray(s.length)
        for (i in 1 until s.length) {
            if (s[i] == ')') {
                if (s[i - 1] == '(') {
                    val tmp = if (i >= 2) dp[i - 2] else 0
                    dp[i] = tmp + 2
                } else if (i - dp[i - 1] > 0 && s[i - dp[i - 1] - 1] == '(') {
                    val tmp = if (i - dp[i - 1] >= 2) dp[i - dp[i - 1] - 2] else 0
                    dp[i] = dp[i - 1] + tmp + 2
                }
                maxans = maxOf(maxans, dp[i])
            }
        }
        return maxans
    }

    private fun solution3Stack(s: String): Int {
        var maxans = 0
        val stack = Stack<Int>()
        stack.push(-1)
        for (i in s.indices) {
            if (s[i] == '(') {
                stack.push(i)
            } else {
                stack.pop()
                if (stack.empty()) {
                    stack.push(i)
                } else {
                    maxans = maxOf(maxans, i - stack.peek())
                }
            }
        }
        return maxans
    }

    private fun solution4NoSpace(s: String): Int {
        var left = 0
        var right = 0
        var maxlength = 0
        for (element in s) {
            if (element == '(') {
                left++
            } else {
                right++
            }
            if (left == right) {
                maxlength = maxOf(maxlength, 2 * right)
            } else if (right >= left) {
                right = 0
                left = right
            }
        }
        right = 0
        left = right
        for (i in s.length - 1 downTo 0) {
            if (s[i] == '(') {
                left++
            } else {
                right++
            }
            if (left == right) {
                maxlength = maxOf(maxlength, 2 * left)
            } else if (left >= right) {
                right = 0
                left = right
            }
        }
        return maxlength
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val strings = arrayOf("(()", ")()())", "")
            for (string in strings) {
                print("string: $string, ")
                val length = LongestValidParentheses().longestValidParentheses(string)
                println("longest valid parentheses: $length")
            }
        }
    }
}