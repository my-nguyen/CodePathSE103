package week5

import java.util.*

class ReconstructItinerary {
    fun findItinerary(tickets: List<List<String>>): List<String> {
        // return solution1(tickets)
        // return solution2(tickets)
        return mine(tickets)
    }

    // data class Destination(val name: String, var visited: Boolean = false)
    private fun mine(tickets: List<List<String>>): List<String> {
        val map = mutableMapOf<String, MutableList<String>>()
        for (i in tickets.indices) {
            val source = tickets[i][0]
            val dest = tickets[i][1]
            map.getOrPut(source) { mutableListOf() }
            map[source]!!.add(dest)
        }

        for ((k, v) in map) {
            v.sort()
        }

        // start at JFK
        val queue = LinkedList<String>()
        queue.add("JFK")
        val result = mutableListOf<String>()
        while (queue.isNotEmpty()) {
            // remove the airport at the top of the queue
            val source = queue.poll()
            // save airport in result
            result.add(source)
            // a source can have multiple destinations, e.g. JFK->SFO and JFK->ATL.
            // take the first entry from the list of destinations
            val dest = map[source]!![0]
            // println("source: $source, dest: $dest, does map contain dest? ${map.containsKey(dest)}")
            if (map.containsKey(dest)) {
                queue.add(dest)
            } else {
                result.add(dest)
            }
            update(map, source)
        }
        return result
    }

    private fun update(map: MutableMap<String, MutableList<String>>, key: String) {
        if (map[key]!!.size == 1)
            map.remove(key)
        else
            map[key]!!.removeAt(0)
    }

    // origin -> list of destinations
    private var flightMap1 = mutableMapOf<String, MutableList<String>>()
    private val visitBitmap = mutableMapOf<String, BooleanArray>()
    private var flights = 0
    private lateinit var result1: List<String>

    private fun solution1(tickets: List<List<String>>): List<String> {
        // Step 1). build the graph first
        for (ticket in tickets) {
            val source = ticket[0]
            val dest = ticket[1]
            val destList = flightMap1.getOrPut(source) { mutableListOf() }
            destList!!.add(dest)
        }

        // Step 2). order the destinations and init the visit bitmap
        for ((key, value) in flightMap1) {
            value.sort()
            visitBitmap[key] = BooleanArray(value.size)
        }
        flights = tickets.size
        val route = mutableListOf<String>()
        route.add("JFK")

        // Step 3). backtracking
        backtracking("JFK", route)
        return result1
    }

    private fun backtracking(origin: String, route: MutableList<String>): Boolean {
        if (route.size == flights + 1) {
            result1 = route.toList()
            return true
        }
        if (!flightMap1.containsKey(origin))
            return false

        val bitmap = visitBitmap[origin]
        // for (dest in flightMap1[origin]!!) {
        for ((i, dest) in flightMap1[origin]!!.withIndex()) {
            if (!bitmap!![i]) {
                bitmap[i] = true
                route.add(dest)
                val ret = backtracking(dest, route)
                route.removeLast()
                bitmap[i] = false
                if (ret)
                    return true
            }
        }
        return false
    }

    // origin -> list of destinations
    var flightMap2 = mutableMapOf<String, MutableList<String>>()
    val result2 = mutableListOf<String>()
    private fun solution2(tickets: List<List<String>>): List<String> {
        // Step 1). build the graph first
        for (ticket in tickets) {
            val origin = ticket[0]
            val dest = ticket[1]
            if (flightMap2.containsKey(origin)) {
                val destList = flightMap2[origin]!!
                destList.add(dest)
            } else {
                val destList = mutableListOf<String>()
                destList.add(dest)
                flightMap2[origin] = destList
            }
        }

        // Step 2). order the destinations
        for ((_, value) in flightMap2) {
            value.sort()
        }
        // Step 3). post-order DFS
        DFS("JFK")
        return result2
    }

    private fun DFS(origin: String) {
        // Visit all the outgoing edges first.
        if (flightMap2.containsKey(origin)) {
            val destList = flightMap2[origin]!!
            while (destList.isNotEmpty()) {
                // while we visit the edge, we trim it off from graph.
                val dest = destList.removeFirst()
                DFS(dest)
            }
        }
        // add the airport to the head of the itinerary
        result2.add(0, origin)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(
                listOf(listOf("MUC","LHR"), listOf("JFK","MUC"), listOf("SFO","SJC"), listOf("LHR","SFO")),
                listOf(listOf("JFK","SFO"), listOf("JFK","ATL"), listOf("SFO","ATL"), listOf("ATL","JFK"), listOf("ATL","SFO"))
            )
            for (tickets in arrays) {
                val itinerary = ReconstructItinerary().findItinerary(tickets)
                println("itinerary: $itinerary")
            }
        }
    }
}