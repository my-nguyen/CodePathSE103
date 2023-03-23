#Backtracking

##Introduction

Backtracking is similar to [Dynamic Programming](https://guides.codepath.org/compsci/Dynamic-Programming) in that it
solves a problem by efficiently performing an exhaustive search over the entire set of possible options. Backtracking is
different in that it structures the search to be able to efficiently eliminate large sub-sets of solutions that are
no longer possible. For this reason, all backtracking algorithms will have a very similar overall structure for
exhaustively searching the space of possible solutions, but the art & difficulty of the particular backtracking solution
will be strategies that can be used to get rid of impossible solutions quickly.

##Combination Sum

Let's motivate backtracking by solving the following problem:

Find all possible combinations of k numbers that add up to a number n, given that only numbers from 1 to 9 can be used
and each combination should be a unique set of numbers. All numbers will be positive integers. The solution set must not
contain duplicate combinations.

You can find this problem on LeetCode as [Combination Sum III](https://leetcode.com/problems/combination-sum-iii/).

###Example 1
Input: k = 3, n = 7
Output: [[1,2,4]]

###Example 2
Input: k = 3, n = 9
Output: [[1,2,6], [1,3,5], [2,3,4]]

A backtracking algorithm has four components:

1. Given a current sub-solution state, what are all the candidates I can choose to move to a new possible sub-solution
   state?
   * For example, in Example 2 with k = 3, n = 9, if we have the partial solution [1], then to add another element to
     the sub-solution, we could choose any of [2, 3, 5, 6] as candidates
2. Decide if all possible solutions derived from the current sub-solution state will be invalid
   * For example, for k = 3, n = 9, [1, 8] and [1, 2, 4] are bad sub-solutions because they can't derive valid solutions.
3. Efficiently backtrack from any current sub-solution state to a previous sub-solution state
   * For example, if we went from sub-solution [1, 2] to [1, 2, 6], then we can backtrack back to [1, 2].
4. Is a particular sub-solution actually a valid solution? If so, we can add it to the list of solutions.
   * For example, for k = 3, n = 9, [1, 2, 6] is a valid solution.

To satisfy the requirement that the solution set cannot contain duplicate combinations (like [1, 3, 5] and [5, 3, 1]),
we'll generate sub-solutions such that each sub-solution is in ascending order.

<pre>
from typing import List  # type annotations require Python 3.6+

def combination_sum(k: int, n: int) -> List[List[int]]:
    def search(solutions: List[List[int]], sol: List[int], k: int, n: int) -> None:
        """
        Use backtracking to derive solutions based on the sub-solution/solution `sol`,
        and then add them to the `solutions` list.
        """
        sum_sol = sum(sol)

        # Check whether `sol` is a valid solution. If so, add it to our solutions set.
        if len(sol) == k and sum_sol == n:
            # Note: we have to make a *copy* of `s` to append to `solutions`, because
            # soon after, we will be removing elements from `s`
            solutions.append(list(sol))
            return
        
        # Optional optimization: Check whether the sub-solution `sol` is bad (can't
        # lead to a valid solution). If so, terminate.
        if len(sol) > k or sum_sol > n or (len(sol) == k and sum_sol != n):
            return

        # Enumerate possible candidates. Note: It's okay if some candidates may be
        # invalid, since we later weed out invalid sub-solutions. To further limit
        # the search space, you can instead write `range(start, min(10, n - sum_sol + 1))`.
        start = sol[-1] + 1 if sol else 1
        for i in range(start, 10):
            sol.append(i)  # move to another sub-solution state...
            search(solutions, sol, k, n)
            del sol[-1]  # ...and move back to previous sub-solution state
  
    solutions = []
    search(solutions, [], k, n)
    return solutions
</pre>

(This solution is partially based on
https://leetcode.com/problems/combination-sum-iii/discuss/60614/Simple-and-clean-Java-code-backtracking.)

##Comparison to Dynamic Programming

Dynamic Programming and Backtracking have multiple similarities and differences and are often confused when first
learning about them. Often, the confusion comes simply from the name collision where there is a part of DP that involves
'backtracking' to recreate an optimal solution sequence at the end of the DP. For example, when finding the minimum path
through a grid in DP we may want to minimize the number of jumps taken, but to reconstruct that final path we often need
to go backwards from our final solution to retrace all of the steps that got us to the optimal point in order to
reconstruct the minimal path. This is not the Backtracking algorithm.

In backtracking, we are exhaustively searching through a solution space by applying local transformations and collecting
solutions we find as we go. It is named 'backtracking' because we will need to back out of each local transformation
we make to our current solution in order to continue searching the rest of the space. Imagine searching a labrynth and
you have no information so your only choice is to search all paths out. Of course you leave a trail of crumbs behind you
so that once you hit a dead end you can backtrack along the crumbs to return to the first position where you could have
made a different decision, i.e., all forks in the road. You can continue to do this until you have explored all possible
paths through the labrynth.

It is similar to DP in that we are exhaustively searching a solution space guaranteeing that we will touch all solutions
that matter. And it is also similar to DP in that the difference between a highly performant algorithm and not is often
how you structure your search. In the case of backtracking, the best solutions often model the problem in some way
that allows them to quickly prune state prefixes that cannot lead to solutions.

Unlike DP, backtracking is typically not looking for one optimal solution, but is instead looking for all that satisfy
some criteria. So the structure of the algorithm is much less about being efficient at throwing previous information
that you don't need because it doesn't lead you to the optimal solution and more about learning what parts of the space
you don't need to explore as you are collecting all solutions that qualify.

###Glossary

* **Candidates** are all the local choices you can make given the current state of the backtracking algorithm.

###References

One of the best discussions of Backtracking can be found in the
[Algorithm Design Manual](https://github.com/addyrookie/Depot-App/raw/master/gmail/The%20Algorithm%20Design%20Manual%202ed%20%20by%20Steven%20S.%20Skiena.pdf)
by Skiena. The backtracking algorithm is discussed in pages 230 - 244. Highly recommended.
