There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1. You are given an array
prerequisites where prerequisites[i] = [ai, bi] indicates that you **must** take course bi first if you want to take
course ai.

* For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.

Return true if you can finish all courses. Otherwise, return false.

###Example 1:
**Input**: numCourses = 2, prerequisites = [[1,0]]  
**Output**: true  
**Explanation**: There are a total of 2 courses to take.
To take course 1 you should have finished course 0. So it is possible.

###Example 2:
**Input**: numCourses = 2, prerequisites = [[1,0],[0,1]]  
**Output**: false  
**Explanation**: There are a total of 2 courses to take.
To take course 1 you should have finished course 0, and to take course 0 you should also have finished course 1. So it is impossible.

###Constraints:

* 1 <= numCourses <= 105
* 0 <= prerequisites.length <= 5000
* prerequisites[i].length == 2
* 0 <= ai, bi < numCourses
* All the pairs prerequisites[i] are **unique**.

#Solution

##Approach 1: Backtracking

###Intuition

The problem could be modeled as yet another graph traversal problem, where each course can be represented as a vertex
in a graph and the dependency between the courses can be modeled as a directed edge between two vertex.

And the problem to determine if one could build a valid schedule of courses that satisfies all the dependencies
(i.e. constraints) would be equivalent to determine if the corresponding graph is a DAG (Directed Acyclic Graph),
i.e. there is no cycle existed in the graph.

![](https://leetcode.com/problems/course-schedule/Figures/207/207_graph.png)

A typical strategy for graph traversal problems would be
[backtracking](https://leetcode.com/explore/learn/card/recursion-ii/472/backtracking/) or simply DFS (depth-first search).

Here let us start with the backtracking algorithm, which arguably might be more intuitive.

As a reminder, [backtracking](https://en.wikipedia.org/wiki/Backtracking) is a general algorithm that is often applied
to solve the [constraint satisfaction problems](https://en.wikipedia.org/wiki/Constraint_satisfaction_problem), which
incrementally builds candidates to the solutions, and abandons a candidate (i.e. backtracks) as soon as it determines
that the candidate would not lead to a valid solution.

The general idea here is that we could enumerate each course (vertex), to check if it could form cyclic dependencies
(i.e. a cyclic path) starting from this course.

The check of cyclic dependencies for each course could be done via **backtracking**, where we incrementally follow
the dependencies until either there is no more dependency or we come across a previously visited course along the path.

###Algorithm

The overall structure of the algorithm is simple, which consists of three main steps:

* Step 1). we build a graph data structure from the given list of course dependencies. Here we adopt the adjacency list
  data structure as shown below to represent the graph, which can be implemented via hashmap or dictionary. Each entry
  in the adjacency list represents a node which consists of a node index and a list of neighbors nodes that follow from
  the node.
  ![](https://leetcode.com/problems/course-schedule/Figures/207/207_adjacency_list.png)
* Step 2). we then enumerate each node (course) in the constructed graph, to check if we could form a dependency cycle
  starting from the node.
* Step 3). we perform the cyclic check via backtracking, where we **breadcrumb** our path (i.e. mark the nodes we visited)
  to detect if we come across a previously visited node (hence a cycle detected). We also remove the breadcrumbs for
  each iteration.

##Approach 2: Postorder DFS (Depth-First Search)

###Intuition

As one might notice that, with the above backtracking algorithm, we would visit certain nodes multiple times, which is
not the most efficient way.

![](https://leetcode.com/problems/course-schedule/Figures/207/207_chain.png)

For instance, in the above graph where the nodes are chained up in a line, the backtracking algorithm would end up of
being a nested two-level iteration over the nodes, which we could rewrite as the following pseudo code:

<pre>
for i in range(0, len(nodes)):
    # start from the current node to check if a cycle might be formed.
    for j in range(i, len(nodes)):
        isCyclic(nodes[j], courseDict, path)
</pre>

One might wonder that if there is a better algorithm that visits each node once and only once. And the answer is yes.

In the above example, for the first node in the chain, once we've done the check that there would be no cycle formed
starting from this node, we don't have to do the same check for all the nodes in the downstream.

The rationale is that given a node, if the subgraph formed by all descendant nodes from this node has no cycle, then
adding this node to the subgraph would not form a cycle either.

From the perspective of graph traversal, the above rationale could be implemented with the strategy of **postorder DFS**
(depth-first search), in which strategy we visit a node's descendant nodes before the node itself.

###Algorithm

We could implement the postorder DFS based on the above backtracking algorithm, by simply adding another bitmap
(i.e. checked[node_index]) which indicates whether we have done the cyclic check starting from a particular node.

Here are the breakdowns of the algorithm, where the first 2 steps are the same as in the previous backtracking algorithm.

* Step 1). We build a graph data structure from the given list of course dependencies.
* Step 2). We then enumerate each node (course) in the constructed graph, to check if we could form a dependency cycle
  starting from the node.
* Step 3.1). We check if the current node has been checked before, otherwise we enumerate through its child nodes via
  backtracking, where we **breadcrumb** our path (i.e. mark the nodes we visited) to detect if we come across a previously
  visited node (hence a cycle detected). We also remove the breadcrumbs for each iteration.
* Step 3.2). Once we visited all the child nodes (i.e. postorder), we mark the current node as checked.
