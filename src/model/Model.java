package model;

import java.util.Observable;

/**
 * Diese Klasse repräsentiert das Model. 
 * Die Klasse enthält die darzustellenden Daten. 
 * Zusätzlich verwendet sie das Observer-Pattern.
 * @author Pieter Haase, Naqib Faizy
 * @version 1.0
 * 
 */
public class Model extends Observable {

	@SuppressWarnings("unused")
	private ShipNames shipNames;
	
	private String playerName = "Player";
	private String enemyName = "Enemy";
	private ShipManager playerShips;
	private ShipManager enemyShips;

	/**
	 * Erstellt das Model.
	 */
	public Model() {
		shipNames = new ShipNames();
		playerShips = new ShipManager(playerName);
		enemyShips = new ShipManager(enemyName);
		update();
	}

	/**
	 * Gibt den Spielernamen zurück.
	 * @return Den Namen des Spielers.
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * Gibt die Schiffe des Spielers zurück.
	 * @return Die Schiffe des Spielers.
	 */
	public ShipManager getPlayerShips() {
		return playerShips;
	}

	/**
	 * Gibt die Schiffe des Gegners zurück.
	 * @return Die Schiffe des Gegners.
	 */
	public ShipManager getEnemyShips() {
		return enemyShips;
	}

	/**
	 * Legt die Schiffe des Gegeners fest.
	 * @param enemyShips Die Schiffe des Gegners.
	 */
	public void setEnemyShips(ShipManager enemyShips) {
		this.enemyShips = enemyShips;
		update();
	}

	/**
	 * Prüft, ob die Flotte eines Spielers versenkt und damit das Spiel beendet
	 * wurde.
	 * @return true - Wenn das Spiel vorbei ist.
	 * @return false - Wenn nicht alle Schiffe eines Spielers versenkt wurde.
	 */
	public boolean gameOver() { // prüft ob das Spiel vorbei ist
		if (playerShips.allShipsSunk() || enemyShips.allShipsSunk())
			return true;
		else
			return false;
	}

	/**
	 * Benachrichtigt die Observer, dass im Model eine Veränderung stattgefunden
	 * hat.
	 */
	public void update() { 
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Gibt den Namen des Gegners zurück.
	 * @return Den Namen des Gegners.
	 */
	public String getEnemyName() {
		return enemyName;
	}

	/**
	 * Legt den Spielernamen fest.
	 * @param playerName Der Spielername.
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
		getPlayerShips().setOwner(playerName);
		getPlayerShips().getGameField().setOwner(playerName);
		update();
	}

	/**
	 * legt den Gegnernamen fest.
	 * @param enemyName Der Gegnername.
	 */
	public void setEnemyName(String enemyName) {
		this.enemyName = enemyName;
		getEnemyShips().setOwner(enemyName);
		getEnemyShips().getGameField().setOwner(enemyName);
		update();
	}

	/**
	 * Legt die Schiffe des Spielers fest.
	 * @param playerships Die Schiffe des Spielers.
	 */
	public void setPlayerShips(ShipManager playerships) {
		this.playerShips = playerships;
		update();
	}

	/**
	 * Setzt alle Schiffe eines Spielers auf den Ausgangszustand zurück.
	 */
	public void resetShips() {
		shipNames = new ShipNames();
		playerShips = new ShipManager(playerName);
		enemyShips = new ShipManager(enemyName);
		update();
	}

	/**
	 * Setzt alle Schiffe eines Spielers zurück und beide Spielfelder
	 * vollständig zurück.
	 */
	public void reset() {
		resetShips();
		playerShips.getGameField().clear();
		enemyShips.getGameField().clear();
		update();
	}

}
