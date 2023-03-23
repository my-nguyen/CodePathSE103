##Topological Sort

###Introduction

Aside from DFS and BFS, the most common graph concept that interviews will test is topological sorting. Topological
sorting produces a linear ordering of nodes in a directed graph such that the direction of edges is respected.

A topological sort is an ordering of nodes for a directed acyclic graph (DAG) such that for every directed edge uv
from vertex u to vertex v, u comes before v in the ordering.

###Example

An application of this algorithm is ordering a sequence of tasks given their dependencies on other tasks. In this
application, there is an directed edge from u to v if task u must be completed before task v can start. For example,
when cooking, we need to turn on the oven (task u) before we can bake the cookies (task v).

![](https://i.imgur.com/Q3MA6dZ.png)

###Implementation:

Topological sort is simply a modification of DFS. Topological sort simply involves running DFS on an entire graph and
adding each node to the global ordering of nodes only after all of a node's children are visited. This ensures that
parent nodes will be ordered before their child nodes honoring the forward direction of edges in the ordering.

###Graph with no cycles

<pre>
from collections import deque

def top_sort(graph):
    sorted_nodes, visited = deque(), set()
    for node in graph.keys():
        if node not in visited:
            dfs(graph, node, visited, sorted_nodes)
    return list(sorted_nodes)

def dfs(graph, start_node, visited, sorted_nodes):
    visited.add(start_node)
    if start_node in graph:
        neighbors = graph[start_node]
        for neighbor in neighbors:
            if neighbor not in visited:
                dfs(graph, neighbor, visited, sorted_nodes)
    sorted_nodes.appendleft(start_node)
</pre>