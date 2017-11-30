/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author student
 */
public class LoginScreenController implements Initializable {

	@FXML
	private TextField Username;

	@FXML
	private Button loginButton;

	@FXML
	private PasswordField passwordField;    

	protected Client client;
	protected String userID;
	protected String password;
	private Stage stage;
	private Scene scene;
	private ChatController chatController;
	public TextField primaryUserName;
	Stage prevStage;
	
	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}    
/**
 * Login sequence. Loads the chat page for the app. Also contains setters for client and username that 
 * is used to transfer these two objects to the chat page.
 * @param event
 * @throws IOException
 * @throws ClassNotFoundException 
 */
	@FXML
	private void loginPressed(ActionEvent event) throws IOException, ClassNotFoundException {
		password= passwordField.getText();
		userID = Username.getText().trim();
		if ((userID.length() == 0)||(password.length()==0)) {
			return;
		}
		stage = new Stage();

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ChatScreen.fxml"));     
		Parent root = (Parent) fxmlLoader.load();          
		ChatController controller = fxmlLoader.<ChatController>getController();
		controller.initialiseConversations();

		int port = 8080;
		String server = "127.0.0.1";
		client = new Client(server, port, controller, userID, password);
		client.start();

		controller.setClient(client);
		controller.setUserID(userID);

		scene = new Scene(root); 
		stage.setScene(scene);
		chatController = new ChatController();
		stage.setMinHeight(600);
		stage.setMinWidth(1300);
		stage.show();
		Stage stage1 = (Stage) prevStage.getScene().getWindow();
		stage1.close();

	}

/**
 * Sets previous stage that can be closed by the GUI.
 * @param stage 
 */
	void setPrevStage(Stage stage) {
		this.prevStage = stage;

	}

	

}
