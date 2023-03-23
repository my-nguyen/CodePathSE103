package week3

import java.util.*


class DailyTemperatures {
    fun dailyTemperatures(temperatures: IntArray): IntArray {
        return bruteForce(temperatures)
        // return leetcode1(temperatures)
    }

    private fun codepath(temperatures: IntArray): IntArray {
        val stack = Stack<Int>()
        val ret = IntArray(temperatures.size)
        for (i in temperatures.indices) {
            while (!stack.isEmpty() &&
                temperatures[i] > temperatures[stack.peek()]
            ) {
                val idx = stack.pop()
                ret[idx] = i - idx
            }
            stack.push(i)
        }
        return ret
    }

    fun hackmd(temperatures: IntArray): IntArray {
        val stack = Stack<Int>()
        val ret = IntArray(temperatures.size)
        for (i in temperatures.indices) {
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                val idx = stack.pop()
                ret[idx] = i - idx
            }
            stack.push(i)
        }
        return ret
    }

    fun leetcode1(temperatures: IntArray): IntArray {
        // stack of indices
        val stack = Stack<Int>()
        val result = IntArray(temperatures.size)
        for (i in temperatures.indices) {
            // remove all indices the element at which is smaller than the current temperature
            // example: [73, 74, 75, 71, 69, 72, 76, 73]
            // i=0, stack=empty: nothing is popped, push 0 onto stack
            // i=1 (74), stack=[0]: pop index 0 (73) from stack, store the distance from 0 to 1 in the result, and push 1 onto stack
            // i=2 (75), stack=[1]: pop index 1 (74) from stack, store the distance from 1 to 2 in the result, and push 2 onto stack
            // i=3 (71), stack=[2]: nothing is popped, and push 3 onto stack
            // i=4 (69), stack=[2, 3]: nothing is popped, and push 4 onto stack
            // i=5 (72), stack=[2, 3, 4]: pop indices 4 (69) and 3 (71) from stack, store the distances from 4 to 5 and from 3 to 5 in the result, and push 5 onto stack
            // i=6 (76), stack=[2, 5]: pop indices 5 (72) and 2 (75) from stack, store the distances from 5 to 6 and from 2 to 6 in the result, and push 6 onto stack
            // i=7 739), stack=[6]: nothing is popped, push 7 onto stack, then end.
            while (stack.isNotEmpty() && temperatures[stack.peek()] < temperatures[i]) {
                val idx = stack.pop()
                println("i: $i, idx: $idx")
                result[idx] = i - idx
            }
            // push index onto stack
            stack.push(i)
        }
        return result
    }

    private fun bruteForce(temperatures: IntArray): IntArray {
        val result = IntArray(temperatures.size)
        for (i in 0 until temperatures.lastIndex) {
            for (j in i + 1..temperatures.lastIndex) {
                if (temperatures[j] > temperatures[i]) {
                    result[i] = j - i
                    break
                }
            }
        }
        return result
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val data = arrayOf(
                intArrayOf(73, 74, 75, 71, 69, 72, 76, 73), intArrayOf(30,40,50,60), intArrayOf(30,60,90)
            )
            for (temperatures in data) {
                val wait = DailyTemperatures().dailyTemperatures(temperatures)
                println("temperatures: ${temperatures.contentToString()}, wait: ${wait.contentToString()}")
            }
        }
    }
}
