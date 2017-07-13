package controller;
import java.util.Scanner;

import model.*;
import view.*;
import network.*;


public class Controller {
	
	Model model;
	Scanner scanner;
	boolean playersTurn = true;
	boolean gameOver = false;
	private AI computer;
	
	int lastX = -1;
	int lastY = -1;
	int lastHitX = -1;
	int lastHitY = -1;
	
	public Controller(){
		model = new Model();
		View view = new View(model);
		model.addObserver(view);
		model.update();
		computer = new AI(model.getPlayerShips().getGameField());
		
		for (int x = 0; x < view.getEnemyPanel().getButtonField().length; x++) {
			for (int y = 0; y < view.getEnemyPanel().getButtonField().length; y++) {
				FieldButton button = view.getEnemyPanel().getButtonField()[x][y]; 
				Field field = model.getEnemyShips().getGameField().getFieldAt(button.getXPos(), button.getYPos()); 
				button.addActionListener(listener -> {
					if (playersTurn && !gameOver){
						if (!field.isHit()){
							field.markAsHit();
							model.update();
							playersTurn = false;
							computerTurn();
							gameOver = model.gameOver();
						}
					}
				});
			}
		}
		
		ConsoleIO.write("Enter command:");
		String command = ConsoleIO.read(); 
		if (command.matches("server")){
			GameServer server = new GameServer(model);
		}
		else if (command.matches("client")){
			GameClient client = new GameClient(model);
		}
	}
	
	private void computerTurn(){
		computer.makeTurn();
			
//		printFields();
		model.update();
		playersTurn = true;
	}
	
	private void printFields(){
		model.getPlayerShips().getGameField().printField();
		model.getEnemyShips().getGameField().printField();
	}
	
	public static void main(String[] args) {
		new Controller();
	}
	
}
