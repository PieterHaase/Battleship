package model;

public class ShipManager {

	private Battleship[] battleshipArray;
	private Destroyer[] destroyerArray;
	private Cruiser[] cruiserArray;
	private Submarine[] submarineArray;
	
	public ShipManager(int battleships, int destroyers, int cruisers, int submarines){

		battleshipArray = new Battleship[battleships];
		
		for (int i = 0; i < battleshipArray.length; i++)
			battleshipArray[i] = new Battleship(ShipNames.randomName());
		
		destroyerArray = new Destroyer[destroyers];
		for (int i = 0; i < destroyerArray.length; i++)
			destroyerArray[i] = new Destroyer(ShipNames.randomName());
			
		cruiserArray = new Cruiser[cruisers];
		for (int i = 0; i < cruiserArray.length; i++)
			cruiserArray[i] = new Cruiser(ShipNames.randomName());
			
		submarineArray = new Submarine[submarines];
		for (int i = 0; i < submarineArray.length; i++)
			submarineArray[i] = new Submarine(ShipNames.randomName());
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
	
}
