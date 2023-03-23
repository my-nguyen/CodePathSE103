package week2

class QueueImplArray {
    val data = IntArray(100)
    var front = -1
    var back = -1

    fun enqueue(x: Int) {
        if ((back+1)%data.size == front) {
            println("Queue is full")
            return
        } else if (isEmpty()) {
            front = 0
            back = 0
        } else {
            back = (back + 1) % data.size
        }
        data[back] = x
    }

    fun dequeue() {
        if (isEmpty())
            return
        else if (front == back) {
            front = -1
            back = -1
        } else {
            front = (front + 1) % data.size
        }
    }

    fun isEmpty() = front == -1 && back == -1

    fun front() = data[front]
}