package model;

public class Model {
	
	private int battleships = 1;
	private int destroyers = 2;
	private int cruisers = 3;
	private int submarines = 4;
	
	
	private GameField gameField = new GameField(10);
	private ShipManager ships = new ShipManager(battleships, destroyers, cruisers, submarines);
	
	public Model() {
		
	}
	
	public GameField getGameField() {
		return gameField;
	}
	
	public ShipManager getShips() {
		return ships;
	}
	
}
