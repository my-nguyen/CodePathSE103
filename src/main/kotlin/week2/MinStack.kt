package week2

import java.util.*

class MinStack {
    // private val stack = NickWhite()
    private val stack = CodePath()

    fun push(x: Int) = stack.push(x)

    fun pop() = stack.pop()

    fun top() = stack.top()

    fun min() = stack.min()

    class NickWhite {
        var data = IntArray(50)
        var size = 0
        var minIndices = Stack<Int>()

        fun push(x: Int) {
            data[size] = x
            // save new index when it's the first element, or when
            // the new element is smaller than the current minimum
            if (size == 0 || data[minIndices.peek()] > x) {
                minIndices.push(size)
            }
            size++
            // if storage is full, increase its capacity
            if (size == data.size) {
                val tmp = IntArray(data.size * 2)
                System.arraycopy(data, 0, tmp, 0, data.size)
                data = tmp
            }
        }

        fun pop() {
            size--
            // since the current minimum just got removed, must update it
            if (minIndices.peek() == size) {
                minIndices.pop()
            }
        }

        fun top() = data[size - 1]

        fun min() = data[minIndices.peek()]
    }

    class CodePath {
        val stackList = mutableListOf<IntArray>()

        fun push(x: Int) {
            var currentMin = if (stackList.isNotEmpty()) stackList.last()[1] else x
            if (currentMin > x)
                currentMin = x
            stackList.add(intArrayOf(x, currentMin))
        }

        fun pop() {
            if (stackList.isNotEmpty())
                stackList.removeLast()
        }

        fun top() = if (stackList.isNotEmpty()) stackList.last()[0] else null

        fun min() = if (stackList.isNotEmpty()) stackList.last()[1] else null
    }

    interface Command {
        fun run(arg: Int?): Int?
    }

    lateinit var data: MinStack

    fun buildMap(): Map<String, Command> {
        val map = mutableMapOf<String, Command>()
        map["MinStack"] = object: Command {
            override fun run(arg: Int?): Int? {
                data = MinStack()
                return null
            }
        }
        map["push"] = object: Command {
            override fun run(arg: Int?): Int? {
                data.push(arg!!)
                return null
            }
        }
        map["getMin"] = object: Command {
            override fun run(arg: Int?): Int? {
                return data.min()
            }
        }
        map["pop"] = object: Command {
            override fun run(arg: Int?): Int? {
                data.pop()
                return null
            }
        }
        map["top"] = object: Command {
            override fun run(arg: Int?): Int? {
                return data.top()
            }
        }
        return map
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val methods = arrayOf(
                arrayOf("MinStack","push","push","push","getMin","pop","top","getMin"),
                arrayOf("MinStack", "push", "push", "getMin", "pop", "getMin")
            )
            val arguments = arrayOf(
                arrayOf(null, -2, 0, -3, null, null, null, null),
                arrayOf(null, 2, 4, null, null, null)
            )

            for (i in methods.indices) {
                val map = MinStack().buildMap()
                for (j in methods[i].indices) {
                    print("${map[methods[i][j]]?.run(arguments[i][j])}, ")
                }
                println()
            }
        }
    }
}