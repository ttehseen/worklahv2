package client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import gui.ChatController;
import java.net.UnknownHostException;


/**
 * Thread class to handle the uploading of a file to another client
 * @author Geoffrey
 */
public class Upload implements Runnable {

	/**
	 * connection that the client should connect to
	 */
	private Socket connection;
	/**
	 * file input stream to read from
	 */
	private FileInputStream in;
	/**
	 * output stream to the client
	 */
	private OutputStream out;
	/**
	 * GUI controller to print succcess status to
	 */
	private ChatController gui;

	/**
	 * Instantiator for an upload object
	 * @param ip IP address to connect to
	 * @param port port to connect to
	 * @param filepath filepath of the file we would like to upload
	 * @param screen GUI controller to print succcess status to
	 */
	public Upload(String ip, int port, File filepath, ChatController screen) throws FileNotFoundException, UnknownHostException, IOException{
//		try {
			this.gui = screen;
			this.connection = new Socket(InetAddress.getByName(ip), port);
			in = new FileInputStream(filepath);
			out = connection.getOutputStream();
//		} 
//		catch (Exception e) {
//			System.out.println("Upload could not occur.");
//		}
	}

	/* 
	 * Method called to run this thread
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {       
			byte[] buffer = new byte[16384];
			int count;
			while ((count = in.read(buffer)) >= 0) {
				out.write(buffer, 0, count);
			}
			out.flush();
                        in.close(); 
                        out.close(); 
                        connection.close(); 
		}
		catch (Exception ex) {
			this.gui.chatView.appendText("........\nUpload Failure. Please try again!\n\n");
			System.out.println("Exception [Upload : run()]");
			ex.printStackTrace();
		}
	}

}