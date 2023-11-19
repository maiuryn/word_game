
import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GuiClient extends Application{	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		primaryStage.setTitle("Word Trivia");
		
		FXMLLoader startLoader = new FXMLLoader(getClass().getResource("/start.fxml"));
		Parent startRoot = startLoader.load();
		StartController startController = startLoader.getController(); 
		Scene startScene = new Scene(startRoot, 640, 480);
		
		FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/menu.fxml"));
		Parent menuRoot = menuLoader.load();
		MenuController menuController = menuLoader.getController(); 
		Scene menuScene = new Scene(menuRoot, 640, 480);
		
		startController.setMenuController(menuController);
		startController.setMenuPage(menuScene);


		
		primaryStage.setScene(startScene);
		primaryStage.show();
		
	}
	
	

}
