Modern applications can handle thousands of transactions per minute. One of the things that you will definitely do to
achieve scalability, is to do replication of your database. There are several reasons for doing so:
1. You want to keep data geographically close to users
2. Balance read/write across multiple machines to prevent throttling of a single database server
3. Build redundancies within your system

There are a lot of interesting subtleties involved in replications that are concerned with durability guarantees and
eventual consistencies. As a product person or application developer, it’s very important to cut through the vagueness
of jargons and understand how replication actually works.

A copy of your data on another machine or node is known as a replica. There are three ways to achieve replication of data.
1. Single master slave replication : All writes go to one node, called master. The changes in master node are replicated
   to other nodes, called slaves. Read requests can go to either master or slave.
2. Many master replication: Instead of writes going to one master, they can go to many masters. Masters can then
   replicate changes made to their data stores to other slaves nodes. Read requests can go to either master or slave.
3. No master replication: There are no master and slaves in this setup. Writes and reads can be sent to any node.

I will cover single master slave replication in some detail before talking about the other configurations.

An important detail worth mentioning is the manner in which replication happens. It can either be synchronous or
asynchronous.

Synchronous replication means that master sends its writes to all slaves and waits for write confirmation from ‘all’
slaves. Once the master receives write confirmations, it then returns a successful write message to client. After all
this, the writes are made visible to other clients. This mode provides the highest durability guarantee. Every write
will be propagated to all nodes and no client connecting to any of the slaves will ever see stale data. If these
guarantees make sense for your application, you should choose synchronous replication.

The biggest drawback with synchronous replication is that it is very slow. If the network connectivity to some nodes is
choppy, it can take forever to make one successful write. In practice, people always invariably choose ‘asynchronous’
replication. In asynchronous replication, a master confirms write to the client, after a successful write on master node.
It also sends changes to other slave replicas but does not wait for a successful write confirmation from other slaves.
This effectively means that durability guarantees are weakened as some of the slave nodes update their data stores later
and in the meantime a client connecting to one of the slave nodes will be presented with old data. However, using
asynchronous replication makes up for this by providing significant performance gains. As a result asynchronous
replication is the dominant replication strategy.

Most applications have read/write ratio skewed heavily in favour of reads i.e. writes will be fewer. In order to take
advantage of this fact, reads are handled by slaves. In an asynchronous master slave configuration, this presents
a problem because of replication lags. Let me go through a few examples of problems due to replication lags and steps
to save them.

1. Reading writes: User submits a write on master and then decides to read his/her write. The read request can be
   serviced by any slave and the slave servicing read request might not have the most recent write on account of
   replication lag. This can clearly cause user frustration, not able to see his/her most recent post. You can resolve
   this by ensuring all reads go to master after 1 minutes of any write. Another approach can be client remembers
   the last timestamp of the write made and then uses this information to read only from those replicas which have
   a more recent timestamp than the client’s timestamp
2. Moving backwards in time: User submits a write that gets replicated on one of the slaves, s1 and not on the others
   due to replication delays. User then reads from slave s1 and sees the write made by him/her earlier. However he/she
   reads again, and this time say the read is handled by a slave which is not s1. User can be quite frustrated to watch
   his/her writes disappearing. Such problems can be resolved by handling reads for one client only from one replica.

There can be other subtle but operationally annoying issues, especially if you are operating in a choppy internet or
full capacity. It’s important to consider replication lags in your overall system design for replicated data stores.

Now on to many master configuration. The most common use case for this configuration is when you have multiple data
centres. Each data centre can have one master and rest of the nodes can be slaves.

The biggest advantage of many master configuration is performance. Your writes are distributed and no longer throttled
by the capacity of single master. However, there are no free lunches in this world. What you have gained in performance
is made up by handling additional complexity in case of concurrent writes.

In case of many master configuration, you can tie users editing their own data, to one master. Essentially from a user’s
perspective, the configuration becomes single master. This is really neat as now you can avoid all merge conflicts due
to concurrent writes on many masters. However if you cannot do this, then you again need to resolve conflicts by using
one of the following strategies:
1. Make the most recent write win
2. Write custom code on detecting conflicts in replication logs
3. Present the user with a list of values, and let the user decide the merge and discard strategy in case of a conflict

Clearly the right strategy is dependent on the the nature of your application and user expectations.

Finally let me introduce no master replication. In this setup, every node can accept both read and write request. You
can immediately see a problem with this approach. Not only your reads can be stale because of replication lags, your
writes can also be inconsistent.

To solve this problem reads and writes are sent in parallel to many nodes. Lets say r reads are made in parallel,
w writes are made in parallel and there are a total of n nodes. As long as r + w > n, you can be sure that at least
one of the r nodes must be up to date and consequently reads will not be stale.