package users;

import java.util.ArrayList;
import groups.Group;
import server.ClientThread;
import tasks.Task;

public class User {
		
	public Group currentGroup;
	public ArrayList <Group> allGroups;
	public String username;
	public ArrayList <Task> tasks;
	public boolean onlineStatus;
	private ClientThread clientThread;
	public String password;
	
	public User(String _username, String _password, ClientThread _clientThread) {
		this.username = _username;
		this.password = _password;
		this.allGroups = new ArrayList <Group>();
		this.tasks = new ArrayList <Task>();
		this.currentGroup = new Group();
		this.onlineStatus = true;
		this.clientThread = _clientThread;
	}
	
	public ArrayList <User> getGroupMembers() {
		return(this.currentGroup.groupMembers);
	}
	
	public void goOffline() {
		this.onlineStatus = false;
		this.clientThread = null;
	}
	
	public void goOnline(ClientThread _clientThread) {
		this.onlineStatus = true;
		this.clientThread = _clientThread;
	}

	public ClientThread getClientThread() {
		return(this.clientThread);
	}
	
}
