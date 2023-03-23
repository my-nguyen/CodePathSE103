#Week 11 - Advanced Guide

##1. Longest Consecutive Sequence

[Original Problem Statement](https://leetcode.com/problems/longest-consecutive-sequence/):

Given an unsorted array of integers, find the length of the longest consecutive elements sequence.

Your algorithm should run in O(n) complexity.

###Understand

Ensure students clarify input and output parameters of the problem:
* Can the input list be Null or empty?
  * “Yes, in either of those cases, we return 0, since there are no consecutive elements.”

Run through a set of example cases:
<pre>
HAPPY CASE
Input: [100, 4, 200, 1, 3, 2]
Output: 4

HAPPY CASE
Input: [5, 2, 3, 4, 1]
Output: 5

EDGE CASE (Duplicate Values)
Input: [1, 0, 2, 1]
Output: 3
</pre>

####Before finishing this section, ensure the students have done the following:

* Established a set (2-3) of test cases to verify their own solution later.
* Established a set (1-2) of edge cases to verify their solution handles complexities.
* Have fully understood the problem and have no clarifying questions.

**NOTE**: Students tend to rush problems. Encouraging students to spend more time in the Understanding phase of the
process may help them avoid issues later in the interview.

###Match

In the Matching step of UMPIRE, you want to think about common patterns and tricks that could apply to this problem.

For Arrays, common solution patterns include:

* Sorting the array first
  * This can help us to find consecutive elements in ascending order. However, this may be too slow, since the fastest
    sorting algorithm we know of is O(N Log(N)).
* Two pointer solutions (left and right pointer variables)
  * Since the input array is not sorted, a left and right pointer solution may not work here.
* Storing the elements of the array in a HashMap or a Set
  * This could work to perform fast lookups. This may need further exploring to solidify a solution.
* Traversing the array with a sliding window
  * Since we don’t have a sorted array nor know the size of the ideal window, this solution may not be feasible for this
    problem.

###Plan

####Before ANY pseudocode, have students explain a general approach to the problem.

Ask the students how they would solve this problem by hand if they were to. This gives a good idea to the brute force
solution. Optimizations can occur on top of this or can inspire new approaches.

If students struggle to create an algorithm pose these questions:

* “What is the brute force solution for this problem?”
* “Since we are looking for elements in an ordered sequence, would sorting help us find an initial solution to this
  problem? What would we do after we sort the array?”
* “When looking for elements in order, what is the delta between elements we look for?”

####Mentors, before diving into specifics about algorithms, have students give a 1-2 sentence summary on their approach.

Sample Approach #1

1-2 Sentence Summary  
Brute force lookup on the array.

1) Keep current and longest streak variables
2) For each number in the array
   * While the succeeding element exists in the array, keep incrementing the search value
   * If the current streak is longer than the max, update the max streak
3) Return the longest/max streak variable

Time Complexity: O(N^3)  
Space Complexity: O(1)

Cons: Algorithm is O(N^3) and we are looking for an O(N) solution.

Sample Approach #2

1-2 Sentence Summary  
Sort the array and traverse the array to find longest streaks of consecutively increasing elements

1) Sort the array, ascending
2) Have a longest and current streak counter variables
3) Traverse the array from left to right
   * If the current element is not equal to the next element in the array
     * If the next element is 1 greater, increment the current streak counter
     * Otherwise, update the max streak value and reset current streak value
   * Increment the current pointer
4) Return the longest/max streak variable

Time Complexity: O(N Log(N))  
Space Complexity: O(1)

Cons: Algorithm is O(N Log(N)) and we are looking for an O(N) solution.

Sample Approach #3

1-2 Sentence Summary  
Store the elements in a HashSet. Traverse the elements of the array. For each element attempt to create a increasing
consecutive streak.

1) Create a Set of all the elements in the input array
2) Create a current and max streak value
2) Traverse the array
   * While the succeeding element exists in the array, keep incrementing the search value and the current streak
   * If the current streak is longer than the max, update the max streak, reset the current streak value
3) Return the max streak

Time Complexity: O(N)  
Space Complexity: O(N)

**NOTE**: Before moving onto implementing the solution, students should have 2-3 happy test cases ready as well as
1-2 edge test cases to verify their solution.

###Implement

Students should discuss how to translate their general algorithm into specific code before implementing.

####Mentors, discuss with students how the algorithm can be implemented:

* This helps students practice understand how algorithm patterns can be easily transferred into code

