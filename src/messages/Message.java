package messages;

import java.io.Serializable;
import java.util.ArrayList;
import groups.Group;

public class Message implements Serializable {

	private static final long serialVersionUID = -4398980948410147192L;

	public String type, sender;
	public Object content;
	public ArrayList <String> group;

	public Message(String _type, String _sender, Object _content) {
		this.type = _type;
		this.sender = _sender;
		this.content = _content;
		this.group = new ArrayList <String>();
	}

	@Override
	public String toString() {
		return "{" + "\n" +
				"\ttype : " + this.type + "\n" +
				"\tsender : " + this.sender + "\n" +
				"\tcontent : " + this.content + "\n" +
				"\tgroup : " + this.group + "\n" +
				"}";
	}
	
	public void setUserList(ArrayList <String> _userList) {
		this.content = _userList;
	}
	
	public void setUserGroups(ArrayList <Group> _groups) {
		this.content = _groups;
	}
	
	public void setGroup(ArrayList <String> group) {
		this.group = group;
	}
	
}


