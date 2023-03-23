package week6

import java.util.*


class SortItemsByGroupsRespectingDependencies {
    fun sortItems(n: Int, m: Int, group: IntArray, beforeItems: List<List<Int>>): IntArray {
        return leetcode1(n, m, group, beforeItems)
    }

    /*
    Logic is to get topological order of vertices and group. Then create final ordering of vertices.

    Steps:

    Create edge graph of vertices and create group depedency graph
    Get topological order of group and vertices
    Traverse group order and put all vertices within group together and sort them based their topological order.
    Step1:
    Let say there are m groups: g0, g1, .... gm-1.
    Let say following are vertices in group:

    g0: v1, v2, v3
    g1: v4, v5, v6
    g2: v7, v8
    and so on
    Lets say vertices has following edges:

    v1->v2,
    v1-> v3,
    v2->v4,
    v4->v5->v6,
    v5->v7
    v7->v8
    Observe edge v2->v4, this means g0 has dependency on g1, i.e create edge from g0 to g1.
    Similalry edge v5->v7 means g1->g2

    Group graph:

    g0 -> g1
    g1 -> g2
    Step2:
    Now create topogical order of vertices and groups:

    vertices: v1, v2, v4, v5, v7, v8, v3, v6
    group: g0, g1, g2
    Step3:
    Now we have to place all vertices within group together. As we have topological order of group: g0 -> g1 -> g2, let
    group vertices together : (v1, v2, v3) -> (v4, v5, v6) -> (v7, v8)

    Now we know that vertices within group should follow topological order. We have topological order of vertices
    calculated above.
    Now sort vertices within group based on their topolocal order.
    Topological Order: v1, v2, v4, v5, v7, v8, v3, v6
    Indexes in order list: 0, 1, 2, 3, 4, 5, 6, 7

    Final order: (g0 -> g1 -> g2)
    Vertex: (v1 -> v2 -> v3) -> (v4 -> v5 -> v6) -> (v7 -> v8)
    Index: (0 -> 1 -> 6) -> (2 -> 3 -> 7) -> (4 -> 5)
     */
    // https://leetcode.com/problems/sort-items-by-groups-respecting-dependencies/discuss/1240498/Java-or-Topological-sorting-or-With-explanation
    private fun leetcode1(n: Int, m: Int, group: IntArray, beforeItems: List<List<Int>>): IntArray {
        var m = m
        val nodeToGroup = mutableMapOf<Int, Int>() // node to group id mapping
        val groups = mutableMapOf<Int, MutableList<Int>>() // group to list of items in group mapping
        val groupGraph = mutableMapOf<Int, MutableList<Int>>() // graph of group dependency
        val edgeGraph = mutableMapOf<Int, MutableList<Int>>()  // graph of edge dependency
        for (i in 0 until n) {
            val g = if (group[i] != -1) group[i] else m++
            nodeToGroup[i] = g
            groups.putIfAbsent(g, mutableListOf())
            groups[g]!!.add(i)
            groupGraph.putIfAbsent(g, mutableListOf())
            edgeGraph[i] = mutableListOf()
        }
        for (i in beforeItems.indices) {
            val g = nodeToGroup[i]!!
            for (j in beforeItems[i].indices) {
                val u = beforeItems[i][j]

                // Create edge from u to i
                edgeGraph[u]!!.add(i)
                val fromGroup = nodeToGroup[u]!!

                // Create edge from group of u to group of i
                // Avoid creating group cycles due to dependency on edges within same group
                if (fromGroup != g) {
                    groupGraph.putIfAbsent(fromGroup, mutableListOf())
                    groupGraph[fromGroup]!!.add(g)
                }
            }
        }
        val groupOrder = getTopologicalOrder(groupGraph) ?: return IntArray(0)
        val edgeOrder = getTopologicalOrder(edgeGraph) ?: return IntArray(0)

        // Topological order within group
        val orderedGroupNodes = mutableMapOf<Int, MutableList<Int>>()
        for (u in edgeOrder) {
            val g = nodeToGroup[u]!!
            orderedGroupNodes.putIfAbsent(g, mutableListOf())
            orderedGroupNodes[g]!!.add(u)
        }
        val ans = IntArray(n)
        var index = 0

        // Now traverse topological order of group and add all nodes within group.
        for (g in groupOrder) {
            for (u in orderedGroupNodes[g]!!) {
                ans[index++] = u
            }
        }
        return ans
    }

