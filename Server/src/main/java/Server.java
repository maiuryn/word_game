import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.scene.control.ListView;

import java.io.File;
import java.util.Scanner; 

public class Server{

	int count = 1;	
	ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
	int port;
	TheServer server;
	private Consumer<Serializable> callback;
	Game game;
	
	Server(Consumer<Serializable> call, int port){
		game = new Game();
		callback = call;
		server = new TheServer(port);
		server.start();
	}

	
	
	public class TheServer extends Thread{
		
		public int port;

		public TheServer(int port) {
			this.port = port;
		}

		public void run() {
		
			try(ServerSocket mysocket = new ServerSocket(port);){
		    System.out.println("Server is waiting for a client!");
			System.out.println("Server is listening on port: " + port + " address: " + mysocket.getInetAddress().getHostAddress());
			
		    	while(true) {
		
					ClientThread c = new ClientThread(mysocket.accept(), count);
					System.out.println("Client Connected " + count);
					callback.accept("Client has connected to server: " + "client id: " + count);
					clients.add(c);
					c.start();
					
					count++;
				
			    }
			}//end of try
				catch(Exception e) {
					callback.accept("Server socket did not launch");
				}
			}//end of while
		}
	
		// One thread per client
		// Handles game logic and responses in back and forth manner through Game obj
		class ClientThread extends Thread{
			
			ClientInfo c;
			Socket connection;
			int count;
			ObjectInputStream in;
			ObjectOutputStream out;
			
			ClientThread(Socket s, int count){
				this.c = new ClientInfo(count);
				this.connection = s;
				this.count = count;	
			}
			
			// public void updateClients(String message) {
			// 	for(int i = 0; i < clients.size(); i++) {
			// 		ClientThread t = clients.get(i);
			// 		try {
			// 			t.out.writeObject(message);
			// 		}
			// 		catch(Exception e) {}
			// 	}
			// }
			
			public void run(){
					
				try {
					in = new ObjectInputStream(connection.getInputStream());
					out = new ObjectOutputStream(connection.getOutputStream());
					connection.setTcpNoDelay(true);	
				}
				catch(Exception e) {
					System.out.println("Streams not open");
				}

				try {
					game.initClient(c);
				}
				catch(Exception e) {
					System.out.println("Error setting up game");
					e.printStackTrace();
				}
				
				// updateClients("new client on server: client #"+count);
					
				while(true) {
					    try {
							// Accept an integer for the category
							Integer category = (Integer)in.readObject();
							callback.accept("Client: " + count + " has started a game for category: " + category + " secret word is: " + c.getWords().get(category));
							
							// Setup the game for that category
							c.getServerMessage().setCategory(category);
							int currentGuesses = 6;
							boolean win = c.getServerMessage().getWin();
							out.reset();
							out.writeObject(c.getServerMessage());

							// Repeat until no more guesses or win
							while (currentGuesses > 0 && !win) {
					    		ClientGuess data = (ClientGuess)in.readObject();
								callback.accept("Client: " + count + " guesses: " + data.getGuessChar() + " for category: " + data.getCategoryString());
								
								c = game.handleGuess(c, data);
								System.out.println(c.getServerMessage().getCorrectChars().get(category));
								currentGuesses = c.getServerMessage().getGuessesLeft().get(category);
								win = c.getServerMessage().getWin();
								System.out.println(c.getServerMessage().getGuessesLeft().get(category));
								out.reset();
								out.writeObject(c.getServerMessage());
								callback.accept("Responded to client " + count);
							}

							boolean reset = true;
							for (Integer n : c.getServerMessage().getAttemptsLeft())
								if (n != 0)
									reset = false;

							if (reset) {
								callback.accept("Finished a game with client " + count + ", see you next time!");
								game.initClient(c);
							}
							else {
								callback.accept("Finished a round with client " + count + ", on to next round!");
								if (win) {
									callback.accept("Client " + count + " won on word " + c.getWords().get(category) + " attempts left: " + c.getServerMessage().getAttemptsLeft().get(category));
									c.getServerMessage().getWins().set(category, true);
									c.getServerMessage().getAttemptsLeft().set(category, 0);
								}
								else {
									callback.accept("Client " + count + " lost on word " + c.getWords().get(category) + " attempts left: " + c.getServerMessage().getAttemptsLeft().get(category));
								}
								game.updateWord(c, category);
								c.getServerMessage().setWin(false);
								c.getServerMessage().getGuessesLeft().set(category, 6);
							}
							
							ServerMessage m = c.getServerMessage();
							if ((m.getWins().get(0) && m.getWins().get(1) && m.getWins().get(2)) || (m.getAttemptsLeft().get(0) == 0 && m.getAttemptsLeft().get(1) == 0 && m.getAttemptsLeft().get(2) == 0)) {
								out.reset();
								out.writeObject(c.getServerMessage());
							}
						}
					    catch(Exception e) {
					    	callback.accept("Client " + count + " disconnected.");
					    	// updateClients("Client #"+count+" has left the server!");
					    	clients.remove(this);
					    	break;
					    }
					}
				}//end of run		
			
		}//end of client thread
}


	
	

	