<pre>
Python Solution (Approach #1):

def longestConsecutive(self, nums):
    longest_streak = 0
    for num in nums:
        current_num = num
        current_streak = 1
        while current_num + 1 in nums:
            current_num += 1
            current_streak += 1
        longest_streak = max(longest_streak, current_streak)
    return longest_streak

Python Solution (Approach #2):

def longestConsecutive(self, nums):
    if not nums:
        return 0

    nums.sort()
    longest_streak, current_streak = 1, 1

    for i in range(1, len(nums)):
        if nums[i] != nums[i-1]:
            if nums[i] == nums[i-1]+1:
                current_streak += 1
            else:
                longest_streak = max(longest_streak, current_streak)
                current_streak = 1

    return max(longest_streak, current_streak)

Python Solution (Approach #3):

def longestConsecutive(self, nums: List[int]) -> int:
    num_set = set(nums)
    longest_streak = 0
    for n in nums:
        cur_num, cur_streak = n, 1
        while cur_num + 1 in num_set:
            cur_num += 1
            cur_streak += 1
        longest_streak = max(longest_streak, cur_streak)
    return longest_streak
</pre>

###Review

Verify the code works with the happy cases created in the “Understand” section. Afterwards, use the code for the edge
cases created in the “Plan” section.

####Before moving on, mentors verify these edge cases:
<pre>
EDGE CASE (Empty Array)
INPUT: []
OUTPUT: 0
</pre>

###Evaluate

Students should discuss the following:

* Student Time Complexity – Best Case: O(N)
* Student Space Complexity – Best Case: O(N) or O(1) [Dependent on Solution]
* Could their solution have been made any more efficient?

Encourage students to discuss alternative solutions, if other valid solutions are present. Helping students understand
there are multiple efficient ways to solve a problem can help alleviate pressure during interview situations.

##2. Maximal Rectangle

[Original Problem Statement](https://leetcode.com/problems/maximal-rectangle/):

Given a 2D binary matrix filled with 0's and 1's, find the largest rectangle containing only 1's and return its area.

###Understand

Ensure students clarify input and output parameters of the problem:

* Can the input be empty?
  * “Yes, the input can be empty. In that case, let’s return 0 since there is no rectangle.”

Run through a set of example cases:
<pre>
HAPPY CASE
Input:
[
["1","0","1","0","0"],
["1","0","1","1","1"],
["1","1","1","1","1"],
["1","0","0","1","0"]
]
Output: 6

EDGE CASE (Single Element)
Input:
[
["1"]
]
Output: 1
</pre>

####Before finishing this section, ensure the students have done the following:

* Established a set (2-3) of test cases to verify their own solution later.
* Established a set (1-2) of edge cases to verify their solution handles complexities.
* Have fully understood the problem and have no clarifying questions.
**NOTE**: Students tend to rush problems. Encouraging students to spend more time in the Understanding phase of the
  process may help them avoid issues later in the interview.

###Match

In the Matching step of UMPIRE, you want to think about common patterns and tricks that could apply to this problem.

Since this problem tends to look like a Dynamic Programming problem, let’s try to match this problem with standard DP
techniques we may have seen before:

* 2D Array Top-Left -> Bottom-Right, Bottom-Up DP Technique
  * The square verison of this problem was solved through this pattern. However, now that we can create rectangles with
    unequal side lengths, this may create an issue with this solution pattern.
* Knapsack-Type Approach
  * There isn’t a Knapsack underlying problem-type to this problem.
* There may be other DP techniques not covered here that may seem fit to discuss

###Plan

####Before ANY pseudocode, have students explain a general approach to the problem.

Ask the students how they would solve this problem by hand if they were to. This gives a good idea to the brute force
solution. Optimizations can occur on top of this or can inspire new approaches.

If students struggle to create an algorithm pose these questions:

* “How can we break this problem down further. What if we look at it row by row?”
* “What if we were calculating thei largest rectangle on each row? We could calculate the heigh of each column on
  each row and compute the largest rectangle on each row.”

####Mentors, before diving into specifics about algorithms, have students give a 1-2 sentence summary on their approach.

Sample Approach #1

1-2 Sentence Summary  
Use previous maximum rectangle computation for each row, similar to Largest Rectangle Histogram problem, to calculate
the largest rectangle in this grid.

Overall Algorithm
1) Create largest area variable to store global max
2) Calculate the height of columns on each row through a standard DP method
3) For each row compute the largest rectangle using the row of the DP array and update max value accordingly
4) Return maximum value from all the rows

Compute Largest Rectangle Algorithm
1) Create a stack to store ascending heights of row
2) Iterate through the row from left to right
   * While the current index height is smaller than the top of the stack height
     * Compute the area of the current index to the top of the stack index and update the max value variable
       * NOTE: Computation of area requires more specific details
   * Push current index onto stack
3) Return largest rectangle area for this row

Time Complexity: O(N * M)  
Space Complexity: O(M)

**NOTE**: Before moving onto implementing the solution, students should have 2-3 happy test cases ready as well as
1-2 edge test cases to verify their solution.

###Implement

Students should discuss how to translate their general algorithm into specific code before implementing.

####Mentors, discuss with students how the algorithm can be implemented:

* This helps students practice understand how algorithm patterns can be easily transferred into code

Python Solution (Approach #1):
<pre>
def maximalRectangle(self, matrix: List[List[str]]) -> int:
    if not matrix or not matrix[0]:
        return 0
    matrix = [[int(matrix[r][c]) for c in range(len(matrix[r]))] for r in range(len(matrix))]
    for r in range(1, len(matrix)):
        for c in range(len(matrix[r])):
            if matrix[r][c] > 0 and matrix[r - 1][c] > 0:
                matrix[r][c] = matrix[r - 1][c] + 1
    return max(largestRectangleArea(row) for row in matrix)

def largestRectangleArea(height: List[int]) -> int:
    height.append(0)
    stack = [-1]
    ans = 0
    for i in range(len(height)):
        while height[i] < height[stack[-1]]:
            h = height[stack.pop()]
            w = i - stack[-1] - 1
            ans = max(ans, h * w)
        stack.append(i)
    height.pop()
    return ans
</pre>

###Review

Verify the code works with the happy cases created in the “Understand” section. Afterwards, use the code for the
edge cases created in the “Plan” section.

####Before moving on, mentors verify these edge cases:
<pre>
EDGE CASE (Empty Array)
INPUT: []
OUTPUT: 0
</pre>

###Evaluate

Students should discuss the following:

* Student Time Complexity – **Best Case: O(N * M)**
* Student Space Complexity – **Best Case: O(M) (Size of a Row)**
* Could their solution have been made any more efficient?

Encourage students to discuss alternative solutions, if other valid solutions are present. Helping students understand
there are multiple efficient ways to solve a problem can help alleviate pressure during interview situations.
