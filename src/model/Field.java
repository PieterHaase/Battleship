package model;

import java.io.Serializable;

/**
 * Diese Klasse repr�sentiert ein Feld.
 * Dieses kann entweder zu einem Schiff geh�ren oder Wasser enthalten.
 * @author Pieter Haase, Naqib Faizy
 * @version 1.0
 *
 */
public class Field implements Serializable {

	private static final long serialVersionUID = 1L;
	private int xPos, yPos;
	private Ship parent;
	private String content;
	private boolean isHit = false;

	/**
	 * Erstellt das Feld.
	 * @param xPos Die x-Position auf dem Spielfeld.
	 * @param yPos Die y-Position auf dem Spielfeld.
	 * @param content Der Inhalt des Feldes, welcher entweder Wasser oder ein Schiffsteil sein kann.
	 */
	public Field(int xPos, int yPos, String content) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.content = content;
	}

	/**
	 * Gibt zur�ck, ob das Feld bereits getroffen wurde.
	 * @return isHit.
	 */
	public boolean isHit() { 
		return isHit;
	}

	/**
	 * Markiert das Feld als getroffen.
	 */
	public void markAsHit() {
		isHit = true;
	}

	/**
	 * Gibt die x-Position zur�ck.
	 * @return xPos.
	 */
	public int getXPos() {
		return xPos;
	}

	/**
	 * Gibt die y-Position zur�ck.
	 * @return yPos.
	 */
	public int getYPos() {
		return yPos;
	}

	/**
	 * Gibt das Schiff zur�ck, zu dem das Feld geh�rt.
	 * @return parent.
	 */
	public Ship getParent() {
		return parent;
	}

	/**
	 * Gibt den Inhalt zur�ck.
	 * @return content.
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Setzt die x- und y-Position.
	 * @param x x-Position.
	 * @param y y-Position.
	 */
	public void setPosition(int x, int y) {
		xPos = x;
		yPos = y;
	}

	/**
	 * Definiert, zu welchem Schiff das Feld geh�rt.
	 * @param parent Das Schiff, zu dem das Feld geh�rt.
	 */
	public void setParent(Ship parent) {
		this.parent = parent;
	}

}
