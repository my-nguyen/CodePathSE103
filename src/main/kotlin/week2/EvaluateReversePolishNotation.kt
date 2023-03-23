package week2

import java.util.*

class EvaluateReversePolishNotation {
    fun evalRPN(tokens: Array<String>): Int {
        val map = mutableMapOf<String, (Float, Float) -> Float>()
        map["+"] = { a, b -> a + b }
        map["-"] = { a, b -> a - b }
        map["*"] = { a, b -> a * b }
        map["/"] = { a, b -> a / b }

        val stack = Stack<Float>()
        for (token in tokens) {
            if (map.containsKey(token)) {
                // pop right before left
                val right = stack.pop()
                val left = stack.pop()
                val operator = map[token]!!
                val result = operator(left, right)
                // println("left: $left, right: $right, result: $result")
                stack.push(result)
            } else {
                stack.push(token.toFloat())
            }
        }
        return stack.pop().toInt()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val strings = arrayOf(
                arrayOf("2", "1", "+"),
                arrayOf("10", "5", "/"),
                arrayOf("2", "1", "+", "3", "*"),
                arrayOf("10", "2", "3", "+", "1", "*", "/")
            )
            for (tokens in strings) {
                val result = EvaluateReversePolishNotation().evalRPN(tokens)
                println("tokens: ${tokens.contentToString()}, result: $result")
            }
        }
    }
}