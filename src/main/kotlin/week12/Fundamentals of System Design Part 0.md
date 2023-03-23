#Fundamentals of System Design

Whether you are a backend developer, product manager or technical manager, everyone needs to know how to build reliable,
scalable and maintainable applications. No one expects a developer or product manager to design a new storage engine.
However, it is expected from the said folks, to appropriately describe load and operating performance parameters of
their applications. They are also expected to cobble together various data systems like storage, caching, stream
processing, search, retrieval and batch processing, and design a resilient system.

Sometime back, I found myself in the position of guiding design of a scalable system. I was surprised at the lack of
concise guides on this topic and this is the motivation behind crafting a multi-part series on ‘Fundamentals of System
Design’.

The number of tools and techniques that can be used to design a good system are polynomial in nature, but the logic and
principles behind them are enduring and constant. My goal is not to make a reader of my articles, expert in say Elastic
search or Mongodb. My goal is to make you conversant in the choice of various kinds of data systems by teaching you
the basic principles of good system design. This multi-part series is also not intended to be an interview preparation
guide. Heaven’s know my own interview skills suck big time :) and hence I will not cap this series by answering
questions like how to design a bitly service or Google autocomplete. There are lot of paid and free articles that help
you prepare for a system design interview.

I will try to post a new article every week with a deviation of +2/-2 days. Do show your love by clapping and provide
feedback by commenting.