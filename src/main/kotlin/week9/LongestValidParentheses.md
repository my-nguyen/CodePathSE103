#Longest Valid Parentheses

Given a string containing just the characters '(' and ')', find the length of the longest valid (well-formed)
parentheses substring.

###Example 1:
**Input**: s = "(()"  
**Output**: 2  
**Explanation**: The longest valid parentheses substring is "()".

###Example 2:
**Input**: s = ")()())"  
**Output**: 4  
**Explanation**: The longest valid parentheses substring is "()()".

###Example 3:
**Input**: s = ""  
**Output**: 0

###Constraints:

* 0 <= s.length <= 3 * 104
* s[i] is '(', or ')'.

##Summary

We need to determine the length of the largest valid substring of parentheses from a given string.

#Solution

##Approach 1: Brute Force

###Algorithm

In this approach, we consider every possible non-empty even length substring from the given string and check whether
it's a valid string of parentheses or not. In order to check the validity, we use the Stack's Method.

Every time we encounter a ‘(’, we push it onto the stack. For every ‘)’ encountered, we pop a ‘(’ from the stack. If ‘(’
isn't available on the stack for popping at anytime or if stack contains some elements after processing complete
substring, the substring of parentheses is invalid. In this way, we repeat the process for every possible substring and
we keep on storing the length of the longest valid string found so far.

Example:
"((())"

(( --> invalid
(( --> invalid
() --> valid, length=2
)) --> invalid
((()--> invalid
(())--> valid, length=4
maxlength=4

##Approach 2: Using Dynamic Programming

###Algorithm

This problem can be solved by using Dynamic Programming. We make use of a dp array where ith element of dp represents
the length of the longest valid substring ending at ith index. We initialize the complete dp array with 0's. Now,
it's obvious that the valid substrings must end with ‘)’. This further leads to the conclusion that the substrings
ending with ‘(’ will always contain '0' at their corresponding dp indices. Thus, we update the dp array only when
‘)’ is encountered.

To fill dp array we will check every two consecutive characters of the string and if

1. s[i] = ‘)’ and s[i−1] = ‘(’, i.e. string looks like ".......()" => dp[i] = dp[i−2] + 2
   We do so because the ending "()" portion is a valid substring anyhow and leads to an increment of 2 in the length
   of the just previous valid substring's length.

2. s[i] = ‘)’ and s[i−1] = ‘)’, i.e. string looks like ".......))" =>
   if s[i−dp[i−1]−1] = ‘(’ then
   dp[i] = dp[i−1] + dp[i−dp[i−1]−2] + 2
   The reason behind this is that if the 2nd last ‘)’ was a part of a valid substring (say subs), for the last ‘)’ to be
   a part of a larger substring, there must be a corresponding starting ‘(’ which lies before the valid substring of
   which the 2nd last ‘)’ is a part (i.e. before subs). Thus, if the character before subs happens to be ‘(’, we update
   the dp[i] as an addition of 2 in the length of subs, which is dp[i−1]. To this, we also add the length of the valid
   substring just before the term "(,sub_s,)" , i.e. dp[i−dp[i−1]−2].

##Approach 3: Using Stack

###Algorithm

Instead of finding every possible string and checking its validity, we can make use of stack while scanning the given
string to check if the string scanned so far is valid, and also the length of the longest valid string. In order to do
so, we start by pushing −1 onto the stack.

For every ‘(’ encountered, we push its index onto the stack.

For every ‘)’ encountered, we pop the topmost element and subtract the current element's index from the top element of
the stack, which gives the length of the currently encountered valid string of parentheses. If while popping the element,
the stack becomes empty, we push the current element's index onto the stack. In this way, we keep on calculating
the lengths of the valid substrings, and return the length of the longest valid string at the end.

##Approach 4: Without extra space

###Algorithm

In this approach, we make use of two counters left and right. First, we start traversing the string from the left towards
the right and for every ‘(’ encountered, we increment the left counter and for every ‘)’ encountered, we increment the
right counter. Whenever left becomes equal to right, we calculate the length of the current valid string and keep track
of maximum length substring found so far. If right becomes greater than left we reset left and right to 0.

Next, we start traversing the string from right to left and similar procedure is applied.