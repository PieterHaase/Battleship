package model;

import java.util.Observable;

public class Model extends Observable{
	/**
	 * Diese Klasse repr�sentiert das Model.
	 * @author Pieter Haase, Naqib Faizy
	 * @version 1.0.
	 */
	@SuppressWarnings("unused")
	private ShipNames shipNames = new ShipNames();
	
	private String playerName = "Player";
	private String enemyName = "Enemy";
	private ShipManager playerShips = new ShipManager(playerName);
	private ShipManager enemyShips = new ShipManager(enemyName);
	
	/**
	 * Erstellt das Model
	 */
	public Model() {
//		playerShips.placeRandomShips();
//		playerShips.initialize();
		enemyShips.placeRandomShips();
		
//		playerShips.printListOfShips();
//		enemyShips.printListOfShips();

//		playerShips.getGameField().printField();
//		enemyShips.getGameField().printField();
	}
	
	/**
	 * Gibt den Spielernamen zur�ck
	 * @return playerName
	 */
	public String getPlayerName(){
		return playerName;
	}

	/**
	 * Gibt die Schiffe des Spielers zur�ck
	 * @return playerShips
	 */
	public ShipManager getPlayerShips() {
		return playerShips;
	}
	
	/**
	 * Gibt die Schiffe des Gegners zur�ck
	 * @return enemyShips
	 */
	public ShipManager getEnemyShips() {
		return enemyShips;
	}
	
	/**
	 * Setzt die Schiffe des Gegeners
	 * @param enemyShips - ...
	 */
	public void setEnemyShips(ShipManager enemyShips) {
		this.enemyShips = enemyShips;
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * Pr�ft, ob die Flotte eines Spielers versenkt wurde und
	 * das Spiel beendet wurde
	 * @return true - Wenn das Spiel vorbei ist
	 */
	public boolean gameOver(){												//pr�ft ob das Spiel vorbei ist
		if (playerShips.allShipsSunk() || enemyShips.allShipsSunk())
			return true;
		else
			return false;
	}
	
	/**
	 * Benachrichtigt die Observer
	 * 
	 */
	public void update(){													//benachrichtigt den Observer (View)
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Gibt den Namen des Gegners zur�ck
	 * @return String
	 */
	public String getEnemyName() {
		return enemyName;
	}
	
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
		getPlayerShips().setOwner(playerName);
		getPlayerShips().getGameField().setOwner(playerName);
		this.setChanged();
		this.notifyObservers();
	}

	public void setEnemyName(String enemyName) {
		this.enemyName = enemyName;
		getEnemyShips().setOwner(enemyName);
		getEnemyShips().getGameField().setOwner(enemyName);
		this.setChanged();
		this.notifyObservers();
	}
}
