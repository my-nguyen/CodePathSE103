package week6

class EvaluateDivision {
    fun calcEquation(equations: List<List<String>>, values: DoubleArray, queries: List<List<String>>): DoubleArray {
        // return luis(equations, values, queries)
        return solution1(equations, values, queries)
        // return solution2(equations, values, queries)
    }

    private fun solution1(equations: List<List<String>>, values: DoubleArray, queries: List<List<String>>): DoubleArray {
        val graph = mutableMapOf<String, MutableMap<String, Double>>()

        // Step 1). build the graph from the equations
        for (i in equations.indices) {
            val dividend = equations[i][0]
            val divisor = equations[i][1]
            val quotient = values[i]

            if (!graph.containsKey(dividend))
                graph[dividend] = mutableMapOf()
            graph[dividend]!![divisor] = quotient
            if (!graph.containsKey(divisor))
                graph[divisor] = mutableMapOf()
            graph[divisor]!![dividend] = 1 / quotient
        }

        // Step 2). Evaluate each query via bactracking (DFS) by verifying if there exists a path from dividend to divisor
        val results = DoubleArray(queries.size)
        for (i in queries.indices) {
            val dividend = queries[i][0]
            val divisor = queries[i][1]

            results[i] = when {
                // base case, either the dividend or the divisor is not part of the graph: set results[i] to -1
                !graph.containsKey(dividend) || !graph.containsKey(divisor) -> -1.0
                // base case, when dividend equals divisor: set results[i] to 1
                dividend == divisor -> 1.0
                // start backtracking
                else -> backtrack(graph, dividend, divisor, 1.0, mutableSetOf<String>())
            }
        }
        return results
    }

    private fun solution2(equations: List<List<String>>, values: DoubleArray, queries: List<List<String>>): DoubleArray {
        val weights = mutableMapOf<String, Pair<String, Double>>()

        // Step 1). build the union groups
        for (i in equations.indices) {
            val dividend = equations[i][0]
            val divisor = equations[i][1]
            val quotient = values[i]
            union(weights, dividend, divisor, quotient)
        }

        // Step 2). run the evaluation, with "lazy" updates in find() function
        val results = DoubleArray(queries.size)
        for (i in queries.indices) {
            val dividend = queries[i][0]
            val divisor = queries[i][1]

            results[i] = if (!weights.containsKey(dividend) || !weights.containsKey(divisor))
                // case 1). at least one variable did not appear before
                -1.0
            else {
                val dividendEntry = find(weights, dividend)
                val dividendGroupId = dividendEntry.first
                val dividendWeight = dividendEntry.second
                val divisorEntry = find(weights, divisor)
                val divisorGroupId = divisorEntry.first
                val divisorWeight = divisorEntry.second

                if (dividendGroupId != divisorGroupId)
                    // case 2). the variables do not belong to the same chain/group
                    -1.0
                else
                    // case 3). there is a chain/path between the variables
                    dividendWeight / divisorWeight
            }
        }
        return results
    }

    private fun find(weights: MutableMap<String, Pair<String, Double>>, nodeId: String): Pair<String, Double> {
        if (!weights.containsKey(nodeId))
            weights[nodeId] = Pair(nodeId, 1.0)

        val entry = weights[nodeId]!!
        if (entry.first != nodeId) {
            // found inconsistency, trigger chain update
            val newEntry = find(weights, entry.first)
            weights[nodeId] = Pair(newEntry.first, entry.second * newEntry.second)
        }
        return weights[nodeId]!!
    }

    private fun union(weights: MutableMap<String, Pair<String, Double>>, dividend: String, divisor: String, value: Double) {
        val dividendEntry = find(weights, dividend)
        val divisorEntry = find(weights, divisor)
        val dividendGroupId = dividendEntry.first
        val divisorGroupId = divisorEntry.first
        val dividendWeight = dividendEntry.second
        val divisorWeight = divisorEntry.second

        // merge the two groups together, by attaching the dividend group to the one of divisor
        if (dividendGroupId != divisorGroupId) {
            weights[dividendGroupId] = Pair(divisorGroupId, divisorWeight * value / dividendWeight)
        }
    }

    private fun backtrack(graph: Map<String, Map<String, Double>>, node: String, target: String, product: Double, visited: MutableSet<String>): Double {
        // temporarily mark the node as visited
        visited.add(node)

        // start backtracking
        var result = -1.0
        val map = graph[node]!!
        // recall that neighbors of current node is a map from divisor to quotient, where quotient is node over divisor
        if (map.containsKey(target))
            // if map contains target, calculate the product and we're done
            result = product * map[target]!!
        else {
            // if map doesn't contain target, dfs on all map entries
            for ((nextNode, value) in map) {
                // only backtrack on entries that haven't been visited
                if (!visited.contains(nextNode)) {
                    result = backtrack(graph, nextNode, target, product * value, visited)
                    if (result != -1.0)
                        break
                }
            }
        }

        // unmark the node
        visited.remove(node)
        return result
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val equations = arrayOf(
                listOf(listOf("a","b"), listOf("b","c")),
                listOf(listOf("a","b"), listOf("b","c"), listOf("bc","cd")),
                listOf(listOf("a","b")),
            )
            val values = arrayOf(
                doubleArrayOf(2.0,3.0),
                doubleArrayOf(1.5,2.5,5.0),
                doubleArrayOf(0.5)
            )
            val queries = arrayOf(
                listOf(listOf("a","c"), listOf("b","a"), listOf("a","e"), listOf("a","a"), listOf("x","x")),
                listOf(listOf("a","c"), listOf("c","b"), listOf("bc","cd"), listOf("cd","bc")),
                listOf(listOf("a","b"), listOf("b","a"), listOf("a","c"), listOf("x","y")),
            )
            for (i in equations.indices) {
                val calculated = EvaluateDivision().calcEquation(equations[i], values[i], queries[i])
                println("calculated: ${calculated.contentToString()}")
            }
        }
    }
}
