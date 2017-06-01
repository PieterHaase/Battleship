package model;

public class GameField {

	private Field[][] gameField;
	private int size;
	private String owner;
	
	public GameField(int size, String owner){
		this.size = size;
		this.owner = owner;
		gameField = new Field[size][size];
		for (int y = 0; y < size; y++){
			for (int x = 0; x < size; x++){
				gameField[x][y] = new Field("water");
			}
		}
	}
	
	public int getSize() {
		return size;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public boolean placeShip(Ship ship, int x, int y, String orientation){
		
		int length = ship.getLength();
		boolean isOccupied = false;
		
		if (orientation == "horizontal"){
			
			if (x + length < size-1) {
				for (int i = 0; i < length; i++){
					if (gameField[x+i][y].getContent() != "water")
						isOccupied = true;
				}
				if (!isOccupied) {
					for (int i = 0; i < length; i++)
						gameField[x+i][y] = ship.getFieldAt(i);
					return true;
				}	
			}
			else
				return false;
		}
		
		if (orientation == "vertical"){
			
			if (y + length < size-1) {
				for (int i = 0; i < length; i++){
					if (gameField[x][y+i].getContent() != "water")
						isOccupied = true;
				}
				if (!isOccupied) {
					for (int i = 0; i < length; i++)
						gameField[x][y+i] = ship.getFieldAt(i);
					return true;
				}
			}
			else
				return false;
		}
		return false;
	}
	
	public void placeRandomShips(ShipManager ships){
		Ship[][] shipArray = ships.getShipArray();
		for (int i = 0; i < shipArray.length; i++)
			placeRandomShips(shipArray[i]);
	}
	
	private void placeRandomShips(Ship[] ships){
		for (int i = 0; i < ships.length; i++){
			String orientation = "horizontal";
			if (RandomInt.randInt(0, 1) == 1)
				orientation = "vertical";
			
			int x = RandomInt.randInt(0, size-1);
			int y = RandomInt.randInt(0, size-1);
			
			do{
				ships[i].setXPosition(x);
				ships[i].setYPosition(y);
				x = RandomInt.randInt(0, size-1);
				y = RandomInt.randInt(0, size-1);
			}
			while (!placeShip(ships[i], x, y, orientation));
			
			ships[i].setOrientation(orientation);
		}
	}
	
	public Field getFieldAt(int x, int y){
		return gameField[x][y];
	}
	

	public boolean isHit(int x, int y){
		gameField[x][y].markAsHit();
		if (gameField[x][y].getContent() != "water")
			return false;
		else return true;
	}
	
}
