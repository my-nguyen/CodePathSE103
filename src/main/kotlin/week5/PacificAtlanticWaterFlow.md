There is an m x n rectangular island that borders both the **Pacific Ocean** and **Atlantic Ocean**. The **Pacific Ocean**
touches the island's left and top edges, and the **Atlantic Ocean** touches the island's right and bottom edges.

The island is partitioned into a grid of square cells. You are given an m x n integer matrix heights where heights[r][c]
represents the **height above sea level** of the cell at coordinate (r, c).

The island receives a lot of rain, and the rain water can flow to neighboring cells directly north, south, east, and west
if the neighboring cell's height is **less than or equal to** the current cell's height. Water can flow from any cell
adjacent to an ocean into the ocean.

Return a **2D list** of grid coordinates result where result[i] = [ri, ci] denotes that rain water can flow from
cell (ri, ci) to **both** the Pacific and Atlantic oceans.

####Example 1:

![](https://assets.leetcode.com/uploads/2021/06/08/waterflow-grid.jpg)

**Input**: heights = [[1,2,2,3,5],[3,2,3,4,4],[2,4,5,3,1],[6,7,1,4,5],[5,1,1,2,4]]  
**Output**: [[0,4],[1,3],[1,4],[2,2],[3,0],[3,1],[4,0]]

####Example 2:
**Input**: heights = [[2,1],[1,2]]  
**Output**: [[0,0],[0,1],[1,0],[1,1]]

####Constraints:

* m == heights.length
* n == heights[r].length
* 1 <= m, n <= 200
* 0 <= heights[r][c] <= 10<sup>5</sup>

#Solution

##Overview

Matrices such as this one are a type of **graph** representation. Standard graph traversal algorithms such as BFS and DFS
can be used to solve this problem. If you aren't familiar with these algorithms, check out the 
[Explore Card on BFS](https://leetcode.com/explore/learn/card/queue-stack/231/practical-application-queue/).

The naive approach would be to check every cell - that is, iterate through every cell, and at each one, start a traversal
that follows the problem's conditions. That is, find every cell that manages to reach both oceans.

This approach, however, is extremely slow, as it repeats a ton of computation. Instead of looking for every path
from cell to ocean, let's start at the oceans and try to work our way to the cells. This will be much faster because
when we start a traversal at a cell, whatever result we end up with can be applied to only that cell. However, when
we start from the ocean and work backwards, we already know that every cell we visit must be connected to the ocean.

##Approach 1: Breadth First Search (BFS)

###Intuition

If we start traversing from the ocean and flip the condition (check for higher height instead of lower height), then
we know that every cell we visit during the traversal can flow into that ocean. Let's start a BFS traversal from
every cell that is immediately beside the Pacific ocean, and figure out what cells can flow into the Pacific. Then,
let's do the exact same thing with the Atlantic ocean. At the end, the cells that end up connected to both oceans
will be our answer.

###Algorithm

1. If the input is empty, immediately return an empty array.

2. Initialize variables that we will use to solve the problem:

   * Number of rows and columns in our matrix;
   * 2 queues, one for the Atlantic Ocean and one for the Pacific Ocean that will be used for BFS;
   * 2 data structures, again one for each ocean, that we'll use to keep track of cells we already visited, to avoid
     infinite loops;
   * A small array [(0, 1), (1, 0), (-1, 0), (0, -1)] that will help with BFS.

3. Figure out all the cells that are adjacent to each ocean, and fill the respective data structures with them.

4. Perform BFS from each ocean. The data structure used to keep track of cells already visited has a double purpose -
   it also contains every cell that can flow into that ocean.

5. Find the intersection, that is all cells that can flow into both oceans.

###Complexity Analysis

* Time complexity: O(M * N), where M is the number of rows and N is the number of columns.

  In the worst case, such as a matrix where every value is equal, we would visit every cell twice. This is because
  we perform 2 traversals, and during each traversal, we visit each cell exactly once. There are M*N cells total,
  which gives us a time complexity of O(2*M*N) = O(M*N).

* Space complexity: O(M * N), where M is the number of rows and N is the number of columns.

  The extra space we use comes from our queues, and the data structure we use to keep track of what cells have been
  visited. Similar to the time complexity, for a given ocean, the amount of space we will use scales linearly with
  the number of cells. For example, in the Java implementation, to keep track of what cells have been visited, we simply
  used 2 matrices that have the same dimensions as the input matrix.

  The same logic follows for the queues - we can't have more cells in the queue than there are cells in the matrix!

##Approach 2: Depth First Search (DFS)

Intuitively, BFS makes more sense for this problem since water flows in the same manner. However, we can also use DFS,
and it doesn't really make much of a difference. So, if you prefer DFS, then that's perfectly fine for this problem.
Additionally, it's possible that your interviewer will ask you to implement the problem recursively instead of iteratively.
Recursion must be DFS, not BFS.

###Algorithm

DFS is very similar to BFS. Instead of using a queue and working iteratively, we'll use recursion. Our dfs method will be
called for every reachable cell. Note: we could also work iteratively with DFS, in which case we would simply use a stack
instead of a queue like in the Approach 1 code, with mostly everything else being identical to the BFS approach.

###Complexity Analysis

* Time complexity: O(M * N), where M is the number of rows and N is the number of columns.

  Similar to approach 1. The dfs function runs exactly once for each cell accessible from an ocean.

* Space complexity: O(M * N), where M is the number of rows and N is the number of columns.

  Similar to approach 1. Space that was used by our queues is now occupied by dfs calls on the recursion stack.

