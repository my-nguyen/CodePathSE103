#Bottom Up Dynamic Programming

In the [Dynamic Programming](https://guides.codepath.org/compsci/Dynamic-Programming) Overview, we solved the
"Hello World" of recursion problems, a function that found the Nth
[Fibonacci number](https://en.wikipedia.org/wiki/Fibonacci_number) in O(N) time complexity. The solution used recursion
and memoization or Top Down DP, which also required O(N) extra space to solve the problem. Now we will use the Bottom Up 
DP technique to solve the same problem in order to achieve greater space efficiency.

##Finding the Bottom

Let's now take a look at the call graph for the recursive algorithm with repeat calls to the same function pointing
to the same node.
![](https://i.imgur.com/yThh6bR.png)

Here we see that f(5) depends on the same result from f(3) as f(4) does. By flattening this graph out as well, we can
see that it is a directed acyclic graph (DAG). It is important that it is acyclic in the top down technique because if
there was a cycle in the call graph the recursive algorithm would have required a cached answer for one that did not
exist already and you would have blown your stack as you continuously evaluated the cycle.

In the bottom up technique, forming the DAG is essential because you need to understand where the other end of the call
graph is or the bottom or the opposite end of the answer from a topologically sorted graph. In this case, that is f(0).
So a bottom up DP solution will start from f(0) and move to the right solving each Fibonacci number until we get to the
answer.

<pre>
class FibBottomUp(Fib):
    def __init__(self):
        self.cache = {}
    def f(self, n):
        c = self.cache
        c[0] = 0
        c[1] = 1
        self.work = 2
        for i in range(2,n+1):
            self.work += 1
            c[i] = c[i-1] + c[i-2]
        return c[n]
print(FibBottomUp().results(6))
</pre>

The 6th Fibonacci number is 8 and requires 7 computations In this solution we replaced the recursive call with an
iterative call and rather than working from the desired number back to the dependencies, we start with the known
dependencies and work up to the solution.

Because we are working up from the dependencies we can often spot when dependencies will no longer be necessary and use
that information to reduce the space requirement of the algorithm.

<pre>
class FibNoCache(Fib):
    def f(self, n):
        if n < 2:
            return n
        self.work = 2
        pp = 0
        p = 1
        for i in range(2, n+1):
            self.work += 1
            t = p + pp
            pp = p
            p = t
        return p
print(FibNoCache().results(6))
</pre>

The 6th Fibonacci number is 8 and requires 7 computations

So now we've achieved the same computation time saving, i.e., going from O(2**N) brute-force to O(N). Further, as is
common with the bottom-up formulation, in this case we can find more storage saving by understanding how we are
traversing the DAG. Since we are traversing from left to right and the dependency of any node is at most two nodes back
we realize that we only need to store 2 values, O(1), rather than retaining all O(N) previous numbers.