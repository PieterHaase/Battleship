package model;

import java.util.Observable;

public class Model extends Observable{
	
	private int battleships = 1;
	private int destroyers = 2;
	private int cruisers = 3;
	private int submarines = 4;
	private int gameFieldSize = 10;
	
	@SuppressWarnings("unused")
	private ShipNames shipNames = new ShipNames();
	
	private GameField playerField = new GameField(gameFieldSize, "Player");
	private GameField enemyField = new GameField(gameFieldSize, "Enemy");
	private ShipManager playerShips = new ShipManager("Player", battleships, destroyers, cruisers, submarines);
	private ShipManager enemyShips = new ShipManager("Enemy", battleships, destroyers, cruisers, submarines);
	
	public Model() {
		playerField.placeRandomShips(playerShips);
		enemyField.placeRandomShips(enemyShips);
		
		printListOfShips(playerShips);
		printListOfShips(enemyShips);

		printField(playerField);
		printField(enemyField);
		
	}
	
	public GameField getPlayerField() {
		return playerField;
	}
	
	public GameField getEnemyField() {
		return enemyField;
	}
	
	public ShipManager getPlayerShips() {
		return playerShips;
	}
	
	public ShipManager getEnemyShips() {
		return enemyShips;
	}
	
	public void printField(GameField field){
		int size = field.getSize(); 
		
		System.out.println("\n" + field.getOwner() + " Ships:");
		for (int y = -1; y < size; y++){
			if (y >= 0)
				System.out.print(" " + y + " ");
			else
				System.out.print("   ");
			
			for (int x = 0; x < size; x++){
				if (y == -1)
					System.out.print(" " + x + " ");
				else{
					String content = field.getFieldAt(x, y).getContent();
					String outputString = "[ ]";
					if (content == "water")
						outputString = "[ ]";
					if (content.contains("Battleship"))
						outputString = "[B]";
					if (content.contains("Destroyer"))
						outputString = "[D]";
					if (content.contains("Cruiser"))
						outputString = "[C]";
					if (content.contains("Submarine"))
						outputString = "[S]";	
					if (content == "water" && field.getFieldAt(x, y).isHit())
						outputString = "[o]";
					if (content != "water" && field.getFieldAt(x, y).isHit())
						outputString = "[X]";
					System.out.print(outputString);
						
				}
			}
			System.out.print("\n");
		}
	}
	
	public void printListOfShips(ShipManager ships){
		System.out.println("\n" + ships.getOwner() + "'s Ships:");
		System.out.println("----------------------------------");
		printListOfShips(ships.getBattleshipArray());
		printListOfShips(ships.getDestroyerArray());
		printListOfShips(ships.getCruiserArray());
		printListOfShips(ships.getSubmarineArray());
	}
	
	private void printListOfShips(Ship[] ships){
		Ship[] shipArray = ships;
		
		System.out.println("Number of " + shipArray[0].getType() + "s: " + shipArray.length);
		for (int i = 0; i < shipArray.length; i++)
			System.out.println(shipArray[i].getType() + " '" + shipArray[i].getName() + "' " + shipArray[i].getOrientation() + " at " + shipArray[i].getXPosition() + "," + shipArray[i].getYPosition());
		System.out.println("");
	}
	
	public boolean gameOver(){
		if (allShipsSunk(playerShips) || allShipsSunk(playerShips))
			return true;
		else
			return false;
	}
	
	public boolean allShipsSunk(ShipManager ships){
		boolean allShipsSunk = true;
		Ship[][] shipArray = ships.getShipArray(); 
		for (int i = 0; i < shipArray.length; i++){
			for (int j = 0; j < shipArray[i].length; j++){
				if (shipArray[i][j].isSunk() == false)
					allShipsSunk = false;
			}
		}
		return allShipsSunk;
	}
	
	public void update(){
		this.setChanged();
		this.notifyObservers();
	}
	
}
