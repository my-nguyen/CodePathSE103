Given a list of accounts where each element accounts[i] is a list of strings, where the first element accounts[i][0] is
a name, and the rest of the elements are emails representing **emails** of the account.

Now, we would like to merge these accounts. Two accounts definitely belong to the same person if there is some common
email to both accounts. Note that even if two accounts have the same name, they may belong to different people as people
could have the same name. A person can have any number of accounts initially, but all of their accounts definitely have
the same name.

After merging the accounts, return the accounts in the following format: the first element of each account is the name,
and the rest of the elements are emails **in sorted order**. The accounts themselves can be returned in **any order**.

####Example 1:
**Input**: accounts = [["John","johnsmith@mail.com","john_newyork@mail.com"],["John","johnsmith@mail.com","john00@mail.com"],["Mary","mary@mail.com"],["John","johnnybravo@mail.com"]]  
**Output**: [["John","john00@mail.com","john_newyork@mail.com","johnsmith@mail.com"],["Mary","mary@mail.com"],["John","johnnybravo@mail.com"]]  
**Explanation**:  
The first and third John's are the same person as they have the common email "johnsmith@mail.com".  
The second John and Mary are different people as none of their email addresses are used by other accounts.  
We could return these lists in any order, for example the answer [['Mary', 'mary@mail.com'], ['John', 'johnnybravo@mail.com'],
['John', 'john00@mail.com', 'john_newyork@mail.com', 'johnsmith@mail.com']] would still be accepted.

####Example 2:  
**Input**: accounts = [["Gabe","Gabe0@m.co","Gabe3@m.co","Gabe1@m.co"],["Kevin","Kevin3@m.co","Kevin5@m.co","Kevin0@m.co"],["Ethan","Ethan5@m.co","Ethan4@m.co","Ethan0@m.co"],["Hanzo","Hanzo3@m.co","Hanzo1@m.co","Hanzo0@m.co"],["Fern","Fern5@m.co","Fern1@m.co","Fern0@m.co"]]  
**Output**: [["Ethan","Ethan0@m.co","Ethan4@m.co","Ethan5@m.co"],["Gabe","Gabe0@m.co","Gabe1@m.co","Gabe3@m.co"],["Hanzo","Hanzo0@m.co","Hanzo1@m.co","Hanzo3@m.co"],["Kevin","Kevin0@m.co","Kevin3@m.co","Kevin5@m.co"],["Fern","Fern0@m.co","Fern1@m.co","Fern5@m.co"]]


####Constraints:
* 1 <= accounts.length <= 1000
* 2 <= accounts[i].length <= 10
* 1 <= accounts[i][j] <= 30
* accounts[i][0] consists of English letters.
* accounts[i][j] (for j > 0) is a valid email.

#Approach #1: Depth-First Search

##Intuition

Draw an edge between two emails if they occur in the same account. The problem comes down to finding connected components
of this graph.

##Algorithm

For each account, draw the edge from the first email to all other emails. Additionally, we'll remember a map from emails
to names on the side. After finding each connected component using a depth-first search, we'll add that to our answer.

##Complexity Analysis

* Time Complexity: O(∑ (a<sub>i</sub> log a<sub>i</sub>)), where is the length of accounts[i]. Without the log factor,
  this is the complexity to build the graph and search for each component. The log factor is for sorting each component
  at the end.

* Space Complexity: O(∑ a<sub>i</sub>), the space used by our graph and our search.

#Approach #2: Union-Find

##Intuition

As in Approach #1, our problem comes down to finding the connected components of a graph. This is a natural fit for
a Disjoint Set Union (DSU) structure.

##Algorithm

As in Approach #1, draw edges between emails if they occur in the same account. For easier interoperability between
our DSU template, we will map each email to some integer index by using emailToID. Then, dsu.find(email) will tell us
a unique id representing what component that email is in.

For more information on DSU, please look at Approach #2 in the article here. For brevity, the solutions showcased below
do not use union-by-rank.

##Complexity Analysis

* Time Complexity: O(A logA), where A = ∑ a<sub>i</sub>, and a<sub>i</sub> is the length of accounts[i]. If we used
  union-by-rank, this complexity improves to O(Aα(A)) ≈ O(A), where α is the Inverse-Ackermann function.

* Space Complexity: O(A), the space used by our DSU structure.
