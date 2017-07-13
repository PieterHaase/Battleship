package model;

import java.io.Serializable;

public class Battleship extends Ship implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String type = "Battleship";
	private static int length = 4;
	
	public Battleship() {
		super(type, length, ShipNames.randomName());
	}
}
