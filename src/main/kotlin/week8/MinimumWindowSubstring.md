#Minimum Window Substring

Given two strings s and t of lengths m and n respectively, return the **minimum window substring** of s such that every
character in t (**including duplicates**) is included in the window. If there is no such substring, return the empty
string "".

The testcases will be generated such that the answer is **unique**.

A **substring** is a contiguous sequence of characters within the string.

###Example 1:
**Input**: s = "ADOBECODEBANC", t = "ABC"  
**Output**: "BANC"  
**Explanation**: The minimum window substring "BANC" includes 'A', 'B', and 'C' from string t.

###Example 2:
**Input**: s = "a", t = "a"  
**Output**: "a"  
**Explanation**: The entire string s is the minimum window.

###Example 3:
**Input**: s = "a", t = "aa"  
**Output**: ""  
**Explanation**: Both 'a's from t must be included in the window.
Since the largest window of s only has one 'a', return empty string.

###Constraints:

* m == s.length
* n == t.length
* 1 <= m, n <= 105
* s and t consist of uppercase and lowercase English letters.

**Follow up**: Could you find an algorithm that runs in O(m + n) time?

#Solution

##Approach 1: Sliding Window

###Intuition

The question asks us to return the minimum window from the string S which has all the characters of the string T. Let us
call a window desirable if it has all the characters from T.

We can use a simple sliding window approach to solve this problem.

In any sliding window based problem we have two pointers. One right pointer whose job is to expand the current window
and then we have the left pointer whose job is to contract a given window. At any point in time only one of these
pointers move and the other one remains fixed.

The solution is pretty intuitive. We keep expanding the window by moving the right pointer. When the window has all
the desired characters, we contract (if possible) and save the smallest window till now.

The answer is the smallest desirable window.

For eg. S = "ABAACBAB" T = "ABC". Then our answer window is "ACB" and shown below is one of the possible desirable windows.

![](https://leetcode.com/problems/minimum-window-substring/Figures/76/76_Minimum_Window_Substring_1.png)

###Algorithm

1. We start with two pointers, left and right initially pointing to the first element of the string S.
2. We use the right pointer to expand the window until we get a desirable window i.e. a window that contains all of
   the characters of T.
3. Once we have a window with all the characters, we can move the left pointer ahead one by one. If the window is still
   a desirable one we keep on updating the minimum window size.
4. If the window is not desirable any more, we repeat step2 onwards.

![](https://leetcode.com/problems/minimum-window-substring/Figures/76/76_Minimum_Window_Substring_2.png)

The above steps are repeated until we have looked at all the windows. The smallest window is returned.

![](https://leetcode.com/problems/minimum-window-substring/Figures/76/76_Minimum_Window_Substring_3.png)

##Approach 2: Optimized Sliding Window

###Intuition

A small improvement to the above approach can reduce the time complexity of the algorithm to O(2 * |filtered_S| + |S| + |T|),
where filtered_S is the string formed from S by removing all the elements not present in T.

This complexity reduction is evident when |filtered_S| <<< |S|.

This kind of scenario might happen when length of string T is way too small than the length of string S and string S
consists of numerous characters which are not present in T.

###Algorithm

We create a list called filtered_S which has all the characters from string S along with their indices in S, but
these characters should be present in T.

<pre>
S = "ABCDDDDDDEEAFFBC" T = "ABC"
filtered_S = [(0, 'A'), (1, 'B'), (2, 'C'), (11, 'A'), (14, 'B'), (15, 'C')]
Here (0, 'A') means in string S character A is at index 0.
We can now follow our sliding window approach on the smaller string filtered_S
</pre>
