package client;

import gui.ChatController;
import java.io.*;
import java.net.*;

public class Download implements Runnable{
	    
	    private ServerSocket server;
	    private Socket connection;
	    private int port;
	    private String saveTo = "";
	    private InputStream in;
	    private FileOutputStream out;
	    private ChatController guiController;
	    
	    public Download(String saveTo, ChatController gui){
	        try {
	            server = new ServerSocket(0);
	            port = server.getLocalPort();
	            this.saveTo = saveTo;
	            this.guiController = gui;
	        } 
	        catch (IOException ex) {
	            System.out.println("An error occurred while attempting to download.");
	        }
	    }

	    @Override
	    public void run() {
	        try {
	            connection = server.accept();
	            System.out.println("Download : "+ connection.getRemoteSocketAddress());
	            
	            in = connection.getInputStream();
	            out = new FileOutputStream(saveTo);
	            
	            byte[] buffer = new byte[1024];
	            int count;
	            
	            while((count = in.read(buffer)) >= 0){
	                out.write(buffer, 0, count);
	            }
	            
	            out.flush();
	            
	            guiController.taskList.getItems().add("The download is complete.\n");
	            
	            if(out != null){ out.close(); }
	            if(in != null){ in.close(); }
	            if(connection != null){ connection.close(); }
	        } 
	        catch (Exception e) {
	            System.out.println("An error occured while running the download.\n");
	        }
	    }
	}



