package server;

import java.net.*;
import messages.Message;
import tasks.Task;
import users.User;
import groups.Group;
import java.util.ArrayList;
import java.io.*;

/* 
 By extending Thread, each newly spawned thread is instantiated as as a unique object associated. 
 We decided against implementing Runnable, because if we had done so, many threads could share the same object instance. 
 */

/**
 * @author Geoffrey
 * This thread class acts as a middle man between the different user clients, sending information
 * about the client to and from the GUI 
 */
public class ClientThread extends Thread {

	/**
	 * Socket for the user client
	 */
	private Socket client;
	/**
	 * Server variable with the database
	 */
	private Server server;
	/**
	 * stream that we will be writing to
	 */
	private ObjectOutputStream out;
	/**
	 * stream that will be reading from
	 */
	private ObjectInputStream in;
	/**
	 * User that has logged in
	 */
	private User user;

	/* Note: Should we change these to buffered input/output streams to be more efficient?
	 * If we implement Buffered I/O streams,this will optimize input and output 
	 * by reducing the number of calls to the native API.
	 */

	// constructor
	/**
	 * Instantiator for creating a client thread
	 * @param c client connection 
	 * @param server server variable holding the database
	 */
	public ClientThread(Socket c, Server server) {
		this.server = server;
		this.client = c;
		this.user = null;
		try {
			out = new ObjectOutputStream(this.client.getOutputStream());
			in = new ObjectInputStream(this.client.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * handles all task messages and adds them to the group
	 * @param message message containing task information
	 * @throws IOException
	 */
	private void handleTask(Message message) throws IOException {
		String task = (String) message.content;
		Task newTask = new Task(task, message.group.get(0));
		this.user.currentGroup.tasks.add(newTask);
		for (User user : this.user.getGroupMembers()) {
			if (!user.currentGroup.checkMembers(this.user.currentGroup.groupMemberNames)) {
				user.allGroups.add(this.user.currentGroup);
				message.type = "notifyUser";
				if (!user.equals(this.user)) {
					this.send(message, user.getClientThread());
				}
			} else {
				if (!user.equals(this.user)) {
					this.send(message, user.getClientThread());
				}
			}
		}
	}

	/**
	 * uploads a file to the server
	 * @param message message to send to the server
	 * @param c client that we would like to send something to
	 */
	public void uploadFile (Message message, ClientThread c) {
		try {
			c.out.writeObject(message);
			c.out.flush();
		} 
		catch (IOException ex) {
			System.out.println("Exception: uploadFile in ClientThread");
			ex.printStackTrace();
		}
	}

	/**
	 * sends a list of online users to the client to pick from to chat with
	 */
	public void sendUsers() {
		Message newMessage = new Message("userList", null, null);
		ArrayList <String> userList = new ArrayList <String>();
		for (User user : server.users) {
			if (user.username != this.user.username && (user.onlineStatus)) {
				userList.add(user.username);
			}
		}
		newMessage.setUserList(userList);
		send(newMessage, this);
	}

	/**
	 * sends a message to other clients
	 * @param message message that we want to send
	 * @param c client thread that we want to reach
	 */
	public void send(Message message, ClientThread c) {
		synchronized (this) {
			try {
				c.out.reset();
				c.out.writeObject(message);
				c.out.flush();
			} 
			catch (IOException ex) {
				System.out.println("Exception: send() in ClientThread");
				ex.printStackTrace();
			}
		}
	}

	/**
	 * updates the group view of the current user and the chat that that person is in
	 * @param message message dictating what group the user should change to for loading of chat history
	 * @throws IOException 
	 */
	private void updateGroup(Message message) throws IOException {
		ArrayList <String> userList = (ArrayList <String>) message.content;
		userList.add(this.user.username);
		boolean newGroupCreated = true;
		for (Group g : this.server.groups) {
			if (g.checkMembers(userList)) {
				this.user.currentGroup = g;
				newGroupCreated = false;
			}
		}
		if (newGroupCreated) {
			Group newGroup = new Group();
			for (String user : userList) {
				newGroup.addUser(getUser(user));
			}
			newGroup.updateGroupName();
			this.user.currentGroup = newGroup;
			this.user.allGroups.add(newGroup);
			this.server.groups.add(newGroup);
		}
		Message updateGroupMessage = new Message("updateGroup", null, null);
		updateGroupMessage.content = this.user.currentGroup;
		send(updateGroupMessage, this);
	}

	private void updateTaskDeadline(Message message) {
		String _task = ((ArrayList <String>) message.content).get(0);
		String deadline = ((ArrayList <String>) message.content).get(1);
		for (Task task : this.user.currentGroup.tasks) {
			if (task.task.equals(_task)) {
				task.setDeadline(deadline);
			}
		}
		Message updateGroupMessage = new Message("updateGroup", null, null);
		updateGroupMessage.content = this.user.currentGroup;
		send(updateGroupMessage, this);
	}
	
	private void removeTask(Message message) {
		String _task = (String) message.content;
		for (Task task : this.user.currentGroup.tasks) {
			if (task.task.equals(_task)) {
				this.user.currentGroup.tasks.remove(task);
			}
		}
		Message updateGroupMessage = new Message("updateGroup", null, null);
		updateGroupMessage.content = this.user.currentGroup;
		send(updateGroupMessage, this);
	}

	/**
	 * gets the user associated with a string
	 * @param _user username that we want to get
	 * @return the user that matches the username
	 */
	private User getUser(String _user) {
		for (User user : server.users) { 
			if (_user.equals(user.username)) {
				return(user);
			}
		}
		return(null);
	}



	/**
	 * sets the user when the user logs in
	 * @param message login information from the user 
	 */
	private void setUser(Message message) {
		for (User user : server.users) {
			if (message.sender.equals(user.username)) {
				if (message.content.equals(user.password)) {
					this.user = user;
					this.user.goOnline(this);
					Message loadUserGroups = new Message("loadUserGroups", null, null);
					loadUserGroups.content = this.user.allGroups;
					send(loadUserGroups, this);
					return;
				}
			}
		}
		this.user = new User(message.sender, (String) message.content, this);
		server.users.add(this.user);
		Message loadUserGroups = new Message("loadUserGroups", null, null);
		ArrayList <Group> groupList = new ArrayList <Group>();
		loadUserGroups.content = groupList;
		send(loadUserGroups, this);
	}


	/* 
	 * runs the main reading thread
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		Thread reading = new Thread() {
			@Override
			public void run() {
				try {
					read();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		reading.start();
	}

	/**
	 * constantly read from the object input stream to check for messages and requests from the client
	 * @throws IOException
	 */
	public void read() throws IOException {
		while (true) {
			try {
				Message message = (Message) in.readObject();
				if (this.user != null) {
					message.setGroup(this.user.currentGroup.groupMemberNames);
				}
				if (message.type.equals("setUser")) {
					this.setUser(message);
				} else if (message.type.equals("updateGroup")) {
					this.updateGroup(message);
				} else if (message.type.equals("task")) {
					this.handleTask(message);
				} else if (message.type.equals("getUsers")) {
					this.sendUsers();
				} else if (message.type.equals("message")) {
					this.messageHandler(message);
				} else if (message.type.equals("goOffline")) {
					this.user.goOffline();
				} else if (message.type.equals("uploadFile")) {
					this.uploadFile(message);
				} else if (message.type.equals("taskDeadline")) {
					this.updateTaskDeadline(message);
				} else if (message.type.equals("removeTask")) {
					this.removeTask(message);
				}
			}
			catch (Exception e) {
				try {
					this.closeConnection();
				} catch (IOException e1) {
					System.out.println("Exception ChatClient sendToServer()");
					e1.printStackTrace();
				}
				break;
			}
		}
	}

	/**
	 * handles all messages sent from a client and decides whether to notify a user or
	 * send the message to the user main chatbox
	 * @param message message that we would like to send to other clients
	 * @throws IOException
	 */
	public void messageHandler(Message message) throws IOException {
		this.user.currentGroup.chatHistory.add(message);
		for (User user : this.user.getGroupMembers()) {
			if (!user.currentGroup.checkMembers(this.user.currentGroup.groupMemberNames)) {
				user.allGroups.add(this.user.currentGroup);
				message.type = "notifyUser";
				if (!user.equals(this.user)) {
					this.send(message, user.getClientThread());
				}
			} else {
				if (!user.equals(this.user)) {
					this.send(message, user.getClientThread());
				}
			}
		}
	}

	/**
	 * uploads file attachements to other clients
	 * @param message
	 */
	public void uploadFile (Message message) {
		for (User user : this.user.getGroupMembers()) {
			if (!user.equals(this.user)) {
				this.send(message, user.getClientThread());
			}
			else {
				if (!user.equals(this.user)) {
					this.send(message, user.getClientThread());
				}
			}
		}
	}

	/**
	 * closes and cleans the connection to the server and client
	 * @throws IOException
	 */
	public void closeConnection() throws IOException
	{
		System.out.println("Client disconnecting, cleaning the data!");
		try {
			this.out.close();
			this.in.close();
			this.client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
