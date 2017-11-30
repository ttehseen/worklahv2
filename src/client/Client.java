package client;

import java.io.File;
import java.io.IOException;
import messages.Message;
import tasks.Task;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import groups.Group;
import gui.ChatController;
import gui.PopupController;

/**
 * Client class to send requests for information to be send to the GUI and, in some cases, show information on the GUI
 * @author Geoffrey
 */
public class Client extends Thread {

	/**
	 * IP address that the client is connected
	 */
	private String ip;
	/**
	 * Port that the client is connected to
	 */
	private int port;
	/**
	 * Connection that the client is connected to with the server
	 */
	private Socket connection;
	/**
	 * Output stream of the client
	 */
	private ObjectOutputStream out;
	/**
	 * Input stream of the client
	 */
	private ObjectInputStream in;
	/**
	 * GUI controller to display things on the GUI
	 */
	private ChatController guiController;
	/**
	 * Popup GUI controller to display a list of users
	 */
	private PopupController popupController;
	/**
	 * client username
	 */
	public String username;
	/**
	 * client password
	 */
	private String password;

	/**
	 * Instantiator for a client object to communicate with the GUI and server
	 * @param ip IP address that the client is connected to
	 * @param p port that the client is connected to
	 * @param _guiController GUI controller of the chat
	 * @param _username client username
	 * @param _password client password
	 * @throws ClassNotFoundException
	 */
	public Client(String ip, int p, ChatController _guiController, String _username, String _password) throws ClassNotFoundException {
		this.ip = ip;
		this.port = p;
		this.guiController = _guiController;
		this.username = _username;
		this.password = _password;
		this.popupController = null;
		try {
			this.connection = new Socket(this.ip, port);
			this.out = new ObjectOutputStream(connection.getOutputStream());
			this.in = new ObjectInputStream(connection.getInputStream());
			setUser();
			System.out.println(this.username);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets the popup controller for the client
	 * @param _popupController
	 */
	public void setPopupController(PopupController _popupController) {
		this.popupController = _popupController;
	}

	/**
	 * sets the user for the client
	 */
	public void setUser() {
		Message setUser = new Message("setUser", this.username, this.password);
		send(setUser);
	}

	/**
	 * send a request for information on the new group the client has clicked on
	 * @param newGroup new group that the client has clicked on
	 */
	public void updateGroup(ArrayList <String> newGroup) {
		Message updateGroupMessage = new Message("updateGroup", this.username, null);
		updateGroupMessage.content = newGroup;
		send(updateGroupMessage);
	}

	/**
	 * sends a message to the rest of the group
	 * @param message
	 */
	public void sendMessageToGroup(String message) {
		Message newMessage = new Message("message", this.username, message);
		send(newMessage);
	}

	/**
	 * sends a task to the rest of the group
	 * @param task task information
	 * @param recipient user responsible for the task
	 */
	public void sendTaskToGroup(String task, String recipient) {
		Message newMessage = new Message("task", this.username, task);
		ArrayList <String> recipients = new ArrayList <String>(); 
		recipients.add(recipient);
		newMessage.setGroup(recipients);
		System.out.println(newMessage);
		send(newMessage);
	}
	
	/**
	 * sends a request to remove the task from the list of tasks
	 * @param task task to be removed
	 */
	public void removeTask(String task) {
		Message newMessage = new Message("removeTask", this.username, task);
		send(newMessage);
		System.out.println((String) newMessage.content);
	}
	
	/**
	 * requests to set the task deadline of a specified task
	 * @param task the task to set the deadline of
	 * @param selectedDate deadline of the task
	 */
	public void setTaskDeadline(String task, String selectedDate) {
		ArrayList <String> taskInformation = new ArrayList <String>();
		taskInformation.add(task);
		taskInformation.add(selectedDate);
		Message newMessage = new Message("taskDeadline", this.username, taskInformation);
		System.out.println(newMessage);
		send(newMessage);
	}
	
	/**
	 * sends a request to the server to upload a file to another client
	 * @param pathToFile path to file that client wants to upload
	 */
	public void uploadFile(File pathToFile) {
		String path = pathToFile.toString().substring(pathToFile.toString().lastIndexOf("/") + 1);
		Message newMessage = new Message("uploadingFile", this.username, this.username + " would like to share '" + path + "' with you!");
		send(newMessage);
		Upload startUpload = new Upload("127.0.0.1", 8888, pathToFile, this.guiController);
		startUpload.run();
	}
	
	/**
	 * gets the online users on the server
	 */
	public void getOnlineUsers() {
		Message newMessage = new Message("getUsers", this.username, null);
		send(newMessage);
	}
	
	/**
	 * sends a request to the server to take the user offline
	 */
	public void goOffline() {
		Message newMessage = new Message("goOffline", this.username, null);
		send(newMessage);
		closeConnection();
	}
	
	/*
	 * Method to run the reading thread in this class
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
	 * Reading thread to read all incoming information
	 * @throws IOException
	 */
	private void read() throws IOException {

		while (true) {
			try {
				Message message = (Message) in.readObject();
				if (message.type.equals("message")) {
					guiController.append((String) message.content, message.sender);
				} else if (message.type.equals("task")) {
					System.out.println("SENT TASK UPDATE");
					System.out.println(message);
					String newTask = (String) message.content;
					guiController.taskList.getItems().add("@" + message.group.get(0) + ": " + newTask + "\n");
				} else if (message.type.equals("userList")) {
					ArrayList <String> userList = (ArrayList <String>) message.content;
					for (String _username : userList) {
						this.popupController.addUserElement(_username);
					}
				} else if (message.type.equals("updateGroup")) {
					ArrayList <Task> taskList = ((Group) message.content).tasks;
					ArrayList <Message> chatHistory = ((Group) message.content).chatHistory;
					System.out.println("UPDATE MESSAGE RECEIVED");
					this.loadHistory(chatHistory);
					this.loadTaskList(taskList);
				} else if (message.type.equals("loadUserGroups")) {
					ArrayList <Group> groupList = ((ArrayList <Group>) message.content);
					for (Group group : groupList) {
						group.groupMemberNames.remove(this.username);
						guiController.addConversation(group.groupMemberNames);
					}
				} else if (message.type.equals("notifyUser")) {
					this.notifyUser(message);
				} else if (message.type.equals("downloadFile")) {
					this.downloadFile(message);
				} else if (message.type.equals("notifyUserTask")) {
					this.notifyUserTask(message);
				}
			}
			catch (Exception e) {
				this.closeConnection();
				e.printStackTrace();
				break;
			}
		}
	}
	
	/**
	 * notifies the client that a new task has come in
	 * @param message message that has been sent to the client
	 */
	public void notifyUserTask(Message message) {
		message.aux.remove(this.username);
		guiController.addConversation(message.aux);
	}
	
	/**
	 * loads the task list of the group the client has selected
	 * @param taskList task list of the group the client has selected
	 */
	public void loadTaskList(ArrayList <Task> taskList) {
		guiController.taskList.getItems().clear();
		System.out.println("RECEIVED");
		System.out.println(taskList);
		System.out.println(guiController.taskList.getItems());
		for (Task newTask : taskList) {
			if (newTask.deadline.equals("")) {
				guiController.populateTaskList("@" + newTask.user + ": " + newTask.task + "\n");

			} else {
				guiController.populateTaskList("@" + newTask.user + ": " + newTask.task + "\ndue by " + newTask.deadline.toString() + "\n");
			}
		}
	}
	
	/**
	 * loads the chat history of the group the client has selected
	 * @param chatHistory chat history of the group the client has selected
	 */
	public void loadHistory(ArrayList <Message> chatHistory) {
		guiController.chatView.setText("");
		for (Message message : chatHistory) {
				guiController.append((String) message.content, message.sender);
			
		}
	}
	
	/**
	 * Calls a thread to download the file that will be sent from another client
	 * @param message file name of the file that will be downloaded
	 */
	public void downloadFile(Message message) {
		guiController.append((String) message.content, message.sender);
		String fileName = (String) message.content;
		int start = fileName.indexOf(" '");
		start += 2;
		int end = fileName.indexOf("' ");
		String newFileName = fileName.substring(start, end);
		Download newDownload = new Download(newFileName, guiController);
		newDownload.run();
	}
	
	/**
	 * adds a new conversation to the GUI
	 * @param newConversation new conversation to be added
	 */
	public void addConversation(ArrayList <String> newConversation) {
		guiController.addConversation(newConversation);
	}

	/**
	 * notifies the client that a new message has come in
	 * @param message new message that has been sent to the client
	 */
	public void notifyUser(Message message) {
		message.group.remove(this.username);
		guiController.addConversation(message.group);
	}

	/**
	 * sends a message to other clients
	 * @param message
	 */
	public void send(Message message) {
		try {
			out.reset();
			out.writeObject(message);
			out.flush();
		} 
		catch (IOException ex) {
			System.out.println("Exception: send() in Client");
			ex.printStackTrace();
		}
	}

	/**
	 * closes and cleans the connection of the client
	 */
	public void closeConnection() {
		System.out.println("Client Disconnecting!");
		try {
			this.out.close();
			this.in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
