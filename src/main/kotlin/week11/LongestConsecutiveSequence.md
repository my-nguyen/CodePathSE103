#Longest Consecutive Sequence

Given an unsorted array of integers nums, return the length of the longest consecutive elements sequence.

You must write an algorithm that runs in O(n) time.

###Example 1:
**Input**: nums = [100,4,200,1,3,2]  
**Output**: 4  
**Explanation**: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.

###Example 2:
**Input**: nums = [0,3,7,2,5,8,4,6,0,1]  
**Output**: 9

###Constraints:
* 0 <= nums.length <= 105
* -109 <= nums[i] <= 109

##Approach 1: Brute Force

###Intuition

Because a sequence could start at any number in nums, we can exhaust the entire search space by building as long
a sequence as possible from every number.

###Algorithm

The brute force algorithm does not do anything clever - it just considers each number in nums, attempting to count
as high as possible from that number using only numbers in nums. After it counts too high (i.e. currentNum refers to
a number that nums does not contain), it records the length of the sequence if it is larger than the current best.
The algorithm is necessarily optimal because it explores every possibility.

##Approach 2: Sorting

###Intuition

If we can iterate over the numbers in ascending order, then it will be easy to find sequences of consecutive numbers.
To do so, we can sort the array.

###Algorithm

Before we do anything, we check for the base case input of the empty array. The longest sequence in an empty array is,
of course, 0, so we can simply return that. For all other cases, we sort nums and consider each number after the first
(because we need to compare each number to its previous number). If the current number and the previous are equal,
then our current sequence is neither extended nor broken, so we simply move on to the next number. If they are unequal,
then we must check whether the current number extends the sequence (i.e. nums[i] == nums[i-1] + 1). If it does,
then we add to our current count and continue. Otherwise, the sequence is broken, so we record our current sequence and
reset it to 1 (to include the number that broke the sequence). It is possible that the last element of nums is part of
the longest sequence, so we return the maximum of the current sequence and the longest one.

![](https://leetcode.com/problems/longest-consecutive-sequence/Figures/128/sorting.png)

Here, an example array is sorted before the linear scan identifies all consecutive sequences. The longest sequence is colored in red.

##Approach 3: HashSet and Intelligent Sequence Building

###Intuition

It turns out that our initial brute force solution was on the right track, but missing a few optimizations necessary
to reach O(n) time complexity.

###Algorithm

This optimized algorithm contains only two changes from the brute force approach: the numbers are stored in a HashSet
(or Set, in Python) to allow O(1) lookups, and we only attempt to build sequences from numbers that are not already part
of a longer sequence. This is accomplished by first ensuring that the number that would immediately precede the current
number in a sequence is not present, as that number would necessarily be part of a longer sequence.

