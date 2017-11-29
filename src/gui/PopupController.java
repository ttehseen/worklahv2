/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import client.Client;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author student
 */
public class PopupController implements Initializable {
    
  @FXML
    private ListView<String> onlineList;
  @FXML
    private Button button2;
    public Client client;
    public ListView<String> primaryUserList;
    private Stage prevStage;
  @FXML
    void onlineUserSelected(MouseEvent event) {
//    chatController.populateUserList("taha");
    }
  @FXML
    void button2Listener(ActionEvent event) {
        
        List<String> showing = onlineList.getSelectionModel().getSelectedItems();
        String listString = String.join(", ", showing);
        primaryUserList.getItems().add(listString);
        client.updateGroup(new ArrayList<String>(showing));
        client.addConversation(new ArrayList<String>(showing));
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();


    }
    
    @FXML
    void enterPressed(KeyEvent event) {
        if(event.getCode()==KeyCode.ENTER){
             List<String> showing = onlineList.getSelectionModel().getSelectedItems();
        String listString = String.join(", ", showing);
        primaryUserList.getItems().add(listString);
        client.updateGroup(new ArrayList<String>(showing));
        client.addConversation(new ArrayList<String>(showing));
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        }
    }
  
  	void setClient(Client _client) {
  		this.client = _client;
  	    this.client.setPopupController(this);
  	}
  
//  @FXML
//    void addRandomElement(ActionEvent event) {
//        Random rand = new Random ();
//    onlineList.getItems().add(Integer.toString(rand.nextInt(50)));
//
//    }
  
  public void addUserElement(String _user) {
	  onlineList.getItems().add(_user);

  }
  
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     onlineList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
    
    void setPrevStage(Stage stage) {
		this.prevStage = stage;

	}
 
}
