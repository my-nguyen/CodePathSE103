#Delete and Earn

You are given an integer array nums. You want to maximize the number of points you get by performing the following
operation any number of times:

* Pick any nums[i] and delete it to earn nums[i] points. Afterwards, you must delete **every** element equal to
  nums[i] - 1 and **every** element equal to nums[i] + 1.

Return the **maximum number of points** you can earn by applying the above operation some number of times.

###Example 1:
**Input**: nums = [3,4,2]  
**Output**: 6  
**Explanation**: You can perform the following operations:  
- Delete 4 to earn 4 points. Consequently, 3 is also deleted. nums = [2].  
- Delete 2 to earn 2 points. nums = [].  
  You earn a total of 6 points.
  
###Example 2:
**Input**: nums = [2,2,3,3,3,4]  
**Output**: 9  
**Explanation**: You can perform the following operations:
- Delete a 3 to earn 3 points. All 2's and 4's are also deleted. nums = [3,3].  
- Delete a 3 again to earn 3 points. nums = [3].  
- Delete a 3 once more to earn 3 points. nums = [].  
  You earn a total of 9 points.

###Constraints:

* 1 <= nums.length <= 2 * 104
* 1 <= nums[i] <= 104

##Approach #1: Dynamic Programming [Accepted]

###Intuition

Because all numbers are positive, if we "take" a number (use it to score points), we might as well take all copies of it,
since we've already erased all its neighbors. We could keep a count of each number so we know how many points taking
a number is worth total.

Now let's investigate what happens when we add a new number X (plus copies) that is larger than all previous numbers.
Naively, our answer would be the previous answer, plus the value of X - which can be solved with dynamic programming.
However, this fails if our previous answer had a number taken that was adjacent to X.

Luckily, we can remedy this. Let's say we knew using, the value of our previous answer, and avoid, the value of
our previous answer that doesn't use the previously largest value prev. Then we could compute new values of using and
avoid appropriately.

###Algorithm

For each unique value k of nums in increasing order, let's maintain the correct values of avoid and using, which
represent the answer if we don't take or take k respectively.

If the new value k is adjacent to the previously largest value prev, then the answer if we must take k is (the point
value of k) + avoid, while the answer if we must not take k is max(avoid, using). Similarly, if k is not adjacent to
prev, the answer if we must take k is (the point value of k) + max(avoid, using), and the answer if we must not take k
is max(avoid, using).

At the end, the best answer may or may not use the largest value in nums, so we return max(avoid, using).

Our demonstrated solutions showcase two different kinds of sorts: a library one, and a radix sort. For each language,
the other kind of solution can be done without much difficulty, by using an array (Python) or HashMap (Java) respectively.

