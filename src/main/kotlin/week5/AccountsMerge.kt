package week5

import java.util.*

class AccountsMerge {
    fun accountsMerge(accounts: List<List<String>>): List<List<String>> {
        // return solution1(accounts)
        return solution2(accounts)
    }

    private fun solution1(accounts: List<List<String>>): List<List<String>> {
        val emailToName = mutableMapOf<String, String>()
        val graph = mutableMapOf<String, MutableList<String>>()
        for (account in accounts) {
            var name = ""
            for (email in account) {
                if (name === "") {
                    name = email
                    continue
                }
                graph.computeIfAbsent(email) {
                        x: String? -> ArrayList()
                }!!.add(account[1])
                graph.computeIfAbsent(account[1]) {
                        x: String? -> ArrayList()
                }!!.add(email)
                emailToName[email] = name
            }
        }
        val seen = mutableSetOf<String>()
        val ans = mutableListOf<List<String>>()
        for (email in graph.keys) {
            if (!seen.contains(email)) {
                seen.add(email)
                val stack = Stack<String>()
                stack.push(email)
                val component = mutableListOf<String>()
                while (!stack.empty()) {
                    val node = stack.pop()
                    component.add(node)
                    for (nei in graph[node]!!) {
                        if (!seen.contains(nei)) {
                            seen.add(nei)
                            stack.push(nei)
                        }
                    }
                }
                component.sort()
                component.add(0, emailToName[email]!!)
                ans.add(component)
            }
        }
        return ans
    }

    private fun solution2(accounts: List<List<String>>): List<List<String>> {
        val dsu = DSU()
        val emailToName = mutableMapOf<String, String>()
        val emailToID = mutableMapOf<String, Int>()
        var id = 0
        for (account in accounts) {
            var name = ""
            for (email in account) {
                if (name === "") {
                    name = email
                    continue
                }
                emailToName[email] = name
                if (!emailToID.containsKey(email)) {
                    emailToID[email] = id++
                }
                dsu.union(emailToID[account[1]]!!, emailToID[email]!!)
            }
        }
        val ans = mutableMapOf<Int, MutableList<String>>()
        for (email in emailToName.keys) {
            val index: Int = dsu.find(emailToID[email]!!)
            ans.computeIfAbsent(index) {
                    x: Int? -> mutableListOf()
            }.add(email)
        }
        for (component in ans.values) {
            Collections.sort(component)
            component.add(0, emailToName[component!![0]]!!)
        }
        // return ArrayList<Any?>(ans.values)
        return ans.values.toList()
    }

    internal class DSU {
        val parent = IntArray(10001) { it }
        fun find(x: Int): Int {
            if (parent[x] != x)
                parent[x] = find(parent[x])
            return parent[x]
        }

        fun union(x: Int, y: Int) {
            parent[find(x)] = find(y)
        }

        /*init {
            parent = IntArray(10001)
            for (i in 0..10000) parent[i] = i
        }*/
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val strings = arrayOf(
                listOf(
                    listOf("John","johnsmith@mail.com","john_newyork@mail.com"),
                    listOf("John","johnsmith@mail.com","john00@mail.com"),
                    listOf("Mary","mary@mail.com"),
                    listOf("John","johnnybravo@mail.com")
                ),
                listOf(
                    listOf("Gabe","Gabe0@m.co","Gabe3@m.co","Gabe1@m.co"),
                    listOf("Kevin","Kevin3@m.co","Kevin5@m.co","Kevin0@m.co"),
                    listOf("Ethan","Ethan5@m.co","Ethan4@m.co","Ethan0@m.co"),
                    listOf("Hanzo","Hanzo3@m.co","Hanzo1@m.co","Hanzo0@m.co"),
                    listOf("Fern","Fern5@m.co","Fern1@m.co","Fern0@m.co")
                )
            )
            for (accounts in strings) {
                val merged = AccountsMerge().accountsMerge(accounts)
                println(merged)
            }
        }
    }
}