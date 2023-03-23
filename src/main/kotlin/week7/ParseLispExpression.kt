package week7

import java.util.*


class ParseLispExpression {
    fun evaluate(expression: String): Int {
        return 0
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val expressions = arrayOf(
                "(let x 2 (mult x (let x 3 y 4 (add x y))))",
                "(let x 3 x 2 x)",
                "(let x 1 y 2 x (add x y) (add x y))",
                "(let x 2 (add (let x 3 (let x 4 x)) x))",
                "(let a1 3 b2 (add a1 1) b2)"
            )
            for (expression in expressions) {
                val eval = ParseLispExpression().evaluate(expression)
                println("value: $eval")
            }
        }
    }
}

/*
Instead of reading exp from left to right, we could read the exp from right to left in this case.
We also need a data structure to build a parse tree, which can store the operation, variable, or value as well as the child and parent nodes.
Since add, mult, and let, are reseved words, we could use a String field to hold operation, variable, or value, named op in this implementaion.
The following are the logic on how to build the tree:

) -> Start a new child node and we don't know what operation will be for this node yet.
Go through all the var, operation, or value and add them into the stack of current node.
( -> we can know the operation now, which is stored at the top of the stack of current node. Thus, we pop the operation and the assign it to current node's op. Go back to the parent node.
We don't need to evalute the let assignment yet. Just build the tree.
As for the evaluation, just start from the root and do DFS to collect the result.
 */
    class Exp(from: Exp?) {
        var exps: Stack<Exp>
        var op: String? = null
        var parent: Exp?
        fun evaluate(vars: Map<String?, Int?>): Int {
            return if (op.equals("add", ignoreCase = true)) {
                exps.pop().evaluate(vars) + exps.pop().evaluate(vars)
            } else if (op.equals("mult", ignoreCase = true)) {
                exps.pop().evaluate(vars) * exps.pop().evaluate(vars)
            } else if (op.equals("let", ignoreCase = true)) {
                val nextVars: MutableMap<String?, Int?> = HashMap(vars)
                while (exps.size > 1) {
                    val varName = exps.pop().op
                    val `val` = exps.pop().evaluate(nextVars)
                    nextVars[varName] = `val`
                }
                exps.pop().evaluate(nextVars)
            } else {
                if (Character.isLetter(op!![0])) {
                    vars[op]!!
                } else {
                    op!!.toInt()
                }
            }
        }

        init {
            exps = Stack()
            parent = from
        }
    }

    fun buildTree(exp: String): Exp {
        val root: Exp = Exp(null)
        var cur: Exp? = root
        var n = exp.length - 1
        while (n >= 0) {
            val c = exp[n]
            if (c == ')') {
                val next: Exp = Exp(cur)
                cur!!.exps.push(next)
                cur = next
            } else if (c == '(') {
                cur!!.op = cur.exps.pop().op
                cur = cur!!.parent
            } else if (c != ' ') {
                var pre = n
                while (pre >= 0 && exp[pre] != '(' && exp[pre] != ' ') pre--
                val next: Exp = Exp(cur)
                next.op = exp.substring(pre + 1, n + 1)
                cur!!.exps.push(next)
                n = pre + 1
            }
            n--
        }
        return root.exps.pop()
    }

    fun evaluate(exp: String): Int {
        return buildTree(exp).evaluate(HashMap())
    }


class Solution2 {
    fun evaluate(expression: String): Int {
        return eval(expression, HashMap())
    }

    fun eval(expression: String, map: Map<String?, Int?>): Int {
        //check for Number - positive or negative
        if (isNumber(expression)) return Integer.valueOf(expression)
        //check if this is a variable
        if (isVariable(expression)) return map[expression]!!

        //result
        var res = 0
        //tokens list
        val list = parse(expression)
        //if the operation is add, then add by recursive call to first operand and second operand
        if (list[0] == "add") res = eval(list[1], map) + eval(list[2], map) else if (list[0] == "mult") res = eval(
            list[1], map
        ) * eval(list[2], map) else {
            //we need new set of map to store the variables in this scope
            //extend on top of existing map
            val newMap: MutableMap<String?, Int?> = HashMap()
            newMap.putAll(map)
            //loop thorugh 1 and length-1 skipping paranthesis
            // step 2 skipping spaces
            var i = 1
            while (i < list.size - 1) {
                //add the tokens and its value will be given by a recursive call
                newMap[list[i]] = eval(list[i + 1], newMap)
                i += 2
            }
            //the last expession recursive call
            res = eval(list[list.size - 1], newMap)
        }
        return res
    }

