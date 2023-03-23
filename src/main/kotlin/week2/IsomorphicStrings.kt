package week2

class IsomorphicStrings {
    fun isIsomorphic(s: String, t: String): Boolean {
        return if (s.length != t.length)
            false
        else
            helper(s, t) && helper(t, s)
    }

    fun helper(s: String, t: String): Boolean {
        val map = mutableMapOf<Char, Char>()
        for (i in s.indices) {
            val key = s[i]
            if (!map.contains(key))
                map[key] = t[i]
            else if (map[key] != t[i])
                return false
        }
        return true
    }
}