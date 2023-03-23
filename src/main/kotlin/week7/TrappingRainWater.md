#Trapping Rain Water

Given n non-negative integers representing an elevation map where the width of each bar is 1, compute how much water
it can trap after raining.

###Example 1:
![](https://assets.leetcode.com/uploads/2018/10/22/rainwatertrap.png)

**Input**: height = [0,1,0,2,1,0,1,3,2,1,2,1]  
**Output**: 6  
**Explanation**: The above elevation map (black section) is represented by array [0,1,0,2,1,0,1,3,2,1,2,1]. In this case,
6 units of rain water (blue section) are being trapped.

###Example 2:
**Input**: height = [4,2,0,3,2,5]  
**Output**: 9

###Constraints:

* n == height.length
* 0 <= n <= 3 * 10<sup>4</sup>
* 0 <= height[i] <= 10<sup>5</sup>

##Approach 1: Brute force

###Intuition

Do as directed in question. For each element in the array, we find the maximum level of water it can trap after the rain,
which is equal to the minimum of maximum height of bars on both the sides minus its own height.

###Algorithm

* Initialize ans=0
* Iterate the array from left to right:
  * Initialize left_max=0 and right_max=0
  * Iterate from the current element to the beginning of array updating:
    * left_max=max(left_max,height[j])
  * Iterate from the current element to the end of array updating:
    * right_max=max(right_max,height[j])
  * Add min(left_max,right_max)−height[i] to ans

##Approach 2: Dynamic Programming

###Intuition

In brute force, we iterate over the left and right parts again and again just to find the highest bar size up to that
index. But, this could be stored. Voila, dynamic programming.

The concept is illustrated as shown:

![](https://leetcode.com/problems/trapping-rain-water/Figures/42/trapping_rain_water.png)

###Algorithm

* Find maximum height of bar from the left end upto an index i in the array left_max
* Find maximum height of bar from the right end upto an index i in the array right_max.
* Iterate over the height array and update ans:
  * min(left_max[i],right_max[i])−height[i] to ans

##Approach 3: Using stacks

###Intuition

Instead of storing the largest bar upto an index as in Approach 2, we can use stack to keep track of the bars that are
bounded by longer bars and hence, may store water. Using the stack, we can do the calculations in only one iteration.

We keep a stack and iterate over the array. We add the index of the bar to the stack if bar is smaller than or equal to
the bar at top of stack, which means that the current bar is bounded by the previous bar in the stack. If we found a bar
longer than that at the top, we are sure that the bar at the top of the stack is bounded by the current bar and a
previous bar in the stack, hence, we can pop it and add resulting trapped water to ans.

###Algorithm
* Use stack to store the indices of the bars.
* Iterate the array:
  * While stack is not empty and height[current] > height[st.top()]
    * It means that the stack element can be popped. Pop the top element as top.
    * Find the distance between the current element and the element at top of stack, which is to be filled. distance = current - st.top() - 1
    * Find the bounded height bounded_height = min(height[current], height[st.top()]) - height[top]
    * Add resulting trapped water to answer ans +=distance×bounded_height
  * Push current index to top of the stack
  * Move current to the next position

##Approach 4: Using 2 pointers

###Intuition

As in Approach 2, instead of computing the left and right parts seperately, we may think of some way to do it in one
iteration. From the figure in dynamic programming approach, notice that as long as right_max[i] > left_max[i] (from
element 0 to 6), the water trapped depends upon the left_max, and similar is the case when left_max[i] > right_max[i]
(from element 8 to 11). So, we can say that if there is a larger bar at one end (say right), we are assured that the
water trapped would be dependant on height of bar in current direction (from left to right). As soon as we find the bar
at other end (right) is smaller, we start iterating in opposite direction (from right to left). We must maintain
left_max and right_max during the iteration, but now we can do it in one iteration using 2 pointers, switching between
the two.

###Algorithm

* Initialize left pointer to 0 and right pointer to size-1
* While left < right, do:
  * If height[left] is smaller than height[right]
    * If height[left] >= left_max, update left_max
    * Else add left_max - height[left] to ans
    * Add 1 to left.
  * Else
    * If height[right] ≥ right_max, update right_max
    * Else add right_max - height[right] to ans
    * Subtract 1 from right.
