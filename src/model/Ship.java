package model;

public abstract class Ship {
	
	private String name;
	private String type;
	private int xPosition;
	private int yPosition;
	private String orientation;
	private int length;
	private Field[] fieldArray;
	
	public Ship(String type, int length, String name){
		fieldArray = new Field[length];
		for (int i=0; i<length; i++){
			fieldArray[i] = new Field(type+i);
		}
			
		this.type = type;
		this.name = name;
		this.length = length;
	}
	
	public Field getFieldAt(int x){
		return fieldArray[x];
	}
	
	public String getType(){
		return type;
	}
	
	public int getLength(){
		return length;
	}
	
	public String getName(){
		return name;
	}
	
	public boolean isSunk(){
		boolean isSunk = true;
		for (int i=0; i<length; i++) {
			if (!fieldArray[i].isHit())
				isSunk = false;
		}
		return isSunk;
	}

	public int getXPosition() {
		return xPosition;
	}

	public void setXPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	public int getYPosition() {
		return yPosition;
	}

	public void setYPosition(int yPosition) {
		this.yPosition = yPosition;
	}

	public String getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

}
