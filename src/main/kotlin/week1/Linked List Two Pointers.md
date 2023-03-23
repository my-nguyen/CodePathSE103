#Linked List Two Pointer

A very useful technique for dealing with linked lists involves iterating through the list with 2 or more pointers.
The differences between how the pointers iterate can be used to make calculations on the list more efficient. This is
best demonstrated with an example and probably the most famous example of this technique is cycle detection.

##Detect Cycle in Linked List

Let's use the problem definition of Linked List Cycle.

Given a linked list, determine if it has a cycle in it. Can you do it with constant extra space?

###Answer: Using extra storage time = O(N), space = O(N)

A cycle can be defined as a list where we point to the same node twice. So the first thing that most people think of
is to use another data structure to store nodes that we have already seen as we traverse the list. Then as we move
through the nodes of the list we check to see if we have already stored the current node in our auxillary data structure
and if we have then we have found a cycle. The typical data structure to choose here is a hash map because it offers
constant time insertion and lookup. Here is an acceptable answer:

<pre>
class Node(object):
    def __init__(self, v):
        self.val = v
        self.next = None

    def insert(self, n):
        n.next = self
        return n

def has_cycle_with_aux(ll):
    aux = set()
    while ll:
        if ll in aux:
        return True
    aux.add(ll)
    ll = ll.next
return False

my_list = Node(7).insert(Node(5))
print(f"List {my_list.val} --> {my_list.next.val} --> {my_list.next.next}")
print(has_cycle_with_aux(my_list))
my_list.next.next = my_list
print(f"List {my_list.val} --> {my_list.next.val} --> {my_list.next.next.val}")
print(has_cycle_with_aux(my_list))
</pre>

####Output:
<pre>
"List 5 --> 7 --> None"
False
"List 5 --> 7 --> 5"
True
</pre>

###Answer: Two Pointers, time = O(N) space = O(1)

We can get rid of the extra auxillary data structure by utilizing only one additional pointer. We can then use the two
pointers to iterate through the list at two different speeds. The motivation being that if there is a cycle, then
the list can be thought of as a circle (at least the part of the list past the self-intersection). Similar to a race
track, the faster pointer must eventually cross paths with the slower pointer, whereas if there is not a cycle they will
never cross paths.

<pre>
def has_cycle(ll):
    if not ll or not ll.next:
        return False
    fp = ll.next
    sp = ll
    while fp and fp.next:
        if fp == sp or fp.next == sp:
            return True
        fp = fp.next.next
        sp = sp.next
    return False

my_list = Node(7).insert(Node(5))
print(f"List {my_list.val} --> {my_list.next.val} --> {my_list.next.next}")
print(has_cycle(my_list))
my_list.next.next = my_list
print(f"List {my_list.val} --> {my_list.next.val} --> {my_list.next.next.val}")
print(has_cycle(my_list))
</pre>

####Output:

<pre>
"List 5 --> 7 --> None"
False
"List 5 --> 7 --> 5"
True
</pre>
