package controller;
import java.util.Scanner;

import model.*;
import view.*;


public class Controller {
	
	Model model;
	Scanner scanner;
	boolean playersTurn = true;
	
	public Controller(){
		model = new Model();
		View view = new View(model);
		model.addObserver(view);
		
		model.update();
		
		for (int x = 0; x < view.getEnemyButtons().length; x++) {
			for (int y = 0; y < view.getEnemyButtons().length; y++) {
				FieldButton button = view.getEnemyButtons()[x][y]; 
				Field field = model.getEnemyField().getFieldAt(button.getXPos(), button.getYPos()); 
				button.addActionListener(listener -> {
					if (playersTurn){
						if (!field.isHit()){
							field.markAsHit();
							model.update();
							playersTurn = false;
							computerTurn();	
						}
					}
				});
			}
		}		
		
		scanner = new Scanner(System.in);
//		while(!model.gameOver()){
//			playerTurn();
//			computerTurn();
//		}
		scanner.close();
		
	}
	
	private void playerTurn(){
//		System.out.println("\nEnter Coordinates:");
		
		while (playersTurn){}
		
//		int x = scanner.nextInt();
//		int y = scanner.nextInt();
//		model.getEnemyField().getFieldAt(x, y).markAsHit();
//		printFields();
		model.update();
		System.out.println("\nEnter Coordinates:");
	}
	
	private void computerTurn(){
		int x = RandomInt.randInt(0, 9);
		int y = RandomInt.randInt(0, 9);
		System.out.println("\nComputer shot at " + x + "," + y);
		model.getPlayerField().getFieldAt(x, y).markAsHit();
		printFields();
		model.update();
		playersTurn = true;
	}
	
	private void printFields(){
		model.printField(model.getPlayerField());
		model.printField(model.getEnemyField());
	}
	
	public static void main(String[] args) {
		new Controller();
	}
	
}
