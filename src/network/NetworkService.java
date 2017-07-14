package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import controller.ConsoleIO;
import model.Field;
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
			ConsoleIO.write("Game Field sent");
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
			ConsoleIO.write("Game Field received");
			model.getEnemyShips().getGameField().printField();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendHit(Field field){
		try {
			outStream.writeObject(field);
			ConsoleIO.write("Hit sent");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void receiveHit(){
		Object object;
		try {
			object = inStream.readObject();
			Field field = (Field)object;
			model.getPlayerShips().getGameField().getFieldAt(field.getXPos(), field.getYPos()).markAsHit();
			ConsoleIO.write("Game Field received");
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
