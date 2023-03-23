package week10

import java.util.*


class Assessment10_4 {
    fun permuteUnique(nums: IntArray): List<List<Int>> {
        return solution(nums)
    }

    private fun solution(numbers: IntArray): List<List<Int>> {
        val results = mutableListOf<List<Int>>()

        // count the occurrence of each number
        val counter = mutableMapOf<Int, Int>()
        for (number in numbers) {
            val count = counter.getOrDefault(number, 0)
            counter[number] = count + 1
        }
        backtrack(LinkedList<Int>(), numbers.size, counter, results)
        return results
    }

    private fun backtrack(combination: LinkedList<Int>, size: Int, counter: MutableMap<Int, Int>, results: MutableList<List<Int>>) {
        if (combination.size == size) {
            // make a deep copy of the resulting permutation, since the permutation would be backtracked later.
            results.add(ArrayList(combination))
        } else {
            for ((number, count) in counter) {
                if (count == 0)
                    continue

                // add this number into the current combination
                combination.addLast(number)
                counter[number] = count - 1

                // continue the exploration
                backtrack(combination, size, counter, results)

                // revert the choice for the next exploration
                combination.removeLast()
                counter[number] = count
            }
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(intArrayOf(1,1,2), intArrayOf(1,2,3))
            for (array in arrays) {
                println("input: ${array.contentToString()}")
                val permutations = Assessment10_4().permuteUnique(array)
                println("permutations:")
                for (permutation in permutations) {
                    println(permutation)
                }
            }
        }
    }
}