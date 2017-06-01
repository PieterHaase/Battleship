package model;

public class ShipManager {
	
	private Ship[][] shipArray = new Ship[4][];

	private Battleship[] battleshipArray;
	private Destroyer[] destroyerArray;
	private Cruiser[] cruiserArray;
	private Submarine[] submarineArray;
	
	public ShipManager(int battleships, int destroyers, int cruisers, int submarines){

		battleshipArray = new Battleship[battleships];
		for (int i = 0; i < battleshipArray.length; i++)
			battleshipArray[i] = new Battleship("a");
		shipArray[0] = battleshipArray;
		
		destroyerArray = new Destroyer[destroyers];
		for (int i = 0; i < destroyerArray.length; i++)
			destroyerArray[i] = new Destroyer("a");
		shipArray[1] = destroyerArray;
			
		cruiserArray = new Cruiser[cruisers];
		for (int i = 0; i < cruiserArray.length; i++)
			cruiserArray[i] = new Cruiser("a");
		shipArray[2] = cruiserArray;
			
		submarineArray = new Submarine[submarines];
		for (int i = 0; i < submarineArray.length; i++)
			submarineArray[i] = new Submarine("a");
		shipArray[3] = submarineArray;
	}
	
	public Battleship getBattleship(int i){
		return battleshipArray[i-1];
	}
	
	public Destroyer getDestroyer(int i){
		return destroyerArray[i-1];
	}
	
	public Cruiser getCruiser(int i){
		return cruiserArray[i-1];
	}
	
	public Submarine getSubmarine(int i){
		return submarineArray[i-1];
	}
	
	public Ship[][] getShipArray() {
		return shipArray;
	}
	
	public Battleship[] getBattleshipArray() {
		return battleshipArray;
	}
	public Destroyer[] getDestroyerArray() {
		return destroyerArray;
	}
	
	public Cruiser[] getCruiserArray() {
		return cruiserArray;
	}
	
	public Submarine[] getSubmarineArray() {
		return submarineArray;
	}
	
}
