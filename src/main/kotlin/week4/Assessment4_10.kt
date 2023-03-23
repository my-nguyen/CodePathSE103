package week4

import TreeNode

class Assessment4_10 {
    // https://www.geeksforgeeks.org/merge-two-bsts-with-limited-extra-space/
    fun mergeTrees(root1: TreeNode?, root2: TreeNode?)/*: TreeNode?*/ {
        // s1 is stack to hold nodes of first BST
        val s1 = SNode()

        // Current node of first BST
        var current1: TreeNode? = root1

        // s2 is stack to hold nodes of second BST
        val s2 = SNode()

        // Current node of second BST
        var current2: TreeNode? = root2

        // If first BST is empty, then output is inorder traversal of second BST
        if (root1 == null) {
            inorder(root2)
            return
        }

        // If second BST is empty, then output is inorder traversal of first BST
        if (root2 == null) {
            inorder(root1)
            return
        }

        // Run the loop while there are nodes not yet printed. The nodes may be in stack(explored, but not printed)
        // or may be not yet explored
        while (current1 != null || !s1.isEmpty() || current2 != null || !s2.isEmpty()) {

            // Following steps follow iterative Inorder Traversal
            if (current1 != null || current2 != null) {
                // Reach the leftmost node of both BSTs and push ancestors of leftmost nodes to stack s1 and s2 respectively
                if (current1 != null) {
                    s1.push(current1)
                    current1 = current1.left
                }
                if (current2 != null) {
                    s2.push(current2)
                    current2 = current2.left
                }
            } else {
                // If we reach a NULL node and either of the stacks is empty, then one tree is exhausted, ptint the other tree
                if (s1.isEmpty()) {
                    while (!s2.isEmpty()) {
                        current2 = s2.pop()
                        current2!!.left = null
                        inorder(current2)
                    }
                    return
                }
                if (s2.isEmpty()) {
                    while (!s1.isEmpty()) {
                        current1 = s1.pop()
                        current1!!.left = null
                        inorder(current1)
                    }
                    return
                }

                // Pop an element from both stacks and compare the popped elements
                current1 = s1.pop()
                current2 = s2.pop()

                // If element of first tree is smaller, then print it and push the right subtree. If the element is larger,
                // then we push it back to the corresponding stack.
                if (current1!!.value < current2!!.value) {
                    print(current1.value.toString() + " ")
                    current1 = current1.right
                    s2.push(current2)
                    current2 = null
                } else {
                    print(current2.value.toString() + " ")
                    current2 = current2.right
                    s1.push(current1)
                    current1 = null
                }
            }
        }
        println(s1.t)
        println(s2.t)
    }

    fun inorder(root: TreeNode?) {
        if (root != null) {
            inorder(root.left)
            print(root.value.toString() + " ")
            inorder(root.right)
        }
    }

    class SNode {
        var head: SNode? = null
        var t: TreeNode? = null
        var next: SNode? = null

        // add an element k to stack
        fun push(k: TreeNode?) {
            val tmp = SNode()

            // Perform memory check here
            tmp.t = k
            tmp.next = head
            head = tmp
        }

        // pop an element t from stack
        fun pop(): TreeNode? {
            val st: SNode? = head
            head = head!!.next
            return st!!.t
        }

        // check whether the stack is empty or not
        fun isEmpty() = head == null
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays1 = arrayOf(arrayOf<Int?>(1,3,2,5), arrayOf<Int?>(1))
            val arrays2 = arrayOf(arrayOf(2,1,3,null,4,null,7), arrayOf<Int?>(1,2))
            for (i in arrays1.indices) {
                print("array1: ${arrays1[i].contentToString()}, array2: ${arrays2[i].contentToString()}")
                val tree1 = TreeNode.build(arrays1[i])
                print(", tree1: ")
                tree1?.print()
                val tree2 = TreeNode.build(arrays2[i])
                print(", tree2: ")
                tree2?.print()
                val merged = Assessment4_10().mergeTrees(tree1, tree2)
                print(", merged: ")
                // merged?.print()
                println()
            }
        }
    }
}