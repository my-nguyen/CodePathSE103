#Linked Lists

##Introduction

Let's start with an example of a very important Linked List. For any running program, the most fundamental structure
that explains the program's state at any time is the Call Stack. If you have had the experience of staring down the face
of a nasty run-time bug, you might attest to this. For example, here is an image of a call stack while debugging in
the Chrome Browser.

A call stack is simply a trace from where the program is currently at all the way back to the start of that process
or thread. This simple trail home is essential for the computer to know what to do when the current function it is
working on exits. In this case, the computer knows it can just go to the first item on that stack remove it from the list,
making the second item now the first thing on the stack, and then doing the rest of the work that the previous first item
was pointing to. This sequence of one item (address in memory) pointing to another item (address in memory) pointing to
another item, and so on, forms a Linked List.

The most common variants of linked lists are:
* Singly Linked List
* Doubly Linked List
* Circular Linked List

All variants refer to how the items (nodes) of the list point to each other. The Singly Linked List will only have one
pointer that has a reference to the next node in the list, whereas, the Doubly Linked List would have a pointer to
the next item as well as the previous item. In almost ALL interviewing scenarios the linked list will be the Singly
Linked List and the rest of the documentation will refer to linked list as that and only the other list types if
specifically mentioned.

A minimal, but quite common Python definition of a linked list node would be:
<pre>
class ListNode:
    def __init__(self, val):
        self.val = val
        self.next = None
</pre>

Where,
<pre>
head = ListNode(1)
head.next = ListNode(2)
</pre>
, would create the list 1 -> 2 and 1 is at the head (front) of the list.

##Pros and Cons

If we think about the call stack example, we were only really operating on one side (the head) of the list. Our
operations consisted of 1) going to the head of the list in order to find the next function that our process needed
to run and 2) adding a new function to the head of the list and having the node that holds that function point to
the previous head.

Linked Lists really shine when we confine our operations to one end of the data structure and also are dealing with
arbitrary, yet small sizes (number of list nodes). The linked list is a good choice in this case because it is both
simple to work with and efficient.

Lists do not do well when you want to support random access, i.e., work at any arbitrary location in the list. This is
because the only way to get to the back of the list, is to go one node at a time asking for the next item down until
you reach your desired location.

We also don't like to store large amounts of information in linked lists because for every value you store you pay
an extra storage penalty of Node object that decorates the item you want to store. Let's summarize the complexity
tradeoffs as follows:

##Time and Space Complexity

**Best cases**: Accessing / Search : O(1) Inserting at head: O(1) Deleting at head: O(1)

**Worst cases**: Accessing / Searching : O(N) Inserting: O(N) Deleting: O(N)

**Best Case** occurs when the node is at the head of the list and **Worst Case** is when the node is at the end of the list.

##Glossary

* **Node** - a position in a list that contains the value of whatever is stored at the position as well as at least
  one reference to another node.
* **Head** — node at the beginning of the list.
* **Tail** — node at the end of the list.
* **Sentinel** — a dummy node, typically placed at the head or end of the list to help make operations simpler
  (e.g., delete) or to indicate the termination of the list.

##Common Operations

###Insert
<pre>
def insert(self, val):
    n = ListNode(val)
    n.next = self
    return n
</pre>

###Delete
<pre>
def delete(self, val):
    s = ListNode("sentinel")
    s.next = self
    p = s
    c = self
    while c:
        if c.val == val:
            p.next = c.next
            return s.next
        p = c
        c = c.next
    return s.next
</pre>

###Length of List
<pre>
def __len__(self):
    c = self
    l = 0
    while c:
        l += 1
        c = c.next
    return l
</pre>

###Patterns List

* [Dummy Head](https://guides.codepath.org/compsci/Dummy-Head)
* [Multiple Pass](https://guides.codepath.org/compsci/Multiple-Pass)
* [Linked List Two Pointer](https://guides.codepath.org/compsci/Linked-List-Two-Pointer)

###References

* https://en.wikipedia.org/wiki/Linked_list
* https://manybutfinite.com/post/journey-to-the-stack