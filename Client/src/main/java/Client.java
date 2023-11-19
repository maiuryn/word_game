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

	Client(Consumer<Serializable> call, String hostAddress, int port){
		this.hostAddress = hostAddress;
		this.port = port;
		callback = call;
	}
	
	public void run() {
		
		try {
			
			socketClient= new Socket(hostAddress, port);
			out = new ObjectOutputStream(socketClient.getOutputStream());
			in = new ObjectInputStream(socketClient.getInputStream());
			socketClient.setTcpNoDelay(true);
		}
		catch(Exception e) {}
		
		while(true) {
			 
			try {
				String message = in.readObject().toString();
				callback.accept(message);
			}
			catch(Exception e) {}
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


}
