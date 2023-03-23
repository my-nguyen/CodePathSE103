package week2

class BrickWall {
    fun leastBricks(wall: List<List<Int>>): Int {
        return useMap(wall)
        // return bruteForce(wall)
    }

    // time complexity: O(S), S=number of bricks
    // space complexity: O(N), N=wall width
    fun useMap(wall: List<List<Int>>): Int {
        // key=column index, value=gap count
        val gaps = mutableMapOf<Int, Int>()
        // a wall is a rectangle, or a 2d array of bricks, say 3x4 or 6x6 in height x width
        // a row is a row of bricks. a 3x4 wall has 3 rows of bricks, each brick is of varying widths
        // iterate each row
        for (row in wall) {
            var position = 0
            // each row contains bricks of varying widths, e.g. given a 6x6 wall as:
            // [1,2,2,1],[3,1,2],[1,3,2],[2,4],[3,1,2],[1,3,1,1]
            // OR
            // [1,2,2,1]
            // [3,1,2]
            // [1,3,2]
            // [2,4]
            // [3,1,2]
            // [1,3,1,1]
            // row = [1,2,2,1] means it has 4 bricks of 1, 2, 2, and 1 in width
            // row = [3,1,2] means it has 3 bricks of 3, 1, and 2 in width
            // so each element in the row array is a brick of different width
            // so with row=[1,2,2,1], then the brick widths are: row[0]=1, row[1]=2, row[2]=2, and row[3]=1
            // at the end of each row element (brick) is a gap
            // so with row=[1,2,2,1], the gaps are at 1, 3(1+2), 5(3+2), and 6(5+1)
            // only iterate to the next to last gap, since the last gap is at the edge of the wall and is not considered
            // 'gaps' will map the column index (gap) to the number of gaps per row. with
            // [1,2,2,1] -> gap[1]=1, gap[3]=1, gap[5]=1
            // [3,1,2]   -> gap[3]=2, gap[4]=1
            // [1,3,2]   -> gap[1]=2, gap[4]=2, gap[5]=2
            // [2,4]     -> gap[2]=1
            // [3,1,2]   -> gap[3]=3, gap[4]=3
            // [1,3,1,1] -> gap[1]=3, gap[4]=4, gap[5]=3
            // altogether: gap[1]=3, gap[2]=1, gap[3]=3, gap[4]=4, gap[5]=3
            // so among these gaps, gap[4] has the maximum value of 4
            // the least bricks to go thru is the wall height minus the maximum gaps
            for (i in 0 until row.size-1) {
                position += row[i];
                gaps[position] = gaps.getOrDefault(position, 0) + 1
            }
        }
        var minCross = wall.size
        if (gaps.isNotEmpty())
            minCross -= gaps.values.maxOrNull()!!

        return minCross
    }

    // In this approach, we consider the given wall as being made up of virtual bricks each of width 1. We traverse over
    // the width of the wall only in terms of these virtual bricks.
    // Firstly, we need to determine the total number of virtual bricks. For this, we determine the width of the given
    // wall by summing up the widths of the bricks in the first row. This width is stored insum. Thus, we need to
    // traverse over the widthsum times now in terms of 1 unit in each iteration.
    // We traverse over the virtual bricks in a column by column fashion. For keeping a track of the actual position
    // at which we are currently in any row, we make use of a pos array. pos[i] refers to the index of the brick in
    // the ith row, which is being treated as the virtual brick in the current column's traversal. Further, we maintain
    // a count variable to keep a track of the number of bricks cut if we draw a line down at the current position.
    // For every row considered during the column-by-column traversal, we need to check if we've hit an actual brick
    // boundary. This is done by updating the brick's width after the traversal. If we don't hit an actual brick boundary,
    // we need to increment count to reflect that drawing a line at this point leads to cutting off 1 more brick. But,
    // if we hit an actual brick boundary, we increment the value of pos[i], with i referring to the current row's index.
    // But, now if we draw a line down through this boundary, we need not increment the count.
    // We repeat the same process for every column of width equal to a virtual brick, and determine the minimum value of
    // count obtained from all such traversals.
    // time complexity: O(M*N); M=wall height, N=wall width
    // space complexity: O(M)
    fun bruteForce(wall: List<MutableList<Int>>): Int {
        var width = 0
        // traverse the first row to obtain the width
        for (brick in wall[0])
            width += brick

        val height = wall.size
        // use positions array to track which brick to deal with
        val positions = IntArray(height)
        var minCross = height
        // set step size to 1
        for (col in 0 until width-1) {
            var count = 0
            // traverse and check all the rows
            for (i in 0 until height) {
                val row = wall[i]
                // cut down the length of the current brick by 1
                // row[positions[i]]--
                if (row[positions[i]] == 0)
                    positions[i]++ // move on to deal with next brick
                else
                    count++ // increment count
            }
            minCross = minOf(minCross, count) // update the result
        }
        return minCross
    }

