package week12

class DesignCircularQueue {
    class MyCircularQueue(k: Int) {

        fun enQueue(value: Int): Boolean {
            return true
        }

        fun deQueue(): Boolean {
            return true
        }

        fun Front(): Int {
            return 0
        }

        fun Rear(): Int {
            return 0
        }

        fun isEmpty(): Boolean {
            return true
        }

        fun isFull(): Boolean {
            return true
        }
    }

    /**
     * Your MyCircularQueue object will be instantiated and called as such:
     * var obj = MyCircularQueue(k)
     * var param_1 = obj.enQueue(value)
     * var param_2 = obj.deQueue()
     * var param_3 = obj.Front()
     * var param_4 = obj.Rear()
     * var param_5 = obj.isEmpty()
     * var param_6 = obj.isFull()
     */

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val methods = arrayOf(
                "MyCircularQueue", "enQueue", "enQueue", "enQueue", "enQueue", "Rear", "isFull", "deQueue", "enQueue", "Rear"
            )
            val params = arrayOf(3,1,2,3,4,null,null,null,4,null)
            for (i in methods.indices) {
            }
        }
    }
}