#Rotate Image

You are given an n x n 2D matrix representing an image, rotate the image by **90** degrees (clockwise).

You have to rotate the image in-place, which means you have to modify the input 2D matrix directly. **DO NOT** allocate
another 2D matrix and do the rotation.

###Example 1:

![](https://assets.leetcode.com/uploads/2020/08/28/mat1.jpg)

**Input**: matrix = [[1,2,3],[4,5,6],[7,8,9]]  
**Output:** [[7,4,1],[8,5,2],[9,6,3]]

###Example 2:

![](https://assets.leetcode.com/uploads/2020/08/28/mat2.jpg)

**Input**: matrix = [[5,1,9,11],[2,4,8,10],[13,3,6,7],[15,14,12,16]]  
**Output**: [[15,13,2,5],[14,3,4,1],[12,6,8,9],[16,7,10,11]]

###Example 3:

**Input**: matrix = [[1]]  
**Output**: [[1]]

###Example 4:

**Input**: matrix = [[1,2],[3,4]]  
**Output**: [[3,1],[4,2]]

###Constraints:

* matrix.length == n
* matrix[i].length == n
* 1 <= n <= 20
* -1000 <= matrix[i][j] <= 1000

#Solution

##Approach 1: Rotate Groups of Four Cells

###Intuition

Observe how the cells move in groups when we rotate the image.

![](https://leetcode.com/problems/rotate-image/Figures/48/48_angles.png)

We can iterate over each group of four cells and rotate them.

##Approach 2: Reverse on Diagonal and then Reverse Left to Right

###Intuition

The most elegant solution for rotating the matrix is to firstly reverse the matrix around the main diagonal, and then
reverse it from left to right. These operations are called transpose and reflect in linear algebra.

**Bonus Question**: What would happen if you reflect and then transpose? Would you still get the correct answer?

Even though this approach does twice as many reads and writes as approach 1, most people would consider it a better
approach because the code is simpler, and it is built with standard matrix operations that can be found in any matrix
library.

###Complexity Analysis

Let M be the number of cells in the grid.

* Time complexity: O(M). We perform two steps; transposing the matrix, and then reversing each row. Transposing
  the matrix has a cost of O(M) because we're moving the value of each cell once. Reversing each row also has a cost of
  O(M), because again we're moving the value of each cell once.
* Space complexity: O(1) because we do not use any other additional data structures.

**Bonus Question**: If you're not too confident with matrices and linear algebra, get some more practice by also coding
a method that transposes the matrix on the other diagonal, and another that reflects from top to bottom. You can test
your functions by printing out the matrix before and after each operation. Finally, use your functions to find three more
solutions to this problem. Each solution uses two matrix operations.

**Interview Tip**: Terrified of being asked this question in an interview? Many people are: it can be intimidating due to
the fiddly logic. Unfortunately, if you do a lot of interviewing, the probability of seeing it at least once is high,
and some people have claimed to have seen it multiple times! This is one of the few questions where I recommend
practicing until you can confidently code it and explain it without thinking too much.