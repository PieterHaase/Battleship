package model;

import java.io.Serializable;
/**
 * Diese Klasse repräsentiert das Schlachtschiff.
 * @author Pieter Haase, Naqib Faizy
 * @version 1.0
 *
 */
public class Battleship extends Ship implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final String type = "Battleship";
	private static int length = 4;
	
	/**
	 * Hier wird das Schlachtschiff erstellt.
	 * Es ist vom Schiffstyp "Battleship" und besitzt eine Länge von 4 Felder.
	 */
	public Battleship() {
		super(type, length, ShipNames.randomName());
	}
	
}
