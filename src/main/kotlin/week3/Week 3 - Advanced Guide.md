#Week 3 - Advanced Guide

Use this guide to support the discussion of today’s session problems.

[Evaluate Reverse Polish Notation](https://hackmd.io/@2aDFKMu8S2CUg05JnLuKKg/HyhH1zghL#1.-Evaluate-Reverse-Polish-Notation)
[Basic Calculator](https://hackmd.io/@2aDFKMu8S2CUg05JnLuKKg/HyhH1zghL#2.-Basic-Calculator)

##1. Evaluate Reverse Polish Notation

[Original Problem Statement](https://leetcode.com/problems/evaluate-reverse-polish-notation/description/):

Evaluate the value of an arithmetic expression in Reverse Polish Notation.

Valid operators are +, -, *, /. Each operand may be an integer or another expression.

Input: ["2", "1", "+", "3", "*"]  
Output: 9  
Explanation: ((2 + 1) * 3) = 9

####Common Mistakes:

* Students tend to avoid using a stack or don’t see it at first. They iterate through the array with non-standard
  techniques that may create a confusing solution. (Which may not pass edge cases)
  * Mentors, urge the use of a data structure from the start to avoid a confusing solution.
* Some students have no seen RPN before. Please take extra time to understand how RPN works before moving forward with
  the problem.

###Understand

Ensure students clarify input and output parameters of the problem:

* Could there be more operators than computable in in the input array?
  * “Let’s assume the operands and operators come in to make a valid expression. No need to check for an excess operator
    or operand for a malformed input array.”
* Are there floating point operations? For example, what would 15/4 be?
  * “Let’s compute all expressions on an integer basis. So, 15/4 would result in 3. We don’t consider the remainder as
    a decimal result.”
* The input type of the array elements are strings, is that correct?
  * “Yes, we need to ensure we are computing integer arithmetic. We need to change those input strings in the array.
    Additionally, note the output of the solution is an Integer not a String.”

Run through a set of example cases:

<pre>
HAPPY CASE
Input: ["4", "13", "5", "/", "+"]
Output: 6

Input: ["10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"]
Output: 22

EDGE CASE
Input: ["5", "13", "-"]
Output: -8
</pre>

####Before finishing this section, ensure the students have done the following:

* Established a set (2-3) of test cases to verify their own solution later.
* Established a set (1-2) of edge cases to verify their solution handles complexities.
* Have fully understood the problem and have no clarifying questions.

NOTE: Students tend to rush problems. Encouraging students to spend more time in the Understanding phase of the process
may help them avoid issues later in the interview.

###Match

In the Matching step of UMPIRE, you want to think about common patterns and tricks that could apply to this problem.

* Hash and Store elements of the array
  * Since we are computing a mathematical expression, it is extremely important to preserve order! This means hashing
    and storing these elements wouldn’t help us since we are dealing with order sensitive information.
* Two Pointer
  * We could possibly use two pointers in this problem. However, there is a lot of backing up and moving forward in
    this problem that depends on multiple factors. Although a solution may be present, this problem doesn’t seem
    to scream this pattern type.
* Sort
  * As mentioned earlier, since we are dealing with order-sensitive data, it is not a good idea to sort the data.

####Since none of the patterns we usually apply to arrays works here, what other strategy could we apply? What have we seen this problem relies on? ORDER!

###Plan

####Before ANY pseudocode, have students explain a general approach to the problem.

Ask the students how they would solve this problem by hand if they were to. This gives a good idea to the brute force
solution. Optimizations can occur on top of this or can inspire new approaches.

####If students struggle to create an algorithm pose these questions:

* “What data structures do we know that deal with order of elements. That preserve the order yet allow us to traverse
  the data in different ways?”
  * Hint towards Stacks and Queues.
  * Once students understand a Stack would be the ideal tool in this situation, have them run through cases in how
    a Stack would be used step-by-step.

####Mentors, before diving into specifics about algorithms, have students give a 1-2 sentence summary on their approach.

###Sample Approach #1

1-2 Sentence Summary  
Use a Stack to traverse through the array and store operands. When encountering an operator, use the top two elements on the Stack.

1. Create a Stack
2. Traverse through the array
   * If the element is an integer, push it onto the Stack
   * If the elment is an operator, pop the top two elements off the Stack, and compute the expression
     * Push the result onto the Stack
3. The Stack should only have 1 element on the top, return the top element

Time Complexity: O(N)  
Space Complexity: O(N)

NOTE: Before moving onto implementing the solution, students should have 2-3 happy test cases ready as well as 1-2 edge
test cases to verify their solution.

###Implement

Students should discuss how to translate their general algorithm into specific code before implementing.

####Mentors, discuss with students how the algorithm can be implemented:

* This helps students practice understand how algorithm patterns can be easily transferred into code

###Python Solution (Approach #1):

<pre>
def evalRPN(self, tokens: List[str]) -> int:
    s = [] # Arrays can act as Stacks in Python
    for c in tokens:
        val = compute(s.pop(), s.pop(), c) if isOperator(c) else c
        s.append(int(val))
    return s.pop()

# Helper Func
def compute(n1, n2, operator):
    if operator == "+":
        return n2 + n1
    elif operator == "-":
        return n2 - n1
    elif operator == "*":
        return n2 * n1
    else:
        return n2 / n1

# Helper Func
def isOperator(c):
    return c == "+" or c == "-" or c == "*" or c == "/"
</pre>

###Review

Verify the code works with the happy cases created in the “Understand” section. Afterwards, use the code for the edge
cases created in the “Plan” section.

####Before moving on, mentors verify these edge cases:

<pre>
EDGE CASE (Negative numbers)
INPUT: ["-3", "-3" "/"]
OUTPUT: 1
</pre>

###Evaluate

Students should discuss the following:

* Student Time Complexity – **Best Case: O(N)**
* Student Space Complexity – **Best Case: O(N)**
* Could their solution have been made any more efficient?

Encourage students to discuss alternative solutions, if other valid solutions are present. Helping students understand
there are multiple efficient ways to solve a problem can help alleviate pressure during interview situations.

##2. Basic Calculator

[Original Problem Statement](https://leetcode.com/problems/basic-calculator/):

Implement a basic calculator to evaluate a simple expression string.

The expression string may contain open ( and closing parentheses ), the plus + or minus sign -, non-negative integers
and empty spaces .

####Common Mistakes:

* Students tend to struggle the most with the parentheses in this problem. Students might create an O(N^2) solution
  to deal with parentheses. After this solution is presented, switch to an O(N) solution.

###Understand

Ensure students clarify input and output parameters of the problem:

* Is the input string a valid expression? Do we need to error check?
  * “Let’s assume error checking is done before the expression is sent to our method. So, we can just assume the string
    is a valid expression.”
* Do we need to take care or consider multiplication/division?
  * “No, for this problem, let’s only deal with addition/subtraction.”

Have students verified any Time/Space Constraints for this problem?

Run through a set of example cases:

<pre>
HAPPY CASE
Input: 1 + (2 + 3) - 4
Output: 2

Input: 5 - 1
Output: 4

EDGE CASE (No Operator)
Input: 1
Output: 1
</pre>

####Before finishing this section, ensure the students have done the following:

* Established a set (2-3) of test cases to verify their own solution later.
* Established a set (1-2) of edge cases to verify their solution handles complexities.
* Have fully understood the problem and have no clarifying questions.

NOTE: Students tend to rush problems. Encouraging students to spend more time in the Understanding phase of the process
may help them avoid issues later in the interview.

###Match

In the Matching step of UMPIRE, you want to think about common patterns and tricks that could apply to this problem.

For Strings/Math/Arrays, common solution patterns include:

* Sorting the string first
  * Sorting the string would remove essential formatting necessary to evaluate the expression. It doesn’t look like
    sorting would help us.
* Two pointer solutions (left and right pointer variables)
  * The two pointer solution may help us find and isolate parenthesized portions of an expression. However, it doesn’t
    look like a full solution to the problem.
* Storing the elements of the string in a HashMap or a Set
  * Without the order of the elements, the HashMap/Set strategy does not help us.
* Traversing the string with a sliding window
  * There isn’t a fixed evaluation size within the string, so sliding windows wouldn’t work.

####It is important students evaluate all strategies, even though none significantly help the problem. This maintains structure in their interview preparation.

NOTE: If students have done the RPN Eval problem before, hint them to remember the solution to that problem. (This helps refer to using a stack).

###Plan

####Before ANY pseudocode, have students explain a general approach to the problem.

If students struggle to create an algorithm pose these questions:

* "If we see a “(”, we are essentially restarting a new computation, right? This is some tribute to recursion, right?
  What data structure could help us simulate that recursion without recursively calling a function?
* “If we see a number after an operator, we essentially add/subtract that number to a rolling answer, correct? Since
  this is all additions/subtractions theres not ‘real’ order of operations. How do we simulate this rolling answer?”

####Mentors, before diving into specifics about algorithms, have students give a 1-2 sentence summary on their approach.

###Sample Approach #1

1-2 Sentence Summary  
Use a stack to store previous operator/operand combinations and compute the answer as we go.

1. Create a stack to store operator/operand combinatins
2. Create variables to store the latest operator, operand, and moving 'result'
3. Iterate through the expression:
   * If the element is a +
     * Add the last operator * operand to the moving result
     * Set the new operator to +
   * If the element is a -
     * Add the last operator * operand to the moving result
     * Set the new operator to -
   * If the element is a number
     * Set the operand to that value
   * If the element is a (
     * Push the operator and operand onto the stack to store for later
   * If the element is a )
     * Add the last operator * operand to the moving result
     * Pop the elements off the stack to compute moving result from before (
4. Compute the last part of the expression
5. Return the moving 'result'

Time Complexity: O(N) [Single, non-nested iteration through the input string]  
Space Complexity: O(N) [Stack]

NOTE: Before moving onto implementing the solution, students should have 2-3 happy test cases ready as well as 1-2 edge
test cases to verify their solution.

###Implement

Students should discuss how to translate their general algorithm into specific code before implementing.

####Mentors, discuss with students how the algorithm can be implemented:

* This helps students practice understand how algorithm patterns can be easily transferred into code

###Python Solution (Approach #1):

<pre>
def calculate(self, s: str) -> int:
    stack = [] # store sign and operand of previous expressions
    operand = 0
    res = 0 # For the on-going result
    POSITIVE, NEGATIVE = 1, -1
    sign = POSITIVE # 1 means positive, -1 means negative

    for c in s:
        if c.isdigit():
            operand = (operand * 10) + int(c)

        elif c == '+':
            res += sign * operand # add previous expression
            sign, operand = POSITIVE, 0

        elif c == '-':
            res += sign * operand
            sign, operand = NEGATIVE, 0

        elif c == '(':
            stack.append(res)
            stack.append(sign)

            sign = POSITIVE
            res = 0

        elif c == ')':
            res += sign * operand

            res *= stack.pop() # stack pop 1, sign
            res += stack.pop() # stack pop 2, operand

            # Reset the operand
            operand = 0

    res += sign * operand
    return res
</pre>

###Review

Verify the code works with the happy cases created in the “Understand” section. Afterwards, use the code for the edge
cases created in the “Plan” section.

####Before moving on, mentors verify these edge cases:

<pre>
EDGE CASE (No operators)
INPUT: 0
OUTPUT: 0
</pre>

###Evaluate

Students should discuss the following:

* Student Time Complexity – **Best Case: O(N)**
* Student Space Complexity – **Best Case: O(N)**
* Could their solution have been made any more efficient?

Encourage students to discuss alternative solutions, if other valid solutions are present. Helping students understand
there are multiple efficient ways to solve a problem can help alleviate pressure during interview situations