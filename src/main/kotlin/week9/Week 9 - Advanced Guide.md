#Week 9 - Advanced Guide

Use this guide to support the discussion of today’s session problems.

[Word Break](https://hackmd.io/@2aDFKMu8S2CUg05JnLuKKg/S1On8RgkD#1.-Word-Break)
[Longest Palindromic Substring](https://hackmd.io/@2aDFKMu8S2CUg05JnLuKKg/S1On8RgkD#2.-Longest-Palindromic-Substring)

##1. Word Break

[Original Problem Statement](https://leetcode.com/problems/word-break/):

Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, determine if s can be
segmented into a space-separated sequence of one or more dictionary words.

NOTE:
* The same word in the dictionary may be reused multiple times in the segmentation.
* You may assume the dictionary does not contain duplicate words.

####Common Mistakes:

* Students sometimes miss how there are invalid paths that ‘trick’ their program down a certain path while there were
  also other valid words in the bank. Usually, this is due to premature matching and no ‘recursive’ efforts.

###Understand

Ensure students clarify input and output parameters of the problem:

* Can the input word be blank?
  * “No, let’s assume the input word is not empty. However, the input word bank may be empty.”
* Can there be multiple valid solutions?
  * “Yes, there may be multiple valid solutions. If so, the end result will still be the same, so the specific solution
    path does not matter much.”

Have students verified any Time/Space Constraints for this problem?

Run through a set of example cases:
<pre>
HAPPY CASE
Input: word = "leetcode", wordList = ["leet", "code"]
Output: True -> Return true because "leetcode" can be segmented as "leet code".

Input: word = "applepenapple", wordList = ["apple", "pen"]
Output: true -> Return true because "applepenapple" can be segmented
as "apple pen apple".

Input: word= "catsandog", wordList = ["cats", "dog", "sand", "and", "cat"]
Output: false
</pre>

####Before finishing this section, ensure the students have done the following:

* Established a set (2-3) of test cases to verify their own solution later.
* Established a set (1-2) of edge cases to verify their solution handles complexities.
* Have fully understood the problem and have no clarifying questions.

NOTE: Students tend to rush problems. Encouraging students to spend more time in the Understanding phase of the process
may help them avoid issues later in the interview.

##Match

In the Matching step of UMPIRE, you want to think about common patterns and tricks that could apply to this problem.

For Strings, common solution patterns include:

* Two pointer solutions (left and right pointer variables) or a pointer on each string
  * Since we are not comparing/computing palindromes, the two pointer solution will not give us an effective solution.
* Storing the characters of the string in a HashMap or a Set
  * In terms of a quick lookup in the word bank, a HashSet makes perfect sense to reduce lookup time from O(N) to O(1)
* Traversing the string with a sliding window
  * A sliding window of each word in the word bank may help us but will not lead to an efficient solution and may create
    a ‘hacky’ solution.

NOTE: Mentors, assist students by diving into other patterns that are usually not covered as often. Examples include:
Tries, DP Tables

###Plan

####Before ANY pseudocode, have students explain a general approach to the problem.

Ask the students how they would solve this problem by hand if they were to. This gives a good idea to the brute force
solution. Optimizations can occur on top of this or can inspire new approaches.

If students struggle to create an algorithm pose these questions:

* “How many different substrings do we need to compare to see if we have a match? Do we only need to traverse the input
  word linearly without any nesting, or do we need nesting?”
* “If we traverse the string without nested loops (string[i:j] combinations), could we find a premature solution that
  isn’t a true solution to the word bank?”

####Mentors, before diving into specifics about algorithms, have students give a 1-2 sentence summary on their approach.

Sample Approach #1

1-2 Sentence Summary  
Recursively check all ~N^2 substrings of the input string

1. Check if the input is empty, if so we have found a solution, return True
2. Iterate the string from left to right:
   * If the current substring exists in the dictionary, recursively call the function for the rest of the string.
     * If the recursive call returns True, return True
3. Return False if none of the recursive calls returns True.

Time Complexity: O(2^N)  
Space Complexity: O(1)

Cons: 2^N runtime is almost never accepted, so we need a faster solution. Most recursive functions, like this one, have
a DP alternative.

Sample Approach #2

1-2 Sentence Summary  
Use a DP Array to check if any substring from i -> j is in the word bank. Build up this solution to the last index of
the input string.

1. Construct a boolean array of size N + 1 as a DP tool
2. Set the first index to True
   * This means there is a valid substring up until that index (exclusive)
3. Iterate from 0 -> N as variable i
   * If the current index, i, in the DP boolean array is True, we iterate from
     * -> N as variable j
     * If the substring input[i:j] is within the word bank, we mark DP[j] as true
       * This signifies the substring of the input from 0 -> j (exlusive) is valid
   * Continue to build the solution to the end
4. The boolean value of the last index signifies whether the input string can be broken

NOTE: N - Size of Input String, M - Size of Input Word Bank

Time Complexity: O(N^3) [Nested Iteration as well as String Creation]  
Space Complexity: O(N) [DP Array of Size N]

NOTE: Before moving onto implementing the solution, students should have 2-3 happy test cases ready as well as 1-2 edge
test cases to verify their solution.

###Implement

Students should discuss how to translate their general algorithm into specific code before implementing.

####Mentors, discuss with students how the algorithm can be implemented:

This helps students practice understand how algorithm patterns can be easily transferred into code

<pre>
Python Solution (Approach #1):
def wordBreak(self, word: str, wordList: List[str]) -> bool:
    if word == "":
        return True
    else:
        wordLen = len(word)
        return any([(word[:i] in wordList) and self.wordBreak(word[i:], wordList) for i in range(1, wordLen+1)])

Python Solution (Approach #2):
def wordBreak(self, word: str, wordList: List[str]) -> bool:
    wordSet = set(wordList) #O(M)
    dp = [True] + [False] * len(word)
    for i in range(len(word)): # O(N)
        if dp[i]:
            for j in range(i + 1, len(word) + 1): # O(N)
                if word[i:j] in wordSet: # O(N) String Generation
                    dp[j] = True
    return dp[-1]
</pre>

###Review

Verify the code works with the happy cases created in the “Understand” section. Afterwards, use the code for the edge
cases created in the “Plan” section.

####Before moving on, mentors verify these edge cases:

<pre>
EDGE CASE (Pre-Mature Exit)
INPUT: word = "aaaaaaa", wordList = ["aaaa","aaa"]
OUTPUT: False
</pre>

###Evaluate

Students should discuss the following:

* Student Time Complexity – Best Case: O(N^3)
* Student Space Complexity – Best Case: O(N)
* Could their solution have been made any more efficient?

Encourage students to discuss alternative solutions, if other valid solutions are present. Helping students understand
there are multiple efficient ways to solve a problem can help alleviate pressure during interview situations.

##2. Longest Palindromic Substring

[Original Problem Statement](https://leetcode.com/problems/longest-palindromic-substring/):

Given a string s, find the longest palindromic substring in s.

####Common Mistakes:
* Students sometimes get confused between the terms substring and subsequence. Verify with students that they understand
  the definition of this problem.

###Understand

Ensure students clarify input and output parameters of the problem:
* Can the input be empty or Null?
  * “The input could be empty but not Null.”

Run through a set of example cases:
<pre>
HAPPY CASE
Input: "babad"
Output: "bab", NOTE: "aba" is also another valid answer

Input: "cbbd"
Output: "bb"

EDGE CASE (Empty String)
Input: ""
Output: ""
</pre>

####Before finishing this section, ensure the students have done the following:
* Established a set (2-3) of test cases to verify their own solution later.
* Established a set (1-2) of edge cases to verify their solution handles complexities.
* Have fully understood the problem and have no clarifying questions.

NOTE: Students tend to rush problems. Encouraging students to spend more time in the Understanding phase of the process
may help them avoid issues later in the interview.

###Match

In the Matching step of UMPIRE, you want to think about common patterns and tricks that could apply to this problem.

For Strings, common solution patterns include:
* Two pointer solutions (left and right pointer variables) or a pointer on each string
  * To identify a palindrome when expanding a palindrome on both left and right sides, a two pointer strategy can help
    us identify if the string is a palindrome much faster. The two pointer strategy is definitely a portion of
    the solution but not the entire solution.
* Storing the characters of the string in a HashMap or a Set
  * A HashMap or Set causes us to lose order of the string elements, which defeats the purpsoe of identifying
    a palindrome. This strategy won’t make an impact on this problem.
* Traversing the string with a sliding window
  * A growing sliding window size may help us identify the longest palindromic substring, but it does not offer
    an efficient solution.

###Plan

####Before ANY pseudocode, have students explain a general approach to the problem.

Ask the students how they would solve this problem by hand if they were to. This gives a good idea to the brute force
solution. Optimizations can occur on top of this or can inspire new approaches.

If students struggle to create an algorithm pose these questions:
* “How can we identify is a string is a palindrome?”
* “If we have a valid palindrome and we added two of the same characters to both sides of the palindrome, is it still
  a palindrome?”

####Mentors, before diving into specifics about algorithms, have students give a 1-2 sentence summary on their approach.

####Sample Approach #1
1-2 Sentence Summary  
2-D DP Array to help store previous solutions. Grow solution by iterating through the 2D Array and comparing characters
that are added to previous palindromes.

1. Create a 2D Boolean Array of N x N size
2. Iterate through the array in column-major order, variable r (row)
   * Nested iteration with upper bound of r, variable c (column)
     * If the elements at indices r and c of the input string are identical, and the difference is at max 2, this is
       a sub-palindrome. Mark DP[r][c] as True.
     * If the elements at indices r and c are identical yet the difference is greater than 2, refer to the sub-problem
       of DP[r + 1][c - 1]and build on top of previous solution.
       * DP[r + 1][c - 1] refers to the sub-string 1 element smaller on both sides when compared to the current 
         from r -> c.
3. Perform another nested iteration, identical pattern as before.
   * Identify the largest length substring (c - r + 1) that is a palindrome(DP[r][c] is True)
4. Return the substring slice of the largest substring found in the second nested traversal.

Time Complexity: O(N^2)  
Space Complexity: O(N^2)

Cons: N^2 Space taken up by the DP Table, which is not essential to the problem.

####Sample Approach #2
1-2 Sentence Summary  
There are 2N-1 possible 'centers' to palindrome substrings of the input string. Build away from the center of each of
these potential centers.

1. Create two different variables to keep track of the start and end of the largest substring palindrome
2. Iterate from left to right of the input string
   * Build from the center of the current index to calculate the largest possible odd length palindrome
   * Build from the current index and its rightwards neighbor to calculate the largest possible even length palindrome.
   * Compute the largest palindrome between the two and update tracker variables
3. Return the slice of the largest calculated palindrome during the iteration.


Time Complexity: O(N^2)  
Space Complexity: O(1)

####Sample Approach #3 (Manacher’s Algorithm)
Students are NOT expected to know this algorithm; however, to understand the most optimal O(N) solution. Take a look at
this [article](https://en.wikipedia.org/wiki/Longest_palindromic_substring#Manacher's_algorithm) to understand.

NOTE: Before moving onto implementing the solution, students should have 2-3 happy test cases ready as well as 1-2 edge
test cases to verify their solution.

###Implement

Students should discuss how to translate their general algorithm into specific code before implementing.

####Mentors, discuss with students how the algorithm can be implemented:

This helps students practice understand how algorithm patterns can be easily transferred into code

<pre>
Python Solution (Approach #1):

def longestPalindrome(self, s):
    if not s or len(s) < 1:
        return ""

    arr = [[False for x in range(len(s))] for y in range(len(s))]
    for i in range(0, len(s)):
        for j in range(0, i + 1):
            if s[i] == s[j] and (i - j + 1 <= 2 or arr[j + 1][i - 1]):
                arr[j][i] = True
    m = -1
    start, end = -1, -1
    for i in range(0, len(s)):
        for j in range(0, i + 1):
            if arr[j][i] and i - j + 1 > m:
                m = i - j + 1
                start, end = j, i

    return s[start: end + 1]

Python Solution (Approach #2):

def longestPalindrome(self, s: str) -> str:
    if not s or len(s) < 1:
        return ""

    start, end = 0, 0
    for i in range(len(s)):
        l1 = find_len(s, i, i)
        l2 = find_len(s, i, i + 1)
        max_len = max(l1, l2)
        if max_len > end - start:
            start = i - (max_len - 1) // 2
            end = i + max_len // 2
    return s[start: end + 1]

def find_len(s, i, j):
    l, r = i, j
    while l >= 0 and r < len(s) and s[l] == s[r]:
        l, r = l - 1, r + 1
    return r - l - 1
</pre>

###Review

Verify the code works with the happy cases created in the “Understand” section. Afterwards, use the code for the edge
cases created in the “Plan” section.

####Before moving on, mentors verify these edge cases:

<pre>
EDGE CASE (Unique Character String)
INPUT: "fhsuer"
OUTPUT: Any single letter string will suffice as a solution.
</pre>

###Evaluate

Students should discuss the following:

* Student Time Complexity – Best Case: O(N^2)
* Student Space Complexity – Best Case: O(1)
* Could their solution have been made any more efficient?

Encourage students to discuss alternative solutions, if other valid solutions are present. Helping students understand
there are multiple efficient ways to solve a problem can help alleviate pressure during interview situations.
