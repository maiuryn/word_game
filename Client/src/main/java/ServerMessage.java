import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class ServerMessage implements Serializable {

    private boolean win;
    private int category;
    private ArrayList<Integer> guessesLeft;
    private ArrayList<Integer> attemptsLeft;
    private ArrayList<ArrayList<String>> correctChars;
    private ArrayList<Boolean> wins;
    private HashSet<String> alreadyGuessed;

    public ServerMessage() {
        init();
    }

    public void init() {
        this.win = false; 
        this.category = -1;
        this.guessesLeft = new ArrayList<>(3);
        for (int i = 0; i < 3; i++)
            guessesLeft.add(null);
        this.attemptsLeft = new ArrayList<>(3);
        for (int i = 0; i < 3; i++)
            attemptsLeft.add(null);
        this.wins = new ArrayList<>(3);
        for (int i = 0; i < 3; i++)
            wins.add(null);
        this.correctChars = new ArrayList<>(3);
        for (int i = 0; i < 3; i++)
            correctChars.add(null);
        this.alreadyGuessed = new HashSet<>();
    }

    public ArrayList<Integer> getAttemptsLeft() {
        return attemptsLeft;
    }

    public ArrayList<ArrayList<String>> getCorrectChars() {
        return correctChars;
    }       

    public ArrayList<Integer> getGuessesLeft() {
        return guessesLeft;
    }       

    public ArrayList<Boolean> getWins() {
        return wins;
    }

    public HashSet<String> getAlreadyGuessed() {
        return alreadyGuessed;
    }

    public boolean getWin() { 
        return win;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void setWin(boolean win) {
        this.win = win;
    }
}
