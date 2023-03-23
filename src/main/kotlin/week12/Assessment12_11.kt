package week12

import TreeNode
import java.util.*
import kotlin.collections.ArrayDeque

class Assessment12_11 {
    fun recoverTree(root: TreeNode?): Unit {

    }

    fun inorder(root: TreeNode?, nums: MutableList<Int?>) {
        if (root == null) return
        inorder(root.left, nums)
        nums.add(root.value)
        inorder(root.right, nums)
    }

    fun findTwoSwapped(nums: List<Int>): IntArray {
        val n = nums.size
        var x = -1
        var y = -1
        for (i in 0 until n - 1) {
            if (nums[i + 1] < nums[i]) {
                y = nums[i + 1]
                // first swap occurence
                x = if (x == -1) nums[i] else break
            }
        }
        return intArrayOf(x, y)
    }

    fun recover(r: TreeNode?, count: Int, x: Int, y: Int) {
        var count = count
        if (r != null) {
            if (r.value == x || r.value == y) {
                r.value = if (r.value == x) y else x
                if (--count == 0) return
            }
            recover(r.left, count, x, y)
            recover(r.right, count, x, y)
        }
    }

    fun solutionSort(root: TreeNode?) {
        val nums = mutableListOf<Int>()
        inorder(root, nums)
        val swapped = findTwoSwapped(nums)
        recover(root, 2, swapped[0], swapped[1])
    }


    fun swap(a: TreeNode?, b: TreeNode?) {
        val tmp: Int = a.value
        a.value = b.value
        b.value = tmp
    }

    fun solutionIterativeInorder(root: TreeNode?) {
        var root: TreeNode? = root
        val stack: Deque<TreeNode> = ArrayDeque()
        var x: TreeNode? = null
        var y: TreeNode? = null
        var pred: TreeNode? = null
        while (!stack.isEmpty() || root != null) {
            while (root != null) {
                stack.add(root)
                root = root.left
            }
            root = stack.removeLast()
            if (pred != null && root.value < pred.value) {
                y = root
                x = if (x == null) pred else break
            }
            pred = root
            root = root.right
        }
        swap(x, y)
    }

    var x: TreeNode? = null
    var y: TreeNode? = null
    var pred: TreeNode? = null
    fun swap(a: TreeNode?, b: TreeNode?) {
        val tmp: Int = a.value
        a.value = b.value
        b.value = tmp
    }

    fun findTwoSwapped(root: TreeNode?) {
        if (root == null) return
        findTwoSwapped(root.left)
        if (pred != null && root.value < pred.value) {
            y = root
            x = if (x == null) pred else return
        }
        pred = root
        findTwoSwapped(root.right)
    }

    fun solutionRecursiveInorder(root: TreeNode?) {
        findTwoSwapped(root)
        swap(x, y)
    }

    fun swap(a: TreeNode?, b: TreeNode?) {
        val tmp: Int = a.value
        a.value = b.value
        b.value = tmp
    }

    fun solutionMorrisInorder(root: TreeNode) {
        // predecessor is a Morris predecessor.
        // In the 'loop' cases it could be equal to the node itself predecessor == root.
        // pred is a 'true' predecessor,
        // the previous node in the inorder traversal.
        var root: TreeNode = root
        var x: TreeNode? = null
        var y: TreeNode? = null
        var pred: TreeNode? = null
        var predecessor: TreeNode? = null
        while (root != null) {
            // If there is a left child
            // then compute the predecessor.
            // If there is no link predecessor.right = root --> set it.
            // If there is a link predecessor.right = root --> break it.
            if (root.left != null) {
                // Predecessor node is one step left
                // and then right till you can.
                predecessor = root.left
                while (predecessor.right != null && predecessor.right !== root) predecessor = predecessor.right

                // set link predecessor.right = root
                // and go to explore left subtree
                if (predecessor.right == null) {
                    predecessor.right = root
                    root = root.left
                } else {
                    // check for the swapped nodes
                    if (pred != null && root.value < pred.value) {
                        y = root
                        if (x == null) x = pred
                    }
                    pred = root
                    predecessor.right = null
                    root = root.right
                }
            } else {
                // check for the swapped nodes
                if (pred != null && root.value < pred.value) {
                    y = root
                    if (x == null) x = pred
                }
                pred = root
                root = root.right
            }
        }
        swap(x, y)
    }
}
