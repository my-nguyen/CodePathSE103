#Palindrome Partitioning

Given a string s, partition s such that every substring of the partition is a **palindrome**. Return all possible
palindrome partitioning of s.

A **palindrome** string is a string that reads the same backward as forward.

###Example 1:
**Input**: s = "aab"  
**Output**: [["a","a","b"],["aa","b"]]

###Example 2:
**Input**: s = "a"  
**Output**: [["a"]]

###Constraints:
* 1 <= s.length <= 16
* s contains only lowercase English letters.

#Solution

##Overview

The aim to partition the string into all possible palindrome combinations. To achieve this, we must generate all
possible substrings of a string by partitioning at every index until we reach the end of the string. Example, abba can
be partitioned as ["a","ab","abb","abba"]. Each generated substring is considered as a potential candidate if it
a [Palindrome](https://en.wikipedia.org/wiki/Palindrome).

Let's look at a few approaches to implement this idea.

##Approach 1: Backtracking

###Intuition

The idea is to generate all possible substrings of a given string and expand each possibility if is a potential
candidate. The first thing that comes to our mind is [Depth First Search](https://en.wikipedia.org/wiki/Depth-first_search).
In Depth First Search, we recursively expand potential candidate until the defined goal is achieved. After that, we
backtrack to explore the next potential candidate.

[Backtracking](https://en.wikipedia.org/wiki/Backtracking) incrementally build the candidates for the solution and
discard the candidates (backtrack) if it doesn't satisfy the condition.

The backtracking algorithms consists of the following steps:
* Choose: Choose the potential candidate. Here, our potential candidates are all substrings that could be generated from
  the given string.
* Constraint: Define a constraint that must be satisfied by the chosen candidate. In this case, the constraint is that
  the string must be a palindrome.
* Goal: We must define the goal that determines if have found the required solution and we must backtrack. Here, our goal
  is achieved if we have reached the end of the string.

###Algorithm

In the backtracking algorithm, we recursively traverse over the string in depth-first search fashion. For each recursive
call, the beginning index of the string is given as start.
1. Iteratively generate all possible substrings beginning at start index. The end index increments from start till
   the end of the string.
2. For each of the substring generated, check if it is a palindrome.

3. If the substring is a palindrome, the substring is a potential candidate. Add substring to the currentList and
   perform a depth-first search on the remaining substring. If current substring ends at index end, end+1 becomes
   the start index for the next recursive call.
4. Backtrack if start index is greater than or equal to the string length and add the currentList to the result.

##Approach 2: Backtracking with Dynamic Programming

###Intuition

This approach uses a similar Backtracking algorithm as discussed in Approach 1. But, the previous approach performs
one extra iteration to determine if a given substring is a palindrome or not. Here, we are repeatedly iterating over
the same substring multiple times and the result is always the same. There are
[Overlapping Subproblems](https://en.wikipedia.org/wiki/Overlapping_subproblems) and we could further optimize
the approach by using dynamic programming to determine if a string is a palindrome in constant time. Let's understand
the algorithm in detail.

###Algorithm

A given string s starting at index start and ending at index end is a palindrome if following conditions are satisfied:
1. The characters at start and end indexes are equal.
2. The substring starting at index start+1 and ending at index end−1 is a palindrome.
![](https://leetcode.com/problems/palindrome-partitioning/Figures/131/palindrome_dp.png)

Let N be the length of the string. To determine if a substring starting at index start and ending at index end is
a palindrome or not, we use a 2 Dimensional array dp of size N*N where,

dp[start][end] = true, if the substring beginning at index start and ending at index end is a palindrome.

Otherwise, dp[start][end] == false.

Also, we must update the dp array, if we find that the current string is a palindrome.

