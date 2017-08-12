package model;
import java.util.Random;
/**
 * Mit dieser Klasse lassen sich Zufallszahlen generieren.
 * @author Pieter Haase, Naqib Faizy
 * @version 1.0
 *
 */
public class RandomInt {
	
	/**
	 * Statische Methode, die eine zufällige Zahl 
	 * zwischen min und (einschließlich) max zurückgibt.
	 * @param min Untere Grenze der Zufallszahl.
	 * @param max Obere Grenze der Zufallszahl.
	 * @return eine Zufallszahl.
	 */
	public static int randInt(int min, int max) {
	    Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min; // nextInt schließt
																// normalerweise
																// die
																// Obergrenze
																// nicht mit
																// ein, daher 1
																// addieren.
		return randomNum;
	}
	
}
