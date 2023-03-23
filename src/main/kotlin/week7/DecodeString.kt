package week7

import java.util.*


class DecodeString {
    data class Record(val count: Int, val string: StringBuilder) {
        override fun toString() = string.repeat(count)
    }

    fun decodeString(string: String): String {
        return NickWhite(string)
    }

    private fun NickWhite(string: String): String {
        val counts = Stack<Int>()
        val substrings = Stack<String>()
        var result = ""
        var i = 0
        while (i < string.length) {
            // there can only be 4 types of character: a digit, an open bracket '[', a close bracket ']', or a letter
            when {
                string[i].isDigit() -> {
                    // it's a digit: collect all digits, convert to an integer and save it
                    var count = 0
                    while (Character.isDigit(string[i])) {
                        count = 10 * count + (string[i] - '0')
                        i++
                    }
                    counts.push(count)
                }
                string[i] == '[' -> {
                    // it's an open bracket: save the previous substring before creating a new substring
                    substrings.push(result)
                    result = ""
                    i++
                }
                string[i] == ']' -> {
                    // it's a close bracket: pop a substring and multiply it by the integer preceding the popped substring
                    val substring = StringBuilder(substrings.pop())
                    val count = counts.pop()
                    for (j in 0 until count) {
                        substring.append(result)
                    }
                    result = substring.toString()
                    i++
                }
                else -> {
                    // it's a letter: keep collecting letters into a string
                    result += string[i]
                    i++
                }
            }
        }
        return result
    }

    // this doesn't work
    private fun mine(string: String): String {
        val stack = Stack<Record>()
        var i = 0
        while (i < string.length) { // 3[a2[c]]
            // check the very first character
            if (string[i].isDigit()) {
                // letter is a digit: extract the whole number, then '[', then the string, then ']',
                // then save the count and the string on the stack

                var count = 0
                // 1. extract the whole number
                while (string[i].isDigit()) { // 3
                    val digit = string[i].toString().toInt()
                    count = count * 10 + digit
                    i++
                }

                // skip the character '['
                i++

                // extract the string
                val sb = StringBuilder() // a
                while (string[i].isLowerCase()) {
                    sb.append(string[i])
                    i++
                }

                // save the count and the string on the stack
                // println("pushing $count $sb, i: $i")
                stack.push(Record(count, sb))
            } else if (string[i].isLowerCase()) {
                // letter is a character: collect the string and save it on the stack
                val sb = StringBuilder()
                while (i < string.length && string[i].isLowerCase()) {
                    sb.append(string[i])
                    i++
                }
                stack.push(Record(1, sb))
            } else if (string[i] == ']') { // 3[a2[c]]
                i++
                val tmp = stack.pop().toString()
                if (stack.isNotEmpty()) {
                    stack.peek().string.append(tmp)
                } else {
                    stack.push(Record(1, StringBuilder(tmp)))
                }
            }
        }

        val result = StringBuilder()
        while (stack.isNotEmpty()) {
            result.insert(0, stack.pop().toString())
        }
        return result.toString()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val strings = arrayOf("3[z]2[2[y]pq4[2[jk]e1[f]]]ef" /*"2[ab2[cd2[ef2[gh]i]j]k]", "3[a]2[bc]", "3[a2[c]]", "2[abc]3[cd]ef", "abc3[cd]xyz"*/)
            for (string in strings) {
                val decoded = DecodeString().decodeString(string)
                println("string: $string, decoded: $decoded")
            }
        }
    }
}