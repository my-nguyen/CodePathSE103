#Week 4 - Advanced Guide

Use this guide to support the discussion of today’s session problems.

[Convert Sorted Array to Binary Search Tree](https://hackmd.io/@2aDFKMu8S2CUg05JnLuKKg/S12s67_38#1-Convert-Sorted-Array-to-Binary-Search-Tree)
[Print a Binary Tree in Vertical Order](https://hackmd.io/@2aDFKMu8S2CUg05JnLuKKg/S12s67_38#2-Print-a-Binary-Tree-in-Vertical-Order)
[Find Largest Value in Each Tree Row](https://hackmd.io/@2aDFKMu8S2CUg05JnLuKKg/S12s67_38#3-Find-Largest-Value-in-Each-Tree-Row)

##1. Convert Sorted Array to Binary Search Tree

[Original Problem Statement](https://leetcode.com/problems/convert-sorted-array-to-binary-search-tree/):

Given an array where elements are sorted in ascending order, convert it to a height balanced BST.

For this problem, a height-balanced binary tree is defined as a binary tree in which the depth of the two subtrees of every node never differ by more than 1.

####Common Mistakes:

* Sometimes, students don’t see the connection between the sorted array and the sorted nature of a BST. This alignment
  offers a simple solution to the problem.

###Understand

Ensure students clarify input and output parameters of the problem:

* Could the input array be null or empty?
  * “Yep! In that case, let’s just return a null reference.”
* Could values be negative?
  * “Yes. Our solution shouldn’t change because of that.”
* After creating the tree, do we need to verify its height-balanced property?
  * “I don’t believe you need to verify it after you make it. Rather, your process in generating the tree should
    guarantee that it should be height-balanced. Does that make sense?”

Run through a set of example cases:
<pre>
HAPPY CASE
Input: [1, 2, 3, 4, 5]
Output:
    3
   / \
  2   4
 /     \
1       5

Input: [1, 2, 3, 4, 5, 6, 7, 8]
Output:
     4
   /   \
  2     6
 / \   / \
1   3 5   7
           \
            8
(Note the subtrees differ in height by 1, but not more than 1)

EDGE CASE (One element)
Input: [1]
Output:  
1
</pre>

####Before finishing this section, ensure the students have done the following:

* Established a set (2-3) of test cases to verify their own solution later.
* Established a set (1-2) of edge cases to verify their solution handles complexities.
* Have fully understood the problem and have no clarifying questions.

NOTE: Students tend to rush problems. Encouraging students to spend more time in the Understanding phase of the process
may help them avoid issues later in the interview.

###Match

In the Matching step of UMPIRE, you want to think about common patterns and tricks that could apply to this problem.

For trees, some of the top things we want to consider are:

* Using a Pre/In/Post-Order Traversal to generate a unique sequence of nodes
  * Since we don’t have a tree to start with, we cannot use these traversals to help us in this problem. However,
    performing an in order traversal of this tree after generating it would yield ascending values.
* Using binary search to find an element
  * We could apply a binary search strategy to this problem to divide the array the way a BST divides a sorted data set.

###Plan

####Before ANY pseudocode, have students explain a general approach to the problem.

Ask the students how they would solve this problem by hand if they were to. This gives a good idea to the brute force
solution. Optimizations can occur on top of this or can inspire new approaches.

####If students struggle to create an algorithm pose these questions:

* “When we look at a BST, from left to right, the elements are sorted. When we look at an array, from left to right,
  the elements are sorted. Can we use this shared pattern between the two to help us generate the BST from the array?”
* “If we chose a random index in the array, everything left of that index is smaller and everything right of that index
  is larger, right? If we were to structure our BST on that index it may not be height-balanced, so how do we choose
  a proper index?”

####Mentors, before diving into specifics about algorithms, have students give a 1-2 sentence summary on their approach.

###Sample Approach #1

1-2 Sentence Summary  
Start at the array middle and recurse left, right halves of the array to build a eight-balanced binary search tree.

1. Calculate center point of current array
2. Create node of center value
3. Set left reference to center of the left half of array (recurse)
4. Set right reference to center of the right half of array (recurse)
5. Return current node

Time Complexity: O(N)  
Space Complexity: O(N) [N includes the Nodes created as part of the output. If not counted, the Space Complexity would be O(1)]

NOTE: Before moving onto implementing the solution, students should have 2-3 happy test cases ready as well as 1-2 edge
test cases to verify their solution.

###Implement

Students should discuss how to translate their general algorithm into specific code before implementing.

####Mentors, discuss with students how the algorithm can be implemented:

* This helps students practice understand how algorithm patterns can be easily transferred into code

###Python Solution (Approach #1):
<pre>
def sortedArrayToBST(self, nums: List[int]) -> TreeNode:
    return helper(nums, 0, len(nums) - 1)

def helper(arr, l, r):
    if l > r:
        return None
    mid = (l + r) // 2
    cur = TreeNode(arr[mid])
    cur.left = helper(arr, l, mid - 1)
    cur.right = helper(arr, mid + 1, r)
    return cur
</pre>

###Review

Verify the code works with the happy cases created in the “Understand” section. Afterwards, use the code for the edge
cases created in the “Plan” section.

####Before moving on, mentors verify these edge cases:
<pre>
EDGE CASE (Empty Array)
INPUT: []
OUTPUT: None
</pre>

###Evaluate

Students should discuss the following:

* Student Time Complexity – Best Case: O(N)
* Student Space Complexity – Best Case: O(N)
* Could their solution have been made any more efficient?

Encourage students to discuss alternative solutions, if other valid solutions are present. Helping students understand
there are multiple efficient ways to solve a problem can help alleviate pressure during interview situations.

###2. Print a Binary Tree in Vertical Order

Original Problem Statement:

Given a binary tree, return the vertical order traversal of its nodes values.

For each node at position (X, Y), its left and right children respectively will be at positions (X-1, Y-1) and (X+1, Y-1).

Return a list of lists of the elements in each vertical level, from left to right and top to bottom in each list.

####Common Mistakes:

* Students haven’t seeen a question that deals with vertical splits of a tree, so it might seem confusing at first.
  Giving students a visual diagram would help them the most in understanding the problem.

NOTE: Leetcode has a different variant of this question, where ties are broken by which element size. This creates
a separate sub-problem, which should not be the main focus of the exercise. This problem is laid out without Leetcode’s
sub-problem. If you have extra time, take a look at breaking ties based on value.

###Understand

Ensure students clarify input and output parameters of the problem:

* Could the input tree be null?
  * “Yep! In that case, let’s return an empty list.”
* Is the input tree a Binary Search Tree?
  * “No, the input tree is a binary tree. There are no guarantees about the tree being complete.”

Run through a set of example cases:
<pre>
Input:
    1
   / \
  3   2
 / \   \  
5   3   9

Output: [[5], [3], [1, 3], [2], [9]]

Input:
     3
    / \
   9   20
 /  \  
15   7

Output: [[9], [3, 15], [20], [7]]


Input:
     1
   /   \
  2     3
 / \    / \  
4   5  6   7

Output: [[4],[2],[1,5,6],[3],[7]] or [[4],[2],[1,6,5],[3],[7]]
</pre>

####Before finishing this section, ensure the students have done the following:

* Established a set (2-3) of test cases to verify their own solution later.
* Established a set (1-2) of edge cases to verify their solution handles complexities.
* Have fully understood the problem and have no clarifying questions.

NOTE: Students tend to rush problems. Encouraging students to spend more time in the Understanding phase of the process
may help them avoid issues later in the interview.

###Match

In the Matching step of UMPIRE, you want to think about common patterns and tricks that could apply to this problem.

For trees, some of the top things we want to consider are:

* Using a Pre/In/Post-Order Traversal to generate a unique sequence of nodes
  * The type of traversal does not matter in this case, since all traversals we know of don’t follow a vertical order
    style. Instead, the student should choose a traversal they are comfortable with to traverse the tree since we need
    all nodes stored at some point.
* Using Binary Search to find an element
  * Since the input tree is not a BST, we cannot apply binary search techniques to this problem.
* Applying a level-order traversal with a queue
  * This problem is very similar to a level-order traversal. However, we are performing the opposite type of traversal.
    Having the knowledge of performing a level-order traversal can definitely help solve this problem.

###Plan

####Before ANY pseudocode, have students explain a general approach to the problem.

Ask the students how they would solve this problem by hand if they were to. This gives a good idea to the brute force
solution. Optimizations can occur on top of this or can inspire new approaches.

####If students struggle to create an algorithm pose these questions:

* “How can we keep track of our horizontal distance to the center root while traversing the tree?”
* “What data structure could we use for this problem?”
* “Is there a way we can store the nodes at a certain horizontal distance from the center?”

####Mentors, before diving into specifics about algorithms, have students give a 1-2 sentence summary on their approach.

###Sample Approach #1

1-2 Sentence Summary  
Traverse through the tree and keep track of the horizontal distance to the center as we traverse. Place node values in
a HashMap with the key being their horizontal distance from the center.

1. Create a HashMap to store hd -> nodes [Int --> List]
2. Keep track of min hd and max hd with an array
3. Traverse through the tree using pre-order traversal (others work as well)
   * Keep track of current horizontal distance to root
     * Update min/max hd array
   * Place node value in List with the proper 'hd' key
   * Call left with 'hd - 1'
   * Call right with 'hd + 1'
4. Iterate from min_hd -> max_hd and trasfer data into a nested list
5. Return nested list

Time Complexity: O(N) [N - Size of Input Tree, This is a single traversal]  
Space Complexity: O(N) [N - Size of Input Tree, since we store in HashMap]

NOTE: Before moving onto implementing the solution, students should have 2-3 happy test cases ready as well as 1-2 edge
test cases to verify their solution.

###Implement

Students should discuss how to translate their general algorithm into specific code before implementing.

####Mentors, discuss with students how the algorithm can be implemented:

* This helps students practice understand how algorithm patterns can be easily transferred into code

###Python Solution (Approach #1):
<pre>
import collections

def verticalTraversal(self, root: TreeNode) -> List[List[int]]:
    arr = [0, -1]
    m = collections.defaultdict(list)
    helper(root, m, 0, arr)
    min_hd, max_hd = arr
    output = []
    for i in range(min_hd, max_hd + 1):
        output.append(m[i])
    return output

def helper(node, m, hd, arr):
    if not node:
        return
    arr[0], arr[1] = min(hd, arr[0]), max(hd, arr[1])
    m[hd].append(node.val)
    helper(node.left, m, hd - 1, arr)
    helper(node.right, m, hd + 1, arr)
</pre>

###Review

Verify the code works with the happy cases created in the “Understand” section. Afterwards, use the code for the edge
cases created in the “Plan” section.

####Before moving on, mentors verify these edge cases:
<pre>
EDGE CASE (Null Tree)
INPUT: None
OUTPUT: []
</pre>

###Evaluate

Students should discuss the following:

* Student Time Complexity – Best Case: O(N)
* Student Space Complexity – Best Case: O(N)
* Could their solution have been made any more efficient?

Encourage students to discuss alternative solutions, if other valid solutions are present. Helping students understand
there are multiple efficient ways to solve a problem can help alleviate pressure during interview situations.

##3. Find Largest Value in Each Tree Row

[Original Problem Statement](https://leetcode.com/problems/find-largest-value-in-each-tree-row/):

You need to find the largest value in each row of a binary tree.

####Common Mistakes:

Students may not know about level-order traversals in trees. As an effect, students may try to traverse the tree with
a pre/in/post order traversal and store level associations in a HashMap.

###Understand

Ensure students clarify input and output parameters of the problem:

* Could the input tree be null?
  * “Yes. In that case, let’s return an empty list. That makes the most sense.”
* Could there be a tie for the largest value in a row?
  * “Yes, there can be. If there is a tie, we just choose that value.”

Have students verified any Time/Space Constraints for this problem?

NOTE: Urge students not to use a HashMap for this problem. This forces a level order traversal of the tree.

Run through a set of example cases:

<pre>
HAPPY CASE
Input:
    1
   / \
  3   2
 / \   \  
5   3   9

Output: [1, 3, 9]

Input:
   -1
   / \
  3   2
 /   /    
5   2

Output: [-1, 3, 5]


EDGE CASE
Input:
1

Output: [1]
</pre>

####Before finishing this section, ensure the students have done the following:

* Established a set (2-3) of test cases to verify their own solution later.
* Established a set (1-2) of edge cases to verify their solution handles complexities.
* Have fully understood the problem and have no clarifying questions.

NOTE: Students tend to rush problems. Encouraging students to spend more time in the Understanding phase of the process
may help them avoid issues later in the interview.

###Match

In the Matching step of UMPIRE, you want to think about common patterns and tricks that could apply to this problem.

For trees, some of the top things we want to consider are:

* Using a Pre/In/Post-Order Traversal to generate a unique sequence of nodes
  * We want to be able to compute maximum values a row at a time. These types of traversals don’t perform level-order
    traversals that are needed in this problem.
* Using Binary Search to find an element
  * We aren’t looking for a specific value, so its hard to use Binary Search in this problem. In addition, the tree is
    not a BST, so Binary Search couldn’t apply to this problem.
* Applying a level-order traversal with a queue
  * Using a level-order traversal for this problem is essential since we are looking for a computation on each level.

###Plan

####Before ANY pseudocode, have students explain a general approach to the problem.

Ask the students how they would solve this problem by hand if they were to. This gives a good idea to the brute force
solution. Optimizations can occur on top of this or can inspire new approaches.

####If students struggle to create an algorithm pose these questions:

* “How could we store a single level’s nodes in one place to calculate the maximum at that row?”
* “If we were to use a pre-order traversal for this problem, how would we compute maximums on each row?”

####Mentors, before diving into specifics about algorithms, have students give a 1-2 sentence summary on their approach.

###Sample Approach #1

1-2 Sentence Summary  
Perform a level-order traversal of the tree with a Queue. Calculate a running max while traversing the row.

1. Create a Queue and place the root in there
2. While the Queue is not empty, iterate through the queue
   * Compute the length of the Queue and iterate that fixed amount (k)for the current row
     * Keep a running max for the row
     * Add the left and right sub-tree nodes to the end of the queue for the next iteration
   * Append this row's max to an output array
3. Return the array of row-wise maximums

Time Complexity: O(N) [Size of the tree, since we only iterate once]  
Space Complexity: O(N) [The queue we create could grow to the size of the tree]

NOTE: Before moving onto implementing the solution, students should have 2-3 happy test cases ready as well as 1-2 edge
test cases to verify their solution.

###Implement

Students should discuss how to translate their general algorithm into specific code before implementing.

####Mentors, discuss with students how the algorithm can be implemented:

This helps students practice understand how algorithm patterns can be easily transferred into code
Python Solution (Approach #1):

<pre>
import sys, queue

def largestValues(self, root: TreeNode) -> List[int]:
    if not root:
        return []
    output = []
    q = queue.Queue()
    q.put(root)
    while q.qsize() > 0:
        cur_max = -sys.maxsize # running max value for this row
        for _ in range(q.qsize()): # fixed size iteration for this row
            node = q.get()
            cur_max = max(cur_max, node.val)
            if node.left:
                q.put(node.left)
            if node.right:
                q.put(node.right)
        output.append(cur_max)
    return output
</pre>

###Review

Verify the code works with the happy cases created in the “Understand” section. Afterwards, use the code for the edge
cases created in the “Plan” section.

####Before moving on, mentors verify these edge cases:
<pre>
EDGE CASE (Null Tree)
INPUT: None
OUTPUT: []
</pre>

###Evaluate

Students should discuss the following:

* Student Time Complexity – Best Case: O(N)
* Student Space Complexity – Best Case: O(N)
* Could their solution have been made any more efficient?

Encourage students to discuss alternative solutions, if other valid solutions are present. Helping students understand
there are multiple efficient ways to solve a problem can help alleviate pressure during interview situations.