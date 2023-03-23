#Fundamentals of System Design

While designing systems, there are three primary concerns that should be addressed — reliability, scalability and
maintainability. These terms are tossed around quite frequently and in this post I want to provide expositions for each
of them.

##Reliability
Reliability means the ability of a system to tolerate faults or problems in order to prevent failures or complete
shutdowns. Large systems are built using fault intolerant components. The beauty and art of system design is to build
fault tolerant systems using fault intolerant components.

Faults can be categorised as hardware or software. For example, a large data centre, with hard disks with MTTF of
50–100 years, will witness disks going bust every day. Memory will be corrupted on a regular basis. Hardware faults can
be addressed by adding redundancies. For example, disks can be stored in RAID configuration, data centres can have
multiple power backups, CPUs can have hot-swapping support.

Software faults can happen due to a variety of reasons. One runaway process can hog your system resources and cause
a systematic crash across all nodes or the operating assumptions of the applications can change and result in crashes.
Software faults can be handled by understanding business requirements and building resiliency to handle deviations from
the same, better monitoring to publish warnings early on, better unit testing and finally by designing better
abstractions and interfaces to easily isolate problems.

##Scalability
Scalability is the system’s ability to deliver reasonable performance in face of increased load. System load can be
described in terms of parameters that best translate an application’s raison d’être technically. For example for
a social networking website, the expected number of the writes (posts) per second or reads (posts in timeline view)
per second can be used to describe load. You can also consider peak reads/writes instead of average, for describing load
on the system.

Performance can be thought of as the system’s operating characteristic when system’s load parameter is changed.
For example you might measure performance in terms of system average response time. You can also measure performance
in terms of distribution of response times. So you might consider the 99th percentile of response time to be under 1 sec
and average response time to be 300 ms. Performance metrics quite often are a part of your SLA with customers. There are
many ways to achieve system scalability which I plan to cover in the subsequent posts in this series.

##Maintainability
Maintainability means writing code that can easily be understood, refactored and upgraded by someone who is not
the original author of the code. Any piece of spaghetti confusing code will ultimately be understood by machines.
Good code should be readable and easily understood so that teams can collaborate. Good code should also have the right
level of abstractions, clean APIs and interfaces so that new functionality can be easily built on top of existing codebase.