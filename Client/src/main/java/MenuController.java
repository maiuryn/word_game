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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.util.StringConverter;

public class MenuController implements Initializable {
    @FXML
	private HBox animals_heartsbox;
	
	@FXML
	private VBox animals_vbox;

	@FXML
	private Button animals_button;

	@FXML
	private HBox foods_heartsbox;

	@FXML
	private VBox foods_vbox;

	@FXML
	private Button foods_button;

	@FXML
	private HBox countries_heartsbox;
	
	@FXML
	private VBox countries_vbox;

	@FXML
	private Button countries_button;

	private Client client;
	private Scene gamePage;
    private GameController gameController;

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setGamePage(Scene gamePage) {
        this.gamePage = gamePage;
    }

	public void initClient(String hostAddress, int port) {
		System.out.println("Connecting to host... Address: " + hostAddress + " Port: " + port);
		this.client = new Client(gameController.getCallback(), hostAddress, port);
		client.start();		
	}

	public Client getClient() {
		return this.client;
	}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
			
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
    }

	public void startAnimals(ActionEvent e) throws IOException {
		startGame("Animals");
        Stage primaryStage = (Stage)((Node)e.getSource()).getScene().getWindow();
		primaryStage.setScene(gamePage);
	}  

	public void startFoods(ActionEvent e) throws IOException {
		startGame("Foods");
        Stage primaryStage = (Stage)((Node)e.getSource()).getScene().getWindow();
		primaryStage.setScene(gamePage);
	}  

	public void startCountries(ActionEvent e) throws IOException {
		startGame("Countries");
        Stage primaryStage = (Stage)((Node)e.getSource()).getScene().getWindow();
		primaryStage.setScene(gamePage);
	}  

	public void startGame(String category) {
        try {
			gameController.setClient(client);
			gameController.init(category);
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

	public void disableCategory(int category) {
		if (category == 0)
			animals_vbox.setDisable(true);
		if (category == 1)
			foods_vbox.setDisable(true);
		if (category == 2)
			countries_vbox.setDisable(true);
	}

	public void enableCategories() {
		animals_vbox.setDisable(false);
		foods_vbox.setDisable(false);
		countries_vbox.setDisable(false);
	}

	private ImageView generateBlackHeart() {
		Image heart = new Image(getClass().getResource("./black_heart.png").toString());
		ImageView view = new ImageView();
		view.setImage(heart);
		return view;
	}

	private ImageView generateRedHeart() {
		Image heart = new Image(getClass().getResource("./red_heart.png").toString());
		ImageView view = new ImageView();
		view.setImage(heart);
		return view;
	}

	public void removeHeart(int category) {
		if (category == 0) {
			animals_heartsbox.getChildren().remove(0);
			animals_heartsbox.getChildren().add(generateBlackHeart());
		}
		if (category == 1) {
			foods_heartsbox.getChildren().remove(0);
			foods_heartsbox.getChildren().add(generateBlackHeart());
		}
		if (category == 2) {
			countries_heartsbox.getChildren().remove(0);
			countries_heartsbox.getChildren().add(generateBlackHeart());
		}
	}

	public void resetHearts() {
		animals_heartsbox.getChildren().clear();
		for (int i = 0; i < 3; i++)
			animals_heartsbox.getChildren().add(generateRedHeart());
		foods_heartsbox.getChildren().clear();
		for (int i = 0; i < 3; i++)
			foods_heartsbox.getChildren().add(generateRedHeart());
		countries_heartsbox.getChildren().clear();
		for (int i = 0; i < 3; i++)
			countries_heartsbox.getChildren().add(generateRedHeart());
	}
}