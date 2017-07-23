package model;

import java.io.Serializable;

public class Submarine extends Ship implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String type = "Submarine";
	private static int length = 1;

	public Submarine() {
		super(type, length, ShipNames.randomName());
	}
}
