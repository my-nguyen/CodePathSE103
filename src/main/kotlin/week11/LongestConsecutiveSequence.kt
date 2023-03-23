package week11

class LongestConsecutiveSequence {
    fun longestConsecutive(numbers: IntArray): Int {
        // return solutionBruteForce(numbers)
        // return solutionSorting(numbers)
        return solutionSet(numbers)
    }

    fun solutionBruteForce(array: IntArray): Int {
        var longestStreak = 0
        // check every number in array
        for (number in array) {
            var currentNumber = number
            var currentStreak = 1

            // keep looking for the next number within array
            while (array.contains(currentNumber + 1)) {
                currentNumber++
                currentStreak++
            }
            longestStreak = maxOf(longestStreak, currentStreak)
        }
        return longestStreak
    }

    private fun solutionSorting(numbers: IntArray): Int {
        if (numbers.isEmpty())
            return 0

        numbers.sort()
        var longestStreak = 1
        var currentStreak = 1
        for (i in 1 until numbers.size) {
            if (numbers[i] == numbers[i - 1] + 1) {
                // if 2 consecutive elements have consecutive values, increment currentStreak
                currentStreak++
            } else if (numbers[i] != numbers[i - 1]) {
                // if 2 consecutive elements don't have consecutive values, save longestStreak and reset currenStreak
                longestStreak = maxOf(longestStreak, currentStreak)
                currentStreak = 1
            }
        }
        return maxOf(longestStreak, currentStreak)
    }

    private fun solutionSet(array: IntArray): Int {
        val numberSet = mutableSetOf<Int>()
        // save all array elements in the Set
        for (number in array)
            numberSet.add(number)

        var longestStreak = 0
        for (number in numberSet) {
            // if number-1 is not in the set, then number is not part of any sequence. in other word, number is
            // the beginning of a new sequence. so find the sequence length by trying to go as far as possible
            if (!numberSet.contains(number - 1)) {
                var currentNumber = number
                var currentStreak = 1
                while (numberSet.contains(currentNumber + 1)) {
                    currentNumber++
                    currentStreak++
                }
                longestStreak = maxOf(longestStreak, currentStreak)
            }
        }
        return longestStreak
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(intArrayOf(100, 4, 200, 1, 3, 2), intArrayOf(0, 3, 7, 2, 5, 8, 4, 6, 0, 1))
            for (numbers in arrays) {
                print("array: ${numbers.contentToString()}, ")
                val longest = LongestConsecutiveSequence().longestConsecutive(numbers)
                println("longest consecutive sequence: $longest")
            }
        }
    }
}

