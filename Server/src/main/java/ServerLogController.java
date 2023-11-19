import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.IntegerStringConverter;
import javafx.stage.Stage;

public class ServerLogController implements Initializable {
	@FXML
	private ListView<String> server_log;

	private Scene menuPage;
	private ServerStartController serverStartController;
	public Server server;


    public void setStartPage(Scene menuPage) {
        this.menuPage = menuPage;
    }

	public void setStartController(ServerStartController serverStartController) {
		this.serverStartController = serverStartController;
	}	
    
    public void startServer(int port) {
        server = new Server(data -> {
			Platform.runLater(()->{
				server_log.getItems().add(data.toString());
			});
		}, port);

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
			

			
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
    }
}