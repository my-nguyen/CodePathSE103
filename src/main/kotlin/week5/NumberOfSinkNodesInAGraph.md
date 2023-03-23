Given a Directed Acyclic Graph of n nodes (numbered from 1 to n) and m edges. The task is to find the number of sink nodes.
A sink node is a node such that no edge emerges out of it.

####Examples:

Input: n = 4, m = 2  
Edges[] = {{2, 3}, {4, 3}}  
Output: 2

![](https://media.geeksforgeeks.org/wp-content/uploads/sinkNodes.jpg)

Only node 1 and node 3 are sink nodes.

Input: n = 4, m = 2  
Edges[] = {{3, 2}, {3, 4}}  
Output: 3

The idea is to iterate through all the edges. And for each edge, mark the source node from which the edge emerged out.
Now, for each node check if it is marked or not. And count the unmarked nodes.

Algorithm:
1. Make any array A[] of size equal to the number of nodes and initialize to 1.
2. Traverse all the edges one by one, say, u -> v.
   (i) Mark A[u] as 1.
3. Now traverse whole array A[] and count number of unmarked nodes.
