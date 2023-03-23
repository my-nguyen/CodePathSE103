##Number of Connected Components in an Undirected Graph

You have a graph of n nodes. You are given an integer n and an array edges where edges[i] = [ai, bi] indicates that
there is an edge between ai and bi in the graph.

Return the number of connected components in the graph.

###Example 1:

![](https://assets.leetcode.com/uploads/2021/03/14/conn1-graph.jpg)

**Input**: n = 5, edges = [[0,1],[1,2],[3,4]]
**Output**: 2

###Example 2:

![](https://assets.leetcode.com/uploads/2021/03/14/conn2-graph.jpg)

**Input**: n = 5, edges = [[0,1],[1,2],[2,3],[3,4]]
**Output**: 1

###Constraints:

* 1 <= n <= 2000
* 1 <= edges.length <= 5000
* edges[i].length == 2
* 0 <= ai <= bi < n
* ai != bi
* There are no repeated edges.

#Solution

##Approach 1: Depth-First Search (DFS)

###Intuition

If you're not familiar with DFS, check out our Explore Card.

In an undirected graph, a connected component is a subgraph in which each pair of vertices is connected via a path.
So essentially, all vertices in a connected component are reachable from one another.

Let's see how we can use DFS to solve the problem. If we run DFS, starting from a particular vertex, it will continue to
visit the vertices depth-wise until there are no more adjacent vertices left to visit. Thus, it will visit all of
the vertices within the connected component that contains the starting vertex. Each time we finish exploring a connected
component, we can find another vertex that has not been visited yet, and start a new DFS from there. The number of times
we start a new DFS will be the number of connected components.

Here is an example illustrating this approach.

![](https://leetcode.com/problems/number-of-connected-components-in-an-undirected-graph/Figures/323/DFS.png)

###Algorithm

1. Create an adjacency list such that adj[v] contains all the adjacent vertices of vertex v.
2. Initialize a hashmap or array, visited, to track the visited vertices.
3. Define a counter variable and initialize it to zero.
4. Iterate over each vertex in edges, and if the vertex is not already in visited, start a DFS from it. Add every vertex
   visited during the DFS to visited.
5. Every time a new DFS starts, increment the counter variable by one.
6. At the end, the counter variable will contain the number of connected components in the undirected graph.

###Complexity Analysis

Here E = Number of edges, V = Number of vertices.

* Time complexity: O(E+V).

  Building the adjacency list will take O(E) operations, as we iterate over the list of edges once, and insert each edge
  into two lists.

  During the DFS traversal, each vertex will only be visited once. This is because we mark each vertex as visited as soon
  as we see it, and then we only visit vertices that are not marked as visited. In addition, when we iterate over the edge
  list of each vertex, we look at each edge once. This has a total cost of O(E+V).

* Space complexity: O(E+V).

  Building the adjacency list will take O(E) space. To keep track of visited vertices, an array of size O(V) is required.
  Also, the run-time stack for DFS will use O(V) space.

##Approach 2: Disjoint Set Union (DSU)

Imagine we have a graph with N vertices and 0 edges. The number of connected components will be N in that graph.

![](https://leetcode.com/problems/number-of-connected-components-in-an-undirected-graph/Figures/323/DSU-1.png)

Let's now add the edge from vertex 1 to vertex 2. This will decrease the number of components by 1. This is because
vertices 1 and 2 are now in the same component.

![](https://leetcode.com/problems/number-of-connected-components-in-an-undirected-graph/Figures/323/DSU-2.png)

When we then add the edge from vertex 2 to vertex 3, the number of components will decrease by 1 again.

![](https://leetcode.com/problems/number-of-connected-components-in-an-undirected-graph/Figures/323/DSU-3.png)

However, this pattern will not continue when we add the edge from vertex 1 to vertex 3. The number of components will not
change because vertices 1, 2, and 3 are already in the same component.

![](https://leetcode.com/problems/number-of-connected-components-in-an-undirected-graph/Figures/323/DSU-4.png)

The above observation is the main intuition behind the DSU approach.

###Algorithm

1. Initialize a variable count with the number of vertices in the input.
2. Traverse all of the edges one by one, performing the union-find method combine on each edge. If the endpoints are
   already in the same set, then keep traversing. If they are not, then decrement count by 1.
3. After traversing all of the edges, the variable count will contain the number of components in the graph.

###Complexity Analysis

Here E = Number of edges, V = Number of vertices.

* Time complexity: O(E * α(n)).

  Iterating over every edge requires O(E) operations, and for every operation, we are performing the combine method
  which is O(α(n)), where α(n) is the inverse Ackermann function.

* Space complexity: O(V).

  Storing the representative/immediate-parent of each vertex takes O(V) space. Furthermore, storing the size of components
  also takes O(V) space.

