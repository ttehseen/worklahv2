package server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import groups.Group;
import users.User;

public class Server
{
	private ServerSocket servSock;
	protected ArrayList <ClientThread> clients;
	protected ArrayList <User> users;
	protected ArrayList <Group> groups;

	public Server(int port) {
		try {
			this.servSock = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 
	
	public void acceptClientLoop() {
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

	public static void main (String[] args) throws Exception {
		Server serv = new Server(8080);
		serv.acceptClientLoop();
	}

}
