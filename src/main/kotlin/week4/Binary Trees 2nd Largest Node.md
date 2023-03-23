###Binary Trees 2nd Largest Node

**Question: Given a binary search tree, find the 2nd largest tree node.**

Assume this is our definition for a tree node.
<pre>
public class TreeNode {
    int value;
    TreeNode left;
    TreeNode right;
}
</pre>

**Method 1:** The first thing to note here is that we are given a binary search tree. Knowing that, our first instinct
may be to do an inorder traversal. Recall that an inorder traversal is implemented as such:
<pre>
void printInorder(TreeNode node) {
    if (node == null) {
        return;
    }
    printInorder(node.left); // process left
    System.out.println(node.value); // process node
    printInorder(node.right); // process right
}
</pre>

Using inorder traversal, we would be able to print every node in the binary search tree in ascending order. We can then
store the nodes in an array and return the second to last element in the array.

The above method would certainly work and return the correct answer, but what would be the time and space complexity?

Since we have to look at every node in the tree, it would take us **O(n) time and O(h) space**, h being the height of
the tree.

Given what we know about binary search trees, perhaps we don't have to look at every node. The trick with most binary
search problems is that there is a straight-forward approach that will get you O(n) running time, but there is almost
always an optimization you can make to get O(logN) running time.

**Method 2:** Recall the main properties of a binary search tree: For every element, 1) All elements to the right are
greater 2) All elements to the left are smaller

Knowing that we can assume that the **rightmost element of a tree is also the largest element** (and similarly,
the leftmost element is the smallest). This is because there can not be anything to the right of the largest element,
otherwise it wouldn't satisfy the definition of a binary search tree.

To fully prove this, we can take the tree below as an example:
<pre>
            (5)
    (2)            (9)
(1)   (3)      (6)   (12)
</pre>

Given that we want the maintain this tree as a binary search tree, is there anywhere else 12 can be put?

If we were looking for the largest element in the tree, our method can be more formally translated into:
1) Does the current node we're looking at have a right child?
2) if yes, recurse on the right child
3) if no, return the current node.

<pre>
TreeNode getLargest(TreeNode node) {
    if (node.right != null) {
        return getLargest(node.right);
    }
    return node;
}
</pre>

Now that we have a method to get the largest element, how can we use that to get the 2nd largest? If we look at our tree
example again, 9 is the second largest node, and it's the parent of the largest node, 12.

<pre>
            (5)
    (2)            (9)
(1)   (3)      (6)   (12)
</pre>

At this point, we might be inclined to write a method to return the parent of the largest element. However, if we look
back to the original question, it never states that the tree we're given will be a balance binary tree. That means,
it has the potential to look something like this:

<pre>
            (5)
    (2)             (12)
(1)   (3)       (7)
             (6)  (9)
</pre>

In this unevenly balanced binary search tree, 12 is still the right most element. However, 5 is its parent, while 9 is
the actual second largest element. This doesn't work with the method we had come up with previously. But what we can
notice here is that 9 is actually the right-most element in the subtree for 12

We can create other examples to see if this is always the case:
<pre>
            (5)
    (2)             (12)
(1)   (3)       (9)
             (7)   
           (6) (8)
</pre>

Another case:
<pre>
            (5)
    (2)             (12)
(1)   (3)       (9)
             (7)  (11)
           (6)
</pre>

And yet another:
<pre>
            (5)
    (2)                 (12)
(1)   (3)         (9)
              (7)    (11)     
            (6) (10)
</pre>
In all of these cases, notice that when the right most element has a left child, the second largest element is
the rightmost element of its left subtree.

We can reason this is true because if the largest element has a subtree, nothing in its subtree can be greater, so
the greatest element in its subtree must be the second great element in the whole tree.

With that, our algorithm to finding the second largest element becomes: 1) traverse through the tree looking for
the largest element a) if the largest element has a left child, return the largest element of the left subtree
b) else, return the parent of the largest element

<pre>
public static TreeNode getSecondLargest(TreeNode node) {
    // we are looking at the right-most element (aka largest) and it has a left child
    // so we want the largest element in its left child
    if (node.right == null && node.left != null) {  
        return getLargest(node.left);  
    }  
    
    // we are looking at the parent of the largest element and the largest element has no children
    // so this is the node we want
    if (node.right != null && node.right.left == null && node.right.right == null) {  
        return node;
    }  
  
    // recurse on the right child until we match one of the above cases
    return getSecondLargest(node.right);  
}
</pre>

Similar problems:

2nd minimum node in a binary tree: https://leetcode.com/problems/second-minimum-node-in-a-binary-tree/description/

Inorder successor: https://leetcode.com/problems/inorder-successor-in-bst/
