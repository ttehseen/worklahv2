/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import sassibot.Answers;
import java.net.URL;
import java.util.ResourceBundle;
import client.Client;
import java.io.File;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.shape.Ellipse;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author student
 */
public class ChatController implements Initializable {

	@FXML
	private ListView<String> userList;

	@FXML
	public ListView<String> taskList;
	@FXML
	public TextArea chatView;
	@FXML
	private ImageView profileImage;
	@FXML
	private ImageView sendButton;
	@FXML
	private TextField chatBox;
	@FXML
	private Button sendButton2;
	@FXML
	private Button newChat;
	@FXML
	private CheckBox botCheckBox;
	@FXML
	private Hyperlink logOut;
	@FXML
	private Button attachButton2;
	@FXML
	private ImageView attachButton;
	@FXML
	protected TextField conversantName;
	@FXML
	private TextField userName;
	@FXML
	private ImageView newChatIcon;
	@FXML
	private DatePicker dateSelector;
	@FXML
	protected ImageView conversantImage;
	@FXML
	protected Ellipse greenCircle;
	@FXML
	private Button emojiButton;
	@FXML
	private MenuItem deletePressed;

	private Client client;

	protected String userID;

	Stage prevStage;
	private Scene scene;
	private Stage stage;
	public ArrayList<ArrayList<String>> conversations;
	String currentConversation;
	boolean sassiBot;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		userName.setText(userID);
	}    
	/**
	 * Initializes conversations.
	 */
	protected void initialiseConversations() {
		this.conversations = new ArrayList<ArrayList<String>>();
		this.sassiBot = false;
		this.currentConversation = "";
	}

	/**
	 * Setters for the Client and UserID.
	 * @param _client 
	 */
	protected void setClient(Client _client) {
		client = _client;
	}

	protected void setUserID(String _userID) {
		userName.setText(_userID);
	}

	/**
	 * Method that listens on a mouse click on the UsersList to open up the conversation in the text box.
	 * @param event 
	 */
	@FXML
	private void userClicked(MouseEvent event) {
		userList.getSelectionModel().getSelectedItem();
		String newConversation = userList.getSelectionModel().getSelectedItem();
		//		newConversation.replace("\u26AB", "");
		if (newConversation.equals(this.currentConversation)) {
			return;
		}
		if (!sassiBot) {
			conversantName.setText(userList.getSelectionModel().getSelectedItem());
			System.out.println(this.conversations);
			System.out.println(newConversation);
			chatView.setText("");
			for (ArrayList <String> conversation : this.conversations) {
				if (newConversation.equals(String.join(", ", conversation))) {
					client.updateGroup(conversation);
					System.out.println("UPDATE GROUP");
				}
			}
		}
		this.currentConversation = newConversation;
		if (!sassiBot) {
			conversantImage.setVisible(true);
			greenCircle.setVisible(true);
		}
	}



	/**
	 * Opens up a popup window that has references to the userList. It receives the list of users online
	 * and can be used to add them to the userlist for communication.
	 * @param event
	 * @throws IOException 
	 */   
	@FXML
	void newChatPressed(MouseEvent event) throws IOException {

		stage = new Stage();
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("Popup.fxml"));     
		AnchorPane frame = fxmlLoader.load();          
		PopupController controller = (PopupController)fxmlLoader.getController();
		controller.primaryUserList = userList;
		controller.setClient(this.client);
		controller.setChatController(this);

		client.getOnlineUsers();

		scene = new Scene(frame); 
		stage.setScene(scene);
		stage.setMinHeight(400);
		stage.setMinWidth(400);
		stage.setMaxHeight(400);
		stage.setMaxWidth(400);
		stage.show();

	}

	/**
	 * Listeners that send emojis.
	 * @param event 
	 */ 
	@FXML
	void emoji1selected(MouseEvent event) {
		String message="\u2615";
		client.sendMessageToGroup(message);
		append(message+"\n", client.username);
	}

	@FXML
	void emoji2selected(MouseEvent event) {
		String message="\u263A";
		client.sendMessageToGroup(message);
		append(message+"\n", client.username);
	}

	@FXML
	void emoji3selected(MouseEvent event) {
		String message="\u26C7";
		client.sendMessageToGroup(message);
		append(message+"\n", client.username);
	}

	@FXML
	void emoji4selected(MouseEvent event) {
		String message="\u2639";
		client.sendMessageToGroup(message);
		append(message+"\n", client.username);
	}     

	/**
	 * Listener for Sassibot. Checks if is the switch is on. If it is
	 * then it resets the ChatBox.
	 * @param event 
	 */
	@FXML
	void botChecked(MouseEvent event) {
		userList.setVisible(false);
		chatView.setText("");
		if (	sassiBot) {
			for (ArrayList <String> conversation : this.conversations) {
				if (this.currentConversation.equals(String.join(", ", conversation))) {
					client.updateGroup(conversation);
				}
			}
			sassiBot = false;
			userList.setVisible(true);
			return;
		}
		sassiBot = true;
		this.conversantName.setText("SASSIBOT");
		this.conversantImage.setVisible(true);
		this.greenCircle.setVisible(false);
	}

	/**
	 * Disconnects and exits the program.
	 * @param event 
	 */
	@FXML
	void logoutPressed(ActionEvent event) {
		client.goOffline();
		System.exit(0);
	}

	/**
	 * 
	 * @param Main
	 * @throws InterruptedException 
	 * Main listener for the class. Checks firstly if the sassibot switch is on. If it is, it talks to it. If not, it checks if the user wants
	 * to appoint a task. If it does, it will add the sent text to the task pane. If not, it adds it sends it over to the chat group selected.
	 */
	@FXML
	protected void sendPressed(MouseEvent event) throws InterruptedException {
		String message = chatBox.getText();

		if (botCheckBox.isSelected()){
			Answers sassiAnswer = new Answers();
			if (message.toLowerCase().contains("why")){
				append(message, client.username);
				append(sassiAnswer.getWhy(), "SASSIBOT");
			}
			else if (message.toLowerCase().contains("how")){
				append(message, client.username);
				append(sassiAnswer.getHow(), "SASSIBOT");
			}
			else if (message.toLowerCase().contains("what")){
				append(message, client.username);
				append(sassiAnswer.getWhat(), "SASSIBOT");}
			else {
				append(message, client.username);
				append("Nice to meet you, but not too nice.", "SASSIBOT");
			}
		}
		else{
			String catchPhrase = "@task ";
			if (message.contains(catchPhrase)) {
				String task = message.replace("@task ", "");
				String task_split[] = task.split(" ", 2);
				taskList.getItems().add("@" + task_split[0] + ": " + task_split[1] + "\n");
				chatBox.setText("");
				client.sendTaskToGroup(task_split[1], task_split[0]);
			}
			else{
				client.sendMessageToGroup(message);
				append(chatBox.getText(), client.username);
			}

			chatBox.setText("");

		}
	}
	/**
	 * Opens up a pop-up calendar that gets the date and appends it to the selected task.
	 * @param event 
	 */
	@FXML
	void dateSelected(ActionEvent event) {
		int selectedIndex = taskList.getSelectionModel().getSelectedIndex();
		String task = taskList.getSelectionModel().getSelectedItem();
		String selectedDate = dateSelector.getValue().toString();
		if (task.contains(" due by ")) {
			task = task.substring(0, task.lastIndexOf(" ")) + " " + selectedDate;

		} else {
			taskList.getItems().add(selectedIndex, task +" due by " + selectedDate );
			taskList.getItems().remove(selectedIndex + 1);
		}
		task = task.replace("@task ", "");
		String task_split[] = task.split(" ", 2);
		client.setTaskDeadline(task_split[1].replace("\n", ""), selectedDate);
	}

	/**
	 * Same as sendPressed above, in case the user wants to use the Enter Key.
	 * @param event
	 * @throws InterruptedException 
	 */	 
	@FXML
	private void enterPressedChat(ActionEvent event) throws InterruptedException {
		String message = chatBox.getText();
		if (botCheckBox.isSelected()){
			Answers sassiAnswer = new Answers();
			if (message.toLowerCase().contains("why")){
				append(message, client.username);
				append(sassiAnswer.getWhy(), "SASSIBOT");
			}
			else if (message.toLowerCase().contains("how")){
				append(message, client.username);
				append(sassiAnswer.getHow(), "SASSIBOT");
			}
			else if (message.toLowerCase().contains("what")){
				append(message, client.username);
				append(sassiAnswer.getWhat(), "SASSIBOT");
			}
			else {
				append(message, client.username);
				append("Nice to meet you, but not too nice.", "SASSIBOT");
			}
		} else {
			String catchPhrase = "@task ";
			if (message.contains(catchPhrase)) {
				String task = message.replace("@task ", "");
				String task_split[] = task.split(" ", 2);
				taskList.getItems().add("@" + task_split[0] + ": " + task_split[1] + "\n");
				chatBox.setText("");
				client.sendTaskToGroup(task_split[1], task_split[0]);
			}
			else {
				client.sendMessageToGroup(message);
				append(chatBox.getText(), client.username);
			}

			chatBox.setText("");

		}
	}
	/*
        Opens up a dialog box to attach files.

	 */	 
	@FXML
	String attachButtonPressed(MouseEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Attachment");
		fileChooser.showOpenDialog(stage);
		File file = fileChooser.showOpenDialog(stage);
		if (file != null) {
			String path = file.toString().substring(file.toString().lastIndexOf("/") + 1);
			this.append(client.username + " would like to share '" + path + "' with you!", client.username);
			System.out.println(file);
			client.uploadFile(file);
		}
		return null;
	}

	/**
	 * Append function to add text to the chat interface. Adds a time stamp every time.
	 * @param str 
	 */
	public void append(String str, String sender) {
		chatView.appendText(sender + ":" + "\n");
		chatView.appendText(str + "\n\n");
		chatView.selectPositionCaret(chatView.getText().length()-1);
		chatBox.setText("");
	}

	/**
	 * Function to populate userList with new users- individuals and groups. Checks if
	 * the user already exists. If it does, it updates it to the top of the file(signalling a new 
	 * message). If not, it adds it.
	 * @param _user 
	 */	 


	public void populateUserList(String _user) throws IllegalStateException {

		try {
			if (userList.getItems().contains(_user)) {
				System.out.println("USERS: " + userList.getItems());
				System.out.println("ADD: " + _user);
				userList.getItems().remove(_user);
				userList.getItems().add(0, _user);

			}
			else {
				userList.getItems().add(0, _user);
			}
		} catch (IllegalStateException e) {
		}
	}
	/**
	 * Method to populate the Tasks. Takes in a string and adds it to the string of tasks.
	 * @param _task 
	 */
	public void populateTaskList(String _task){
		taskList.getItems().add(_task);
	}
	/**
	 * Method to add to the conversations.
	 * @param _conversation 
	 */
	public void addConversation(ArrayList <String> _conversation) {
		for (ArrayList <String> conversation : this.conversations) {
			if (checkMembers(conversation, _conversation)) {
				return;
			}
		}
		conversations.add(_conversation);
		populateUserList(String.join(", ", _conversation));
	}
	/**
	 * Method to check the Members of a group.
	 * @param group1
	 * @param group2
	 * @return 
	 */	
	public boolean checkMembers(ArrayList <String> group1, ArrayList <String> group2) {
		Collections.sort(group1);
		Collections.sort(group2);
		return(group1.equals(group2));
	}

	/**
	 * Method that deletes a task by right-clicking and pressing on it.
	 * @param event 
	 */
	@FXML
	void deleteSelected(ActionEvent event) {
		int selectedIndex = taskList.getSelectionModel().getSelectedIndex();
		String task = taskList.getItems().get(selectedIndex);
		taskList.getSelectionModel().clearSelection();
		taskList.getItems().remove(task);
		chatView.setText("");
		taskList.getItems().removeAll();
		String task_split[] = task.split(" ", 2);
		String removeTask = task_split[1];
		if (task.contains("due by")) {
			String[] taskDate = task_split[1].split("\\s+");
			System.out.println(taskDate);
			String end = taskDate[taskDate.length - 3];
			System.out.println(end);
			removeTask = task_split[1].substring(0, task_split[1].indexOf(end)).trim();
			System.out.println(removeTask);

		}
		client.removeTask(removeTask.replace("\n", ""));
	}

}
