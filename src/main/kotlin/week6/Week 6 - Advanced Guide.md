#Week 6 - Advanced Guide

Use this guide to support the discussion of today’s session problems.

[Max Chunks to Make Sorted](https://hackmd.io/@2aDFKMu8S2CUg05JnLuKKg/Hkk2iXBaU#1-Max-Chunks-to-Make-Sorted)
[Trapping Rain Water](https://hackmd.io/@2aDFKMu8S2CUg05JnLuKKg/Hkk2iXBaU#2-Trapping-Rain-Water)

##1. Max Chunks to Make Sorted

[Original Problem Statement](https://leetcode.com/problems/max-chunks-to-make-sorted-ii/):

Given an array arr of integers (not necessarily distinct), we split the array into some number of "chunks" (partitions),
and individually sort each chunk. After concatenating them, the result equals the sorted array.

What is the most number of chunks we could have made?

###Common Mistakes:

* Students get confused why the question asks for the maximum number of chunks rather than minimum.
  * Mentors, take time to explain to the student that the minimum number of chunks for any array is 1, and that problem
    is trivial, since the answer is always 1.
* Students may not clearly understand the potential for duplicate elements
  * There are two variants of the problem. This is the problem with duplicates.

###Understand

Ensure students clarify input and output parameters of the problem:

* Could the input array be null?
  * “Let’s assume the input array is not null. In addition, the input array should have at least 1 element.”
* Could the input numbers be negative?
  * “Yes the input numbers could be positive, negative, or zero.”

Run through a set of example cases:
<pre>
HAPPY CASE
Input: [5,4,3,2,1]
Output: 1

HAPPY CASE
Input: [2,1,3,4,4]
Output: 4

EDGE CASE
Input: [1]
Output: 1
</pre>

###Before finishing this section, ensure the students have done the following:

* Established a set (2-3) of test cases to verify their own solution later.
* Established a set (1-2) of edge cases to verify their solution handles complexities.
* Have fully understood the problem and have no clarifying questions.

NOTE: Students tend to rush problems. Encouraging students to spend more time in the Understanding phase of the process
may help them avoid issues later in the interview.

###Match

*In the Matching step of UMPIRE, you want to think about common patterns and tricks that could apply to this problem*.

In Strings/Arrays, common problem patterns include:

* Hash and Store
  * At each index, what information do we need? A potential solution could store the minimum number to the right of each
    index, key is the index. We could potentially hash that information; however, an array could serve the same purpose.
* Two Pointer
  * Two pointer may help us reference both sides of the array at the same time. However, since the array is not sorted,
    a two pointer solution may not assist us as much.
* Sort
  * How do we know when to split a chunk? If we know the underlying strategy on when to split a chunk (which relies on
    the sorted version of the data), sorting may potentially help us.

###Plan

**Before ANY pseudocode, have students explain a general approach to the problem**.

Ask the students how they would solve this problem by hand if they were to. This gives a good idea to the brute force
solution. Optimizations can occur on top of this or can inspire new approaches.

If students struggle to create an algorithm pose these questions:

* “When do we know we can chunk off a section? Let’s say the first chunk, at what point do we know it is safe enough
  to separate and sort this subarray?”
  * This should hint to the student the exact solution to the problem.
* “If we are at an index and there are no smaller values to the right of that index, can we make a chunk at that point?
  Why or why not?”

**Mentors, before diving into specifics about algorithms, have students give a 1-2 sentence summary on their approach**.

###Sample Approach #1

1-2 Sentence Summary  
Create an array that contains the minimum element to the right of a given index. Use this array to justify where
we create a chunk.

1. Generate an array that stores the minimum value to the right of a given index
2. Create two variables to store the running max value from the left and number of chunks, number of chunks to 1 by default
3. Iterate through both arrays in parallel
  * Update the maximum based on the original array.
  * If the maximum from the left (inclusive) is less than or equal to the minimum from the right, we create a chunk
    there because there is no value to the right of the current subarray that would be required to be sorted with
    the left chunk.
    * By "create a chunk", increment the chunk variable
  * Else, continue growing this chunk
4. Return number of chunks

Time Complexity: O(N)  
Space Complexity: O(N)

###Sample Approach #2

1-2 Sentence Summary  
Create a sorted clone and iterate through both to check if at any point they contain the same values

1. Create a copy of the input array and sort it
2. Create a variable to store the number of chunks, set to 0
3. Create a dictionary to keep track of values between both arrays
4. Iterate through both arrays in parallel
   * Increment value of key of the element in the sorted array
   * Decrement value of key of the element in the original array
   * If the length of the dictionary is 0, or all keys have value 0, we have found a potential chunk
     * Increment number of chunks variable
5. Return number of chunks

Time Complexity: O(N log(N)) [Fastest Sort Time - O(N log(N)), Iteration - O(N)]  
Space Complexity: O(N) [Space of new array - O(N), Max space of Dictionary - O(N)]

NOTE: Before moving onto implementing the solution, students should have 2-3 happy test cases ready as well as 1-2 edge
test cases to verify their solution.

###Implement

Students should discuss how to translate their general algorithm into specific code before implementing.

**Mentors, discuss with students how the algorithm can be implemented**:

* This helps students practice understand how algorithm patterns can be easily transferred into code

###Python Solution (Approach #1):
<pre>
def maxChunksToSorted(self, arr: List[int]) -> int:
    mins_from_right = get_mins_from_right(arr)
    curr_max = 0
    result = 1
    for i in range(len(arr) - 1):
        curr_max = max(curr_max, arr[i])
        if curr_max <= mins_from_right[i + 1]:
            result += 1
    return result

def get_mins_from_right(arr):
    result = []
    curr_min = float('inf')
    for element in reversed(arr):
        curr_min = min(element, curr_min)
        result.append(curr_min)
    return list(reversed(result))
</pre>

###Python Solution (Approach #2):
<pre>
def maxChunksToSorted(self, arr: List[int]) -> int:
    s_arr = sorted(arr)
    output = 0
    m = collections.defaultdict(int)
    for i in range(len(arr)):
        insert(m, s_arr[i], 1)
        insert(m, arr[i], -1)
        if len(m) == 0:
            output += 1
    return output

def insert(m, c, delta):
    m[c] += delta
    if m[c] == 0:
        del m[c]
</pre>

###Review

Verify the code works with the happy cases created in the “Understand” section. Afterwards, use the code for the edge
cases created in the “Plan” section.

**Before moving on, mentors verify these edge cases**:
<pre>
EDGE CASE (Identical elements)
INPUT: [1, 1, 1]
OUTPUT: 3
</pre>

###Evaluate

Students should discuss the following:
* Student Time Complexity – Best Case: O(N)
* Student Space Complexity – Best Case: O(N)
* Could their solution have been made any more efficient?

Encourage students to discuss alternative solutions, if other valid solutions are present. Helping students understand
there are multiple efficient ways to solve a problem can help alleviate pressure during interview situations.

##2. Trapping Rain Water

[Original Problem Statement]([Original Problem Statement]()https://leetcode.com/problems/trapping-rain-water/description/https://leetcode.com/problems/trapping-rain-water/description/):

Given n non-negative integers representing an elevation map where the width
of each bar is 1, compute how much water it is able to trap after raining.
<pre>
Input: [0,1,0,2,1,0,1,3,2,1,2,1]
            _
    _      | |_   _
_  | |_   _| | |_| |_
_|_|_|_|_|_|_|_|_|_|_|_|

Output: 6
              _
      _      | |_   _
  _  | |X X X| | |X| |_
_|_|X|_|_|X|_|_|_|_|_|_|
</pre>

**Common Mistakes**:

* Students try to rush this problem by knowing array techniques they may already know. However, understanding the logic
  behind conditions that allow water to be trapped is the most important aspect to this problem.

###Understand

Ensure students clarify input and output parameters of the problem:

* Could no water be trapped in any of the cells?
  * “That is definitely possible.”
* Can water be held on the edges of the array?
  * “No. Let’s assume the edges can’t really hold water because there is nothing outside the array holding water in those positions. Does that make sense?”
* Could an index have a negative value?
  * “Let’s assume all the values are non-negative.”

Run through a set of example cases:
<pre>
HAPPY CASE
Input: [1, 3, 4, 5, 0]
Output: 0

Input: [1, 0, 2, 4, 2, 4]
Output: 3

EDGE CASE
Input: [0, 0, 0]
Output: 0
</pre>

**Before finishing this section, ensure the students have done the following**:

* Established a set (2-3) of test cases to verify their own solution later.
* Established a set (1-2) of edge cases to verify their solution handles complexities.
* Have fully understood the problem and have no clarifying questions.

NOTE: Students tend to rush problems. Encouraging students to spend more time in the Understanding phase of the process
may help them avoid issues later in the interview.

###Match

In the Matching step of UMPIRE, you want to think about common patterns and tricks that could apply to this problem.

In Strings/Arrays, common problem patterns include:

* Hash and Store
  * What would we hash at each index? If we do hash, how does that help us compute the amount of water we can trap.
* Two Pointer
  * Two pointer may help us reference both sides of the array at the same time. Since we know that water can be trapped
    at a cell that has elements on both sides that are higher than itself. This could help us in this situation.
* Sort
  * Since the order of the elements matters, sorting may not result in a potential solution path.

###Plan

**Before ANY pseudocode, have students explain a general approach to the problem**.

Ask the students how they would solve this problem by hand if they were to. This gives a good idea to the brute force
solution. Optimizations can occur on top of this or can inspire new approaches.

**If students struggle to create an algorithm pose these questions**:

* “If you’re at a certain index of the array. How do you know if water will be stored at that index? What conditions have to be met?”
* “How can we speed up finding those conditions to hold water?”

**Mentors, if students are completely stuck**:

Help them understand the conditions that need to be met for water to be
stored at an index
<pre>
 _
| |                            _
| |                           | |
| |              _            | |
LEFT_MAX <--- CUR_IDX ---> RIGHT_MAX
   3             0             2

MIN(left_max, right_max) - CUR_HEIGHT --> Potential Water Stored
- MIN(3, 2) - 0 --> 2 - 0 --> 2

MAX(0, Potential Water Stored) --> Actual Water Stored
- MAX(0, 2) --> 2
    - This is because we don't want to count places where water can't be stored

                     _                   
                    | |                            _
                    | |              X            | |
                    | |              X            | |
                    LEFT_MAX <--- CUR_IDX ---> RIGHT_MAX
                       3             0 (Holds 2)   2
</pre>

**Mentors, before diving into specifics about algorithms, have students give a 1-2 sentence summary on their approach**.

###Sample Approach #1

1-2 Sentence Summary  
Iterate through the array and for each element find the minimum amount of water to store there.

1. Iterate through the array with a variable to store the total water storage
2. At each point:
   * Find the highest element on the left
   * Find the highest element on the right
   * Calculate if current index can hold water, add it to water storage variable
3. Return water storage variable

Time Complexity: O(N^2)  
Space Complexity: O(1)

Cons: Slow time complexity, nested iteration could be avoided

###Sample Approach #2 (Dynamic Programming)

1-2 Sentence Summary  
Use an additional array to store the minimum left/right max at each point. Use left and right sweeps to reduce time
complexity.

1. Create an auxiliary array, same size as input array
2. Iterate from left to right of the input array
   * Store the running maximum at each index of the auxiliary array
3. Iterate from right to left of the input array
   * Update the auxiliary array to store the minimum of the left and right running max values
4. Iterate through both arrays in parallel
   * Calculate and sum the water storage at each index
5. Return the summed water storage.

Time Complexity: O(N)  
Space Complexity: O(N)

Cons: Additional space complexity. Could we do it without the space?

###Sample Approach #3

1-2 Sentence Summary  
Two pointer approach to calculate water at each index, moving towards middle.

1. Create Left and Right pointers, water variable
2. Keep track of the maxes from both Left and Right sides
3. Move the smaller pointer towards the center
   * With the smaller pointer, update the correct side maximum value
   * Calculate water at the smaller pointer spot, update return variable
     * Calculate water storage with the smaller maximum side value (L/R)
4. Return total water amount

Time Complexity: O(N)  
Space Complexity: O(1) - Same number of pointers made, no variable dependent
on N.

NOTE: Before moving onto implementing the solution, students should have 2-3 happy test cases ready as well as 1-2 edge
test cases to verify their solution.

###Implement

Students should discuss how to translate their general algorithm into specific code before implementing.

**Mentors, discuss with students how the algorithm can be implemented**:

* This helps students practice understand how algorithm patterns can be easily transferred into code

###Python Solution (Approach #1):

NOTE: Time Limit Exceeded on Leetcode
<pre>
def trap(self, height: List[int]) -> int:
    water = 0
    for i in range(len(height)):
        left_idx, left_max = i - 1, 0
        while left_idx >= 0:
            left_max = max(left_max, height[left_idx])
            left_idx -= 1

        right_idx, right_max = i + 1, 0
        while right_idx < len(height):
            right_max = max(right_max, height[right_idx])
            right_idx += 1

        water += max(0, min(left_max, right_max) - height[i])
    return water
</pre>

###Python Solution (Approach #2):

<pre>
def trap(self, height: List[int]) -> int:
    if len(height) < 1:
        return 0

    dp = [0] * len(height)
    highest_left = height[0]
    for i in range(1, len(height) - 1):
        dp[i] = highest_left
        highest_left = max(highest_left, height[i])

    highest_right = height[-1]
    for i in range(len(height) - 1, 0, -1):
        dp[i] = min(dp[i], highest_right)
        highest_right = max(highest_right, height[i])

    return sum([max(0, dp[i] - height[i]) for i in range(1, len(height) - 1)])
</pre>

###Python Solution (Approach #3):

<pre>
def trap(self, height: List[int]) -> int:
    left, right = 0, len(height) - 1
    left_max, right_max = 0, 0
    water = 0
    while left < right:
        if height[left] < height[right]:
            left_max = max(left_max, height[left])
            water += max(0, left_max - height[left])
            left += 1
        else:
            right_max = max(right_max, height[right])
            water += max(0, right_max - height[right])
            right -= 1
    return water
</pre>

###Review

Verify the code works with the happy cases created in the “Understand” section. Afterwards, use the code for the edge
cases created in the “Plan” section.

NOTE: Mentors, ensure students closely track their solution through test cases. Since this problem is complex, some false
positive cases might occur.

**!Before moving on, mentors verify these edge cases**:
<pre>
EDGE CASE (Empty List)
INPUT: []
OUTPUT: 0
</pre>

###Evaluate

Students should discuss the following:

* Student Time Complexity – Best Case: O(N)
* Student Space Complexity – Best Case: O(1)
* Could their solution have been made any more efficient?

Encourage students to discuss alternative solutions, if other valid solutions are present. Helping students understand
there are multiple efficient ways to solve a problem can help alleviate pressure during interview situations.