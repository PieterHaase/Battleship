package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Diese Klasse repräsentiert den Ship-Manager. Sie verwaltet alle Schiffe eines
 * Spielers.
 * 
 * @author Pieter Haase, Naqib Faizy
 * @version 1.0.
 *
 */
public class ShipManager implements Serializable {

	private static final long serialVersionUID = 1L;
	private int battleships = 1;
	private int destroyers = 2;
	private int cruisers = 3;
	private int submarines = 4;
	private int gameFieldSize = 10;

	private ArrayList<Ship[]> shipArrayList = new ArrayList<>();

	private Battleship[] battleshipArray;
	private Destroyer[] destroyerArray;
	private Cruiser[] cruiserArray;
	private Submarine[] submarineArray;

	private GameField gameField;
	private String owner;

	/**
	 * Erstellt den ShipManager eines Spielers.
	 * 
	 * @param owner
	 *            Der Besitzer der Schiffe
	 */
	public ShipManager(String owner) {
		this.owner = owner;
		initialize();
		newGameField();

	}

	/**
	 * Erstellt ein neues Spielfeld
	 */
	public void newGameField() {
		gameField = new GameField(gameFieldSize, this.owner);

		battleshipArray = new Battleship[battleships];
		destroyerArray = new Destroyer[destroyers];
		cruiserArray = new Cruiser[cruisers];
		submarineArray = new Submarine[submarines];

		shipArrayList = new ArrayList<>();

		shipArrayList.add(battleshipArray);
		shipArrayList.add(destroyerArray);
		shipArrayList.add(cruiserArray);
		shipArrayList.add(submarineArray);

		initialize();
	}

	/**
	 * Füllt den Shipmanager mit Schiffen.
	 */
	public void initialize() {
		for (int i = 0; i < shipArrayList.size(); i++) {
			Ship[] ships = shipArrayList.get(i);
			for (int j = 0; j < ships.length; j++) {

				if (i == 0) {
					ships[j] = new Battleship();
				}
				if (i == 1) {
					ships[j] = new Destroyer();
				}
				if (i == 2) {
					ships[j] = new Cruiser();
				}
				if (i == 3) {
					ships[j] = new Submarine();
				}
			}
		}
	}

	/**
	 * Platziert an zufälligen Stellen Schiffe.
	 */
	public void placeRandomShips() {
		for (int i = 0; i < shipArrayList.size(); i++) {
			Ship[] ships = shipArrayList.get(i);
			for (int j = 0; j < ships.length; j++) {

				if (i == 0) {
					ships[j] = new Battleship();
				}
				if (i == 1) {
					ships[j] = new Destroyer();
				}
				if (i == 2) {
					ships[j] = new Cruiser();
				}
				if (i == 3) {
					ships[j] = new Submarine();
				}

				String orientation = "horizontal";
				if (RandomInt.randInt(0, 1) == 1)
					orientation = "vertical";
				int x;
				int y;
				do {
					x = RandomInt.randInt(0, gameFieldSize - 1);
					y = RandomInt.randInt(0, gameFieldSize - 1);
				} while (!gameField.placeShip(ships[j], x, y, orientation));
				ships[j].setOrientation(orientation);
				ships[j].setPosition(x, y);

			}
		}
	}

	/**
	 * Gibt eine Liste der Schiffe auf der Konsole aus.
	 */
	public void printListOfShips() {
		System.out.println("\n" + owner + "'s Ships:");
		System.out.println("----------------------------------");
		for (Ship[] shipArray : shipArrayList) {
			System.out.println("Number of " + shipArray[0].getType() + "s: " + shipArray.length);
			for (int i = 0; i < shipArray.length; i++)
				System.out.println(
						shipArray[i].getType() + " '" + shipArray[i].getName() + "' " + shipArray[i].getOrientation()
								+ " at " + shipArray[i].getXPosition() + "," + shipArray[i].getYPosition());
			System.out.println("");
		}
	}

	/**
	 * Prüft ob von einem Spiele aller Schiffe versenkt wurden.
	 * @return true - Wenn alle Schiffe des Spielers gesunken sind.
	 */
	public boolean allShipsSunk() {
		boolean allShipsSunk = true;
		for (Ship[] shipArray : shipArrayList) {
			for (int i = 0; i < shipArray.length; i++) {
				if (shipArray[i].isSunk() == false)
					allShipsSunk = false;
			}
		}
		return allShipsSunk;
	}

	/**
	 * Gibt das Spielfeld eines Spielers zurück.
	 * @return gameField
	 */
	public GameField getGameField() {
		return gameField;
	}

	/**
	 * Gibt die Größe des Spielfelds zurück.
	 * @return gameFieldSize
	 */
	public int getGameFieldSize() {
		return gameFieldSize;
	}
	
	/**
	 * Gibt den Besitzer des ShipManagers zurück.
	 * @return owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * Legt den Besitzer des ShipManagers fest.
	 * @param owner Der Besitzer des ShipManagers
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * Gibt eine ArrayList mit allen Schiffen eines Spielers zurück.
	 * @return shipArrayList
	 */
	public ArrayList<Ship[]> getShipArrayList() {
		return shipArrayList;
	}

	/**
	 * Gibt die Anzahl der Schiffe in der ArrayList zurück.
	 * @return number
	 */
	public int getNoOfShips() {
		int number = 0;
		for (int i = 0; i < shipArrayList.size(); i++) {
			number += shipArrayList.get(i).length;
		}
		return number;
	}

}
