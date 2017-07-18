package model;

import java.io.Serializable;

import controller.ConsoleIO;

public class GameField  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Field[][] gameField;
	private int size;
	private String owner;
	
	public GameField(int size, String owner){
		this.size = size;
		this.owner = owner;
		gameField = new Field[size][size];
		for (int y = 0; y < size; y++){
			for (int x = 0; x < size; x++){
				gameField[x][y] = new Field(x, y, "water");
			}
		}
	}
	
	public int getSize() {
		return size;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public boolean placeShip(Ship ship, int x, int y, String orientation){
		
		int length = ship.getLength();
		boolean isOccupied = false;
		boolean isPlaced = false;
		
		if (orientation == "horizontal"){
			while(!isPlaced){
				if (x + (length -1) <= size-1) {
					for (int i = 0; i < length; i++){
						if (gameField[x+i][y].getContent() != "water")
							isOccupied = true;
					}
					if (!isOccupied) {
						for (int i = 0; i < length; i++){
							gameField[x+i][y] = ship.getFieldAt(i);
							gameField[x+i][y].setParent(ship);
							isPlaced = true;
						}
						return true;
					}
					else
						return false;
				}
				else
					x--;
			}
			
				
		}
		if (orientation == "vertical"){
			while(!isPlaced){
				if (y + (length -1) <= size-1) {
					for (int i = 0; i < length; i++){
						if (gameField[x][y+i].getContent() != "water")
							isOccupied = true;
					}
					if (!isOccupied) {
						for (int i = 0; i < length; i++){
							gameField[x][y+i] = ship.getFieldAt(i);
							gameField[x][y+i].setParent(ship);	
							isPlaced = true;
						}
						return true;
					}
					else
						return false;
				}
				else
					y--;	
			}
		}
		return false;
	}
	
	public Field getFieldAt(int x, int y){
		return gameField[x][y];
	}
	
	public void printField(){			//gibt das Spielfeld auf der Konsole aus		
		
		System.out.println("\n" + owner + " Ships:");
		
		for (int y = -1; y < size; y++){				//Nummerierung der Zeilen und Spalten
			if (y >= 0)
				System.out.print(" " + y + " ");
			else
				System.out.print("   ");	
			for (int x = 0; x < size; x++){
				if (y == -1)
					System.out.print(" " + x + " ");
				else{
					String content = gameField[x][y].getContent();		//füllen der Felder abhängig vom Schiffstyp, bzw Wasser
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
					if (content == "water" && gameField[x][y].isHit())
						outputString = "[o]";
					if (content != "water" && gameField[x][y].isHit())
						outputString = "[X]";
					System.out.print(outputString);
				}
			}
			System.out.print("\n");
		}
	}
	
}
