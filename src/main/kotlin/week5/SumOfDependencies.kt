package week5

class SumOfDependencies {
    fun addEdge(adjacency: Array<MutableList<Int>>, from: Int, to: Int) {
        adjacency[from].add(to)
    }

    fun findSum(adjacency: Array<MutableList<Int>>, vertex: Int): Int {
        var sum = 0
        for (i in 0 until vertex) {
            println("i: $i, size: ${adjacency[i].size}")
            sum += adjacency[i].size
        }
        return sum
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val vertices = arrayOf(4)
            val from = arrayOf(
                arrayOf(0,0,1,2)
            )
            val to = arrayOf(
                arrayOf(2,3,3,3)
            )
            for (i in vertices.indices) {
                val adjacency = Array(vertices[i]) { mutableListOf<Int>() }
                val sum = SumOfDependencies()
                for (j in from[i].indices) {
                    sum.addEdge(adjacency, from[i][j], to[i][j])
                }
                println("Sum of dependencies: ${sum.findSum(adjacency, vertices[i])}")
            }
        }
    }
}