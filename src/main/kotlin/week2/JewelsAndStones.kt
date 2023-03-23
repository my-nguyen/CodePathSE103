package week2

class JewelsAndStones {
    fun numJewelsInStones(jewels: String, stones: String): Int {
        val set = mutableSetOf<Char>()
        for (c in jewels) {
            set.add(c)
        }
        var count = 0
        for (c in stones) {
            if (set.contains(c))
                count++
        }
        return count
    }
}