package week10

class LetterCasePermutation {
    fun letterCasePermutation(string: String): List<String> {
        return listOf()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val strings = arrayOf("a1b2", "3z4", "12345", "0")
            for (string in strings) {
                print("string: $string, ")
                val permutation = LetterCasePermutation().letterCasePermutation(string)
                println("permutations: $permutation")
            }
        }
    }
}