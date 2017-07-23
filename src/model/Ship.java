package model;

import java.io.Serializable;

public abstract class Ship implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String type;
	private int xPosition;
	private int yPosition;
	private String orientation;
	private int length;
	private Field[] fieldArray;

	public Ship(String type, int length, String name) {
		fieldArray = new Field[length];
		for (int i = 0; i < length; i++) {
			fieldArray[i] = new Field(xPosition + i, yPosition + i, type + i);
		}

		this.type = type;
		this.name = name;
		this.length = length;
	}

	public Field getFieldAt(int x) {
		return fieldArray[x];
	}

	public String getType() {
		return type;
	}

	public int getLength() {
		return length;
	}

	public String getName() {
		return name;
	}

	public boolean isSunk() {
		boolean isSunk = true;
		for (int i = 0; i < length; i++) {
			if (!fieldArray[i].isHit())
				isSunk = false;
		}
		return isSunk;
	}

	public int getXPosition() {
		return xPosition;
	}

	public int getYPosition() {
		return yPosition;
	}

	public void setPosition(int x, int y) {
		xPosition = x;
		yPosition = y;
		for (int i = 0; i < length; i++) {
			if (orientation == "horizontal")
				fieldArray[i].setPosition(x + i, y);
			if (orientation == "vertical")
				fieldArray[i].setPosition(x, y + i);
		}
	}

	public String getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

}
