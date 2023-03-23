Given a directed graph, check whether the graph contains a cycle or not. Your function should return true if the given
graph contains at least one cycle, else return false.

####Example1:
**Input**: n = 4, e = 6  
0 -> 1, 0 -> 2, 1 -> 2, 2 -> 0, 2 -> 3, 3 -> 3  
**Output**: Yes
####Diagram:

![](https://media.geeksforgeeks.org/wp-content/uploads/20200429134027/Untitled-Diagram1411.png)

The diagram clearly shows a cycle 0 -> 2 -> 0

####Example2:
**Input**: n = 4, e = 4  
0 -> 1, 0 -> 2, 1 -> 2, 2 -> 3  
**Output**: No
####Diagram:

![](https://media.geeksforgeeks.org/wp-content/uploads/20200429134054/Untitled-Diagram151.png)

The diagram clearly shows no cycle

Approach: Depth First Traversal can be used to detect a cycle in a Graph. DFS for a connected graph produces a tree.
There is a cycle in a graph only if there is a back edge present in the graph. A back edge is an edge that is from a node
to itself (self-loop) or one of its ancestors in the tree produced by DFS. In the following graph, there are 3 back
edges, marked with a cross sign. We can observe that these 3 back edges indicate 3 cycles present in the graph.

![](https://media.geeksforgeeks.org/wp-content/uploads/cycle.png)

For a disconnected graph, Get the DFS forest as output. To detect cycle, check for a cycle in individual trees by
checking back edges.

To detect a back edge, keep track of vertices currently in the recursion stack of function for DFS traversal. If a vertex
is reached that is already in the recursion stack, then there is a cycle in the tree. The edge that connects the current
vertex to the vertex in the recursion stack is a back edge. Use recStack[] array to keep track of vertices in the
recursion stack.

Dry run of the above approach:

![](https://media.geeksforgeeks.org/wp-content/cdn-uploads/20190704130006/DetectCycleInaDirectedGraph.png)

####Algorithm:
1. Create the graph using the given number of edges and vertices.
2. Create a recursive function that initializes the current index or vertex, visited, and recursion stack.
3. Mark the current node as visited and also mark the index in recursion stack.
4. Find all the vertices which are not visited and are adjacent to the current node. Recursively call the function for
   those vertices, If the recursive function returns true, return true.
5. If the adjacent vertices are already marked in the recursion stack then return true.
6. Create a wrapper class, that calls the recursive function for all the vertices and if any function returns true return
   true. Else if for all vertices the function returns false return false.

##Tarjan's strongly connected components algorithm

Tarjan's strongly connected components algorithm is an algorithm in graph theory for finding the strongly connected
components (SCCs) of a directed graph. It runs in linear time, matching the time bound for alternative methods including
Kosaraju's algorithm and the path-based strong component algorithm. The algorithm is named for its inventor, Robert Tarjan.

###Overview
The algorithm takes a directed graph as input, and produces a partition of the graph's vertices into the graph's strongly
connected components. Each vertex of the graph appears in exactly one of the strongly connected components. Any vertex
that is not on a directed cycle forms a strongly connected component all by itself: for example, a vertex whose in-degree
or out-degree is 0, or any vertex of an acyclic graph.

The basic idea of the algorithm is this: a depth-first search (DFS) begins from an arbitrary start node (and subsequent
depth-first searches are conducted on any nodes that have not yet been found). As usual with depth-first search,
the search visits every node of the graph exactly once, declining to revisit any node that has already been visited.
Thus, the collection of search trees is a spanning forest of the graph. The strongly connected components will be
recovered as certain subtrees of this forest. The roots of these subtrees are called the "roots" of the strongly
connected components. Any node of a strongly connected component might serve as a root, if it happens to be the first
node of a component that is discovered by search.

###Stack invariant
Nodes are placed on a stack in the order in which they are visited. When the depth-first search recursively visits a node
v and its descendants, those nodes are not all necessarily popped from the stack when this recursive call returns.
The crucial invariant property is that a node remains on the stack after it has been visited if and only if there exists
a path in the input graph from it to some node earlier on the stack. In other words, it means that in the DFS a node
would be only removed from the stack after all its connected paths have been traversed. When the DFS will backtrack
it would remove the nodes on a single path and return to the root in order to start a new path.

At the end of the call that visits v and its descendants, we know whether v itself has a path to any node earlier on
the stack. If so, the call returns, leaving v on the stack to preserve the invariant. If not, then v must be the root
of its strongly connected component, which consists of v together with any nodes later on the stack than v (such nodes
all have paths back to v but not to any earlier node, because if they had paths to earlier nodes then v would also have
paths to earlier nodes which is false). The connected component rooted at v is then popped from the stack and returned,
again preserving the invariant.

###Bookkeeping
Each node v is assigned a unique integer v.index, which numbers the nodes consecutively in the order in which they are
discovered. It also maintains a value v.lowlink that represents the smallest index of any node known to be reachable from
v through v's DFS subtree, including v itself. Therefore v must be left on the stack if v.lowlink < v.index, whereas v
must be removed as the root of a strongly connected component if v.lowlink == v.index. The value v.lowlink is computed
during the depth-first search from v, as this finds the nodes that are reachable from v.

##The algorithm in pseudocode

<pre>
algorithm tarjan is
   input: graph G = (V, E)
   output: set of strongly connected components (sets of vertices)

    index := 0
    S := empty stack
    for each v in V do
        if v.index is undefined then
            strongconnect(v)
        end if
    end for
   
    function strongconnect(v)
        // Set the depth index for v to the smallest unused index
        v.index := index
        v.lowlink := index
        index := index + 1
        S.push(v)
        v.onStack := true
      
        // Consider successors of v
        for each (v, w) in E do
            if w.index is undefined then
                // Successor w has not yet been visited; recurse on it
                strongconnect(w)
                v.lowlink := min(v.lowlink, w.lowlink)
            else if w.onStack then
                // Successor w is in stack S and hence in the current SCC
                // If w is not on stack, then (v, w) is an edge pointing to an SCC already found and must be ignored
                // Note: The next line may look odd - but is correct.
                // It says w.index not w.lowlink; that is deliberate and from the original paper
                v.lowlink := min(v.lowlink, w.index)
            end if
        end for
      
        // If v is a root node, pop the stack and generate an SCC
        if v.lowlink = v.index then
            start a new strongly connected component
            repeat
                w := S.pop()
                w.onStack := false
                add w to current strongly connected component
            while w ≠ v
            output the current strongly connected component
        end if
    end function
</pre>