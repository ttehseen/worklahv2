package groups;

import java.io.Serializable;
import messages.Message;
import tasks.Task;

import java.util.ArrayList;
import java.util.Collections;

import users.User;

public class Group implements Serializable {

	private static final long serialVersionUID = 7710781962991992645L;

	public String groupName;
	public transient ArrayList <User> groupMembers;
	public ArrayList <String> groupMemberNames;
	public ArrayList <Message> chatHistory;
	public ArrayList <Task> tasks;
	
	public Group() {
		this.groupMembers = new ArrayList <User>();
		this.groupMemberNames = new ArrayList <String>();
		this.chatHistory = new ArrayList <Message>();
		this.tasks = new ArrayList <Task>();
	}

	public void addUser(User newUser) {
		this.groupMembers.add(newUser);
		this.groupMemberNames.add(newUser.username);
	}
	
	public void updateGroupName() {
		ArrayList <String> groupMemberNames = new ArrayList<String>();
		for (User user : this.groupMembers) {
			groupMemberNames.add(user.username);
		}
		this.groupName = String.join(",", groupMemberNames);
	}
	
	public boolean checkMembers(ArrayList <String> members) {
		Collections.sort(members);
		Collections.sort(this.groupMemberNames);
		return(members.equals(groupMemberNames));
	}
	
	public void addTask(Task newTask) {
		this.tasks.add(newTask);
	}
	
}
