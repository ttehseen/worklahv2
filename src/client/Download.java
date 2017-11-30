package client;

import gui.ChatController;
import java.io.*;
import java.net.*;

/**
 * Class initialised to download a file
 * @author Geoffrey
 */
public class Download implements Runnable {

	/**
	 * server socket to connect to for the uploader
	 */
	private ServerSocket server;
	/**
	 * connection that the uploader and downloader can connect to
	 */
	private Socket connection;
	/**
	 * directory to save the file to
	 */
	private String saveTo = "";
	/**
	 * input stream of bytes
	 */
	private InputStream in;
	/**
	 * output stream of bytes
	 */
	private FileOutputStream out;
	/**
	 * GUI controller to send messages about download and upload success
	 */
	private ChatController guiController;

	/**
	 * Instantiator to create a download thread
	 * @param saveTo file directory to save to
	 * @param gui GUI controller
	 */
	public Download(String saveTo, ChatController gui){
		try {
			server = new ServerSocket(8888);
			this.saveTo = saveTo;
			this.guiController = gui;
		} 
		catch (IOException ex) {
			System.out.println("An error occurred while attempting to download.");
		}
	}

	/* 
	 * Method called to run the thread
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			connection = server.accept();
			System.out.println("Download : "+ connection.getRemoteSocketAddress());
			in = connection.getInputStream();
			out = new FileOutputStream(saveTo);
			BufferedOutputStream bufferedOut = new BufferedOutputStream(out);
			byte[] buffer = new byte[16384];
			int count;
			while((count = in.read(buffer)) >= 0) {
				bufferedOut.write(buffer, 0, count);
			}
			bufferedOut.flush();
			guiController.chatView.appendText("........\nDownload Complete. Saved to current working directory.\n\n");

			if (out != null) { 
				out.close(); 
			}
			if (in != null) { 
				in.close(); 
			}
			if (connection != null) {
				connection.close(); 
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
			guiController.chatView.appendText("........\nDownload Failure. Please try again!\n\n");
			System.out.println("An error occured while running the download.\n");
		}
	}
}



