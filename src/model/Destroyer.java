package model;

import java.io.Serializable;
/**
 * Diese Klasse repr�sentiert den Zerst�rer
 * @author Pieter Haase, Naqib Faizy
 * @version 1.0
 *
 */
public class Destroyer extends Ship implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static String type = "Destroyer";
	private static int length = 3;

	/**
	 * Hier wird der Zerst�rer erstellt.
	 * Es besitzt den Namen "Destroyer" und hat eine L�nge von 3 Felder.
	 */
	public Destroyer() {
		super(type, length, ShipNames.randomName());
	}
	
}
