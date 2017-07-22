package model;

import java.util.Observable;

/**
 * Diese Klasse repräsentiert das Model.
 * @author Pieter Haase, Naqib Faizy
 * @version 1.0.
 */
public class Model extends Observable{

	
	@SuppressWarnings("unused")
	private ShipNames shipNames;
	
	private String playerName = "Player";
	private String enemyName = "Enemy";
	private ShipManager playerShips;
	private ShipManager enemyShips;
	
	/**
	 * Erstellt das Model
	 */
	public Model() {
		shipNames = new ShipNames();
		playerShips = new ShipManager(playerName);
		enemyShips = new ShipManager(enemyName);
		update();
//		playerShips.placeRandomShips();
//		playerShips.initialize();
		
		
//		playerShips.printListOfShips();
//		enemyShips.printListOfShips();

//		playerShips.getGameField().printField();
//		enemyShips.getGameField().printField();

	}
	
	/**
	 * Gibt den Spielernamen zurück
	 * @return playerName
	 */
	public String getPlayerName(){
		return playerName;
	}

	/**
	 * Gibt die Schiffe des Spielers zurück
	 * @return playerShips
	 */
	public ShipManager getPlayerShips() {
		return playerShips;
	}
	
	/**
	 * Gibt die Schiffe des Gegners zurück
	 * @return enemyShips
	 */
	public ShipManager getEnemyShips() {
		return enemyShips;
	}
	
	/**
	 * Setzt die Schiffe des Gegeners
	 * @param enemyShips ...
	 */
	public void setEnemyShips(ShipManager enemyShips) {
		this.enemyShips = enemyShips;
		update();
	}
	
	/**
	 * Prüft, ob die Flotte eines Spielers versenkt wurde und
	 * das Spiel beendet wurde
	 * @return true - Wenn das Spiel vorbei ist
	 */
	public boolean gameOver(){												//prüft ob das Spiel vorbei ist
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
	 * Gibt den Namen des Gegners zurück
	 * @return String
	 */
	public String getEnemyName() {
		return enemyName;
	}
	
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
		getPlayerShips().setOwner(playerName);
		getPlayerShips().getGameField().setOwner(playerName);
		update();
	}

	public void setEnemyName(String enemyName) {
		this.enemyName = enemyName;
		getEnemyShips().setOwner(enemyName);
		getEnemyShips().getGameField().setOwner(enemyName);
		update();
	}
	
	/**
	 * Setzt die Schiffe des Spielers
	 * @param playerships ... 
	 */
	public void setPlayerShips(ShipManager playerships) {
		this.playerShips = playerships;
		update();
	}
	
	public void resetShips(){
		shipNames = new ShipNames();
		playerShips = new ShipManager(playerName);
		enemyShips = new ShipManager(enemyName);
		update();
	}

	public void reset() {
		resetShips();
		playerShips.getGameField().clear();
		enemyShips.getGameField().clear();
		update();
	}
}
