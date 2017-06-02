package model;

public class ShipManager {
	
	private String owner;
	private Ship[][] shipArray = new Ship[4][];

	private Battleship[] battleshipArray;
	private Destroyer[] destroyerArray;
	private Cruiser[] cruiserArray;
	private Submarine[] submarineArray;
	
	public ShipManager(String owner, int battleships, int destroyers, int cruisers, int submarines){
		this.setOwner(owner);

		battleshipArray = new Battleship[battleships];
		for (int i = 0; i < battleshipArray.length; i++)
			battleshipArray[i] = new Battleship(ShipNames.randomName());
		shipArray[0] = battleshipArray;
		
		destroyerArray = new Destroyer[destroyers];
		for (int i = 0; i < destroyerArray.length; i++)
			destroyerArray[i] = new Destroyer(ShipNames.randomName());
		shipArray[1] = destroyerArray;
			
		cruiserArray = new Cruiser[cruisers];
		for (int i = 0; i < cruiserArray.length; i++)
			cruiserArray[i] = new Cruiser(ShipNames.randomName());
		shipArray[2] = cruiserArray;
			
		submarineArray = new Submarine[submarines];
		for (int i = 0; i < submarineArray.length; i++)
			submarineArray[i] = new Submarine(ShipNames.randomName());
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

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
	
}
