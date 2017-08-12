package model;

import java.util.Observable;

/**
 * Diese Klasse repr�sentiert das Model. 
 * Die Klasse enth�lt die darzustellenden Daten. 
 * Zus�tzlich verwendet sie das Observer-Pattern.
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
	 * Gibt den Spielernamen zur�ck.
	 * @return Den Namen des Spielers.
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * Gibt die Schiffe des Spielers zur�ck.
	 * @return Die Schiffe des Spielers.
	 */
	public ShipManager getPlayerShips() {
		return playerShips;
	}

	/**
	 * Gibt die Schiffe des Gegners zur�ck.
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
	 * Pr�ft, ob die Flotte eines Spielers versenkt und damit das Spiel beendet
	 * wurde.
	 * @return true - Wenn das Spiel vorbei ist.
	 * @return false - Wenn nicht alle Schiffe eines Spielers versenkt wurde.
	 */
	public boolean gameOver() { // pr�ft ob das Spiel vorbei ist
		if (playerShips.allShipsSunk() || enemyShips.allShipsSunk())
			return true;
		else
			return false;
	}

	/**
	 * Benachrichtigt die Observer, dass im Model eine Ver�nderung stattgefunden
	 * hat.
	 */
	public void update() { 
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Gibt den Namen des Gegners zur�ck.
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
	 * Setzt alle Schiffe eines Spielers auf den Ausgangszustand zur�ck.
	 */
	public void resetShips() {
		shipNames = new ShipNames();
		playerShips = new ShipManager(playerName);
		enemyShips = new ShipManager(enemyName);
		update();
	}

	/**
	 * Setzt alle Schiffe eines Spielers zur�ck und beide Spielfelder
	 * vollst�ndig zur�ck.
	 */
	public void reset() {
		resetShips();
		playerShips.getGameField().clear();
		enemyShips.getGameField().clear();
		update();
	}

}
