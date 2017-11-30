package tasks;

import java.io.Serializable;

/**
 * This object stores task information for each group
 * @author Geoffrey
 */
public class Task implements Serializable {
	
	/**
	 * serial ID for internet transfer
	 */
	private static final long serialVersionUID = -254108039420780039L;
	
	/**
	 * user responsible for finishing the task
	 */
	public String user;
	/**
	 * actual task of the task instance
	 */
	public String task;
	/**
	 * deadline of the task
	 */
	public String deadline;
	
	/**
	 * instantiates a task instance
	 * @param _task asssigns a task to the object
	 * @param _user user that should be responsible for the task
	 */
	public Task(String _task, String _user) {
		this.task = _task;
		this.user = _user;
		this.deadline = "";
	}
	
	/**
	 * sets the deadline of a task
	 * @param _deadline deadline we'd like to set the task to be
	 */
	public void setDeadline(String _deadline) {
		this.deadline = _deadline;
	}
	
	/* 
	 * String override method to print the task
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return("Task: " + this.task + "\nUser: " + this.user + "\nDeadline: " + this.deadline);
	}
}
