import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
    ArrayList<String> animals;
    ArrayList<String> foods;
    ArrayList<String> countries;

    public Game() {
        animals = loadFile("./animal_list.txt");
        foods = loadFile("./food_list.txt");
        countries = loadFile("countries_list.txt");
    }

    public void initClient(ClientInfo c) {
        updateWord(c, 0);
        updateWord(c, 1);
        updateWord(c, 2);

        ServerMessage m = c.getServerMessage();
        ArrayList<Integer> attemptsLeft = m.getAttemptsLeft();
        attemptsLeft.set(0, 3);
        attemptsLeft.set(1, 3);
        attemptsLeft.set(2, 3);
        ArrayList<Integer> guessesLeft = m.getGuessesLeft();
        guessesLeft.set(0, 6);
        guessesLeft.set(1, 6);
        guessesLeft.set(2, 6);
        ArrayList<Boolean> wins = m.getWins();
        wins.set(0, false);
        wins.set(1, false);
        wins.set(2, false);
    }

    private ArrayList<String> initialGuess(String s) {
        ArrayList<String> initGuess = new ArrayList<>();
        for (int i = 0; i < s.length(); i++)
            initGuess.add("*");
        return initGuess;
    }

    public void updateWord(ClientInfo c, int category) {
        ArrayList<String> list;
        
        category = category != -1 ? category : c.getServerMessage().getCategory();

        if (category == 0)
            list = animals;
        else if (category == 1)
            list = foods;
        else
            list = countries;
            
        String word = getWord(c, list);
        c.getWords().set(category, word);
        c.getServerMessage().getCorrectChars().set(category, initialGuess(word));
    } 

    // private ArrayList<String> createEmpty(String s) {
    //     ArrayList<String> res = new ArrayList<>();
    //     for (int i = 0; i < s.length(); i++) 
    //         res.add("*");
    //     return res;
    // }
 
    private String getWord(ClientInfo c, ArrayList<String> list) {
        Random r = new Random();
        int i = r.nextInt(0, list.size());
        String word = list.get(i);

        while (c.getServerMessage().getAlreadyGuessed().contains(word)) {
            i = r.nextInt(0, list.size());
            word = list.get(i);
        }

        ServerMessage m = c.getServerMessage();
        return word;
    }

    public void updateGuess(ArrayList<String> correctChars, String secretWord, String c) {
        for (int i = 0; i < secretWord.length(); i++) 
            if (secretWord.charAt(i) == c.charAt(0)) 
                correctChars.set(i, c);
    }

    public boolean checkWin(ArrayList<String> correctChars, String secretWord) {
        boolean matching = true;
        for (int i = 0; i < secretWord.length(); i++) {
            System.out.println(secretWord.charAt(i) + " " + correctChars.get(i).charAt(0));
            if (secretWord.charAt(i) != correctChars.get(i).charAt(0)) {
                matching = false;
            }
        }
        return matching;
    }

    public ClientInfo handleGuess(ClientInfo c, ClientGuess g) {
        try {
            ServerMessage m = c.getServerMessage();
            String guess = g.getGuessChar();
            int category = g.getCategory();
            String secretWord = c.getWords().get(category);
            ArrayList<String> correct = m.getCorrectChars().get(category);
            
            System.out.println("Guess: " + guess + " category: " + category + " secret word: " + secretWord);

            if (secretWord.contains(guess)) {
                // Update c to contain all of the positions that contain that character    
                updateGuess(correct, secretWord, guess);
                m.setWin(checkWin(correct, secretWord));
            }
            else { 
                m.getGuessesLeft().set(category, m.getGuessesLeft().get(category) - 1);
                if (m.getGuessesLeft().get(category) == 0) {
                    m.getAttemptsLeft().set(category, m.getAttemptsLeft().get(category) - 1);
                    m.getAlreadyGuessed().add(c.getWords().get(category));
                }
                    
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return c;
    }


    public ArrayList<String> loadFile(String path) {
		try {
			File file = new File(getClass().getResource(path).toURI());
			Scanner scanner = new Scanner(file);
            ArrayList<String> list = new ArrayList<>();
            
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine();
                list.add(word.toLowerCase());
            }

            scanner.close();
            return list;
		} catch (Exception e) {
            e.printStackTrace();
			System.out.println("Can't load file" + path);
            System.exit(1);
		}

        return null;
	}
	
    public ArrayList<String> getAnimals() {
        return animals;
    }

    public ArrayList<String> getCountries() {
        return countries;
    }

    public ArrayList<String> getFoods() {
        return foods;
    }
}
