A **transformation sequence** from word beginWord to word endWord using a dictionary wordList is a sequence of words
beginWord -> s1 -> s2 -> ... -> sk such that:

* Every adjacent pair of words differs by a single letter.
* Every si for 1 <= i <= k is in wordList. Note that beginWord does not need to be in wordList.
* sk == endWord

Given two words, beginWord and endWord, and a dictionary wordList, return all the **shortest transformation sequences**
from beginWord to endWord, or an empty list if no such sequence exists. Each sequence should be returned as a list of
the words [beginWord, s1, s2, ..., sk].

####Example 1:
**Input**: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]  
**Output**: [["hit","hot","dot","dog","cog"],["hit","hot","lot","log","cog"]]  
**Explanation**: There are 2 shortest transformation sequences:  
"hit" -> "hot" -> "dot" -> "dog" -> "cog"  
"hit" -> "hot" -> "lot" -> "log" -> "cog"

####Example 2:
**Input**: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log"]  
**Output**: []  
**Explanation**: The endWord "cog" is not in wordList, therefore there is no valid transformation sequence.


####Constraints:
* 1 <= beginWord.length <= 5
* endWord.length == beginWord.length
* 1 <= wordList.length <= 1000
* wordList[i].length == beginWord.length
* beginWord, endWord, and wordList[i] consist of lowercase English letters.
* beginWord != endWord
* All the words in wordList are unique.

#Solution

##Overview

This problem is an extension of the problem Word Ladder, where we only need to find the minimum number of words in
the transformation from beginWord to endWord. Here, we need to find all the transformations that exist between beginWord
and endWord that are the minimum length. We can use BFS to find the minimum number of words in the transformation,
however, finding all such transformations is tricky because the number of transformations may be enormous.

##Approach 1: Breadth-First Search (BFS) + Backtracking

###Intuition

The problem can be correlated with the graph data structure. We can represent the words as the vertices and an edge can
be used to connect two words which differ by a single letter.

Before diving further let's see how we can find all the direct connections of a particular word. To find the adjacent
words for a particular word, one approach is to traverse all of the other words and add an edge for those that differ
by a single letter. This approach requires O(N * K) time where N is the number of words given and K is the maximum length
of a word. The observation behind the optimal approach is that the words only consist of lowercase English letters.
Hence we can change each character of the word to all other English lowercase characters and check whether or not
that word exists in the wordList(this particular check operation takes O(1) in C++ while in Java it will take O(K)
due to the immutable nature of Strings).This way the number of operations will be (25*K*K+1), hence the time complexity
will be O(K<sup>2</sup>)

Thus we can find all the words that are directly connected. Now, the task is to find all of the shortest paths from
beginWord to endWord.

The naive way to do this is to use backtracking. We will start from beginWord, then traverse all the adjacent words until
we reach the endWord. When we reach the endWord, we can compare the path length and find all the paths that have
the minimum path length. This method however is extremely inefficient because the number of paths between two vertices
can be enormous.

Let's try to optimize our approach. Somehow, we need to reduce the number of traversed paths. Let's say the number of
shortest paths that exist between beginWord and endWord is x and the number of paths that we must traverse to find these
shortest paths is y. The closer the value y gets to the value x, the more efficient our approach will be.

The diagram below shows the graph that represents the connectivity among words. As shown in the diagram we want to go
from red to tax. While backtracking on this graph, we will also cover the edges upwards that is from the tad to ted
similarly from tex we will traverse to ted as well as rex. The key observation here is that going back in the upward
direction will never lead us to the shortest path. We should always traverse the edges in the direction of beginWord
to endWord.

