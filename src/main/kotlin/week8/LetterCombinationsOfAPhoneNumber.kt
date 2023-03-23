package week8

import java.util.*

class LetterCombinationsOfAPhoneNumber {
    fun letterCombinations(digits: String): List<String> {
        if (digits == "")
            return listOf()

        val map = mutableMapOf<Char, String>()
        val buttons = arrayOf("abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz")
        for (i in '2'..'9') {
            map[i] = buttons[i-'2']
        }
        val queue = LinkedList<String>()
        queue.push("")
        for (digit in digits) {
            val size = queue.size
            for (i in 1..size) {
                val top = queue.poll()
                // println("size: $size, top: $top")
                for (letter in map[digit]!!) {
                    val string = "$top$letter"
                    // println("pushing $string")
                    queue.add(string)
                }
            }
        }
        return queue.toList()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val strings = arrayOf("23", "", "2")
            for (digits in strings) {
                // print("digits: $digits, ")
                val letters = LetterCombinationsOfAPhoneNumber().letterCombinations(digits)
                println("letter combinations: $letters")
            }
        }
    }
}