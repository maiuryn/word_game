import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.GridPane;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;

public class GameController implements Initializable {

    @FXML
    private Text category_text;

    @FXML 
    private HBox guess_box;

    @FXML
    private GridPane letters_gp;

    @FXML
    private VBox guess_letter;

    private Client client;
    private Scene menuPage;
    private MenuController menuController;

    private int category;
    private String categoryString;

    private BorderPane losePage;
    private BorderPane loseAllPage;
    private BorderPane winPage;
    private BorderPane winAllPage;

    private Scene loseScene;
    private Scene loseAllScene;
    private Scene winScene;
    private Scene winAllScene;

    private Stage primaryStage;

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }

    public void setMenuPage(Scene menuPage) {
        this.menuPage = menuPage;
    }

    private BorderPane loadPage(String url) {
        BorderPane bp = new BorderPane(); 
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
            bp = loader.load();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bp;
    }

    private VBox generateLetter() {
        VBox letter = new VBox();
        try {
            FXMLLoader letterLoader = new FXMLLoader(getClass().getResource("/letter.fxml"));
            letter = letterLoader.load();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return letter;
    }

    public void setClient(Client c) {
        this.client = c;
    }

    public Client getClient() {
		return this.client;
	}

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


    public Consumer<Serializable> getCallback() {
        // Update the words on the screen
        return data -> {Platform.runLater(() -> {
            try {
                ServerMessage m = (ServerMessage)data;
                ArrayList<String> guessedCharacters = m.getCorrectChars().get(category);
                PauseTransition pause = new PauseTransition(Duration.seconds(3));

                System.out.println(m.getWins().toString());
                System.out.println(m.getAttemptsLeft().toString());
                System.out.println(m.getCategory());
                System.out.println(m.getWin());
                System.out.println(m.getAlreadyGuessed().toString());
                System.out.println(m.getGuessesLeft().toString());
                

                updateCharacters(guessedCharacters);
                if (m.getWins().get(0) && m.getWins().get(1) && m.getWins().get(2)) {
                    // update to win all screen
                    menuController.resetHearts();
                    menuController.enableCategories();
                    pause.setOnFinished(null);
                    primaryStage.setScene(winAllScene);
                    return;
                }
                else if (m.getWin()) {
                    // update to win screen
                    System.out.println("bruh");
                    menuController.disableCategory(category);
                    primaryStage.setScene(winScene);
                    pause.setOnFinished(e -> {
                        primaryStage.setScene(menuPage);
                    });
                    pause.play();
                    return;
                }
                else if (m.getAttemptsLeft().get(0) == 0 && m.getAttemptsLeft().get(1) == 0 && m.getAttemptsLeft().get(2) == 0) {
                    // complete loss, show reset screen
                    menuController.resetHearts();
                    menuController.enableCategories();
                    pause.setOnFinished(null);
                    primaryStage.setScene(loseAllScene);
                    return;
                }
                else if (m.getAttemptsLeft().get(category) == 0) {
                    // run out of attempts, disable category in menu, show loss
                    menuController.removeHeart(category);
                    menuController.disableCategory(category);
                    primaryStage.setScene(loseScene);
                    pause.setOnFinished(e -> {
                        primaryStage.setScene(menuPage);
                    });
                    pause.play();
                    return;
                }
                else if (m.getGuessesLeft().get(category) == 0) {
                    // out of guesses, show loss
                    menuController.removeHeart(category);
                    primaryStage.setScene(loseScene);
                    pause.setOnFinished(e -> {
                        primaryStage.setScene(menuPage);
                    });
                    pause.play();
                    return;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });};
    }

    private void updateCharacters(ArrayList<String> guessedCharacters) {
        guess_box.getChildren().clear();
            
        for (int i = 0; i < guessedCharacters.size(); i++) 
            guess_box.getChildren().add(generateLetter());

        for (int i = 0; i < guessedCharacters.size(); i++) {
            if (guessedCharacters.get(i).charAt(0) != '*') {
                ((Text)(((VBox)guess_box.getChildren().get(i)).getChildren().get(0))).setText(guessedCharacters.get(i).toUpperCase());
            }
        }
    }

    public void init(String category) {
        this.losePage = loadPage("/lose.fxml");
        this.loseAllPage = loadPage("lose_all.fxml");
        this.winPage = loadPage("/win.fxml");
        this.winAllPage = loadPage("/win_all.fxml");
        this.loseScene = new Scene(losePage, 640, 480);
        this.loseAllScene = new Scene(loseAllPage, 640, 480);
        this.winScene = new Scene(winPage, 640, 480);
        this.winAllScene = new Scene(winAllPage, 640, 480);

        ((Button)loseAllPage.lookup("#lose_button")).setOnAction(e -> {
            Stage primaryStage = (Stage)((Node)e.getSource()).getScene().getWindow();
            primaryStage.setScene(menuPage);
        });
        ((Button)winAllPage.lookup("#win_button")).setOnAction(e -> {
            Stage primaryStage = (Stage)((Node)e.getSource()).getScene().getWindow();
            primaryStage.setScene(menuPage);
        });
        ((Button)loseAllPage.lookup("#quit_button")).setOnAction(e -> {
            Platform.exit();
            System.exit(0);
        });
        ((Button)winAllPage.lookup("#quit_button")).setOnAction(e -> {
            Platform.exit();
            System.exit(0);
        });

        undisableButtons();
        category_text.setText(category);
        this.categoryString = category;

        if (category == "Animals")
            this.category = 0;
        if (category == "Foods")
            this.category = 1;
        if (category == "Countries")
            this.category = 2;

        client.setCategory(this.category);
        client.setCategoryString(this.categoryString);
        client.send(Integer.valueOf(this.category));
    }

    public void sendCharacter(String c) {
        ClientGuess guess = new ClientGuess(c, category, categoryString);
        client.send(guess);
    }

    
    public void connect(ActionEvent e) throws IOException{
        try {

            Stage primaryStage = (Stage)((Node)e.getSource()).getScene().getWindow();
            primaryStage.setScene(menuPage);
        } catch (Exception x) {
            x.printStackTrace();
            
        }
    }

    private void undisableButtons() {
        for (Node b : letters_gp.getChildren())
            ((Button)b).setDisable(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
			 
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
    }

    @FXML
    private Button a_button;

    @FXML
    private Button b_button;

    @FXML
    private Button c_button;

    @FXML
    private Button d_button;

    @FXML
    private Button e_button;

    @FXML
    private Button f_button;

    @FXML
    private Button g_button;
    
    @FXML
    private Button h_button;

    @FXML
    private Button i_button;

    @FXML
    private Button j_button;

    @FXML
    private Button k_button;

    @FXML
    private Button l_button;

    @FXML
    private Button m_button;

    @FXML
    private Button n_button;

    @FXML
    private Button o_button;

    @FXML
    private Button p_button;

    @FXML
    private Button q_button;
    
    @FXML
    private Button r_button;

    @FXML
    private Button s_button;

    @FXML
    private Button t_button;

    @FXML
    private Button u_button;

    @FXML
    private Button v_button;

    @FXML
    private Button w_button;

    @FXML
    private Button x_button;

    @FXML
    private Button y_button;

    @FXML
    private Button z_button;

    public void a_button_press(ActionEvent e) throws IOException {
        a_button.setDisable(true);
        sendCharacter("a");
    }

    public void b_button_press(ActionEvent e) throws IOException {
        b_button.setDisable(true);
        sendCharacter("b");
    }

    public void c_button_press(ActionEvent e) throws IOException {
        c_button.setDisable(true);
        sendCharacter("c");
    }

    public void d_button_press(ActionEvent e) throws IOException {
        d_button.setDisable(true);
        sendCharacter("d");
    }

    public void e_button_press(ActionEvent e) throws IOException {
        e_button.setDisable(true);
        sendCharacter("e");
    }

    public void f_button_press(ActionEvent e) throws IOException {
        f_button.setDisable(true);
        sendCharacter("f");
    }

    public void g_button_press(ActionEvent e) throws IOException {
        g_button.setDisable(true);
        sendCharacter("g");
    }

    public void h_button_press(ActionEvent e) throws IOException {
        h_button.setDisable(true);
        sendCharacter("h");
    }

    public void i_button_press(ActionEvent e) throws IOException {
        i_button.setDisable(true);
        sendCharacter("i");
    }

    public void j_button_press(ActionEvent e) throws IOException {
        j_button.setDisable(true);
        sendCharacter("j");
    }

    public void k_button_press(ActionEvent e) throws IOException {
        k_button.setDisable(true);
        sendCharacter("k");
    }

    public void l_button_press(ActionEvent e) throws IOException {
        l_button.setDisable(true);
        sendCharacter("l");
    }

    public void m_button_press(ActionEvent e) throws IOException {
        m_button.setDisable(true);
        sendCharacter("m");
    }

    public void n_button_press(ActionEvent e) throws IOException {
        n_button.setDisable(true);
        sendCharacter("n");
    }

    public void o_button_press(ActionEvent e) throws IOException {
        o_button.setDisable(true);
        sendCharacter("o");
    }

    public void p_button_press(ActionEvent e) throws IOException {
        p_button.setDisable(true);
        sendCharacter("p");
    }

    public void q_button_press(ActionEvent e) throws IOException {
        q_button.setDisable(true);
        sendCharacter("q");
    }

    public void r_button_press(ActionEvent e) throws IOException {
        r_button.setDisable(true);
        sendCharacter("r");
    }

    public void s_button_press(ActionEvent e) throws IOException {
        s_button.setDisable(true);
        sendCharacter("s");
    }

    public void t_button_press(ActionEvent e) throws IOException {
        t_button.setDisable(true);
        sendCharacter("t");
    }

    public void u_button_press(ActionEvent e) throws IOException {
        u_button.setDisable(true);
        sendCharacter("u");
    }

    public void v_button_press(ActionEvent e) throws IOException {
        v_button.setDisable(true);
        sendCharacter("v");
    }

    public void w_button_press(ActionEvent e) throws IOException {
        w_button.setDisable(true);
        sendCharacter("w");
    }

    public void x_button_press(ActionEvent e) throws IOException {
        x_button.setDisable(true);
        sendCharacter("x");
    }

    public void y_button_press(ActionEvent e) throws IOException {
        y_button.setDisable(true);
        sendCharacter("y");
    }

    public void z_button_press(ActionEvent e) throws IOException {
        z_button.setDisable(true);
        sendCharacter("z");
    }
}
