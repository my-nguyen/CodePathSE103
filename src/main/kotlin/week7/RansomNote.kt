package week7

class RansomNote {
    fun canConstruct(ransomNote: String, magazine: String): Boolean {
        val counts = IntArray(26)
        for (c in magazine) {
            val index = c - 'a'
            counts[index]++
        }

        for (c in ransomNote) {
            val index = c - 'a'
            if (counts[index] == 0)
                return false
            else
                counts[index]--
        }
        return true
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val ransomNotes = arrayOf("a", "aa", "aa")
            val magazines = arrayOf("b", "ab", "aab")
            for (i in ransomNotes.indices) {
                val constructable = RansomNote().canConstruct(ransomNotes[i], magazines[i])
                println("ransom note: ${ransomNotes[i]}, magazine: ${magazines[i]}, can construct? $constructable")
            }
        }
    }
}