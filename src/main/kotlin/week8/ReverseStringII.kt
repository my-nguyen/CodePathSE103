package week8

class ReverseStringII {
    fun reverseStr(string: String, count: Int): String {
        val sb = StringBuilder()
        var i = 0
        while (i < string.length) {
            var length = minOf(string.length-i, count)
            var substring = string.substring(i, i+length)
            sb.append(substring.reversed())
            i += length

            if (i >= string.length)
                break

            length = minOf(string.length-i, count)
            substring = string.substring(i, i+length)
            sb.append(substring)
            i += length
        }
        return sb.toString()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val strings = arrayOf("abc", /*"abcdefg", "abcd"*/)
            val counts = arrayOf(5, 2, 2)
            for (i in strings.indices) {
                val reversed = ReverseStringII().reverseStr(strings[i], counts[i])
                println("reversed: $reversed")
            }
        }
    }
}