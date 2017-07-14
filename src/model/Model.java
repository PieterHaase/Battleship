package model;

import java.util.Observable;

public class Model extends Observable{
	
	@SuppressWarnings("unused")
	private ShipNames shipNames = new ShipNames();
	
	private String playerName = "Player 1";
	private ShipManager playerShips = new ShipManager("Player");
	private ShipManager enemyShips = new ShipManager("Enemy");
	
	public Model() {
		playerShips.placeRandomShips();
		enemyShips.placeRandomShips();
		
//		playerShips.printListOfShips();
//		enemyShips.printListOfShips();

//		playerShips.getGameField().printField();
//		enemyShips.getGameField().printField();
	}
	
	public String getPlayerName(){
		return playerName;
	}

	public ShipManager getPlayerShips() {
		return playerShips;
	}
	
	public ShipManager getEnemyShips() {
		return enemyShips;
	}
	
	public void setEnemyShips(ShipManager enemyShips) {
		this.enemyShips = enemyShips;
		this.setChanged();
		this.notifyObservers();
	}
	
	public boolean gameOver(){												//prüft ob das Spiel vorbei ist
		if (playerShips.allShipsSunk() || enemyShips.allShipsSunk())
			return true;
		else
			return false;
	}
	
	public void update(){													//benachrichtigt den Observer (View)
		this.setChanged();
		this.notifyObservers();
	}
	
}
