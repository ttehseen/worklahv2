package tasks;

import java.io.Serializable;

public class Task implements Serializable {
	
	private static final long serialVersionUID = -254108039420780039L;
	
	public String user;
	public String task;
	public String deadline;
	
	public Task(String _task, String _user) {
		this.task = _task;
		this.user = _user;
		this.deadline = "";
	}
	
	public void setDeadline(String _deadline) {
		this.deadline = _deadline;
	}
}
