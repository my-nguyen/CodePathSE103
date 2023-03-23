#Dynamic Programming

##Introduction

Dynamic programming (DP) is an algorithm design technique for solving some of the seemingly most nasty problems you
might face in an interview. The problem will often take the form:

Given a set of rules, find the optimal cost of some objective that is governed by those rules.

Which can show up as finding an optimal path through a maze, finding an optimal score for a game, or finding the number
of possible outputs to a function. If you've searched the internet for tough interview problems, some of these examples will start to sound familiar. These are often labeled by interviewees as the toughest or scariest problems to be asked. Fortunately, with a better understanding of DP, these problems become drastically more simple.

##Fibonacci Numbers

The [Fibonacci numbers](https://en.wikipedia.org/wiki/Fibonacci_number) form a useful numerical sequence that have been
studied for a long time, since around 200 B.C. actually by an Indian mathematician despite the credited name. They also
happen to be the "Hello World" of recursive computation, which is a great place to start to demonstrate the DP design
technique.

We will solve the following problem:
<pre>
Given:
The 0th Fibonacci number is 0, F(0) = 0.
The 1st number is 1, F(1) = 1.
All other numbers after are F(n) = F(n-1) + F(n-2) or summing the previous two numbers.
Design a function that can compute the nth number.
</pre>

The problem statement suggests a recursive solution to this problem could be written as:
<pre>
def f(n):
if n < 2:
return n
return fib(n-1) + fib(n-2)
</pre>

We can look at the function call graph of this solution for f(5):

![](https://i.imgur.com/BRUwYWc.png)

The call graph is a great way to get an idea of the computational complexity of a recursive algorithm and in this case
also find wasted computations which are repeated sub-trees. For example, f(2) is calculated three times.

As we know from our study of trees, a full or balanced tree will have size 2**n where n is the height of the tree or
in this case the Fibonacci number we are solving for.

The call graph is a great visualization because it also gives us a hint that the actual required computations to solve
for f(n) is just n, i.e., the unique nodes of the call graph.

So this is exactly the kind of problem DP can help us with. There are two main patterns to DP design, 1) Top Down or
Recursion + Memoization and 2) Bottom Up sometimes referred to as DP Table design. Since, this problem is given as
a recursive algorithm let's start with Top Down.

##Top Down Dynamic Programming

[Memoization](https://en.wikipedia.org/wiki/Memoization) simply means we will store computations we make so that if
we ever need them again we will just look them up rather than recomputing. That sounds like a great idea for calculating
our Fibonacci numbers and dealing with the repetitive calculations. So let's first take a look at the brute-force
recursive algorithm, with a little work counter added for illustration.

<pre>
class Fib(object):
def __init__(self):
self.work = 0

    def results(self, n):
        return f"The {n}th Fibonacci number is {self.f(n)} and requires {self.work} computations" 
    
    def f(self, n):
        self.work += 1
        if n < 2:
            return n
        return self.f(n-1) + self.f(n-2)

print(Fib().results(6))
</pre>

The result will be: The 6th Fibonacci number is 8 and requires 25 computations

That confirms our hypothesis that our complexity is more than O(N) and perhaps O(2**N). So let's alter our code with
memoization. We will just need to store past calculations and look them up before blindly computing. The common storage
mechanism is a hash table.

<pre>
class FibMemo(Fib):

    def __init__(self):
        self.cache = {}

    def f(self, n):
        if n in self.cache:
            return self.cache[n]
        self.work += 1
        if n < 2:
            return n
        self.cache[n] = self.f(n-1) + self.f(n-2)
        return self.cache[n]

print(FibMemo().results(6))
</pre>

The result will be: The 6th Fibonacci number is 8 and requires 7 computations

At its core, thats really all there is to dynamic programming. Once you find a recursive solution to your problem that
exhibits the computational explosion because of wasteful repetition you can really improve performance by saving
computation and adding storage.

Similar to other recursive algorithms, however, there can be some benefit to transforming the problem into an iterative
algorithm. When you do this you will often benefit by making the algorithm even more efficient by using storage more
effectively, both in terms of the function call stack and auxillary storage for the problem itself. In terms of dynamic
programming, we consider this the [Bottom Up DP Technique](https://guides.codepath.org/compsci/Bottom-Up-DP-Technique).

##Time/Space Complexity

There are three main variants of DP problems that you will see in programming interviews. They are distinguished by
the way we will define our the subproblem that is the core of the recursion or iterative computation. The key to looking
at this is to see what work is "left to do". The three main variants are:

* Single Suffix/Prefix -- Where the subproblem solves for some position i and then recurses or iterates on the rest
  defined by either f[:,i-1] OR f[i+1,:]. This is often O(N).
* Subsequence -- You will solve for both ends of an interval in your sequence and be left to solve for f[i, j]. This is
  often O(N**2).
* Double Suffix/Prefix -- More rare, but sometimes you will solve for i and j separately and be left with work in two
  linear sequences f[:,i-1] and g[:j-1]. This is likely O(N**2).

##Glossary

* **Memoization** use an auxiliary cache structure to return early from a function if the result of the function is
  already stored in the cache. The cache is often implemented as a hash.
* **Recursion** a function that calls itself.
* **Subproblem** a recursion can be split into given conditions (base cases) and the rest that you have to compute or
  subproblem.
* **Suffix** the rest of a sequence from the current position to the end.
* **Prefix** the rest of a sequence from the current position to the beginning.
* **DP Table** A 2D visualization technique for solving dynamic programming bottom up.

##Patterns List

* [Bottom Up DP Technique](https://guides.codepath.org/compsci/Bottom-Up-DP-Technique)
* [DP Table](https://guides.codepath.org/compsci/DP-Table)

##References

If you like videos, the introduction to dynamic programming in this class is very good: https://www.youtube.com/watch?v=OQ5jsbhAv_M
