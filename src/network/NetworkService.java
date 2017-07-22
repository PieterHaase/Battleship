package network;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import controller.ConsoleIO;
import controller.Controller;
import model.Field;
import model.Model;
import model.ShipManager;
import view.View;

public class NetworkService {
	
	private Model model;
	private View view;
	private Controller controller;
	private ObjectInputStream inStream;
	private ObjectOutputStream outStream;
	
	public NetworkService(Controller controller, ObjectInputStream inStream, ObjectOutputStream outStream){
		this.controller = controller;
		model = controller.getModel();
		view = controller.getView();
		this.inStream = inStream;
		this.outStream = outStream;
	}
	
	public void sendPlayerShips(){
		try {
			outStream.writeObject(model.getPlayerShips());
			ConsoleIO.write("Game Field sent");
			model.getPlayerShips().getGameField().printField();
			if(!controller.enemyShipsReceived())
				view.displayPrompt("Wait for " + model.getEnemyName() + " to place his/her ships...");
			else
				if(!controller.getPlayersTurn())
					view.displayPrompt("Wait for " + model.getEnemyName() + "'s turn...");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendEnemyShips(){
		try {
			outStream.writeObject(model.getEnemyShips());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean receiveEnemyShips(){
		Object object;
		try {
			object = inStream.readObject();
			model.getEnemyShips().getGameField().clear();
			model.setEnemyShips((ShipManager)object);
			ConsoleIO.write("Game Field received");
			model.getEnemyShips().getGameField().printField();
			return true;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public void sendHit(Field field){
		try {
			outStream.writeObject(field);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean receive(){
		Object object;
		try {
			object = inStream.readObject();
			String string = "";
			Field field = new Field(0, 0, "");
			Message message = new Message("","");
			
			if(object.getClass().equals(string.getClass())){
				string = (String)object;
				if(string.equals("~NEWGAME")){
					ShipManager shipManager = (ShipManager)inStream.readObject();
					model.setPlayerShips(shipManager);
					controller.setAllShipsPlaced(true);
					controller.newGame();					
				}
				else{
					model.setEnemyName(string);
					view.displayMessage("You are playing against " + model.getEnemyName());
				}
			}
			
			if(object.getClass().equals(ShipManager.class)){
				ShipManager shipManager = (ShipManager)object;
//				model.getEnemyShips().getGameField().clear();
				model.setEnemyShips(shipManager);
				controller.setEnemyShipsReceived(true);
				controller.multiplayerGame();
			}
			
			if(object.getClass().equals(field.getClass())){
				field = (Field)object;
				model.getPlayerShips().getGameField().getFieldAt(field.getXPos(), field.getYPos()).markAsHit();
				model.update();
				model.getPlayerShips().getGameField().printField();
				controller.setPlayersTurn(true);
			}
			if(object.getClass().equals(message.getClass())){
				message = (Message)object;
				view.displayMessage(message);
			}
			
			return true;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public void sendMessage(String message){
		try {
			outStream.writeObject(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendMessage(Message message){
		try {
			outStream.writeObject(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendPlayerName(String playerName) {
		try {
			outStream.writeObject(playerName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
