We start at some node in a directed graph, and every turn, we walk along a directed edge of the graph. If we reach
a terminal node (that is, it has no outgoing directed edges), we stop.

We define a starting node to be **safe** if we must eventually walk to a terminal node. More specifically, there is a
natural number k, so that we must have stopped at a terminal node in less than k steps for **any choice of where to walk**.

Return an array containing all the safe nodes of the graph. The answer should be sorted in **ascending** order.

The directed graph has n nodes with labels from 0 to n - 1, where n is the length of graph. The graph is given in
the following form: graph[i] is a list of labels j such that (i, j) is a directed edge of the graph, going from node i
to node j.

###Example 1:
![](https://s3-lc-upload.s3.amazonaws.com/uploads/2018/03/17/picture1.png)
**Input**: graph = [[1,2],[2,3],[5],[0],[5],[],[]]  
**Output**: [2,4,5,6]  
**Explanation**: The given graph is shown above.

###Example 2:
**Input**: graph = [[1,2,3,4],[1,2],[3,4],[0,4],[]]  
**Output**: [4]

###Constraints:

* n == graph.length
* 1 <= n <= 104
* 0 <= graph[i].length <= n
* graph[i] is sorted in a strictly increasing order.
* The graph may contain self-loops.
* The number of edges in the graph will be in the range [1, 4 * 104].

#Approach #1: Reverse Edges

##Intuition

The crux of the problem is whether you can reach a cycle from the node you start in. If you can, then there is a way to
avoid stopping indefinitely; and if you can't, then after some finite number of steps you'll stop.

Thinking about this property more, a node is eventually safe if all it's outgoing edges are to nodes that are eventually
safe.

This gives us the following idea: we start with nodes that have no outgoing edges - those are eventually safe. Now,
we can update any nodes which only point to eventually safe nodes - those are also eventually safe. Then, we can update
again, and so on.

However, we'll need a good algorithm to make sure our updates are efficient.

##Algorithm

We'll keep track of graph, a way to know for some node i, what the outgoing edges (i, j) are. We'll also keep track
of rgraph, a way to know for some node j, what the incoming edges (i, j) are.

Now for every node j which was declared eventually safe, we'll process them in a queue. We'll look at all parents
i = rgraph[j] and remove the edge (i, j) from the graph (from graph). If this causes the graph to have no outgoing edges
graph[i], then we'll declare it eventually safe and add it to our queue.

Also, we'll keep track of everything we ever added to the queue, so we can read off the answer in sorted order later.

#Approach #2: Depth-First Search

##Intuition

As in Approach #1, the crux of the problem is whether you reach a cycle or not.

Let us perform a "brute force": a cycle-finding DFS algorithm on each node individually. This is a classic
"white-gray-black" DFS algorithm that would be part of any textbook on DFS. We mark a node gray on entry, and black
on exit. If we see a gray node during our DFS, it must be part of a cycle. In a naive view, we'll clear the colors
between each search.

##Algorithm

We can improve this approach, by noticing that we don't need to clear the colors between each search.

When we visit a node, the only possibilities are that we've marked the entire subtree black (which must be eventually
safe), or it has a cycle and we have only marked the members of that cycle gray. So indeed, the invariant that gray
nodes are always part of a cycle, and black nodes are always eventually safe is maintained.

In order to exit our search quickly when we find a cycle (and not paint other nodes erroneously), we'll say the result
of visiting a node is true if it is eventually safe, otherwise false. This allows information that we've reached a cycle
to propagate up the call stack so that we can terminate our search early.
