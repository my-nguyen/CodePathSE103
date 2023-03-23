You are given an m x n grid where each cell can have one of three values:

* 0 representing an empty cell,
* 1 representing a fresh orange, or
2 representing a rotten orange.

Every minute, any fresh orange that is **4-directionally adjacent** to a rotten orange becomes rotten.

Return the minimum number of minutes that must elapse until no cell has a fresh orange. If this is impossible, return -1.

###Example 1:
![](https://assets.leetcode.com/uploads/2019/02/16/oranges.png)
**Input**: grid = [[2,1,1],[1,1,0],[0,1,1]]  
**Output**: 4

###Example 2:
**Input**: grid = [[2,1,1],[0,1,1],[1,0,1]]  
**Output**: -1  
**Explanation**: The orange in the bottom left corner (row 2, column 0) is never rotten, because rotting only happens
4-directionally.

###Example 3:
**Input**: grid = [[0,2]]  
**Output**: 0  
**Explanation**: Since there are already no fresh oranges at minute 0, the answer is just 0.

###Constraints:

* m == grid.length
* n == grid[i].length
* 1 <= m, n <= 10
* grid[i][j] is 0, 1, or 2.

#Solution

##Approach 1: Breadth-First Search (BFS)

###Intuition

This is yet another 2D traversal problem. As we know, the common algorithmic strategies to deal with these problems
would be **Breadth-First Search** (BFS) and **Depth-First Search** (DFS).

As suggested by its name, the BFS strategy prioritizes the **breadth** over depth, i.e. it goes wider before it goes
deeper. On the other hand, the DFS strategy prioritizes the **depth** over breadth.

The choice of strategy depends on the nature of the problem. Though sometimes, they are both applicable for the same
problem. In addition to 2D grids, these two algorithms are often applied to problems associated with tree or graph data
structures as well.

In this problem, one can see that BFS would be a better fit.

Because the process of rotting could be explained perfectly with the BFS procedure, i.e. the rotten oranges will
contaminate their neighbors first, before the contamination propagates to other fresh oranges that are farther away.

If one is not familiar with the algorithm of BFS, one can refer to our
[Explore card of Queue & Stack](https://leetcode.com/explore/learn/card/queue-stack/231/practical-application-queue/)
which covers this subject.

However, it would be more intuitive to visualize the rotting process with a graph data structure, where each node
represents a cell and the edge between two nodes indicates that the given two cells are adjacent to each other.

![](https://leetcode.com/problems/rotting-oranges/Figures/994/994_grid_graph.png)

In the above graph (pun intended), as we can see, starting from the top rotten orange, the contamination would propagate
layer by layer (or level by level), until it reaches the farthest fresh oranges. The number of minutes that are elapsed
would be equivalent to the number of levels in the graph that we traverse during the propagation.

###Algorithm

One of the most distinguished code patterns in BFS algorithms is that often we use a queue data structure to keep track
of the candidates that we need to visit during the process.

The main algorithm is built around a loop iterating through the queue. At each iteration, we pop out an element from
the head of the queue. Then we do some particular process with the popped element. More importantly, we then append
neighbors of the popped element into the queue, to keep the BFS process running.

In the above implementations, we applied some tricks to further optimize both the time and space complexities.

* Usually in BFS algorithms, we keep a visited table which records the visited candidates. The visited table helps us
  to avoid repetitive visits.

* But as one notices, rather than using the visited table, we reuse the input grid to keep track of our visits, i.e.
  we were altering the status of the input grid **in-place**.

* This in-place technique reduces the memory consumption of our algorithm. Also, it has a constant time complexity
  to check the current status (i.e. array access, grid[row][col]), rather than referring to the visited table which
  might be of constant time complexity as well (e.g. hash table) but in reality could be slower than array access.

* We use a **delimiter** (i.e. (row=-1, col=-1)) in the queue to separate cells on different levels. In this way, we only
  need one queue for the iteration. As an alternative, one can create a queue for each level and alternate between
  the queues, though technically the initialization and the assignment of each queue could consume some extra time.

##Approach 2: In-place BFS

###Intuition

Although there is no doubt that the best strategy for this problem is BFS, some users in the Discussion forum have
proposed different implementations of BFS with constant space complexity O(1). To name just a few, one can see the posts
from [@manky](https://leetcode.com/problems/rotting-oranges/discuss/569248/Alternate-approach-BFS-O(N-*-Height)-but-constant-space-easy-to-understand-and-modular-code)
and [@votrubac](https://leetcode.com/problems/rotting-oranges/discuss/238579/C%2B%2BJava-with-picture-BFS).

As one might recall from the previous BFS implementation, its space complexity is mainly due to the queue that we were
using to keep the order for the visits of cells. In order to achieve O(1) space complexity, we then need to eliminate
the queue in the BFS.

The secret in doing BFS traversal without a queue lies in the technique called
[in-place algorithm](https://en.wikipedia.org/wiki/In-place_algorithm), which transforms input to solve the problem
without using auxiliary data structure.

Actually, we have already had a taste of in-place algorithm in the previous implementation of BFS, where we directly
modified the input grid to mark the oranges that turn rotten, rather than using an additional visited table.

How about we apply the in-place algorithm again, but this time for the role of the queue variable in our previous BFS
implementation?

The idea is that at each **round** of the BFS, we mark the cells to be visited in the input grid with a specific timestamp.

By round, we mean a snapshot in time where a group of oranges turns rotten.

###Algorithm

![](https://leetcode.com/problems/rotting-oranges/Figures/994/994_timestamp_I.png)

![](https://leetcode.com/problems/rotting-oranges/Figures/994/994_timestamp_II.png)

In the above graph, we show how we manipulate the values in the input grid in-place in order to run the BFS traversal.

1) Starting from the beginning (with timestamp=2), the cells that are marked with the value 2 contain rotten oranges.
   From this moment on, we adopt a **rule** stating as "the cells that have the value of the current timestamp (i.e. 2)
   should be visited at this round of BFS.".

2) For each of the cell that is marked with the current timestamp, we then go on to mark its neighbor cells that hold
   a fresh orange with the **next** timestamp (i.e. timestamp += 1). This **in-place** modification serves the same
   purpose as the queue variable in the previous BFS implementation, which is to select the candidates to visit for
   the next round.

3) At this moment, we should have timestamp=3, and meanwhile we also have the cells to be visited at this round marked
   out. We then repeat the above step (2) until there is no more new candidates generated in the step (2) (i.e. the end
   of BFS traversal).

To summarize, the above algorithm is still a BFS traversal in a 2D grid. But rather than using a queue data structure
to keep track of the visiting order, we applied an in-place algorithm to serve the same purpose as a queue in a more
classic BFS implementation.

