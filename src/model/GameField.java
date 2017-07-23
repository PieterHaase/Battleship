package model;

import java.io.Serializable;
/**
 * Diese Klasse repräsentiert ein Spielfeld
 * @author Pieter Haase, Naqib Faizy
 * @version 1.0.
 *
 */
public class GameField implements Serializable{

	private static final long serialVersionUID = 1L;
	private Field[][] gameField;
	private int size;
	private String owner;
	
	/**
	 * Erstellt das Spielfeld mit einer bestimmten Größe
	 * und weist ihr einen Besitzer zu.
	 * @param size Größe des Spielfelds
	 * @param owner Besitzer des Spielfelds
	 */
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
	
	/**
	 * Gibt die Größe des Spielfelds zurück.
	 * @return size
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Gibt den Besitzer des Spielfelds zurück.
	 * @return owner
	 */
	public String getOwner() {
		return owner;
	}
	
	/**
	 * Platziert ein Schiff an der Stelle x,y auf dem Spielfeld. 
	 * Dabei wird überprüft, ob der Platz bereits belegt ist.
	 * @param ship Das zu platzierende Schiff
	 * @param x x-Position des Schiffes
	 * @param y y-Position des Schiffes
	 * @param orientation Gibt an, ob es horizontal oder vertikal platziert wird
	 * @return true - Wenn das Schiff erfolgreich platziert wurde
	 * @return false - Wenn der Platz bereits belegt ist
	 */
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
							gameField[x+i][y].setPosition(x+i,y);
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
							gameField[x][y+i].setPosition(x,y+i);
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
	
	/**
	 * Gibt das Feld an der Stelle x,y zurück.
	 * @param x die x-Position des Feldes
	 * @param y die y-Position des Feldes
	 * @return Field 
	 */
	public Field getFieldAt(int x, int y){
		return gameField[x][y];
	}
	
	/**
	 * Gibt das Spielfeld auf der Konsole aus.		
	 */
	public void printField(){
		
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
						
					if (content != "water" && gameField[x][y].isHit())
						outputString = "[X]";
					if(gameField[x][y].isHit()){
						if(content.equals("water")){
							outputString = "[o]";	
						}
						else
						outputString = "[X]";	
					}
					System.out.print(outputString);
				}
			}
			System.out.print("\n");
		}
	}
	
	/**
	 * Entfernt alle bereits platzierte Schiffe.
	 */
	public void clear(){
		for (int y = 0; y < size; y++){
			for (int x = 0; x < size; x++){
				gameField[x][y] = new Field(x, y, "water");
			}
		}
	}

	/**
	 * Legt fest, wem das Spielfeld gehört.
	 * @param playerName Name des Spielers
	 */
	public void setOwner(String playerName) {
		owner = playerName;
	}
	
}
