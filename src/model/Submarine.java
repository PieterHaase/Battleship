package model;

import java.io.Serializable;
/**
 * Diese Klasse repräsentiert das U-Boot.
 * @author Pieter Haase, Naqib Faizy
 * @version 1.0.
 *
 */
public class Submarine extends Ship implements Serializable {

	private static final long serialVersionUID = 1L;
	private static String type = "Submarine";
	private static int length = 1;

	/**
	 * Hier wird das U-Boot erstellt.
	 * Es ist vom Schiffstyp "Submarine" und besitzt eine Länge von 1 Feld.
	 */
	public Submarine() {
		super(type, length, ShipNames.randomName());
	}
	
}
