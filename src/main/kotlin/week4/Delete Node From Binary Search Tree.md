#Delete Node From Binary Search Tree

##Pseudocoding

For this problem, we will practice our pseudocoding skills and walk through the process of coming up with a pseudocode
solution.

###Advantages of prototyping and writing pseudocode

When we approach a problem with prototypes or pseudocodes, we are able to maximize our time on solving the bigger
picture. Pseudocoding allows us to not worry about the smaller details (like off-by-one errors or coding language
specific problems) in the beginning.

It's also a great way to iterate through different solutions in an efficient way. Often times our first instinct for the
solution may not work out. If we're working with pseudocode, it's easier for us to brainstorm different approaches
before spending too much time writing code.

Pseucoding also allows us to break up the problem into smaller subproblems. If we are able to come up with a few helper
methods, our solution now becomes a lot easier to implement and iterate on. It also makes it easier for the interviewer
to undestand our approach.

##Problem : Delete a given node from a BST

For our pseudocoding practice, we will look at how to delete a given node from a Binary Search Tree (BST) while still
maintaining the qualities of a BST https://leetcode.com/problems/delete-node-in-a-bst/description/

###Possible Cases

Since this is a binary search tree, we are guaranteed that each node will have at most two children. Given that, we can
assume the following scenarios:

* The node we want to delete has zero children
* The node we want to delete has one child
* The node we want to delete has two children

###Come up with solutions for the different cases

If the node we want to delete has zero children... this is great! all we have to do is set this node to null

If the node we want to delete has one child... replace the node with the node's child

If the node we want to delete has two children... 1) find the minimum value in the right subtree 2) replace the node
value with the found minimum value 3) remove the node that is now duplicated in the right subtree (this is not
immediately obvious at all, and to get a better understanding of why this is the case, it would be helpful to draw out
some examples to see how this will always work)

###Create helper methods

Having helper methods can make our code a lot simpler and easier to understand. To be time efficient, we can stub out
these helper methods and go back to working on the bigger picture of solving the problem as a whole. (Sometimes the
interviewer may be nice and even let you skip implementing the helper methods!)

For what we have so far, it seems like some useful helper methods could be:
findMinNode(TreeNode node) removeDuplicateNode(TreeNode node)

###Sketch out the pseudocode

Now we can take everything we've come up with and put it into words
<pre>
if node is null ->
    return the node (either we were given an empty tree or we've iterated through the tree and didn't find the value,
    in which case we return the original tree)

if current node is less than the value we're looking for ->
    recursively call function on right side

if current node is great than the value ->
    recursively call function on left side

otherwise, we've found the node we want to delete
-> if the node has no children, return null
-> if the node has one child, return the non-null subtree
-> otherwise:
    -> find the min value in the right subtree
    -> set the current node's value to the found min value
    -> delete the now duplicated node
    -> return the current node
</pre>

that was a lot of words, which doesn't help us a lot of terms of efficiency, so let's translate those words into some
pseudocode
<pre>
Node deleteNode(Node root, int valueToDelete) {
    if root = null
        return node
    if root.value < valueToDelete
        deleteNode(root.right, valueToDelete)
    if root.value > valueToDelete
        deleteNode(root.left, valueToDelete)
    else
        if (isLeafNode(root))
            return null

        if (root.right == null) 
            return root.left
        if (root.left == null)
            return root.right
        else 
            minValue = findMinInRightSubtree(root)
            root.value = minValue
            removeDuplicateNode(root)
            return root
</pre>
