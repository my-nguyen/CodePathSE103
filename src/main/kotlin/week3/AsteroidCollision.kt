package week3

import java.util.*

class AsteroidCollision {
    fun asteroidCollision(asteroids: IntArray): IntArray {
        val stack = Stack<Int>()
        for (asteroid in asteroids) {
            if (stack.isEmpty() || stack.peek() * asteroid > 0 || stack.peek() < 0) {
                // if stack is empty or if stack top has the same sign as current asteroid (both are moving in
                // the same direction), or if stack top is moving left and current asteroid is moving right and
                // they can't collide: save current asteroid
                stack.push(asteroid)
            } else {
                // here stack is not empty and stack top is moving right and current asteroid is moving left
                while (stack.isNotEmpty() && stack.peek() > 0 && stack.peek() < -asteroid) {
                    // keep popping stack top as long as it's moving right and its magnitude is less than
                    // the current left-moving asteroid
                    stack.pop()
                }
                if (stack.isEmpty() || stack.peek() < 0) {
                    // either stack is empty or stack top is moving left, in the same direction as current asteroid
                    stack.push(asteroid)
                } else if (stack.peek() == -asteroid) {
                    // stack top is moving right and has the same magnitude as the current left-moving asteroid:
                    // destroy both stack top and current asteroid
                    stack.pop()
                } else {
                    // stack top is moving right but its magnitude is bigger than current asteroid: keep it there
                    // and do nothing
                }
            }
        }

        return stack.toIntArray()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val data = arrayOf(
                /* intArrayOf(5,10,-5), intArrayOf(8,-8), intArrayOf(10,2,-5), intArrayOf(-2,-1,1,2),*/
                intArrayOf(1, 2, 3, 4, 5, -6),
                intArrayOf(5, 4, 3, 2, 1, -3),
                intArrayOf(5, 4, 3, 2, 1, -1, -2, -3, -4, -5),
                intArrayOf(5, 4, 3, 2, 1, -1, -2, -3),
                intArrayOf(5, 4, 3, 2, 1, -1, -2, -3, -4, -5, -6),
                intArrayOf(5, 4, 3, 2, 1, -1, -2, -5, -6)
            )
            for (asteroids in data) {
                val collided = AsteroidCollision().asteroidCollision(asteroids)
                println("asteroids: ${asteroids.contentToString()}, post collision: ${collided.contentToString()}")
            }
        }
    }
}