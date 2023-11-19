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
import javafx.scene.text.Text;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.util.StringConverter;

public class MenuController implements Initializable {
    
	private Client client;

	public void initClient(String hostAddress, int port) {
		System.out.println("Connecting to host... Address: " + hostAddress + " Port: " + port);
		this.client = new Client(data->{

		}, hostAddress, port);
		client.start();		
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