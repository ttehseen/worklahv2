package users;

import java.util.ArrayList;
import groups.Group;
import server.ClientThread;
import tasks.Task;

/**
 * This class stores all vital user information that will be saved to the server
 * @author Geoffrey
 */
public class User {
		
	/**
	 * current group that the user is in on the GUI
	 */
	public Group currentGroup;
	/**
	 * all groups that the user is in
	 */
	public ArrayList <Group> allGroups;
	/**
	 * user name of the user
	 */
	public String username;
	/**
	 * all tasks that have been assigned to the user
	 */
	public ArrayList <Task> tasks;
	/**
	 * flag to indicate whether the user is online or not
	 */
	public boolean onlineStatus;
	/**
	 * thread that th user is running on
	 */
	private ClientThread clientThread;
	/**
	 * password of the user
	 */
	public String password;
	
	/**
	 * instantiates a user object
	 * @param _username username that needs to be set
	 * @param _password password that needs to be set
	 * @param _clientThread client thread attached to the user
	 */
	public User(String _username, String _password, ClientThread _clientThread) {
		this.username = _username;
		this.password = _password;
		this.allGroups = new ArrayList <Group>();
		this.tasks = new ArrayList <Task>();
		this.currentGroup = new Group();
		this.onlineStatus = true;
		this.clientThread = _clientThread;
	}
	
	/**
	 * gets the list of group members that the user is currently talking to
	 * @return list of group members that the user is currently talking to
	 */
	public ArrayList <User> getGroupMembers() {
		return(this.currentGroup.groupMembers);
	}
	
	/**
	 * set the user to be offline
	 */
	public void goOffline() {
		this.onlineStatus = false;
		this.clientThread = null;
	}
	
	/**
	 * take the user online
	 * @param _clientThread thread that the user should be attached to
	 */
	public void goOnline(ClientThread _clientThread) {
		this.onlineStatus = true;
		this.clientThread = _clientThread;
	}

	/**
	 * gets the client thread of the user
	 * @return client thread of the user
	 */
	public ClientThread getClientThread() {
		return(this.clientThread);
	}
	
}
