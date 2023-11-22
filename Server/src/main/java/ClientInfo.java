import java.util.ArrayList;

public class ClientInfo {
    private ServerMessage s;
    private int id;
    private ArrayList<String> words;

    public ClientInfo(int id) {
        init(id);
    }

    public void init(int id) {
        this.s = new ServerMessage();
        this.id = id;
        this.words = new ArrayList<>(3);
        for (int i = 0; i < 3; i++)
            words.add(null);
    }

    public ServerMessage getServerMessage() {
        return s;
    }

    public int getId() {
        return id;
    }

    public ArrayList<String> getWords() {
        return words;
    }
}
