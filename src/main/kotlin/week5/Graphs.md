##Graphs

###Introduction

Graphs are one of the most prevalent data structures in computer science. It's a powerful data structure that's utilized
to represent relationships between different types of data. In a graph, each data point is stored as a **node** and each
relationship between those data points is represented by an **edge.** For example, a social network is considered a graph
in which each person is considered a node and a friendship between two people is considered an edge.

Graphs are best utilized for problems in which there are binary relationships between objects. Once a problem can be
represented as a graph, the problem can generally be solved based off of one of the key graph algorithms. For interviews,
it is vital to know how to implement a graph, basic graph traversals (BFS, DFS) and how to sort topologically the graph.

###Graph Terminology

####Graph components

Graphs consist of a set of..
* **vertices,** which are also referred to as nodes
  * Nodes that are directly connected by an edge are commonly referred to as **neighbors.**
* **edges,** connections between pairs of vertices

![](https://i.imgur.com/Zq3wULu.png)

###Graph types

####Directed & undirected graphs

A **directed** graph is a graph that in which all edges are associated with a direction. An example of a directed edge
would be a one way street.

An **undirected** graph is a graph in which all edges do not have a direction. An example of this would be a friendship!

![](https://i.imgur.com/JWL96oK.png)

####Cyclic & acyclic graphs

Before going over the what cyclic and acyclic graphs are, there are two key terms to cover: **path** and **cycle**.
A **path** is a sequence of vertices connected by edges and a **cycle** a path whose first and last vertices are the same.

A **cyclic** graph means that there contains a least one cycle within the graph.

An **acyclic** graph has no cycles within it.

![](https://i.imgur.com/2z9J2E5.png)

A commonly used phrase when referring to graphs is a **directed acylic graph (DAG)**, which is a directed graph in which
there are no cycles. In a DAG, these two terms are commonly used to denote nodes with special properties:
* Sink nodes have no outgoing edges, only incoming edges
* Source nodes only have outgoing edges, no incoming edges

###Graph representations

####Adjacency lists

**Adjacency list** is the most common way to represent graphs. With this approach of representing a graph, each node
stores a list of its adjacent vertices. For undirected graphs, each edge from u to v would be stored twice: once in u's
list of neighbors and once in v's list of neighbors.

![](https://i.imgur.com/JwA2sxn.png)

####Edge sets/ lists

An **edge set** simply represents a graph as a collection of all its edges.

![](https://i.imgur.com/F2XET50.png)

####Adjacency matrix

An **adjacency matrix** represents a graph with n nodes as a n by n boolean matrix, in which matrix[u][v] is set to true
if an edge exists from node u to node v.

![](https://i.imgur.com/GzU4BKw.png)

The representation of a graph is efficient for checking if an edge exists between a pair of vertices. However, it may be
less efficient for search algorithms because it requires iterating through all the nodes in the graph to identify
a node's neighbors.

####Runtime Analysis

Below is a chart of the most common graph operations and their runtimes for each of the graph representations. In the
chart below, V represents the number of verticies in the graph and E represents the number of edges in the graph.

<pre>
Representation	    Getting all adjacent    Traversing	    hasEdge(u, v)	        Space
                    edges for a vertex      entire graph
Adjacency matrix    O(V)	                O(V2)	        O(1)	                O(V2)
Edge Set            O(E)	                O(E)	        O(E)	                O(E)
Adjacency List	    O(1)	                O(V + E)        O(max number of edges 	O(E + V)
                                                              a vertex has)
Credit: UC Berkeley data structures course
</pre>

###Glossary

1. vertex (node): used to represent a single data point
2. edge: a connection between a pair of vertices
3. neighbor: a neighbor node is a node that is directly connected to another node by an edge
4. directed graph: a graph in which all edges have direction
5. undirected graph: a graph in which all edges have no direction
6. path: a sequence of vertices connected by edges
7. cycle: a paththat begins and ends at the same vertex
8. cyclic graph: a graph which contains at least one cycle
9. acyclic graph: a graph whichdoes not contain a cycle
10. adjacency list: an approach to representing graphs in which each node stores a list of its adjacent vertices
11. edge set/list: an approach to representing graphs in which a graph is a collection of all its edges
12. adjacency matrix: an approach to representing graphs in which a graph with n nodes is storeed as an n by n boolean
    matrix, where matrix[u][v] is true if an edge exists between node u to node v.
13. sink nodes: in a DAG, a sink node has no outgoing edges
14. source nodes: in a DAG, a source node only has outgoing edges
15. directed acylic graph (DAG): a directed graph in which there are no cycles

###Extra graph algorithms

**NOTE:** This section covers algorithms that will generally not come up in interviews.

####Union find, disjoint sets

* Guide: https://www.hackerearth.com/practice/notes/disjoint-set-union-union-find/
* Interview question bank: https://leetcode.com/tag/union-find/

####Shortest paths algorithms

* Guide: https://www.hackerearth.com/practice/algorithms/graphs/shortest-path-algorithms/tutorial/
* Interview question bank: https://www.hackerearth.com/practice/algorithms/graphs/shortest-path-algorithms/practice-problems/

####Minimum spanning tree algorithms

* Guide: https://www.hackerearth.com/practice/algorithms/graphs/minimum-spanning-tree/tutorial/
* Interview question bank: https://www.hackerearth.com/practice/algorithms/graphs/minimum-spanning-tree/practice-problems/