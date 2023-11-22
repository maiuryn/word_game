import java.io.Serializable;
import java.util.ArrayList;

public class ClientGuess implements Serializable{
    private String guessChar;
    private int category;
    private String categoryString;

    public ClientGuess(String guessChar, int category, String categoryString) {
        this.guessChar = guessChar;
        this.category = category;
        this.categoryString = categoryString;
    }

    public String getGuessChar() {
        return guessChar;
    }

    public int getCategory() {
        return category;
    }

    public String getCategoryString() {
        return categoryString;
    }
}