    /*
    After seeing the hint, we want to create two graphs one for items and other for groups
    Moving non-grouped items (with -1) to individual groups is copied from @ispobock :
    https://leetcode.com/problems/sort-items-by-groups-respecting-dependencies/discuss/931999/Java-Generic-Topologic-Sort-Solution-Implemented-with-BFS-Using-Priority-Queue

    Prepare graphs for bothe groups and items
    Run topsort on groups and if there is a cycle stop and return empty array
    Now for each group returned in #2, run topsort of items that are present in that group. In other words, to run
    topsort on items, instead of picking items from 0 to n, pick the items that belong to the groups returned in #2 in
    that order
    If there is cycle in #3 return empty array
    If not return answer from #3
     */
    // https://leetcode.com/problems/sort-items-by-groups-respecting-dependencies/discuss/958566/Java-beats-99-with-topsort
    var findex = 0
    private fun leetcode2(n: Int, m: Int, group: IntArray, beforeItems: List<List<Int>>): IntArray {
        //keep all non grouped items into separate groups,
        //so that we find the cycle among all items that belong to individual groups
        var m = m
        for (i in group.indices) {
            if (group[i] == -1) group[i] = m++
        }
        val groupMap = Array(m) { mutableListOf<Int>() }
        val groupItemMap = Array(m) { mutableListOf<Int>() }
        for (i in 0 until m) {
            groupMap[i] = ArrayList()
            groupItemMap[i] = ArrayList()
        }
        val items = Array(m) { mutableListOf<Int>() }
        //prepare group graph, group to item relation from given items
        for (i in 0 until n) {
            items[i] = ArrayList()
            for (j in beforeItems[i]) {
                //if dependent group is not the same group we are walking through
                if (group[j] != group[i]) groupMap[group[i]].add(group[j])
                items[i].add(j)
            }
            groupItemMap[group[i]].add(i)
        }
        val sortedGroups = IntArray(m)
        //if groups cannot be topsorted, stop
        if (!topsortGroups(groupMap, sortedGroups)) return intArrayOf()
        val answer = IntArray(n)
        //if items cannot be topsorted, stop
        return if (!topsortItems(groupItemMap, items, answer, sortedGroups)) intArrayOf() else answer
    }

    private fun topsortGroups(groupMap: Array<MutableList<Int>>, sortedGroups: IntArray): Boolean {
        val visited = IntArray(sortedGroups.size)
        findex = 0
        for (i in sortedGroups.indices) {
            if (!dfs(groupMap, visited, sortedGroups, i)) return false
        }
        return true
    }

    private fun topsortItems(groupItemMap: Array<MutableList<Int>>, items: Array<MutableList<Int>>, answer: IntArray, sortedGroups: IntArray): Boolean {
        val visited = IntArray(items.size)
        findex = 0
        for (i in sortedGroups.indices) {
            //fetch items belong to group i
            for (item in groupItemMap[sortedGroups[i]]) {
                if (!dfs(items, visited, answer, item)) return false
            }
        }
        return true
    }

    private fun dfs(graph: Array<MutableList<Int>>, visited: IntArray, buffer: IntArray, node: Int): Boolean {
        if (visited[node] == 1) return true
        if (visited[node] == -1) return false //cycle
        visited[node] = -1
        for (i in graph[node]) {
            if (!dfs(graph, visited, buffer, i)) return false
        }
        buffer[findex++] = node
        visited[node] = 1
        return true
    }

    private fun getTopologicalOrder(graph: Map<Int, MutableList<Int>>): List<Int>? {
        val indegree = getIndegree(graph)
        val q = LinkedList<Int>()
        for (key in indegree.keys) {
            if (indegree[key] == 0) q.add(key)
        }
        val list = mutableListOf<Int>()
        while (!q.isEmpty()) {
            val u = q.poll()
            list.add(u)
            if (graph[u] != null) {
                for (v in graph[u]!!) {
                    indegree[v] = indegree[v]!! - 1
                    if (indegree[v] == 0) {
                        q.add(v)
                        indegree.remove(v)
                    }
                }
            }
        }
        return if (list.size == graph.size) list else null
    }

    private fun getIndegree(graph: Map<Int, MutableList<Int>>): MutableMap<Int, Int> {
        val indegree = mutableMapOf<Int, Int>()
        for ((key, value) in graph) {
            indegree.putIfAbsent(key, 0)
            for (`val` in value!!) {
                indegree[`val`] = indegree.getOrDefault(`val`, 0)!! + 1
            }
        }
        return indegree
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val n = arrayOf(8, 8)
            val m = arrayOf(2, 2)
            val groups = arrayOf(intArrayOf(-1,-1,1,0,0,1,0,-1), intArrayOf(-1,-1,1,0,0,1,0,-1))
            val before = arrayOf(
                listOf(listOf(), listOf(6), listOf(5), listOf(6), listOf(3,6), listOf(), listOf(), listOf()),
                listOf(listOf(), listOf(6), listOf(5), listOf(6), listOf(3), listOf(), listOf(4), listOf())
            )
            for (i in n.indices) {
                val items = SortItemsByGroupsRespectingDependencies().sortItems(n[i], m[i], groups[i], before[i])
                println("items: ${items.contentToString()}")
            }
        }
    }
}