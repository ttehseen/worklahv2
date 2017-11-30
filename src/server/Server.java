package server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import groups.Group;
import users.User;

/**
 * 
 * @author Geoffrey
 */
public class Server
{
	/**
	 * server socket for clients to connect to
	 */
	private ServerSocket servSock;
	/**
	 * array of clients currently connected to the server
	 */
	protected ArrayList <ClientThread> clients;
	/**
	 * users currently stored on the server
	 */
	protected ArrayList <User> users;
	/**
	 * groups currently stored on the server
	 */
	protected ArrayList <Group> groups;

	/**
	 * Instantiator to create a server object
	 * @param port
	 */
	public Server(int port) {
		try {
			this.servSock = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 
	
	/**
	 * Starts a thread to keep accepting clients
	 */
	private void acceptClientLoop() {
		groups = new ArrayList <Group>();
		clients = new ArrayList <ClientThread>();
		users = new ArrayList <User>();
		while (true) {
			Socket c;
			try {
				c = this.servSock.accept();
				ClientThread th = new ClientThread(c, this);
				th.start();
				clients.add(th);
				System.out.println("Client Accepted.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Main to create the server
	 * @param args
	 * @throws Exception
	 */
	public static void main (String[] args) {
		Server serv = new Server(8080);
		serv.acceptClientLoop();
	}

}
