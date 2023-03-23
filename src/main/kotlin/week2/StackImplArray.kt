package week2

class StackImplArray {
    val MAX_SIZE = 101
    val data = IntArray(MAX_SIZE)
    var top = -1

    fun push(x: Int) {
        if (top == MAX_SIZE)
            println("Error: stack overflow")
        else {
            top++
            data[top] = x
        }
    }

    fun pop() {
        if (top == -1)
            println("Error: stack empty")
        else
            top--
    }

    fun peek() = data[top]

    fun print() {
        print("Stack: ")
        for (i in 0..top) {
            print("${data[i]} ")
        }
        println()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val stack = StackImplArray()
            stack.push(2)
            stack.print()
            stack.push(5)
            stack.print()
            stack.push(10)
            stack.print()
            stack.pop()
            stack.print()
            stack.push(12)
            stack.print()
        }
    }
}