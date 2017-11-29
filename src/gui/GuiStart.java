/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *`
 * @author WorkLah
 */
public class GuiStart extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
		Pane myPane = (Pane) myLoader.load();
		LoginScreenController controller = (LoginScreenController) myLoader.getController();
		controller.setPrevStage(primaryStage);
		primaryStage.setMinHeight(483);
		primaryStage.setMinWidth(375);
		primaryStage.setMaxHeight(483);
		primaryStage.setMaxWidth(375);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		Scene myScene = new Scene(myPane);
		primaryStage.setScene(myScene);
		primaryStage.show();

	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
