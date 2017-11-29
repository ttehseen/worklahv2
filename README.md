# worklah

## How does worklah actually work?

First, the socket server (`ServerSocket`) runs. When worklah connects to the server, the server opens a TCP connection between the server and the client, and a main server thread () runs.

We built worklah in a way that allows the server to open multiple concurrent connections when there are multiple clients requesting connections (as reflected in `_____`) )

This was done by having the main server thread start another thread to keep listening on a certain port (8080). When there is a connection request from the client, that connection is established. (clients that try to access at the same time will be put in a queue) When this socket connection is established, we triggered certain methods like (start) that will impact both the server side and client side). 

Once the connection is established, the server opens another thread (ClientThread) to chat with the clients who are connected.

---
The app protocol that we have implemented is supported explicity - it manually inserts all the meta information. 
Something that we could certainly have improved on is our implementation of abstract class - for example, where clients 
write messages to a stream, but the specifics are handles completely by the classes. Ideally, instead of flushing the stream, this would call this would call a method that would then send the associated encapsulated message.
