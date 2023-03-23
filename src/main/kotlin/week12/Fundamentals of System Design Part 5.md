Partitioning or sharding is the technique of breaking down a large dataset and distributing it across many disks.
Reads/writes, after partitioning can be parallelised across many nodes. Partitioning is combined with replication to
achieve scalability.

One easy way to achieve partitioning is to divvy up the entire dataset based on some fixed criteria. For example for
an e-commerce website, you can put all transactions for mobile phones on node 1, shoes on node 2 and so on. The problem
with this approach is that this can result in skewed distribution. If mobile phones are the most popular items, the node
handling mobile phone transactions will always be overloaded. One easy workaround is to assign partitions randomly.
This ensures all your data gets spread evenly across nodes, but you must have already guessed the problem with
this approach. When you issue a read query, it needs to be be sent to all the nodes, making reads costlier.

Another way to handle this problem is to design key based range partitions. You can first create a hash of key and then
assign a range of resultant hash values to certain partitions. If you use a 32 bit hash function, your keys can be
mapped to one of 2^32 – 1 hash values. You can then choose to assign keys 1–10 to say partition 1 and so on.
The partition boundaries can be chosen randomly or manually.

While hash based partition ranges help in reducing asymmetric loads, there are no easy ways, at least at database level,
to completely solve this problem. If for the said e-commerce website, iPhone X is the highest grossing product for
one month, node partitions hosting iPhone X transactions will be stressed. The application developer in this case can
add a 2 digit random key to original key hash and consequently distribute load for one key across 100 partitions.
The challenge will again be reading as 100 parallel reads now need to be fired to find one transaction for iPhone X.
Hence it makes sense to add random digits to only a few number of “hot” keys.

That’s all I had to write in this post. I will post the next article in this series after a week or two. Till then
I would love to hear your comments and feedback.