package week8


class MinimumWindowSubstring {
    fun minWindow(s: String, t: String): String {
        return solution1(s, t)
    }

    private fun solution1(s: String, t: String): String {
        if (s.isEmpty() || t.isEmpty())
            return ""

        // Dictionary which keeps a count of all the unique characters in t.
        val dictT = mutableMapOf<Char, Int>()
        for (i in t.indices) {
            val count = dictT.getOrDefault(t[i], 0)
            dictT[t[i]] = count + 1
        }

        // Number of unique characters in t, which need to be present in the desired window.
        val required = dictT.size

        // Left and Right pointer
        var l = 0
        var r = 0

        // formed is used to keep track of how many unique characters in t are present in the current window in its
        // desired frequency. e.g. if t is "AABC" then the window must have two A's, one B and one C. Thus formed
        // would be = 3 when all these conditions are met.
        var formed = 0

        // Dictionary which keeps a count of all the unique characters in the current window.
        val windowCounts = mutableMapOf<Char, Int>()

        // ans list of the form (window length, left, right)
        val ans = intArrayOf(-1, 0, 0)
        while (r < s.length) {
            // Add one character from the right to the window
            var c = s[r]
            val count = windowCounts.getOrDefault(c, 0)
            windowCounts[c] = count + 1

            // If the frequency of the current character added equals to the desired count in t then increment
            // the formed count by 1.
            if (dictT.containsKey(c) && windowCounts[c]!!.toInt() == dictT[c]!!.toInt()) {
                formed++
            }

            // Try and contract the window till the point where it ceases to be 'desirable'.
            while (l <= r && formed == required) {
                c = s[l]
                // Save the smallest window until now.
                if (ans[0] == -1 || r - l + 1 < ans[0]) {
                    ans[0] = r - l + 1
                    ans[1] = l
                    ans[2] = r
                }

                // The character at the position pointed by the `Left` pointer is no longer a part of the window.
                windowCounts[c] = windowCounts[c]!! - 1
                if (dictT.containsKey(c) && windowCounts[c]!!.toInt() < dictT[c]!!.toInt()) {
                    formed--
                }

                // Move the left pointer ahead, this would help to look for a new window.
                l++
            }

            // Keep expanding the window once we are done contracting.
            r++
        }
        return if (ans[0] == -1) "" else s.substring(ans[1], ans[2] + 1)
    }

    private fun solution2(s: String, t: String): String {
        if (s.isEmpty() || t.isEmpty()) {
            return ""
        }

        val dictT = mutableMapOf<Char, Int>()
        for (i in t.indices) {
            val count = dictT.getOrDefault(t[i], 0)
            dictT[t[i]] = count + 1
        }
        val required = dictT.size

        // Filter all the characters from s into a new list along with their index. The filtering criteria is that
        // the character should be present in t.
        val filteredS = mutableListOf<Pair<Int, Char>>()
        for (i in s.indices) {
            val c = s[i]
            if (dictT.containsKey(c)) {
                filteredS.add(Pair(i, c))
            }
        }
        var l = 0
        var r = 0
        var formed = 0
        val windowCounts = mutableMapOf<Char, Int>()
        val ans = intArrayOf(-1, 0, 0)

        // Look for the characters only in the filtered list instead of entire s. This helps to reduce our search.
        // Hence, we follow the sliding window approach on as small list.
        while (r < filteredS.size) {
            var c = filteredS[r].second
            val count = windowCounts.getOrDefault(c, 0)
            windowCounts[c] = count + 1
            if (dictT.containsKey(c) && windowCounts[c]!!.toInt() == dictT[c]!!.toInt()) {
                formed++
            }

            // Try and contract the window till the point where it ceases to be 'desirable'.
            while (l <= r && formed == required) {
                c = filteredS[l].second

                // Save the smallest window until now.
                val end = filteredS[r].first
                val start = filteredS[l].first
                if (ans[0] == -1 || end - start + 1 < ans[0]) {
                    ans[0] = end - start + 1
                    ans[1] = start
                    ans[2] = end
                }
                windowCounts[c] = windowCounts[c]!! - 1
                if (dictT.containsKey(c) && windowCounts[c]!!.toInt() < dictT[c]!!.toInt()) {
                    formed--
                }
                l++
            }
            r++
        }
        return if (ans[0] == -1) "" else s.substring(ans[1], ans[2] + 1)
    }
}