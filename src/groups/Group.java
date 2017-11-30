package groups;

import java.io.Serializable;
import messages.Message;
import tasks.Task;

import java.util.ArrayList;
import java.util.Collections;

import users.User;

/**
 * Group class to store all information about users, chat history, etc. in a group
 * @author Geoffrey
 */
public class Group implements Serializable {

	/**
	 * Serial ID to allow for transfer over networks
	 */
	private static final long serialVersionUID = 7710781962991992645L;

	/**
	 * group name of the group
	 */
	public String groupName;
	/**
	 * group members in the group
	 */
	public transient ArrayList <User> groupMembers;
	/**
	 * names of the group members in the group
	 */
	public ArrayList <String> groupMemberNames;
	/**
	 * chat history of the group
	 */
	public ArrayList <Message> chatHistory;
	/**
	 * tasks currently alive in the group
	 */
	public ArrayList <Task> tasks;
	
	/**
	 * Instantiates a group object
	 */
	public Group() {
		this.groupMembers = new ArrayList <User>();
		this.groupMemberNames = new ArrayList <String>();
		this.chatHistory = new ArrayList <Message>();
		this.tasks = new ArrayList <Task>();
	}

	/**
	 * adds a new user to the group
	 * @param newUser new user we'd like to add to the group 
	 */
	public void addUser(User newUser) {
		this.groupMembers.add(newUser);
		this.groupMemberNames.add(newUser.username);
	}
	
	/**
	 * sets the group name of the group
	 */
	public void updateGroupName() {
		ArrayList <String> groupMemberNames = new ArrayList<String>();
		for (User user : this.groupMembers) {
			groupMemberNames.add(user.username);
		}
		this.groupName = String.join(",", groupMemberNames);
	}
	
	/**
	 * checks if the members of the group is equal to the members in the argument
	 * @param members group that'd we'd like to group this current group to
	 * @return true if the members of the same, false if not
	 */
	public boolean checkMembers(ArrayList <String> members) {
		Collections.sort(members);
		Collections.sort(this.groupMemberNames);
		return(members.equals(groupMemberNames));
	}
	
	/**
	 * adds a new task to the group
	 * @param newTask new task to be added
	 */
	public void addTask(Task newTask) {
		this.tasks.add(newTask);
	}
	
}
