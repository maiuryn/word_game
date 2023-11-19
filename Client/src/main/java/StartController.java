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

public class StartController implements Initializable {
    @FXML
	private TextField port_input_text;

    @FXML
    private TextField host_input_text;
	
	@FXML
	private Button connect_button;

    @FXML
    private Text error_text;

	private Scene menuPage;
    private MenuController menuController;

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }

    public void setMenuPage(Scene menuPage) {
        this.menuPage = menuPage;
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

    private void limitHostInput() {
		// Force port input text to be IP address
		UnaryOperator<Change> filter = change -> {
			if (change.getControlNewText().matches("-|([0-9]|\\.)*"))
				return change;
			return null;
		};
		host_input_text.setTextFormatter(new TextFormatter<String>(filter));
	}

    public void connect(ActionEvent e) throws IOException{
        error_text.setVisible(false);
        try {
            menuController.initClient(host_input_text.getText(), Integer.parseInt(port_input_text.getText()));

            Stage primaryStage = (Stage)((Node)e.getSource()).getScene().getWindow();
            primaryStage.setScene(menuPage);
        } catch (Exception x) {
            x.printStackTrace();
            error_text.setVisible(true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
			limitPortInput();
            limitHostInput();
			
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
    }
}