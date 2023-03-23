All databases implement indexes. Indexes are additional abstraction implemented in databases to support high read
throughput. So why not index everything in database ? Because indexes are additional abstractions and make writes slower.
Hence it is the role of application developer, to define which columns/keys require indexing based on the read/write
workloads of the application.

The most prevalent way of indexing is using B-trees. B-trees divvy up the database in pages or blocks of few Kbs. Each
page can be located by its unique address on disk. This way pages can reference each other.

For example in the diagram above, if you are looking for say key 10, start by looking at the root node. 10 lies between
7 and 16, so follow the pointer between 7 and 16 and you will land at the middle node in second row. 10 lies between
9 and 12 so follow the pointer the next page referenced by the on-disk pointer between 9 and 12. Keep on searching till
you reach at the leaf node containing the key and its corresponding value. The value will typically be the byte offset
of the location where the record is actually stored.

Another indexing technique which is used in newer databases like Elastic Search, Hbase, Cassandra, Riak is based on
the BigTable paper by Google. Here is the short summary of how it works under the hood:

1. A new write is added to an in-memory sorted balanced tree (like a red black tree or an AVL tree). This in-memory tree
   is known as memtable.
2. When the memtable becomes big, it is flushed to the disk as an SSTable file. Think of SSTable as a sorted key-value
   storage on disk of the in-memory tree i.e. memtable which is already sorted. While the SSTable is being written on
   disk, new write can continue in memtable.
3. Reads are first directed towards memtable. If the keys are not found in memtable, it’s searched in the most recent
   SSTable and then the next most recent SSTable and so on and so forth. SSTables are sorted so it is easy to do range
   queries on them.
4. In the background, duplicate key are removed from SSTable and the most recent key value is retained. This process is
   known as compaction.
5. Compacted SSTable are merged into new SSTable. Since SSTables are sorted, merging them using a merge sort like
   algorithm is quite fast.

This indexing scheme is known LSM Tree or Log Structured Merge Tree.

As a thumb rule, LSM trees can handle higher write workloads and B-trees are good for high read workload. This is
because writes in SSTables are always sequential unlike B trees, where random writes take place (B-tree pages need not
be sequentially arranged on disk)

In order to bake durability, writes in B-trees are also preceded with writes to an additional file known as Write Ahead
Logs (WAL). WALs are append only files, that help to restore B-tree to a consistent state in case of a crash. This
implies that writing to a B-tree means, first writing to WAL, which in turn means additional work and slower writes.

Typically the merge and compaction process in an LSM tree is very fast but sometimes it can lag behind the writes. This
can happen when database is experiencing very high written e workload. Slow compaction and merging adversely affects
reads as many more SSTables need to be read now. This is a big disadvantage of LSM trees that is in very high write
throughputs scenarios, its performance can get unstable, unlike that of B-trees which exudes generally stable
performance.

Finally if transactional semantics are of paramount importance, then B-trees are more preferable. In LSM trees, same key
can be present in multiple SSTable. In B-tree, one key is present at only place and it’s value is updated in-place. As a
result, transactional isolation is easily achieved in Btrees (I intend to cover transactional isolation in a later post).