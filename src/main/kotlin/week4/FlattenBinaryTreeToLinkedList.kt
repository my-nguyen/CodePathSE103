package week4

import TreeNode

class FlattenBinaryTreeToLinkedList {
    fun flatten(root: TreeNode?) {
        val list = mutableListOf<TreeNode>()
        preorder(root, list)
        for (i in 0 until list.lastIndex) {
            list[i].left = null
            list[i].right = list[i+1]
        }
    }

    /*
        // Stores root of branch that was previously flattened
        private TreeNode prevRoot = null;

        // Recursively flattens using post-order traversal
        // Visits each node once: O(n) time, O(h) space
        public void flatten(TreeNode root) {
            if (root == null) return;

            // Flattens subtrees in post-order
            // This ensures flattened right sub-list comes after the left sub-list
            flatten(root.right);
            flatten(root.left);

            // After children are flattened, just set head of sub-list as child
            root.right = prevRoot;
            root.left = null;

            prevRoot = root
        }
    */

    private fun preorder(node: TreeNode?, list: MutableList<TreeNode>) {
        if (node == null)
            return

        // first, process current node
        list.add(node)
        // then recursive calls on left and right children
        preorder(node.left, list)
        preorder(node.right, list)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(arrayOf(1,2,5,3,4,null,6), /*arrayOf()*/)
            for (array in arrays) {
                print("array: ${array.contentToString()}")
                val tree = TreeNode.build(array)
                TreeNode.print(tree, ", tree")
                FlattenBinaryTreeToLinkedList().flatten(tree)
                TreeNode.print(tree, ", flattened")
            }
        }
    }
}