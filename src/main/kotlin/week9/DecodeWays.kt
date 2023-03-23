package week9

class DecodeWays {
    fun numDecodings(string: String): Int {
        return srinidhi99(string)
    }

    private fun srinidhi99(string: String): Int {
        val dp = IntArray(string.length + 1)
        // extra; ignore this case
        dp[0] = 1
        // base case when there's only 1 character
        dp[1] = if (string[0] == '0') 0 else 1
        for (i in 2..string.length) {
            val oneDigit = string.substring(i - 1, i).toInt()
            val twoDigits = string.substring(i - 2, i).toInt()

            // only count when a 1-digit number is within range 1 to 9, and ignore '0'
            if (oneDigit > 0)
                dp[i] += dp[i - 1]
            // only count when a 2-digit number is within range 10 to 26
            if (twoDigits in 10..26)
                dp[i] += dp[i - 2]
        }
        return dp[string.length]
    }

    private fun mine(string: String): Int {
        if (string.isNullOrEmpty())
            return 0
        // the first character can't be zero
        if (string[0] == '0')
            return 0
        if (string.length == 1)
            return 1

        val right = if (string.last() == '0') {
            // check the last 2 characters
            val sub = string.substring(string.lastIndex - 1)
            if (sub != "10" && sub != "20")
                return 0
            string.lastIndex - 2
        } else
            string.lastIndex - 1

        var count = 1
        for (i in right downTo 0) {
            val tmp = string.substring(i, i+2)
            if (tmp in "10".."26") {
                count++
                println("tmp: $tmp, in here")
            }
            println("count: $count")
        }
        return count
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val strings = arrayOf("27", "1", "12", "226", "0", "06")
            for (string in strings) {
                val count = DecodeWays().numDecodings(string)
                println("number: $count")
            }
        }
    }
}