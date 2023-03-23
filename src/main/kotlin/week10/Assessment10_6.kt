package week10

class Assessment10_6 {
    fun countArrangement(N: Int): Int {
        return solutionBruteForce(N)
    }

    var count = 0
    private fun solutionBruteForce(N: Int): Int {
        val nums = IntArray(N)
        for (i in 1..N)
            nums[i - 1] = i
        permute1(nums, 0)
        return count
    }

    private fun solutionBetterBruteForce(N: Int): Int {
        val nums = IntArray(N)
        for (i in 1..N)
            nums[i - 1] = i
        permute(nums, 0)
        return count
    }

    private fun solutionBacktracking(N: Int): Int {
        val visited = BooleanArray(N + 1)
        calculate(N, 1, visited)
        return count
    }

    private fun calculate(N: Int, pos: Int, visited: BooleanArray) {
        if (pos > N)
            count++
        for (i in 1..N) {
            if (!visited[i] && (pos % i == 0 || i % pos == 0)) {
                visited[i] = true
                calculate(N, pos + 1, visited)
                visited[i] = false
            }
        }
    }

    private fun permute(nums: IntArray, l: Int) {
        if (l == nums.size) {
            count++
        }
        for (i in l until nums.size) {
            swap(nums, i, l)
            if (nums[l] % (l + 1) == 0 || (l + 1) % nums[l] == 0) permute(nums, l + 1)
            swap(nums, i, l)
        }
    }

    private fun permute1(nums: IntArray, l: Int) {
        if (l == nums.size - 1) {
            var i = 1
            while (i <= nums.size) {
                if (nums[i - 1] % i != 0 && i % nums[i - 1] != 0)
                    break
                i++
            }
            if (i == nums.size + 1) {
                count++
            }
        }
        for (i in l until nums.size) {
            swap(nums, i, l)
            permute1(nums, l + 1)
            swap(nums, i, l)
        }
    }

    private fun swap(nums: IntArray, x: Int, y: Int) {
        val temp = nums[x]
        nums[x] = nums[y]
        nums[y] = temp
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val numbers = arrayOf(2, 1)
            for (number in numbers) {
                print("number: $number, ")
                val count = Assessment10_6().countArrangement(number)
                println("number of beautiful arrangements: $count")
            }
        }
    }
}
