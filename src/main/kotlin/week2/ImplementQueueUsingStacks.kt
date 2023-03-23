package week2

import java.util.*

class ImplementQueueUsingStacks {
    class MyQueue() {

        val inStack = Stack<Int>()
        val outStack = Stack<Int>()

        fun push(x: Int) {
            inStack.push(x)
        }

        fun pop(): Int {
            transfer()
            return outStack.pop()
        }

        fun peek(): Int {
            transfer()
            return outStack.peek()
        }

        fun empty(): Boolean {
            return outStack.isEmpty() && inStack.isEmpty()
        }

        private fun transfer() {
            if (outStack.isEmpty()) {
                while (inStack.isNotEmpty())
                    outStack.push(inStack.pop())
            }
        }
    }

    interface Command {
        fun run(arg: Int?): Int?
    }

    lateinit var queue: MyQueue

    fun buildMap(): Map<String, Command> {
        val map = mutableMapOf<String, Command>()
        map["MyQueue"] = object: Command {
            override fun run(arg: Int?): Int? {
                queue = MyQueue()
                return null
            }
        }
        map["push"] = object: Command {
            override fun run(arg: Int?): Int? {
                queue.push(arg!!)
                return null
            }
        }
        map["peek"] = object: Command {
            override fun run(arg: Int?): Int? {
                return queue.peek()
            }
        }
        map["pop"] = object: Command {
            override fun run(arg: Int?): Int? {
                return queue.pop()
            }
        }
        map["empty"] = object: Command {
            override fun run(arg: Int?): Int? {
                return if (queue.empty()) 0 else 1
            }
        }
        return map
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val methods = arrayOf("MyQueue", "push", "push", "peek", "pop", "empty")
            val arguments = arrayOf(null, 1, 2, null, null, null)
            val map = ImplementQueueUsingStacks().buildMap()
            for (i in methods.indices) {
                println("${map[methods[i]]?.run(arguments[i])}")
            }
        }
    }
}