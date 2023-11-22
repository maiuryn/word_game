
import java.io.IOException;
import java.util.HashMap;
import java.util.function.UnaryOperator;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.converter.IntegerStringConverter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class GuiServer extends Application{
	private TextField port_input_text;
	private Button server_start_button;
	private ListView<String> server_log;

	private Server server;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {		
		try {
			FXMLLoader startLoader = new FXMLLoader(getClass().getResource("/server_start.fxml"));
			Parent root1 = startLoader.load();
			ServerStartController startController = startLoader.getController(); 
			Scene scene1 = new Scene(root1, 640, 480);

			FXMLLoader logLoader = new FXMLLoader(getClass().getResource("/server_log.fxml"));
			Parent root2 = logLoader.load();
			ServerLogController logController = logLoader.getController(); 
			Scene scene2 = new Scene(root2, 640, 480);
			
			startController.setLogPage(scene2);
			startController.setLogController(logController);
			
			logController.setStartPage(scene1);
			logController.setStartController(startController);

			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent t) {
					Platform.exit();
					System.exit(0);
				}
			});
			
			primaryStage.setScene(scene1);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
