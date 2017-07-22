package model;

import java.util.Observable;

/**
 * Diese Klasse repr�sentiert das Model.
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
	 * @param enemyShips ...
	 */
	public void setEnemyShips(ShipManager enemyShips) {
		this.enemyShips = enemyShips;
		update();
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
