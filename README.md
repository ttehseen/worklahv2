# worklah

## Instructions

Instructions for network configuration:

Our current implementation of WorkLah runs on the IP 127.0.0.1 for the user to test the application to test it at their convenience locally. To run over a network, change the IP and port number in Server.class, Upload.class and Download.class (for attachments to also work).



Instructions for login:

Our program automatically saves all user data but the user _must_ enter the correct username and password that they used to login in the first time. Our server de facto registers a new user with a username and password when they login for the first time.

![alt text](http://url/to/img1.png)
worklahv2/img1.png

[[https://github.com/username/repository/blob/master/img/octocat.png|alt=octocat]]
[[https://github.com/ttehseen/worklahv2/blob/master/img1.png|alt=img1]]
![Alt text](worklahv2/img1.png?raw=true "Optional Title")
![Figure 1-1](https://github.com/ttehseen/worklahv2/blob/master/img1.png "Figure 1-1")

Instructions for starting a new chat:

Click the new chat icon button at the top right of the application. It will display a list of currently online users. Select from the set of users to start a new chat.



Instructions for starting a new group chat:

Same as above, but to select multiple users, hold down the _cmd_ while selecting users to join the group.



Instructions for task functionality:

1. Creation: 

The structure for creating a new task is as follows:

'@task user_task_assigned_to actual_task'

For example, to assign the task 'Do the dishes' to 'Joe', one would type:

'@task Joe Do the dishes'

2. Deadlining: 

To assign a deadline for the task, select the task and choose a deadline from the date selector at the bottom of the task list.

3. Deleting

To delete a task, right click the task and click 'Delete'



Instructions for using Sassibot:

To use Sassibot, check the box 'Sassibot' and begin chatting. Sassibot disables other chats while in use so be sure to disable Sassibot before chatting with other users again!



Instructions for logging out:

To logout, click the logout button on the bottom left of the application. 


Final Instructions:

Please kill the server process from the console if you would like to delete the database/start a new trial run!



## How does worklah actually work?

First, the socket server (`ServerSocket`) runs. When worklah connects to the server, the server opens a TCP connection between the server and the client, and a main server thread () runs.

We built worklah in a way that allows the server to open multiple concurrent connections when there are multiple clients requesting connections (as reflected in `_____`) )

This was done by having the main server thread start another thread to keep listening on a certain port (8080). When there is a connection request from the client, that connection is established. (clients that try to access at the same time will be put in a queue) When this socket connection is established, we triggered certain methods like (start) that will impact both the server side and client side). 

Once the connection is established, the server opens another thread (ClientThread) to chat with the clients who are connected.

---
The app protocol that we have implemented is supported explicity - it manually inserts all the meta information. 
Something that we could certainly have improved on is our implementation of abstract class - for example, where clients 
write messages to a stream, but the specifics are handles completely by the classes. Ideally, instead of flushing the stream, this would call this would call a method that would then send the associated encapsulated message.
---
