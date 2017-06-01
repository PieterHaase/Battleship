package controller;
import java.util.Scanner;

import model.*;
import view.*;


public class Controller {
	
	Model model;
	Scanner scanner;
	
	public Controller(){
		model = new Model();
		//View view = new View(model);
		
		scanner = new Scanner(System.in);
		while(!model.gameOver()){
			playerTurn();
			computerTurn();
		}
		scanner.close();
		
	}
	
	private void playerTurn(){
		System.out.println("\nEnter Coordinates:");
		
		int x = scanner.nextInt();
		int y = scanner.nextInt();
		model.getEnemyField().getFieldAt(x, y).markAsHit();
		printFields();
	}
	
	private void computerTurn(){
		int x = RandomInt.randInt(0, 9);
		int y = RandomInt.randInt(0, 9);
		System.out.println("\nComputer shot at " + x + "," + y);
		model.getPlayerField().getFieldAt(x, y).markAsHit();
		printFields();
	}
	
	private void printFields(){
		model.printField(model.getPlayerField());
		model.printField(model.getEnemyField());
	}
	
	public static void main(String[] args) {
		new Controller();
	}
	
}
