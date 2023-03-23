package week7

import java.util.*


class TrappingRainWaterII {
    fun trapRainWater(heightMap: Array<IntArray>): Int {
        return 0
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val heights = arrayOf(
                arrayOf(intArrayOf(1,4,3,1,3,2), intArrayOf(3,2,1,3,2,4), intArrayOf(2,3,3,2,3,1)),
                arrayOf(intArrayOf(3,3,3,3,3), intArrayOf(3,2,2,2,3), intArrayOf(3,2,1,2,3), intArrayOf(3,2,2,2,3), intArrayOf(3,3,3,3,3))
            )
            for (height in heights) {
                val trap = TrappingRainWaterII().trapRainWater(height)
                println("trap: $trap")
            }
        }
    }
}

/*
All the boundry rows/cols will not able to hold water as they will not be covered by four side
Create a Node class to hold the X, Y and height (Z - Index) of a cube. Basically you need to store the coordinate of a cube
If you see below example, position (1,1) having value 2, we can only hold the water if the minimum height (Z-Index) of it's neighbour is greater than it's own value.
3.1) This gives you two idea, you have to somehow get the minimm of all the node neighbour before you process node.
3.2) You should use a data structure which will effciently give you minimum element ( Min Heap to the rescue and Java implementation of that is PriorityQueue

{
{1, 4, 3, 1, 3, 2},
{3, 2, 1, 3, 2, 4},
{2, 3, 3, 2, 3, 1}
}
To kick start the algorithm, one thing you know for sure is all the boundry node will not be able to store any water so lets just add all those node to PriorityQueue
Poll the queue and check the maxHeight uptill that iteration if the maxHeight is less than new height then change maxHeight otherwise if it's more than you can store water. Add the water volume by taking the delta between maxHeigth and new height.
If you need more details let me know in the comments and will be happy to provide those.
 */
class Node(x: Int, y: Int, height: Int) : Comparable<Node> {
    var x: Int
    var y: Int
    var height: Int
    override operator fun compareTo(node: Node): Int {
        return height - node.height
    }

    init {
        this.x = x
        this.y = y
        this.height = height
    }
}

class Solution1 {
    private fun solution1(heightMap: Array<IntArray>?): Int {
        if (heightMap == null || heightMap.isEmpty()) return 0
        val rows = heightMap.size
        val cols: Int = heightMap[0].size
        val visited = Array(rows) {
            BooleanArray(
                cols
            )
        }
        return volumeOfRainWater(rows, cols, heightMap, visited)
    }

    private fun volumeOfRainWater(row: Int, col: Int, height: Array<IntArray>, visited: Array<BooleanArray>): Int {
        //crete heap with outer cells as they will not hold water
        val minHeap = PriorityQueue<Node>()

        //add first and last column in heap - as they are in boundry incapable of holding water
        for (walker in 0 until row) {
            minHeap.add(Node(walker, 0, height[walker][0]))
            minHeap.add(Node(walker, col - 1, height[walker][col - 1]))
            visited[walker][0] = true
            visited[walker][col - 1] = true
        }

        //add first and last rows in heap = as they are incapable of holding water
        for (walker in 0 until col) {
            minHeap.add(Node(0, walker, height[0][walker]))
            minHeap.add(Node(row - 1, walker, height[row - 1][walker]))
            visited[0][walker] = true
            visited[row - 1][walker] = true
        }
        val direction = arrayOf(intArrayOf(0, 1), intArrayOf(0, -1), intArrayOf(1, 0), intArrayOf(-1, 0))

        //poll one element from heap(which will give you minimum) and check it's neighbour. Keep pooling untill it's empty
        var node: Node? = null
        var maxHeight = 0
        var waterVolume = 0
        while (!minHeap.isEmpty()) {
            node = minHeap.poll()
            val x = node.x
            val y = node.y
            val h = node.height
            if (h < maxHeight) {
                waterVolume += maxHeight - h
            } else {
                maxHeight = h
            }
            for (walker in 0..3) {
                val nx = node.x - direction[walker][0]
                val ny = node.y - direction[walker][1]
                if (nx < 0 || ny < 0 || nx >= row || ny >= col) continue
                if (visited[nx][ny] == true) continue
                val nh = height[nx][ny]
                minHeap.add(Node(nx, ny, nh))
                visited[nx][ny] = true
            }
        }
        return waterVolume
    }

    private fun heapify() {}
}

private fun solution2(heightMap: Array<IntArray>): Int {
    val n = heightMap.size
    val m: Int = heightMap[0].size
    val pq = PriorityQueue { a: Int, b: Int ->
        val r1 = a / m
        val c1 = a % m
        val r2 = b / m
        val c2 = b % m
        heightMap[r1][c1] - heightMap[r2][c2]
    }
    val visted = Array(n) { BooleanArray(m) }
    for (i in 0 until n) {
        for (j in 0 until m) {
            if (i == 0 || j == 0 || i == n - 1 || j == m - 1) {
                pq.add(i * m + j)
                visted[i][j] = true
            }
        }
    }
    val dir = arrayOf(intArrayOf(1, 0), intArrayOf(-1, 0), intArrayOf(0, 1), intArrayOf(0, -1))
    var minBulding = 0
    var water = 0
    while (!pq.isEmpty()) {
        val idx = pq.poll()
        val r = idx / m
        val c = idx % m
        minBulding = Math.max(minBulding, heightMap[r][c])
        water += minBulding - heightMap[r][c]
        for (d in 0..3) {
            val i = r + dir[d][0]
            val j = c + dir[d][1]
            if (i >= 0 && j >= 0 && i < n && j < m && !visted[i][j]) {
                pq.add(i * m + j)
                visted[i][j] = true
            }
        }
    }
    return water
}

