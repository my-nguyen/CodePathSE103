package week5

import java.util.*

class KeysAndRooms {
    fun canVisitAllRooms(rooms: List<List<Int>>): Boolean {
        return new(rooms)
    }

    private fun old(rooms: List<List<Int>>): Boolean {
        val entered = BooleanArray(rooms.size)
        entered[0] = true
        val queue = LinkedList<Int>()
        for (room in rooms[0]) {
            if (room != null) {
                queue.add(room)
            }
        }
        while (queue.isNotEmpty()) {
            val room = queue.poll()
            if (room != null && !entered[room]) {
                entered[room] = true
                for (chambre in rooms[room]) {
                    if (chambre != null) {
                        queue.add(chambre)
                    }
                }
            }
        }
        for (unlocked in entered) {
            if (!unlocked) {
                return false
            }
        }
        return true
    }

    private fun new(rooms: List<List<Int>>): Boolean {
        if (rooms == null)
            return true

        val visited = BooleanArray(rooms.size)
        val queue = LinkedList<Int>()
        queue.add(0)
        while (queue.isNotEmpty()) {
            val node = queue.poll()
            if (!visited[node]) {
                visited[node] = true
                for (room in rooms[node]) {
                    queue.add(room)
                }
            }
        }

        for (node in visited) {
            if (!node)
                return false
        }
        return true
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val data = arrayOf(
                listOf(listOf(1), listOf(2), listOf(3), listOf()),
                listOf(listOf(1,3), listOf(3,0,1), listOf(2), listOf(0))
            )
            for (rooms in data) {
                val canVisit = KeysAndRooms().canVisitAllRooms(rooms)
                println("can visit all rooms? $canVisit")
            }
        }
    }
}