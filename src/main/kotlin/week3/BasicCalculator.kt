package week3

import java.util.*

class BasicCalculator {
    fun calculate(s: String): Int {
        return hackmd(s)
    }

    private fun hackmd(string: String): Int {
        // to store sign and operand of previous expressions
        val stack = Stack<Int>()
        var operand = 0
        var result = 0
        val POSITIVE = 1
        val NEGATIVE = -1
        var sign = POSITIVE

        for (c in string) {
            if (c.isDigit())
                operand = (operand * 10) + c.toString().toInt()
            else {
                when (c) {
                    '+' -> {
                        // add previous expression
                        result += sign * operand
                        sign = POSITIVE
                        operand = 0
                    }
                    '-' -> {
                        result += sign * operand
                        sign = NEGATIVE
                        operand = 0
                    }
                    '(' -> {
                        stack.push(result)
                        stack.push(sign)
                        sign = POSITIVE
                        result = 0
                    }
                    ')' -> {
                        result += sign * operand
                        // pop the sign
                        result *= stack.pop()
                        // pop the operand
                        result += stack.pop()
                        // Reset the operand
                        operand = 0
                    }
                }
            }
        }
        result += sign * operand
        return result
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val strings = arrayOf("1 + 1", " 2-1 + 2 ", "(1+(4+5+2)-3)+(6+8)", "+48 + -48")
            for (string in strings) {
                val result = BasicCalculator().calculate(string)
                println("string: $string, result: $result")
            }
        }
    }
}