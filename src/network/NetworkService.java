package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import controller.ConsoleIO;
import model.Model;
import model.ShipManager;

public class NetworkService {
	
	private Model model;
	private ObjectInputStream inStream;
	private ObjectOutputStream outStream;
	
	public NetworkService(Model model, ObjectInputStream inStream, ObjectOutputStream outStream){
		this.model = model;
		this.inStream = inStream;
		this.outStream = outStream;
	}
	
	public void sendPlayerShips(){
		try {
			outStream.writeObject(model.getPlayerShips());
			ConsoleIO.write("Package sent");
			model.getPlayerShips().getGameField().printField();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void receiveEnemyShips(){
		Object object;
		try {
			object = inStream.readObject();
			model.setEnemyShips((ShipManager)object);
			ConsoleIO.write("Package received");
			model.getEnemyShips().getGameField().printField();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