    //check if first character is a digit or -
    fun isNumber(expr: String): Boolean {
        val c = expr[0]
        return Character.isDigit(c) || c == '-'
    }

    //if character is lower case
    fun isVariable(expr: String): Boolean {
        val c = expr[0]
        return Character.isLowerCase(c)
    }

    fun parse(expr: String): List<String> {
        //list to hold all the tokens
        var expr = expr
        val res: MutableList<String> = ArrayList()
        //skip the first and last character for '(' and  ')''
        expr = expr.substring(1, expr.length - 1)
        var i = 0
        // strip off the tokens by iteration
        while (i < expr.length) {
            val j = find(expr, i)
            //add to the list
            res.add(expr.substring(i, j))
            i = j + 1
        }
        return res
    }

    fun find(expr: String, i: Int): Int {
        var index = i
        //if the expression contains parantheses
        if (expr[index] == '(') {
            var count = 1
            index++
            // loop further and check if any more expressions
            while (index < expr.length && count > 0) {
                //increment and decrement based on the parantheses
                if (expr[index] == '(') count++ else if (expr[index] == ')') count--
                index++
            }
        } else  // increment the index until space
            while (index < expr.length && expr[index] != ' ') index++
        return index
    }
}

class Solution {
    //expression = integer | letExpress | addExpress | multExpress | variable
    //integer = [+/-] {1-9}
    //variable = {a-z}{1-9}{a-z}
    //letExpress = (let {variable expression} expr) --> hashmap.
    //addExpress = (add expression expression)
    //multExpress = (mult expression expression)
    var i = 0
    var s: String? = null
    var n = 0
    var vars = HashMap<String, Stack<Int>>()
    fun evaluate(expression: String?): Int {
        s = expression
        n = s!!.length
        return expression()
    }

    private fun expression(): Int {
        if (isInteger) return integer()
        return if (s!![i] == '(') {
            if (s!![i + 1] == 'a') addExpression() else if (s!![i + 1] == 'm') multExpression() else letExpression()
        } else {
            vars[variable()]!!.peek()
        }
    }

    private fun letExpression(): Int {
        i += 5 // consume "(let "
        val assigned: MutableList<String> = ArrayList() //when out of scope remove the assignment
        var res: Int? = null
        while (i < n && s!![i] != '(') { // not last expression.
            if (isInteger) { //we expect a var but it's an integer, meaning it's the last expr
                res = integer()
                break
            }
            val `var` = variable()
            if (s!![i] == ')') { //the next token is ) meaning this is the last expression.
                res = vars[`var`]!!.peek()
                break
            }
            i++ // consume space
            val `val` = expression()
            assigned.add(`var`)
            vars.computeIfAbsent(
                `var`
            ) { k: String? -> Stack() }.push(`val`)
            i++ // consume space
        }

        //last expr is an expression.
        if (res == null) res = expression()
        i++ // consume ")"
        for (`var` in assigned) {
            vars[`var`]!!.pop()
        }
        return res
    }

    private fun addExpression(): Int {
        i += 5 // consume "(add "
        var res = expression()
        i++ // consume " "
        res += expression()
        i++ // consume ")"
        return res
    }

    private val isInteger: Boolean
        private get() = i < n && (s!![i] == '+' || s!![i] == '-' || Character.isDigit(s!![i]))

    private fun multExpression(): Int {
        i += 6 // consume "(mult "
        var res = expression()
        i++ // consume " "
        res *= expression()
        i++ // consume ")"
        return res
    }

    private fun integer(): Int {
        var isNeg = false
        if (s!![i] == '+') i++
        if (s!![i] == '-') {
            isNeg = true
            i++
        }
        var num = 0
        while (i < n && Character.isDigit(s!![i])) {
            num = num * 10 + s!![i++].toInt() - '0'.toInt()
        }
        return if (isNeg) -num else num
    }

    private fun variable(): String {
        val sb = StringBuilder()
        while (i < n && Character.isAlphabetic(s!![i].toInt()) ||
            Character.isDigit(s!![i])
        ) {
            sb.append(s!![i++])
        }
        return sb.toString()
    }
}