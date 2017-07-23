package model;

import java.io.Serializable;
/**
 * 
 * @author Pieter Haase, Naqib Faizy
 * @version 1.0.
 *
 */
public class Cruiser extends Ship implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static String type = "Cruiser";
	private static int length = 2;
	
	public Cruiser() {
		super(type, length, ShipNames.randomName());
	}
	
}
