package week2

import java.util.*

class FindTheCelebrity {
    // Build an adjacency matrix and find a column where all elements are 1, besides the identity edge.
    // 1) Build an N-1 * N-1 matrix
    // 2) Iterate through each cell of the matrix
    // 3) Populate each element with the response of knows(a, b)
    // 4) Iterate through the adjacency matrix again
    //    a) If a column has all incoming edges except for the edge to itself, we have found a celebrity
    // 5) If no celebrity column, return -1
    // Time Complexity: O(N^2)
    // Space Complexity: O(N^2)
    // Cons: Calls knows(a, b) many times and takes a lot of space.
    fun approach1(n: Int): Int {
        val adjacentMatrix = Array(n) { BooleanArray(n) }
        for (i in 0 until n) {
            for (j in 0 until n) {
                adjacentMatrix[i][j] = knows(i, j)
            }
        }

        for (col in 0 until n) {
            var valid = true
            /*for (row in 0 until n) {
                if (col != row && !adjacentMatrix[row][col]) {
                    valid = false
                    break
                }
            }*/
            var row = 0
            while (row < n && valid) {
                if (col != row && !adjacentMatrix[row][col])
                    valid = false
                row++
            }
            if (valid)
                return col
        }
        return -1
    }

    // Use a stack to iterate through all the individuals at the party. Pop the stack and call knows(a, b) on individuals
    // till a celebrity is found.
    // 1) Create a stack and push elements 0 --> N-1 onto the stack
    // 2) Create two variables left and right
    // 3) Pop the first two elements into left and right respectively
    // 4) While the stack has individuals on it:
    //    a) If left knows right, left cannot be a celebrity
    //        i) Pop the next element into left
    //        ii) Refer to right as a potential celebrity
    //    b) If left does not know right, right cannot be a celebrity
    //        i) Pop the next element into right
    //        ii) Refer to left as a potential celebrity
    // 5) The potential celebrity reference is the most valid individual
    // 6) Loop through all individuals and verify:
    //    a) Potential celebrity does not know anyone
    //    b) Everyone except the potential celebrity knows the potential celebrity
    // 7) If valid, return celebrity, else return -1
    // Time Complexity: O(N)
    // Space Complexity: O(N)
    // Cons: The stack is not really necessary
    fun approach2(n: Int): Int {
        if (n < 2)
            return -1

        val stack = Stack<Int>()
        for (i in 0 until n) {
            stack.push(i)
        }
        var left = stack.pop()
        var right = stack.pop()
        var potentialCelebrity = if (!knows(left, right)) left else right
        while (stack.isNotEmpty()) {
            if (knows(left, right)) {
                potentialCelebrity = right
                left = stack.pop()
            } else {
                potentialCelebrity = left
                right = stack.pop()
            }
        }

        for (i in 0 until n) {
            if (i != potentialCelebrity && !knows(i, potentialCelebrity))
                return -1
        }
        return potentialCelebrity
    }

    // Use a two-pointer approach and iterate towards N-1/2 to find a celebrity.
    // 1) Create two pointers left and right that point to 0 and N-1 respectively
    // 2) Loop while left pointer value is less than right pointer value (l < r)
    // 3) Check if left knows right
    //    a) If left knows right, left cannot be a celebrity. Increment left pointer
    //    b) If left does not know right, left could be a celebrity. Decrement right pointer
    // 4) At this point, left and right pointer should have same value
    //    a) Left pointer value should be a potential celebrity
    // 5) Loop through all individuals and verify:
    //    a) Potential celebrity does not know anyone
    //    b) Everyone except the potential celebrity knows the potential celebrity
    // 6) If valid, return celebrity, else return -1
    // Time Complexity: O(N)
    // Space Complexity: O(1)
    fun approach3(n: Int): Int {
        var left = 0
        var right = n - 1
        while (left < right) {
            if (knows(left, right))
                left++
            else
                right--
        }

        for (i in 0 until n) {
            if (i != left && (!knows(i, left) || knows(left, i)))
                return -1
        }
        return left
    }

    fun knows(a: Int, b: Int): Boolean {
        return false
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val matrices = arrayOf(
                arrayOf(intArrayOf(0,0,1,0,0), intArrayOf(0,0,1,0,0), intArrayOf(0,0,0,0,0), intArrayOf(0,0,1,0,0), intArrayOf(0,0,1,0,0)),
                arrayOf(intArrayOf(0,0,0,0,1), intArrayOf(0,0,0,1,0), intArrayOf(0,1,0,0,0), intArrayOf(0,0,0,0,0), intArrayOf(1,0,0,1,0)),

            )
        }
    }
}