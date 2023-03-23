package week3

import java.util.*

class ValidateStackSequences {
    fun validateStackSequences(pushed: IntArray, popped: IntArray): Boolean {
        return mine(pushed, popped)
    }

    private fun mine(pushed: IntArray, popped: IntArray): Boolean {
        val stack = Stack<Int>()
        var i = 0
        // first, keep pushing onto stack elements in pushed that don't match the first element in popped
        while (i < pushed.size && pushed[i] != popped[0]) {
            stack.push(pushed[i])
            i++
        }
        // println("i: $i, stack size: ${stack.size}")

        var j = 0
        // at this point either there's no more elements in pushed, or the current element in pushed matches
        // the first element in popped
        while (true) {
            if (stack.isNotEmpty() && stack.peek() == popped[j]) {
                // println("stack top equals popped at $j")
                // the stack top matches the current element in popped: remove stack top and advance to the next element in popped
                stack.pop()
                j++
            } else if (i < pushed.size) {
                if (pushed[i] == popped[j]) {
                    // println("pushed at $i equals popped at $j")
                    // the current element in pushed matches that in popped: advance to the next element in both pushed and popped
                    j++
                } else {
                    // println("not equal, saving pushed at $i")
                    // both current element in pushed and stack top don't match current element in popped
                    stack.push(pushed[i])
                }
                i++
            } else {
                break
            }
        }
        return stack.isEmpty()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val pushed = arrayOf(intArrayOf(1,2,3,4,5), intArrayOf(1,2,3,4,5), intArrayOf(2,1,0))
            val popped = arrayOf(intArrayOf(4,5,3,2,1), intArrayOf(4,3,5,1,2), intArrayOf(1,2,0))
            for (i in pushed.indices) {
                val isValid = ValidateStackSequences().validateStackSequences(pushed[i], popped[i])
                println("pushed: ${pushed[i].contentToString()}, popped: ${popped[i].contentToString()}, is valid? $isValid")
            }
        }
    }
}