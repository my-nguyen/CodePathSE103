#Week 10 - Advanced Guide

Use this guide to support the discussion of today’s session problems.

[Palindrome Partitioning](https://hackmd.io/@2aDFKMu8S2CUg05JnLuKKg/HktHcNRJD#1-Palindrome-Partitioning)
[Maximal Square](https://hackmd.io/@2aDFKMu8S2CUg05JnLuKKg/HktHcNRJD#2-Maximal-Square)

##1. Palindrome Partitioning

[Original Problem Statement](https://leetcode.com/problems/palindrome-partitioning/):

Given a string s, partition s such that every substring of the partition is a palindrome.

Return all possible palindrome partitioning of s.

###Common Mistakes:

* Students forget to see that this problem is a ‘generate’ problem-type, which creates a different type of solution.

###Understand

Ensure students clarify input and output parameters of the problem:

* Could the input string be empty?
  * “Let’s assume the input can be empty, and in that case we should return a doubly nested list.”
* What if there are no sub-palindromes within the input string?
  * “A single character string is always a palindrome. That means every input string can be broken down into each single
    character to be sub-palindromes.”

Have students verified any Time/Space Constraints for this problem?

Run through a set of example cases:

<pre>
HAPPY CASE
Input: "aab"
Output:
[
    ["aa","b"],
    ["a","a","b"]
]

HAPPY CASE
Input: "racecar"
Output:
[
    ["r","a","c","e","c","a","r"],
    ["r","a","cec","a","r"],
    ["r","aceca","r"],
    ["racecar"]
]

EDGE CASE (Only Single Letter Palindromes)
Input: "asdf"
Output:
[
    ["a","s","d","f"]
]
</pre>

####Before finishing this section, ensure the students have done the following:

* Established a set (2-3) of test cases to verify their own solution later.
* Established a set (1-2) of edge cases to verify their solution handles complexities.
* Have fully understood the problem and have no clarifying questions.

**NOTE**: Students tend to rush problems. Encouraging students to spend more time in the Understanding phase of
the process may help them avoid issues later in the interview.

###Match

In the Matching step of UMPIRE, you want to think about common patterns and tricks that could apply to this problem.

Since this problem tends to look like a Dynamic Programming problem, let’s try to match this problem with standard DP
techniques we may have seen before:

* 2D Array Top-Left -> Bottom-Right, Bottom-Up DP Technique
  * Since this problem doesn’t deal with a 2D-Array, we cannot apply this pattern towards this problem.
* Knapsack-Type Approach
  * There isn’t a Knapsack underlying problem-type to this problem.
* Cache previous results, generally
  * Caching previously found palindromes within the input string may help us save time in solving this problem.
* There may be other DP techniques not covered here that may seem fit to discuss

###Plan

####Before ANY pseudocode, have students explain a general approach to the problem.

Ask the students how they would solve this problem by hand if they were to. This gives a good idea to the brute force
solution. Optimizations can occur on top of this or can inspire new approaches.

If students struggle to create an algorithm pose these questions:

* “This problem can generate any substrings of size 1->N. How can we generate all of the different possibilities for
  substrings and check to see if they are palindromes?”
* “How can we combine recursion as well as caching, such as a HashSet, to better compute palindromic substrings of
  the input string?”

####Mentors, before diving into specifics about algorithms, have students give a 1-2 sentence summary on their approach.

###Sample Approach #1

1-2 Sentence Summary  
Cache all palindromic substrings of the input. Iterate through each index of the
input string with varying string lengths and perform a cache lookup to generate
palindromic substring lists.

1. Store all palindromic substring indices within a HashSet
2. Create an array of empty lists of size N + 1 to store list of partial substrings
3. Iterate backwards from the end of the input string to the front, as variable i
   * Now perform a nested iteration forward to the end to check all substring lengths until the end of the string
     * If the current substring from i -> j is a palindrome, add it to all partial word lists in index j and place them
       in index i.

**NOTE**: Before moving onto implementing the solution, students should have 2-3 happy test cases ready as well as 1-2
edge test cases to verify their solution.

###Implement

Students should discuss how to translate their general algorithm into specific code before implementing.

####Mentors, discuss with students how the algorithm can be implemented:

* This helps students practice understand how algorithm patterns can be easily transferred into code

Python Solution (Approach #1):

<pre>
def partition(self, s: str) -> List[List[str]]:
    if len(s) < 1:
        return [[]]
    memo = generate_palindromes(s) # O(N^3)
    DP = [[] for _ in range(len(s) + 1)]

    for i in range(len(s) - 1, -1, -1): # O(N)
        for j in range(i + 1, len(s) + 1): # O(N)
            if (i, j) in memo: # O(1)
                for elem in DP[j]: 
                    DP[i].append([s[i:j]] + elem)
                if len(DP[j]) == 0:
                    DP[i].append([s[i:j]])
    return DP[0]

def generate_palindromes(s: str): # O(N^3)
    memo = set()
    for i in range(len(s)): # O(N)
        for j in range(i + 1, len(s) + 1): # O(N)
            this_s = s[i:j] # O(N)
            if this_s == this_s[::-1]: # O(N)
                memo.add((i, j))
    return memo
</pre>

###Review

Verify the code works with the happy cases created in the “Understand” section. Afterwards, use the code for the edge
cases created in the “Plan” section.

####Before moving on, mentors verify these edge cases:

<pre>
EDGE CASE (Empty String)
INPUT: ""
OUTPUT: [[]]
</pre>

###Evaluate

Students should discuss the following:

* Could their solution have been made any more efficient?
* **Since this problem is difficult to analyze in terms of runtime complexity, student’s are not expected to fully
  understand the runtime of their solution.**

Encourage students to discuss alternative solutions, if other valid solutions are present. Helping students understand
there are multiple efficient ways to solve a problem can help alleviate pressure during interview situations.

##2. Maximal Square

[Original Problem Statement](https://leetcode.com/problems/maximal-square/):

Given a 2D binary matrix filled with 0's and 1's, find the largest square containing only 1's and return its area.

###Understand

Ensure students clarify input and output parameters of the problem:

* Can the input be an empty grid? Or can it just be a 1x1 matrix?
* “In the empty case, there is no square, so we return 0 as the area. On the other hand, if it is a 1x1 grid and
  the single element is a 1, then we return 1, since that is its area.”

Run through a set of example cases:

<pre>
HAPPY CASE
Input:  1 0 1 0 0
1 0 1 1 1
1 1 1 1 1
1 0 0 1 0

Output: 4

HAPPY CASE
Input:  1 0 1 0
1 0 1 1
1 1 1 0

Output: 1

EDGE CASE
Input: 0
Output: 0
</pre>

####Before finishing this section, ensure the students have done the following:

* Established a set (2-3) of test cases to verify their own solution later.
* Established a set (1-2) of edge cases to verify their solution handles complexities.
* Have fully understood the problem and have no clarifying questions.

**NOTE**: Students tend to rush problems. Encouraging students to spend more time in the Understanding phase of
the process may help them avoid issues later in the interview.

###Match

In the Matching step of UMPIRE, you want to think about common patterns and tricks that could apply to this problem.

Since this problem tends to look like a Dynamic Programming problem, let’s try to match this problem with standard DP
techniques we may have seen before:

* 2D Array Top-Left -> Bottom-Right, Bottom-Up DP Technique
  * This technique may work in this problem since see a 2D array and a potential to use the top-left to bottom-right
    strategy. What is an expression to determine if a square exists in this type of traversal? How do we look back?
* Knapsack-Type Approach
  * This solution requires a certain formula to look back. However, in this problem we don’t have a clear cut formula
    to build up a solution nor do we have unique look backs. For the most part, this problem looks back at immediate
    indices to determine if a square exists.
* There may be other DP techniques not covered here that may seem fit to discuss

###Plan

####Before ANY pseudocode, have students explain a general approach to the problem.

Ask the students how they would solve this problem by hand if they were to. This gives a good idea to the brute force
solution. Optimizations can occur on top of this or can inspire new approaches.

If students struggle to create an algorithm pose these questions:

* “If we are at the bottom right of a 2x2 square, how do we look back to know we are in a square? If all 2x2 squares
  have already been identified, how do we know we are in the bottom right of a 3x3 square?”
* “Think about the different 2x2 squares in a 3x3 square. How can this help you identify the 3x3 square when you know
  most of the 2x2 squares?”

####Mentors, before diving into specifics about algorithms, have students give a 1-2 sentence summary on their approach.

###Sample Approach #1

1-2 Sentence Summary  
Top-Left to Bottom-Right Build Up Approach. We look back at immediate squares to know if we are extending a previous
square towards a larger one.

1. Iterate from top-left to bottom right of the matrix, from index 1 onwards
   * If the current square is a 1
     * If the minimum value of the top, top-left, and left squares is non-zero, we have formed atleast a square of size
       of that minimum value.
     * Re-assign the current square value to that minimum + 1 (since we extended the squaree)
2. Return the maximum value found on the matrix, squared.

Time Complexity: O(N * M) [Nested traversal of the matrix]  
Space Complexity: O(1) [No extra space allocated]

**NOTE**: Before moving onto implementing the solution, students should have 2-3 happy test cases ready as well as 1-2
edge test cases to verify their solution.

###Implement

Students should discuss how to translate their general algorithm into specific code before implementing.

####Mentors, discuss with students how the algorithm can be implemented:

* This helps students practice understand how algorithm patterns can be easily transferred into code

Python Solution (Approach #1):

<pre>
def maximalSquare(self, matrix: List[List[str]]) -> int:
    if len(matrix) < 1:
        return 0
    mx = 0
    for r in range(len(matrix)):
        for c in range(len(matrix[r])):
            if r > 0 and c > 0:
                matrix[r][c] = min(matrix[r - 1][c - 1], matrix[r - 1][c], matrix[r][c - 1]) + 1 if matrix[r][c] == 1 else matrix[r][c]
            mx = max(int(matrix[r][c]), mx)
    return mx**2
</pre>

###Review

Verify the code works with the happy cases created in the “Understand” section. Afterwards, use the code for the edge
cases created in the “Plan” section.

####Before moving on, mentors verify these edge cases:

<pre>
EDGE CASE (1x1 Case)
INPUT: 1
OUTPUT: 1
</pre>

###Evaluate

Students should discuss the following:

* Student Time Complexity – **Best Case: O(N * M)**
* Student Space Complexity – **Best Case: O(1)**
* Could their solution have been made any more efficient?

Encourage students to discuss alternative solutions, if other valid solutions are present. Helping students understand
there are multiple efficient ways to solve a problem can help alleviate pressure during interview situations.