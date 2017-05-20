package model;

public class GameField {

	Field[][] gameField;
	
	public GameField(int size){
		gameField = new Field[size][size];
	}
	
	public void placeShip(Ship ship, int x, int y, String orientation){			//muss noch gegen Platzieren auﬂerhalb des Spielfelds abgesichert werden

		if (orientation == "horizontal"){
			for (int i = 0; i < ship.getLength(); i++){
				gameField[x+i][y] = ship.getFieldAt(i);
			}
		}
		
		if (orientation == "vertical"){
			for (int i = 0; i < ship.getLength(); i++){
				gameField[x][y+i] = ship.getFieldAt(i);
			}
		}
	}
	
}
