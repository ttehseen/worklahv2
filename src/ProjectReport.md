# YSC3232 Object-Oriented Programming: Final Project Report

**Group Name: GST**

**Project Name: Work Lah!**

**Project Members: Geoffrey, Swarnima, and Taha.**

  

# INTRODUCTION

### Outline:

We succeeded in creating a simple cross-platform client-server chat application that real-time multiple client messaging. 
As mentioned in our project proposal, the primary way in which we sought to improve existing chat applications was by introducing easy, efficient task management functionality. 
This—along with the implementation of the chat app itself—was successfully done.

It must, however, be noted that we also some experienced various issues. 
These can be summarised in the following broad categories: 

1. Conceptualising (Timeline and Features) 
2. Code Integration 
3. Group Availability 

These have been detailed in this report.

### Assignment Specification: 

A chat system that allows users to share data.

 

Specification detailed in Project Proposal


# CHALLENGES FACED

## Conceptualising (Timeline and Features)

We had a rather warped sense of how much time different parts of the implementation would take. 
This ultimately resulted in our original timeline being almost completely inaccurate. 

We originally planned to use Swing for our user interface, 
but had to quickly pivot to JavaFX once we realised that Swing did not support rich text and images, 
both of which were essential for the design aesthetic we were striving to achieve. 

At the time we wrote our project proposal, we thought that we had a lucid, reasonable idea of how WorkLah would work, be implemented, as well as what it would look like. 
These ideas crystallised as we worked on the prototype for our presentation in the classroom. 
While we had started some of the networks backend based on the Sockets practice we had already done in class, we underestimated the complexity of multi-threading and synchronisation. 
While working on network functionality, once the basic GUI was completed, 
we all realised that Swarnima’s original code could be made more efficient by moving most of its information to the server side. 
This resulted in a significant restructuring of the backend fairly late in the course of the assignment. 
In its final implementation, the server has access to an array of users on the server side, while the client side threads act as middlemen for the data streams.

Owing to our lack of knowledge regarding databases, we were also uncertain about how we would go about setting attributes to messages and users. 
Ultimately, we settled on the class Message.java which provided us with a useful, quick way of instantiating metadata on a user. 
This was the first example of a number of implementation issues that we only found out about and then resolved while we were writing the application itself.

## Code Integration

One of the biggest issues we faced was in integrating our code. We discussed the way we were going to implement our chat app in a detailed manner well in advance, but this caused problems. 

All three of us have immensely varied coding styles, and two of us were also completely new to Git. As a result of this, there was a general distrust of Git at first as the two of us who were largely unfamiliar with Git stayed away from the platform. 
This resulted in our first mistake. 
Taha worked on the GUI while Swarnima worked on the network backend, and then Geoffrey worked on integrating these two very different implementations, which was both time consuming and frustrating - especially as it could have been easily avoided. 
We worked to resolve this by moving to Git as soon as we could, late though it may have been. 

Unfortunately, this introduced more problems. As mentioned earlier, there was a lack of familiarity with the platform. 
As a result, there were some push-pull inconsistencies that took time to figure out and rectify. 
These were caused by people directly committing to the master branch, rather than making commits to alternative branches, and then merging those secondary branches with the master branch. 
However, after sitting with each other and working together, we eventually settled on a system of continuous integration that worked well for all of us.

Once we agreed on how the backend would work, Swarnima and Geoffrey were able to work on an agreed upon system. 
Ultimately, everyone worked on a little bit of everything. 
Swarnima primarily worked on networks and features, Geoffrey on GUI, networks, and assumed the bulk of integrating network functionality with the GUI, 
while Taha focussed on the GUI and features. 

We realised that the one of the best ways in which we should have worked together was to actually employ the plethora of features that GitHub offered in terms of workflow management. 
In hindsight, flagging and resolving issues using the Issues tab, as well as using the Wiki tab would have served us well.

## Group Availability

To be efficient, and to be considerate of everyone’s time, we split up the work: Taha would primarily be in charge of the GUI, and Geoffrey and Swarnima would be responsible for the network and feature implementation. 

Due to a number of logistical issues, one of us was bedridden and anaemic over Reading Week, another’s laptop needed to be repaired, we could only start work in full force slightly less than a week before the submission deadline on 27th November. 
This had the unfortunate consequence of the implementation of networks happening almost completely out of sync with that of the GUI. 

Furthermore, Taha, who was responsible for the GUI, was both without a laptop and also fell extremely ill in the three intense days before the submission deadline. 
This brought some of the work to a standstill, as we needed a well-functioning GUI to test and implement our other methods. 
So, while code for both the networks and the GUI had already been written, they could not be integrated. 
At the time, Taha’s version of the GUI implemented his own version of a simple network. 
Swarnima’s implementation was far more complex, and the number of its moving parts made it difficult to relate to Taha’s GUI. We resolved this by working together to comment out the code properly until we had a well-functioning GUI. 

Geoffrey, even though he had to juggle other assignments, he came through to work on integrating the GUI with the networks - modifying both to suit each other better, especially when Taha was unwell. 
Ultimately, it was a combination of sleepless nights, clear communication, and efficiently dividing up the work that enabled us to work together. At any time since Friday, 26th November, at least two people were working on the assignment round the clock. 
