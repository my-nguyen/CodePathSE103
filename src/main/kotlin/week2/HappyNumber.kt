package week2

class HappyNumber {
    fun isHappy(n: Int): Boolean {
        val set = mutableSetOf<Int>()
        var current = n
        while (current != 1) {
            // println("current: $current")
            if (set.contains(current))
                return false

            set.add(current)
            var sum = 0
            while (current != 0) {
                val remain = current % 10
                // print("pre-sum: $sum, remain: $remain")
                sum += remain*remain
                // println("post-sum: $sum")
                current /= 10
            }
            // println("sum: $sum")
            current = sum
        }
        return true
    }
}