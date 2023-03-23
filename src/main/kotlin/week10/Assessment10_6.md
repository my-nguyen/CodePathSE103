#Beautiful Arrangement

Suppose you have n integers labeled 1 through n. A permutation of those n integers perm (**1-indexed**) is considered
a **beautiful arrangement** if for every i (1 <= i <= n), **either** of the following is true:

* perm[i] is divisible by i.
* i is divisible by perm[i].

Given an integer n, return the **number** of the **beautiful arrangements** that you can construct.

###Example 1:
**Input**: n = 2  
**Output**: 2  
**Explanation**:
<pre>
The first beautiful arrangement is [1,2]:
- perm[1] = 1 is divisible by i = 1
- perm[2] = 2 is divisible by i = 2
The second beautiful arrangement is [2,1]:
- perm[1] = 2 is divisible by i = 1
- i = 2 is divisible by perm[2] = 1
</pre>

###Example 2:
**Input**: n = 1  
**Output**: 1

###Constraints:
* 1 <= n <= 15

#Solution

##Approach #1 Brute Force [Time Limit Exceeded]

###Algorithm

In the brute force method, we can find out all the arrays that can be formed using the numbers from 1 to N (by creating
every possible permutation of the given elements). Then, we iterate over all the elements of every permutation generated
and check for the required conditions of divisibility.

In order to generate all the possible pairings, we make use of a function permute(nums, current_index). This function
creates all the possible permutations of the elements of the given array.

To do so, permute takes the index of the current elementindex as one of the arguments. Then, it swaps the current element
with every other element in the array, lying towards its right, so as to generate a new ordering of the array elements.
After the swapping has been done, it makes another call to permute but this time with the index of the next element
in the array. While returning back, we reverse the swapping done in the current function call.

Thus, when we reach the end of the array, a new ordering of the array's elements is generated.

##Approach #2 Better Brute Force [Accepted]

###Algorithm

In the brute force approach, we create the full array for every permutation and then check the array for the given
divisibilty conditions. But this method can be optimized to a great extent. To do so, we can keep checking the elements
while being added to the permutation array at every step for the divisibility condition and can stop creating it
any further as soon as we find out the element just added to the permutation violates the divisiblity condition.

##Approach #3 Backtracking [Accepted]

###Algorithm

The idea behind this approach is simple. We try to create all the permutations of numbers from 1 to N. We can fix
one number at a particular position and check for the divisibility criteria of that number at the particular position.
But, we need to keep a track of the numbers which have already been considered earlier so that they aren't reconsidered
while generating the permutations. If the current number doesn't satisfy the divisibility criteria, we can leave all
the permutations that can be generated with that number at the particular position. This helps to prune the search space
of the permutations to a great extent. We do so by trying to place each of the numbers at each position.

We make use of a visited array of size N. Here, visited[i] refers to the number being already placed/not placed in
the array being formed till now(True indicates that the number has already been placed).

We make use of a calculate function, which puts all the numbers pending numbers from 1 to N (i.e. not placed till now
in the array), indicated by a False at the corresponding visited[i] position, and tries to create all the permutations
with those numbers starting from the pos index onwards in the current array. While putting the number, we check whether
the ith number satisfies the divisibility criteria on the go i.e. we continue forward with creating the permutations
with the number i at the posth position only if the number i and pos satisfy the given criteria. Otherwise, we continue
with putting the next numbers at the same position and keep on generating the permutations.

