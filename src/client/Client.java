package client;

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

public class Client extends Thread {

	private String ip;
	private int port;
	private Socket connection;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private ChatController guiController;
	private PopupController popupController;
	private String username;
	private String password;

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
	
	public void setPopupController(PopupController _popupController) {
		this.popupController = _popupController;
	}

	public void setUser() {
		Message setUser = new Message("setUser", this.username, this.password);
		send(setUser);
	}

	public void updateGroup(ArrayList <String> newGroup) {
		Message updateGroupMessage = new Message("updateGroup", this.username, null);
		updateGroupMessage.content = newGroup;
		send(updateGroupMessage);
	}

	public void sendMessageToGroup(String message) {
		Message newMessage = new Message("message", this.username, message);
		send(newMessage);
	}

	public void sendTaskToGroup(String task, String recipient) {
		Message newMessage = new Message("task", this.username, task);
		ArrayList <String> recipients = new ArrayList <String>(); 
		recipients.add(recipient);
		newMessage.setGroup(recipients);
		send(newMessage);
	}
	
	public void removeTask(String task) {
		Message newMessage = new Message("removeTask", this.username, task);
		send(newMessage);
	}
	
	public void setTaskDeadline(String task, String selectedDate) {
		ArrayList <String> taskInformation = new ArrayList <String>();
		taskInformation.add(task);
		taskInformation.add(selectedDate);
		Message newMessage = new Message("taskDeadline", this.username, taskInformation);
		send(newMessage);
	}
	
	public void getOnlineUsers() {
		Message newMessage = new Message("getUsers", this.username, null);
		send(newMessage);
	}
	
	public void goOffline() {
		Message newMessage = new Message("goOffline", this.username, null);
		send(newMessage);
		closeConnection();
	}
	
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

	private void read() throws IOException {

		while (true) {
			try {
				Message message = (Message) in.readObject();
				if (message.type.equals("message")) {
					this.displayMessage("[" + message.sender + "] : " + (String) message.content + "\n");
				} else if (message.type.equals("task")) {
					Task newTask = (Task) message.content;
					if (newTask.deadline.equals("")) {
						guiController.taskList.getItems().add("@" + newTask.user + ": " + newTask.task + "\n");
					} else {
						guiController.taskList.getItems().add("@" + newTask.user + ": " + newTask.task + " due by " + newTask.deadline.toString() + "\n");
					}
				} else if (message.type.equals("userList")) {
					ArrayList <String> userList = (ArrayList <String>) message.content;
					for (String _username : userList) {
						this.popupController.addUserElement(_username);
					}
				} else if (message.type.equals("updateGroup")) {
					ArrayList <Task> taskList = ((Group) message.content).tasks;
					ArrayList <Message> chatHistory = ((Group) message.content).chatHistory;
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
				}
			}
			catch (Exception e) {
				this.closeConnection();
				break;
			}
		}
	}
	
	public void loadTaskList(ArrayList <Task> taskList) {
		for (Task newTask : taskList) {
			if (newTask.deadline.equals("")) {
				guiController.populateTaskList("@" + newTask.user + ": " + newTask.task + "\n");

			} else {
				guiController.populateTaskList("@" + newTask.user + ": " + newTask.task + "\ndue by " + newTask.deadline.toString() + "\n");
			}
		}
	}
	
	public void loadHistory(ArrayList <Message> chatHistory) {
		for (Message message : chatHistory) {
			if (message.sender.equals(this.username)) {
				guiController.append2((String) message.content + "\n");
			} else {
				guiController.append((String) message.content);
			}
		}
	}
	
	public void addConversation(ArrayList <String> newConversation) {
		guiController.addConversation(newConversation);
	}

	public void notifyUser(Message message) {
		message.group.remove(this.username);
		guiController.addConversation(message.group);
	}

	public void displayMessage(String message) {
		guiController.append(message);
	}

	public void displayTask(String message) {
		guiController.append(message);
	}

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
