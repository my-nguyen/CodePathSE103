#Regions Cut By Slashes

An n x n grid is composed of 1 x 1 squares where each 1 x 1 square consists of a '/', '\', or blank space ' '. These
characters divide the square into contiguous regions.

Given the grid grid represented as a string array, return the number of regions.

Note that backslash characters are escaped, so a '\' is represented as '\\'.

###Example 1:
![](https://assets.leetcode.com/uploads/2018/12/15/1.png)

**Input**: grid = [" /","/ "]  
**Output**: 2

###Example 2:
![](https://assets.leetcode.com/uploads/2018/12/15/2.png)

**Input**: grid = [" /","  "]  
**Output:** 1

###Example 3:
![](https://assets.leetcode.com/uploads/2018/12/15/3.png)

**Input**: grid = ["\\/","/\\"]  
**Output:** 4  
**Explanation:** (Recall that because \ characters are escaped, "\\/" refers to \/, and "/\\" refers to /\.)

###Example 4:
![](https://assets.leetcode.com/uploads/2018/12/15/4.png)

**Input**: grid = ["/\\","\\/"]  
**Output:** 5  
**Explanation**: (Recall that because \ characters are escaped, "\\/" refers to \/, and "/\\" refers to /\.)

###Example 5:
![](https://assets.leetcode.com/uploads/2018/12/15/5.png)

**Input**: grid = ["//","/ "]  
**Output**: 3

###Constraints:

* n == grid.length
* n == grid[i].length
* 1 <= n <= 30
* grid[i][j] is either '/', '\', or ' '.

#Solution

##Approach 1: Union-Find

###Intuition

To find the number of components in a graph, we can use either depth-first search or union find. The main difficulty
with this problem is in specifying the graph.

One "brute force" way to specify the graph is to associate each grid square with 4 nodes (north, south, west, and east),
representing 4 triangles inside the square if it were to have both slashes. Then, we can connect all 4 nodes if the grid
square is " ", and connect two pairs if the grid square is "/" or "". Finally, we can connect all neighboring nodes
(for example, the east node of the square at grid[0][0] connects with the west node of the square at grid[0][1]).

This is the most straightforward approach, but there are other approaches that use less nodes to represent the underlying
information.

###Algorithm

Create 4*N*N nodes, one for each grid square, and connect them as described above. After, we use a union find structure
to find the number of connected components.

We will skip the explanation of how a DSU structure is implemented. Please refer to
https://leetcode.com/problems/redundant-connection/solution/ for a tutorial on DSU.