import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;



public class Client extends Thread{

	
	Socket socketClient;
	
	ObjectOutputStream out;
	ObjectInputStream in;
	
	private Consumer<Serializable> callback;

	private String hostAddress;
	private int port;

	private int category;
	private String categoryString;

	// Callback for receiving guess string
	// Callback for sending letter guess

	Client(Consumer<Serializable> call, String hostAddress, int port){
		this.hostAddress = hostAddress;
		this.port = port;
		callback = call;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public void setCategoryString(String categoryString) {
		this.categoryString = categoryString;
	}
	
	public void run() {
		
		try {
			
			socketClient= new Socket(hostAddress, port);
			out = new ObjectOutputStream(socketClient.getOutputStream());
			in = new ObjectInputStream(socketClient.getInputStream());
			socketClient.setTcpNoDelay(true);
		}
		catch(Exception e) {
			System.out.println("Problem with connecting to the server.");
			System.exit(1);
		}
		
		while(true) {
			 
			try {
				ServerMessage info = (ServerMessage)in.readObject();
				System.out.println(info.getCorrectChars().get(category).toString());
				callback.accept(info);
			}
			catch(Exception e) {
				e.printStackTrace();

			}
		}
	
    }
	
	public void send(ClientGuess data) {
		
		try {
			out.writeObject(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void send(String data) {
		
		try {
			out.writeObject(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void send(Integer data) {
		
		try {
			out.writeObject(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// need a serializable class to receive info
	// word length
	// remaining guesses
	// categories and words already guessed
	// winning/losing
	// setup: wait until class comes in
	// wait for guess, then send
	// wait for response, then update screen

	// word bank max length 12
}
