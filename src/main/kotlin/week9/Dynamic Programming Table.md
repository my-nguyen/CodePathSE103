#Dynamic Programming Table

This is one of the most helpful visualization techniques for designing bottom-up DP algorithms when the problem is
a multi-prefix/multi-suffix or subsequence problem type. It is basically a way of drawing the DAG of computation when
the computational structure of your problem is best explained in two dimensions. Let's motivate this with a popular
example problem.

##Longest Palindromic Substring

Let's take a look at the problem of Finding the [Longest Palindromic Substring](https://leetcode.com/problems/longest-palindromic-substring/).

Given a string s, find the longest palindromic substring in s. You may assume that the maximum length of s is 1000.

####Example:
Input: "babad"  
Output: "bab"

Note: "aba" is also a valid answer.  
See [leetcode](https://leetcode.com/problems/longest-palindromic-substring/) version.

##Brute Force Solution

One direct way to solve this is to look at all substrings of the string, and see if it is a palindrome. If it is, save
it if its length is greater than the previously saved substring. In this case, our algorithm would analyze the following
substrings:
<pre>
babad
baba
 abad
bab
 aba
  bad
ba
 ab
  ba
   ad
b
 a
  b
   a
    d
</pre>

If you count this up and think about the combinatorics happening here, you see that this algorithm has time complexity
O(N^3). I consider this a brute-force technique either because it was the first thing that popped into my head and I'm
guessing there is a better way or because I already know there is a better way and I'm just working up to it. The
brute-force solution, although often trivial compared to what the interviewer is asking for, is a great way to start
thinking about possible recursive sub-problems that can avoid the inefficiency of the brute-force algorithm.

##Recursive Formulation

It can be hard to directly jump to the insight that leads to structuring the solution in a useful recursive way, but at
least by laying out all of the computations of the brute-force strategy you can see that overlapping calculations and
then trying to use that to see if you can solve some of the lines of the brute force solution if you knew earlier or
later lines.

In this case, I can see that looking at line 1 is interesting because a sub-string of it was a palindrome namely aba
from line 5 and yet we were able to reject it because when you compare the first and last characters of line 1, b and d
they are not equivalent. So this suggests a function for determining if a string is a palindrome is to test the outer
characters as well as the remaining string. Anytime you have a statement where you can neatly segment current data items
and rest you have a promising path. So I would write our hypothesis recursion as:

<pre>
def is_p(s, i, j):
    return s[i] == s[j] and is_p(i+1, j-1)
</pre>

After this, I typically try to find what the base case would be or information we can assume, and it feels safe to say
that any string of single character is a palindrome.

<pre>
def is_p(s, i, j):
    if i == j:
        return True
    return s[i] == s[j] and is_p(i+1, j-1)
</pre>

This seems fairly promising, but it doesn't directly tell us how to structure the recursion dependencies for that we
again want to think of the computation DAG and topologically sort it, as we did with Bottom Up DP Technique.

Anytime when the rest function is suffixes or prefixes of some linear structure it can be done fairly easily in a line,
but when we are dealing with sub-intervals (both endpoints moving), sub-trees, or paired suffixes/prefixes a 2D
visualization is very helpful.

##DP Table Visualization

When you are dealing with a recursive solution where the rest involves the possibility of changing either or both
endpoints, a standard approach is to draw a table with the elements on columns and rows. In this case, it is the
characters of the string.
![](https://i.imgur.com/GP1zpg3.png)

Based on our recursive problem definition, it seems most useful to interpret the coordinates on the table as the
coordinates of either endpoint of the sub-string we are testing. So is_p(0,1) or the first row and second column would
be the substring 'ba'. So let's start filling in the table with our easiest information, i.e., the base cases
is_p(i,i) == True. We can also consider the base case is_p(i,j) == False when j < i, i.e., we don't accept strings with
negative length.
![](https://i.imgur.com/fFCtoE5.png)

From here can often be the most challenging step in the algorithm design process, this is figuring out what to solve
next. The table can be helpful to visually think about just adding more information next to the boundary of what you
don't know, a breadth first search is a great thing to think about, we just want to explore the boundary of the unknown.
In this case, exploring the next diagonal towards the upper right, or where j == i + 1 seems like a good idea. We can
confidently state that any two character string is a palindrome if s[i] == s[j]. So we can proceed as follows, look at
table location i == 0, j == 1 or 'ba', they are different so we fill in False, and continue iteration i and j until
we reach i == len(s) - 2.
![](https://i.imgur.com/x26Yvf8.png)
![](https://i.imgur.com/kPDpSVj.png)

Now we continue to the next diagonal to the right where j - i == 2 or 3 character strings. Let's look at the first one
s[0:2] == "bab". Here we can now apply our general recursive formula, is_p(s,0,2) => s[0] == s[2] and is_p(s,1,1) or
"b" == "b" and True because is_p(s,1,1) is simply the value stored in the second row, second col.
![](https://i.imgur.com/dfw8KIr.png)

So according to the DP Table our algorithm has a simple visual definition whereby for any substring s[i:j] where
j > i + 1 the computation is simply dependent on the characters at the row and column and the answer previously computed
in the [i + 1, j - 1] location that is already calculated. This visual description also makes the complexity analysis
straightforward. Essentially, we have a computation for each location in the upper-right triangle of the matrix and
each cell requires only constant work. So we have designed an O(N**2) algorithm where N is the length of the string.

<pre>
def find_longest(s):
    if not s or len(s) == 1:
        return s
    dp = [[None for c in s] for c in s]
    longest = None
    for i in range(len(s)):
        dp[i][i] = True
    longest = [0, 1]

    for k in range(1,len(s)):
        for i in range(len(s)-k):
            j = i + k
            if k == 1:
                is_p = s[i] == s[j]
            else:
                is_p = s[i] == s[j] and dp[i+1][j-1]
            if is_p:
                longest = [i, j + 1]
            dp[i][j] = is_p
    return s[longest[0]:longest[1]]

find_longest("babad")
</pre>

'aba'
