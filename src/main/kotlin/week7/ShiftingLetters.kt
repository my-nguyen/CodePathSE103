package week7

class ShiftingLetters {
    fun shiftingLetters(string: String, shifts: IntArray): String {
        // use LongArray for sums to account for integer overflow in summing up large int values
        val sums = LongArray(shifts.size)
        sums[sums.lastIndex] = shifts.last().toLong() // sums[0,0,9]
        for (i in shifts.lastIndex-1 downTo 0) { // index 1 - 0
            sums[i] = shifts[i] + sums[i+1] // [18,14,9]
        }

        val array = CharArray(string.length)
        for (i in array.indices) {
            array[i] = 'a' + ((string[i]-'a' + sums[i]) % 26).toInt()
        }

        return array.joinToString("")
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val strings = arrayOf("mkgfzkkuxownxvfvxasy", "abc")
            val shifts = arrayOf(
                intArrayOf(505870226,437526072,266740649,224336793,532917782,311122363,567754492,595798950,81520022,684110326,
                    137742843,275267355,856903962,148291585,919054234,467541837,622939912,116899933,983296461,536563513),
                intArrayOf(3,5,9)
            )
            for (i in strings.indices) {
                val letters = ShiftingLetters().shiftingLetters(strings[i], shifts[i])
                println("shifted: $letters")
            }
        }
    }
}