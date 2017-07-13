package model;

import java.io.Serializable;

public class Cruiser extends Ship implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String type = "Cruiser";
	private static int length = 2;
	
	public Cruiser() {
		super(type, length, ShipNames.randomName());
	}
}