![](https://leetcode.com/problems/word-ladder-ii/Figures/126/126A.png)

To ensure that we never traverse up the ladder, let's use directed edges to connect the words. The edges
in the graph below are all directed towards endWord. Also, notice that graphs produced by BFS do not contain cycles.
Thus, the graph will be a Directed Acyclic Graph (DAG).

![](https://leetcode.com/problems/word-ladder-ii/Figures/126/126B.png)

Now for the easy part, think of the previous graph as a bunch
of layers and observe that once we reach a particular layer we don't want the future words to have the connection back to
this layer. We will build our DAG using BFS. We will then add all the directed edges from the words present in the current
layer and once all words in this layer have been traversed, we will remove them from the wordList. This way we will avoid
adding any edges that point towards beginWord.

After constructing the graph, we can use our same backtracking approach to find the shortest paths between beginWord and
endWord. Also, note that in the graph all paths between beginWord and endWord, obtained through BFS, will be the shortest
possible. This is because all the edges in the graph will be directed in the direction of beginWord to endWord.
Furthermore, there will not be any edge between the words that are on the same level. Therefore, iterating over any edge
will bring us one step closer to the endWord, thus there is no need to compare the length of the path each time we reach
the endWord.

##Algorithm

1. Store the words present in wordList in an unordered set so that the words can be efficiently removed during the
   breadth-first search.

2. Perform the BFS, and add the edges to the adjacency list adjList. Also once a level is finished remove the visited
   words from the wordList.

3. Start from beginWord and while keep tracking of the current path as currPath traverse all the possible paths,
   whenever the path leads to the endWord store the path in shortestPaths.

##Complexity Analysis

* Time complexity: O(N * K<sup>2</sup> + α).

  Here N is the number of words in wordList, K is the maximum length of a word, α is the number of possible paths from
  beginWord to endWord in the directed graph we have.

  Copying the wordList into the set will take O(N).

  In BFS, every word will be traversed and for each word, we will find the neighbors using the function findNeighbors
  which has a time complexity of O(K<sup>2</sup>). Therefore the total complexity for all the N words will be
  O(N * K<sup>2</sup>). Also, each word will be enqueued and will be removed from the set hence it will take O(N).
  The total time complexity of BFS will therefore be equal to O(N * K<sup>2</sup>).

  While backtracking, we will essentially be finding all the paths from beginWord to endWord. Thus the time complexity
  will be equal to O(α).

  We can estimate the upper bound for α by assuming that every layer except the first and the last layer in the DAG has
  x number of words and is fully connected to the next layer. Let h represent the height of the DAG, so the total number
  of paths will be x<sup>h</sup> (because we can choose any one word out of x words in each layer and each choice will be
  part of a valid shortest path that leads to the endWord). Here, h equals (N−2)/x. This would result in
  x<sup>(N−2)/x</sup> total paths, which is maximized when x = 2.718, which we will round to 3 because x must be
  an integer. Thus the upper bound for α is 3<sup>(N/3)</sup>, however, this is a very loose bound because the nature of
  this problem precludes the possibility of a DAG where every layer is fully connected to the next layer.

  The total time complexity is therefore equal to O(N * K<sup>2</sup> + α)

* Space complexity: O(NK).

  Here N is the Number of words in wordList, K is the Maximum length of a word.

  Storing the words in a set will take O(NK) space.

  To build the adjacency list O(N) space is required as the BFS will produce a directed graph and hence there will be at
  max(N - 1) edges.

  In backtracking, stack space will be consumed which will be equal to the maximum number of active functions in the
  stack which is equal to the N as the path can have all the words in the wordList. Hence space required is O(N).

  The total space complexity is therefore equal to O(NK).

##Approach 2: Bidirectional Breadth-First Search (BFS) + Backtracking

###Intuition

This approach is very similar to the previous one in that both approaches will use the same Directed Acyclic Graph (DAG).
The difference lies in the way that the graph is produced, which is better optimized in this case.

In the previous solution, we performed BFS starting from beginWord and explored down the ladder until we reached endWord.
However, now we will start from beginWord stored in forwardQueue and from endWord stored in backwardQueue and explore
towards the middle of the ladder. At each iteration, we will connect the words to their neighbors, which we will find
using findNeighbors. At each iteration, we will have two options, either to connect edges using theforwardQueue or by
using the backwardQueue. The optimal choice is to use the queue that contains fewer words. In this solution,
for simplicity, we will swap forwardQueue and backwardQueue whenever forwardQueue is larger. In doing so, we will always
choose the forwardQueue.

We will traverse the forwardQueue find the neighbors of each of the words present using findNeighbors and add the edge
to the adjList. We will also store the next layer words in the visited list. Once a level is finished we can assign
the forwardQueue to visited. We will repeat this process until either the size of forwardQueue becomes zero, signifying
that there is no valid sequence from beginWord to endWord, or until the contents of forwardQueue and backwardQueue
overlap. The reason for stopping when forwardQueue and backwardQueue overlap is because after completing this level,
it is guaranteed that beginWord and endWord will be connected by at least one sequence that that passes through the words
that exist in both queues. Thus, the DAG will be completed.

The remaining process is the same as Approach 1, once the queue has been traversed, at each iteration, we will remove
those words from the wordList to avoid including any edges to previous layers. Once the graph is complete, we will use
backtracking to find all of the paths between beginWordand endWord.

##Algorithm

1. Store the words present in wordList in an unordered set so that the words can be efficiently removed during the
   breadth-first search.

2. Perform a bidirectional BFS. Initialize two queues, forwardQueue with beginWord and backwardQueue with endWord. At
   each iteration add the edges to the adjacency list adjList by extending the shorter queue. The parameter direction is
   used to decide in which direction the edges should be connected, where 1 indicates towards endWord (down the ladder)
   and vice versa. Also once a level is finished, remove the forwardQueue words from the wordList.

3. If a sequence connecting beginWord to endWord does not exist, return an empty list. Otherwise, start from beginWord
   and while keeping track of the current path as currPath traverse all the possible paths, whenever the path leads to
   the endWord store the path in shortestPaths.

##Complexity Analysis

* Time complexity: O(N * K<sup>2</sup> + α).

  Here N is the Number of words in wordList, K is the maximum length of a word, α is the Number of possible paths from
  beginWord to endWord in the directed graph we have.

  Copying the wordList into the set will take O(N).

  In the worst-case scenario, the number of operations in the bidirectional BFS will be equal to the BFS approach
  discussed before. However, in some cases, this approach will perform better because the search space is reduced by
  selecting the shorter queue at each iteration. In bidirectional BFS, at most, every word will be traversed once, and
  for each word, we will find the neighbors using the function findNeighbors which has a time complexity of
  O(K<sup>2</sup>). Therefore the total complexity for all the N words will be O(N * K<sup>2</sup>). Also, each word will
  be enqueued and will be removed from the set which will take O(N). Thus, the total time complexity of bidirectional BFS
  will be O(N * K<sup>2</sup>)

  In the backtracking process, we will essentially find all of the paths from beginWord to endWord. Thus, the time
  complexity is equal to O(α).

  We can estimate the upper bound for α by assuming that every layer except the first and the last layer in the DAG has
  x number of words and is fully connected to the next layer. Let h represent the height of the DAG, so the total number
  of paths will be x<sup>h</sup> (because we can choose any one word out of x words in each layer and each choice will be
  part of a valid shortest path that leads to the endWord). Here, h equals (N - 2) / x. This would result in
  x<sup>(N−2)/x</sup> total paths, which is maximized when x = 2.718, which we will round to 3 because x must be
  an integer. Thus the upper bound for 3<sup>(N/3)</sup>, however, this is a very loose bound because the nature of this
  problem precludes the possibility of a DAG where every layer is fully connected to the next layer.

  The total time complexity is therefore equal to O(N * K<sup>2</sup> + α).

* Space complexity: O(N K).

  Here N is the Number of words in wordList, K is the Maximum length of a word.

  Storing the words in a set will take O(N K) space.

  To build the adjacency list O(N) space is required as the BFS will produce a directed graph and hence there will be
  at most (N - 1) edges. Also, in the worst-case scenario, the combined size of both queues will be equal to N.

  In backtracking, stack space will be consumed which will be equal to the maximum number of active functions in
  the stack, which is equal to the N as the path can have all the words in the wordList. Hence the space required is O(N).

  The total space complexity is therefore equal to O(N K).
