package model;

import java.io.Serializable;
/**
 * Diese abstrakte Klasse definiert das Schiff.
 * Aus dieser Klasse erben alle Schiffstypen.
 * @author Pieter Haase, Naqib Faizy
 * @version 1.0
 *
 */
public abstract class Ship implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String type;
	private int xPosition;
	private int yPosition;
	private String orientation;
	private int length;
	private Field[] fieldArray;

	/**
	 * Erstellt das Schiff.
	 * @param type Definiert den Schiffstypen.
	 * @param length Definiert die Länge.
	 * @param name Definiert den Namen.
	 */
	public Ship(String type, int length, String name) {
		fieldArray = new Field[length];
		for (int i = 0; i < length; i++) {
			fieldArray[i] = new Field(xPosition + i, yPosition + i, type + i);
		}

		this.type = type;
		this.name = name;
		this.length = length;
	}

	/**
	 * Gibt das Feld an der Stelle x zurück.
	 * @param x die x-Position des Feldes.
	 * @return ein Array aus Feldern an der Stelle x.
	 */
	public Field getFieldAt(int x) {
		return fieldArray[x];
	}

	/**
	 * Gibt den Schiffstypen zurück.
	 * @return Den Schiffstypen.
	 */
	public String getType() {
		return type;
	}

	/**
	 * Gibt die Länge des Schiffes zurück.
	 * @return die Länge des Schiffes.
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Gibt den Namen des Schiffes zurück.
	 * @return den Namen des Schiffes.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Prüft, ob das Schiff gesunken ist.
	 * @return true - Wenn das Schiff gesunken ist. 
	 */
	public boolean isSunk() {
		boolean isSunk = true;
		for (int i = 0; i < length; i++) {
			if (!fieldArray[i].isHit())
				isSunk = false;
		}
		return isSunk;
	}

	/**
	 * Gibt die x-Position des Schiffes zurück.
	 * @return die Position an Stelle x.
	 */
	public int getXPosition() {
		return xPosition;
	}

	/**
	 * Gibt die y-Position des Schiffes zurück.
	 * @return die Position an Stelle y.
	 */
	public int getYPosition() {
		return yPosition;
	}

	/**
	 * Legt die x- und y-Position des Schiffes fest.
	 * @param x x-Position des Schiffes.
	 * @param y y-Position des Schiffes.
	 */
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

	/**
	 * Gibt die Ausrichtung des Schiffes zurück.
	 * @return die Ausrichtung des Schiffes.
	 */
	public String getOrientation() {
		return orientation;
	}

	/**
	 * Legt die Ausrichtung des Schiffes fest.
	 * @param orientation Die Ausrichtung.
	 */
	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

}
