package week12


class Assessment12_12 {
    fun removeInvalidParentheses(string: String): List<String> {
        return solutionBacktracking(string)
    }

    fun solutionBacktracking(string: String): List<String> {
        reset()
        recurse(string, 0, 0, 0, StringBuilder(), 0)
        return validExpressions.toList()
    }

    fun solutionLimitedBacktracking(s: String): List<String> {
        var left = 0
        var right = 0

        // First, we find out the number of misplaced left and right parentheses.
        for (i in s.indices) {

            // Simply record the left one.
            if (s[i] == '(') {
                left++
            } else if (s[i] == ')') {
                // If we don't have a matching left, then this is a misplaced right, record it.
                right = if (left == 0) right + 1 else right

                // Decrement count of left parentheses because we have found a right
                // which CAN be a matching one for a left.
                left = if (left > 0) left - 1 else left
            }
        }
        recurse(s, 0, 0, 0, left, right, StringBuilder())
        return ArrayList(validExpressions)
    }

    private val validExpressions = mutableSetOf<String>()
    private var minimumRemoved = 0
    private fun reset() {
        validExpressions.clear()
        minimumRemoved = Int.MAX_VALUE
    }

    private fun recurse(string: String, index: Int, leftCount: Int, rightCount: Int, expression: StringBuilder, removedCount: Int) {
        // If we have reached the end of string.
        if (index == string.length) {

            // If the current expression is valid.
            if (leftCount == rightCount) {

                // If the current count of removed parentheses is <= the current minimum count
                if (removedCount <= minimumRemoved) {

                    // Convert StringBuilder to a String. This is an expensive operation.
                    // So we only perform this when needed.
                    val possibleAnswer = expression.toString()

                    // If the current count beats the overall minimum we have till now
                    if (removedCount < minimumRemoved) {
                        validExpressions.clear()
                        minimumRemoved = removedCount
                    }
                    validExpressions.add(possibleAnswer)
                }
            }
        } else {
            val currentCharacter = string[index]
            val length = expression.length

            // If the current character is neither an opening bracket nor a closing one,
            // simply recurse further by adding it to the expression StringBuilder
            if (currentCharacter != '(' && currentCharacter != ')') {
                expression.append(currentCharacter)
                recurse(string, index + 1, leftCount, rightCount, expression, removedCount)
                expression.deleteCharAt(length)
            } else {

                // Recursion where we delete the current character and move forward
                recurse(string, index + 1, leftCount, rightCount, expression, removedCount + 1)
                expression.append(currentCharacter)

                // If it's an opening parenthesis, consider it and recurse
                if (currentCharacter == '(') {
                    recurse(string, index + 1, leftCount + 1, rightCount, expression, removedCount)
                } else if (rightCount < leftCount) {
                    // For a closing parenthesis, only recurse if right < left
                    recurse(string, index + 1, leftCount, rightCount + 1, expression, removedCount)
                }

                // Undoing the append operation for other recursions.
                expression.deleteCharAt(length)
            }
        }
    }

    private fun recurse(string: String, index: Int, leftCount: Int, rightCount: Int, leftRem: Int, rightRem: Int, expression: StringBuilder) {

        // If we reached the end of the string, just check if the resulting expression is
        // valid or not and also if we have removed the total number of left and right
        // parentheses that we should have removed.
        if (index == string.length) {
            if (leftRem == 0 && rightRem == 0) {
                validExpressions.add(expression.toString())
            }
        } else {
            val character = string[index]
            val length = expression.length

            // The discard case. Note that here we have our pruning condition.
            // We don't recurse if the remaining count for that parenthesis is == 0.
            if (character == '(' && leftRem > 0 || character == ')' && rightRem > 0) {
                recurse(string, index + 1, leftCount, rightCount,
                    leftRem - if (character == '(') 1 else 0,
                    rightRem - if (character == ')') 1 else 0,
                    expression
                )
            }
            expression.append(character)

            // Simply recurse one step further if the current character is not a parenthesis.
            if (character != '(' && character != ')') {
                recurse(string, index + 1, leftCount, rightCount, leftRem, rightRem, expression)
            } else if (character == '(') {

                // Consider an opening bracket.
                recurse(string, index + 1, leftCount + 1, rightCount, leftRem, rightRem, expression)
            } else if (rightCount < leftCount) {

                // Consider a closing bracket.
                recurse(string, index + 1, leftCount, rightCount + 1, leftRem, rightRem, expression)
            }

            // Delete for backtracking.
            expression.deleteCharAt(length)
        }
    }
}