private fun solution3(heightMap: Array<IntArray>): Int {
    val n = heightMap.size
    val m: Int = heightMap[0].size
    if (n <= 2 || m <= 2) {
        return 0 // as these are boundary and no water can be stored there
    }
    val pq = PriorityQueue { a: pair, b: pair ->
        a.`val` - b.`val` // minimum priority queue across val
    }
    val flag = Array(n) { BooleanArray(m) } // to check how many cells are considered as boundary
    // marking flags for corners
    flag[0][0] = true
    flag[0][m - 1] = true
    flag[n - 1][0] = true
    flag[n - 1][m - 1] = true

    // Adding all boundary elements in priority queue except corners as they doesn't matter
    // top and bottom boundaries
    for (i in 1 until m - 1) {
        flag[0][i] = true
        flag[n - 1][i] = true
        pq.add(pair(0, i, heightMap[0][i]))
        pq.add(pair(n - 1, i, heightMap[n - 1][i]))
    }
    // left and right boundaries
    for (i in 1 until n - 1) {
        flag[i][0] = true
        flag[i][m - 1] = true
        pq.add(pair(i, 0, heightMap[i][0]))
        pq.add(pair(i, m - 1, heightMap[i][m - 1]))
    }
    var ans = 0
    val dir = arrayOf(
        intArrayOf(0, 1),
        intArrayOf(1, 0),
        intArrayOf(0, -1),
        intArrayOf(-1, 0)
    ) // 4 directions from where water can come to a cell
    while (pq.size != 0) {
        val rpair = pq.remove()
        for (i in dir.indices) {
            val x = rpair.r + dir[i][0]
            val y = rpair.c + dir[i][1]
            if (x >= 0 && y >= 0 && x < n && y < m && !flag[x][y]) {
                if (heightMap[x][y] < rpair.`val`) {
                    ans += rpair.`val` - heightMap[x][y]
                    heightMap[x][y] = rpair.`val`
                }
                flag[x][y] = true
                pq.add(pair(x, y, heightMap[x][y]))
            }
        }
    }
    return ans
}

class pair(var r: Int, var c: Int, var `val`: Int)

internal class Solution4 {
    internal inner class Pair(var row: Int, var col: Int, var `val`: Int)

    private fun solution4(heightMap: Array<IntArray>): Int {
        val n = heightMap.size
        val m: Int = heightMap[0].size
        val visited = Array(n) { BooleanArray(m) }
        val pq = PriorityQueue { x: Pair, y: Pair -> x.`val` - y.`val` }
        for (i in heightMap.indices) {
            pq.add(Pair(i, 0, heightMap[i][0]))
            visited[i][0] = true
            pq.add(Pair(i, m - 1, heightMap[i][m - 1]))
            visited[i][m - 1] = true
        }
        for (i in 0 until m) {
            visited[0][i] = true
            pq.add(Pair(0, i, heightMap[0][i]))
            visited[n - 1][i] = true
            pq.add(Pair(n - 1, i, heightMap[n - 1][i]))
        }
        var ans = 0
        while (!pq.isEmpty()) {
            val curr = pq.poll()
            val currVal = curr.`val`
            val i = curr.row
            val j = curr.col
            if (i > 0 && !visited[i - 1][j]) {
                if (heightMap[i - 1][j] < currVal) {
                    ans += currVal - heightMap[i - 1][j]
                    pq.add(Pair(i - 1, j, currVal))
                } else {
                    pq.add(Pair(i - 1, j, heightMap[i - 1][j]))
                }
                visited[i - 1][j] = true
            }
            if (i < n - 1 && !visited[i + 1][j]) {
                if (heightMap[i + 1][j] < currVal) {
                    ans += currVal - heightMap[i + 1][j]
                    pq.add(Pair(i + 1, j, currVal))
                } else {
                    pq.add(Pair(i + 1, j, heightMap[i + 1][j]))
                }
                visited[i + 1][j] = true
            }
            if (j > 0 && !visited[i][j - 1]) {
                if (heightMap[i][j - 1] < currVal) {
                    ans += currVal - heightMap[i][j - 1]
                    pq.add(Pair(i, j - 1, currVal))
                } else {
                    pq.add(Pair(i, j - 1, heightMap[i][j - 1]))
                }
                visited[i][j - 1] = true
            }
            if (j < m - 1 && !visited[i][j + 1]) {
                if (heightMap[i][j + 1] < currVal) {
                    ans += currVal - heightMap[i][j + 1]
                    pq.add(Pair(i, j + 1, currVal))
                } else {
                    pq.add(Pair(i, j + 1, heightMap[i][j + 1]))
                }
                visited[i][j + 1] = true
            }
        }
        return ans
    }
}