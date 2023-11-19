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
import javafx.scene.Node;
import javafx.scene.text.Text;

public class ServerStartController implements Initializable {
    @FXML
	private TextField port_input_text;
	
	@FXML
	private Button server_start_button;

	@FXML
	private ListView<String> server_log;

    @FXML
    private Text error_text;

	private Scene logPage;
    private ServerLogController serverLogController;

    public void setLogController(ServerLogController serverLogController) {
        this.serverLogController = serverLogController;
    }

    private void limitPortInput() {
		// Force port input text to be numeric
		UnaryOperator<Change> filter = change -> {
			if (change.getControlNewText().matches("-|[0-9]{0,5}"))
				return change;
			return null;
		};
		port_input_text.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 5555, filter));
	}

    public void setLogPage(Scene logPage) {
        this.logPage = logPage;
    }
    
    public void startServer(ActionEvent e) throws IOException{
        error_text.setVisible(false);
        try {
            serverLogController.startServer(Integer.parseInt(port_input_text.getText()));

            Stage primaryStage = (Stage)((Node)e.getSource()).getScene().getWindow();
            primaryStage.setScene(logPage);
        } catch (Exception x) {
            x.printStackTrace();
            error_text.setVisible(true);
        }
        
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
			limitPortInput();
			
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
    }
}