    // In this approach, instead of traversing over the columns in terms of 1 unit each time, we traverse over
    // the columns in terms of the width of the smallest brick encountered while traversing the current column. Thus,
    // we update pos array and sums appropriately depending on the width of the smallest brick. Rest of the process
    // remains the same as the first approach.
    // The optimization achieved can be viewed by considering this example:
    // [[100, 50, 50],
    //  [50, 100],
    //  [150]]
    // In this case, we directly jump over the columns in terms of widths of 50 units each time, rather than making
    // traversals over widths incrementing by 1 unit each time.
    // Time complexity: O(n∗m). In worst case, we traverse over the length(n) of the wall m times, where m is the height
    // of the wall.
    // Space complexity: O(m). pos array of size m is used, where m is the height of the wall
    fun approach2(wall: List<MutableList<Int>>): Int {
        var width = 0
        for (brick in wall[0])
            width += brick

        val bricks = IntArray(wall.size)
        var minCross = Int.MAX_VALUE
        while (width != 0) {
            var count = 0
            var minWidth = Int.MAX_VALUE
            for (i in wall.indices) {
                val row = wall[i]
                if (row[bricks[i]] != 0) {
                    count++
                } else {
                    bricks[i]++
                }
                minWidth = Math.min(minWidth, row[bricks[i]])
            }
            for (i in wall.indices) {
                val row = wall[i]
                row[bricks[i]] = row[bricks[i]] - minWidth
            }
            width -= minWidth
            minCross = minOf(minCross, count)
        }
        return minCross
    }

    // from hackmd.io
    // Use a HashMap to store number of gaps at column indices. Then, use the max gap value to calculate the least
    // number of bricks to cross in a vertical line.
    // 1) Create a HashMap to store the number of gaps at each index of a row.
    // 2) Iterate through each row within the wall
    // 3) Create a sum variable
    // 4) Iterate through the row, besides the last index
    //    a) At each point calculate the sum up until that index
    //    b) Index into the current sum key within the HashMap
    //    c) Increment that value to indicate there is one more gap at that index
    // 5) Calculate the index with the largest number of gaps from the HashMap
    //    a) The largest 'value' within the HashMap indicates the index with the highest number of gaps
    // 6) Return the height of the wall minus the highest gap value
    //    - This means the number of bricks you will cross in creating a line there
    // Time Complexity: O(N * M) where N = max bricks per row, M = number of rows
    // Space Complexity: O(N * M)
    fun codePath(wall: List<List<Int>>): Int {
        val map = mutableMapOf<Int, Int>()
        for (row in wall) {
            var total = 0
            for (brick in 0 until row.lastIndex) {
                total += brick
                map[total] = map.getOrDefault(total, 0) + 1
            }
        }
        return if (map.isEmpty()) // edge case
            wall.size
        else
            wall.size - map.values.maxOrNull()!!
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val data = arrayOf(
                listOf(listOf(1,2,2,1), listOf(3,1,2), listOf(1,3,2), listOf(2,4), listOf(3,1,2), listOf(1,3,1,1)),
                listOf(listOf(1,2,3), listOf(1,3,2), listOf(4,1,1)),
                listOf(listOf(3), listOf(1,1,1), listOf(2,1)),
                listOf(listOf(3,3,3)),
            )
            for (wall in data) {
                val bricks = BrickWall().leastBricks(wall)
                println("bricks: $bricks")
            }
        }
    }
}