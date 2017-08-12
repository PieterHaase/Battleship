package model;

import java.io.Serializable;
/**
 * Diese Klasse repräsentiert den Kreuzer.
 * @author Pieter Haase, Naqib Faizy
 * @version 1.0
 *
 */
public class Cruiser extends Ship implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static String type = "Cruiser";
	private static int length = 2;
	
	/**
	 * Hier wird der Kreuzer erstellt.
	 * Es ist vom Schiffstyp "Cruiser" und besitzt eine Länge von 2 Felder.
	 */
	public Cruiser() {
		super(type, length, ShipNames.randomName());
	}
	
}
