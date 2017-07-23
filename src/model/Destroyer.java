package model;

import java.io.Serializable;
/**
 * 
 * @author Pieter Haase, Naqib Faizy
 * @version 1.0.
 *
 */
public class Destroyer extends Ship implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static String type = "Destroyer";
	private static int length = 3;

	public Destroyer() {
		super(type, length, ShipNames.randomName());
	}
	
}
