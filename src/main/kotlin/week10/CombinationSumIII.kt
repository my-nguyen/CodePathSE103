package week10


class CombinationSumIII {
    fun combinationSum3(count: Int, sum: Int): List<List<Int>> {
        return jinwu(count, sum)
    }

    private fun jinwu(count: Int, sum: Int): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        recurse(count, sum, 1, result)
        return result
    }

    private fun recurse(count: Int, sum: Int, start: Int, result: MutableList<List<Int>>, combination: MutableList<Int> = mutableListOf()) {
        if (combination.size == count && sum == 0) {
            // found a combination with the required count and matching the sum: save it in result
            val list = combination.toList()
            result.add(list)
        } else {
            // combination not found
            for (i in start..9) {
                // try a different combination with the current i
                combination.add(i)
                recurse(count, sum - i, i + 1, result, combination)
                // backtrack by removing the current i from combination
                combination.removeLast()
            }
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val counts = arrayOf(3, 3, 4, 3, 9)
            val sums = arrayOf(7, 9, 1, 2, 45)
            for (i in sums.indices) {
                print("count: ${counts[i]}, sum: ${sums[i]}, ")
                val combinations = CombinationSumIII().combinationSum3(counts[i], sums[i])
                print("combinations: ")
                if (combinations.isEmpty())
                    print("[]")
                else {
                    for (combination in combinations)
                        print("$combination, ")
                }
                println()
            }
        }
    }
}