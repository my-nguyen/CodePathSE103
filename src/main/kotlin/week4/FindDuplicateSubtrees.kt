package week4

import TreeNode


class FindDuplicateSubtrees {
    fun findDuplicateSubtrees(root: TreeNode?): List<TreeNode?> {
        // return mine(root)
        return leetcode(root)
    }

    // 1. Create a signature for each node.
    // 2. Add node to the result if you have already seen that signature before.
    // 3. Make sure to add prefix for sub-left tree and sub-right tree representations in order to avoid some cases
    // at which signature are same but trees are not duplicate.
    // https://leetcode.com/problems/find-duplicate-subtrees/discuss/1269235/Java-Easy-to-Understand-Solution-with-Explanation
    private fun leetcode(root: TreeNode?): List<TreeNode?> {
        // frequency of string ids, where a string id is "l#4r#" or "ll#4r#2r#", or "lll#4r#2r#3rl#4r#"
        val map = mutableMapOf<String, Int>()
        val result = mutableListOf<TreeNode>()
        traverse(root, map, result)
        return result
    }

    fun traverse(node: TreeNode?, map: MutableMap<String, Int>, result: MutableList<TreeNode>): String? {
        if (node == null) {
            // mark a null with "#"
            return "#"
        }

        // "l#4r#": left-null, center-4, right-null: this is leaf node 4 (with null left child and null right child)
        // "ll#4r#2r#": node with value 2, left child is leaf node 4 and right child is null
        val key = StringBuilder()
            .append("l")
            .append(traverse(node.left, map, result))
            .append(node.value)
            .append("r")
            .append(traverse(node.right, map, result))
            .toString()
        val count = map.getOrDefault(key, 0)
        if (count == 1) {
            result.add(node)
        }
        map[key] = count + 1
        println("key: $key, value: ${map[key]}")
        return key
    }

    private fun mine(root: TreeNode?): List<TreeNode?> {
        val result = mutableListOf<TreeNode>()
        if (root == null)
            return result

        val visited = mutableMapOf<Int, MutableList<TreeNode>>()
        dfs(root, visited, result)
        return result
    }

    private fun dfs(root: TreeNode?, visited: MutableMap<Int, MutableList<TreeNode>>, result: MutableList<TreeNode>) {
        if (root != null) {
            visited.putIfAbsent(root.value, mutableListOf())
            val list = visited[root.value]!!
            // traverse the saved list to see if the current root node has the same subtree as any of those in list
            for (node in list) {
                if (isEqual(node, root)) {
                    var found = false
                    for (entry in result) {
                        if (isEqual(entry, root)) {
                            found = true
                            break
                        }
                    }
                    if (!found)
                        result.add(root)
                    break
                }
            }
            list.add(root)

            dfs(root.left, visited, result)
            dfs(root.right, visited, result)
        }
    }

    private fun isEqual(node1: TreeNode?, node2: TreeNode?): Boolean {
        return if (node1 == null && node2 == null) {
            true
        } else if (node1 != null && node2 != null && node1.value == node2.value) {
            isEqual(node1.left, node2.left) && isEqual(node1.right, node2.right)
        } else {
            false
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val arrays = arrayOf(
                arrayOf(1,2,3,4,null,2,4,null,null,4) /*arrayOf<Int?>(2,1,1), arrayOf(2,2,2,3,null,3,null)*/
            )
            for (array in arrays) {
                print("array: ${array.contentToString()}")
                val tree = TreeNode.build(array)
                TreeNode.print(tree, ", tree")
                val duplicates = FindDuplicateSubtrees().findDuplicateSubtrees(tree)
                println(", duplicates: $duplicates")
            }
        }
    }
}