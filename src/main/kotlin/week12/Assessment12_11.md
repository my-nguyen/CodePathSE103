#Recover Binary Search Tree

You are given the root of a binary search tree (BST), where exactly two nodes of the tree were swapped by mistake. Recover the tree without changing its structure.

**Follow up**: A solution using O(n) space is pretty straight forward. Could you devise a constant space solution?

###Example 1:
![](https://assets.leetcode.com/uploads/2020/10/28/recover1.jpg)

**Input**: root = [1,3,null,null,2]  
**Output**: [3,1,null,null,2]  
**Explanation**: 3 cannot be a left child of 1 because 3 > 1. Swapping 1 and 3 makes the BST valid.

###Example 2:
![](https://assets.leetcode.com/uploads/2020/10/28/recover2.jpg)

**Input**: root = [3,1,4,null,null,2]  
**Output**: [2,1,4,null,null,3]  
**Explanation**: 2 cannot be in the right subtree of 3 because 2 < 3. Swapping 2 and 3 makes the BST valid.

###Constraints:
* The number of nodes in the tree is in the range [2, 1000].
* -231 <= Node.val <= 231 - 1

#Solution

##Approach 1: Sort an Almost Sorted Array Where Two Elements Are Swapped

###Intuition

Let's start from straightforward but not optimal solution with a linear time and space complexity. This solution serves
to identify and discuss all subproblems.

It's known that
[inorder traversal of BST is an array sorted in the ascending order](https://leetcode.com/articles/delete-node-in-a-bst/).
Here is how one could compute an inorder traversal

<pre>
public void inorder(TreeNode root, List<Integer> nums) {
    if (root == null) return;
    inorder(root.left, nums);
    nums.add(root.val);
    inorder(root.right, nums);
}
</pre>

Here two nodes are swapped, and hence inorder traversal is an almost sorted array where only two elements are swapped.
To identify two swapped elements in a sorted array is a classical problem that could be solved in linear time. Here is
a solution code

<pre>
public int[] findTwoSwapped(List<Integer> nums) {
    int n = nums.size();
    int x = -1, y = -1;
    for(int i = 0; i < n - 1; ++i) {
        if (nums.get(i + 1) < nums.get(i)) {
            y = nums.get(i + 1);
            // first swap occurence
            if (x == -1) x = nums.get(i);
            // second swap occurence
            else break;
        }
    }
    return new int[]{x, y};
}
</pre>

When swapped nodes are known, one could traverse the tree again and swap their values.  
![](https://leetcode.com/problems/recover-binary-search-tree/Figures/99/iinorde.png)

###Algorithm

Here is the algorithm:
1. Construct inorder traversal of the tree. It should be an almost sorted list where only two elements are swapped.
2. Identify two swapped elements x and y in an almost sorted array in linear time.
3. Traverse the tree again. Change value x to y and value y to x.

##What Is Coming Next

In approach 1 we discussed three easy subproblems of this hard problem:
1. Construct inorder traversal.
2. Find swapped elements in an almost sorted array where only two elements are swapped.
3. Swap values of two nodes.

Now we will discuss three more approaches, and basically they are all the same :
* Merge steps 1 and 2, i.e. identify swapped nodes during the inorder traversal.
* Swap node values.

The difference in-between the following approaches is in a chosen method to implement inorder traversal :
* Approach 2 : Iterative.
* Approach 3 : Recursive.
* Approach 4 : Morris.

![](https://leetcode.com/problems/recover-binary-search-tree/Figures/99/mmorris.png)

Iterative and recursive approaches here do less than one pass, and they both need up to O(H) space to keep stack, where
H is a tree height.

Morris approach is two pass approach, but it's a constant-space one. 

##Approach 2: Iterative Inorder Traversal

###Intuition

Here we construct inorder traversal by iterations and identify swapped nodes at the same time, in one pass.

Iterative inorder traversal is simple: go left as far as you can, then one step right. Repeat till the end of nodes in
the tree.

To identify swapped nodes, track the last node pred in the inorder traversal (i.e. the predecessor of the current node)
and compare it with current node value. If the current node value is smaller than its predecessor pred value,
the swapped node is here.

There are only two swapped nodes here, and hence one could break after having the second node identified.

Doing so, one could get directly nodes (and not only their values), and hence swap node values in O(1) time, drastically
reducing the time needed for step 3.

##Approach 3: Recursive Inorder Traversal

###Iterative approach 2 could be converted into recursive one.

Recursive inorder traversal is extremely simple: follow Left->Node->Right direction, i.e. do the recursive call for
the left child, then do all the business with the node (= if the node is the swapped one or not), and then do
the recursive call for the right child.

On the following figure the nodes are numerated in the order you visit them, please follow 1-2-3-4-5 to compare
different DFS strategies.

![](https://leetcode.com/problems/recover-binary-search-tree/Figures/99/ddfs.png)

##Approach 4: Morris Inorder Traversal

We discussed already iterative and recursive inorder traversals, which both have great time complexity though use up to
O(N) to keep stack. We could trade in performance to save space.

The idea of Morris inorder traversal is simple: to use no space but to traverse the tree.

How that could be even possible? At each node one has to decide where to go: left or right, traverse left subtree or
traverse right subtree. How one could know that the left subtree is already done if no additional memory is allowed?

The idea of [Morris](https://www.sciencedirect.com/science/article/pii/0020019079900681) algorithm is to set
the temporary link between the node and its [predecessor](https://leetcode.com/articles/delete-node-in-a-bst/):
predecessor.right = root. So one starts from the node, computes its predecessor and verifies if the link is present.

* There is no link? Set it and go to the left subtree.
* There is a link? Break it and go to the right subtree.

There is one small issue to deal with : what if there is no left child, i.e. there is no left subtree? Then go
straightforward to the right subtree.