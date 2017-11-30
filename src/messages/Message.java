package messages;

import java.io.Serializable;
import java.util.ArrayList;
import groups.Group;

/**
 * Message class to hold the requests and posts to and from the client and server
 * @author Geoffrey
 */
public class Message implements Serializable {

	/**
	 * Serial ID to allow messages to be transferred over the server
	 */
	private static final long serialVersionUID = -4398980948410147192L;

	/**
	 * type of request or post and the sender of the request or psot
	 */
	public String type, sender;
	/**
	 * main content of the message
	 */
	public Object content;
	/**
	 * group that this message belongs to
	 */
	public ArrayList <String> group;
	/**
	 * auxiliary arraylist of string that can be used as an extra variable in case needed
	 */
	public ArrayList <String> aux;	

	/**
	 * Instantiator for a message object
	 * @param _type type of post or request
	 * @param _sender sender of the post or request
	 * @param _content main content of the message
	 */
	public Message(String _type, String _sender, Object _content) {
		this.type = _type;
		this.sender = _sender;
		this.content = _content;
		this.group = new ArrayList <String>();
		this.aux = new ArrayList <String>();
	}

	/*
	 * Method to print messages in case needed or for debugging
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{" + "\n" +
				"\ttype : " + this.type + "\n" +
				"\tsender : " + this.sender + "\n" +
				"\tcontent : " + this.content + "\n" +
				"\tgroup : " + this.group + "\n" +
				"}";
	}
	
	/**
	 * Sets the user list of online users on the server
	 * @param _userList online users on the server
	 */
	public void setUserList(ArrayList <String> _userList) {
		this.content = _userList;
	}
	
	/**
	 * Sets the groups the the user is currently in
	 * @param _groups groups that the user is currently in
	 */
	public void setUserGroups(ArrayList <Group> _groups) {
		this.content = _groups;
	}
	
	/**
	 * sets the group that this message is stored in
	 * @param group group that this message is stored in
	 */
	public void setGroup(ArrayList <String> group) {
		this.group = group;
	}
	
	/**
	 * Sets an auxiliary variable to store an arraylist of strings in case there is a need for another variable
	 * @param group group of strings that can be used as an auxiliary variable
	 */
	public void setAuxiliary(ArrayList <String> group) {
		this.aux = group;
	}
	
}


