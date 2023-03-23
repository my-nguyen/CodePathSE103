package week7

class Assessment7_10 {
    fun minWindow(s: String, t: String): String {
        return ""
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val strings = arrayOf("ADOBECODEBANC", "a", "a")
            val samples = arrayOf("ABC", "a", "aa")
            for (i in strings.indices) {
                print("string: ${strings[i]}, sample: ${samples[i]}, ")
                val window = Assessment7_10().minWindow(strings[i], samples[i])
                println("window: $window")
            }
        }
    }
}