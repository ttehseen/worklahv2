package client;

import gui.GuiStart;
import messages.Message;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import gui.ChatController;

/*
 *			For GUI, if this helps. Its a fairly basic implementation of a FileChooser in Java FX.
 *
 *			import javafx.stage.FileChooser;
 * 
 *          openButton.setOnAction(
 *          new EventHandler<ActionEvent>() {
 *              @Override
 *              public void handle(final ActionEvent e){
 *              	File file =  fileChooser.showOpenDialog(gui);
 *              	if (file != null) {
 *              		openFile(file);
 *              	}
 *              }
 *			});

        	openMultipleButton.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                    List<File> list =
                        fileChooser.showOpenMultipleDialog(stage);
                    if (list != null) {
                        for (File file : list) {
                            openFile(file);
                        }
                    }
                }
            });
 * 
 */

public class Upload implements Runnable{

	private String ip;
	private int port;
	private Socket connection;
	public FileInputStream in;
	public OutputStream out;
	public File file;
	public ChatController gui;

	public Upload(String ip, int port, File filepath, ChatController screen){
		super();
		try {
			this.file = filepath; 
			this.gui = screen;
			this.connection = new Socket(InetAddress.getByName(ip), port);
			in = new FileInputStream(filepath);
			out = connection.getOutputStream();
		} 
		catch (Exception e) {
			System.out.println("Upload could not occur.");
		}
	}

	@Override
	public void run() {
		try {       
			byte[] buffer = new byte[1024];
			int count;

			while ((count = in.read(buffer)) >= 0){
				out.write(buffer, 0, count);
			}
			out.flush();

			Message msg = (Message) in.readObject();

			if (msg.type.equals("upload")) {
				this.displayMessage.append("A file has been uploaded by "+msg.sender+".");
			}

			if(in != null){ in.close(); }
			if(out != null){ out.close(); }
			if(connection != null){ connection.close(); }
		}
		catch (Exception ex) {
			System.out.println("Exception [Upload : run()]");
			ex.printStackTrace();
		}
	}

}