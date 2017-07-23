package model;
import java.util.Random;

public class RandomInt {
	
	public static int randInt(int min, int max) {
	    Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min; // nextInt schlieﬂt
																// normalerweise
																// die
																// Obergrenze
																// nicht mit
																// ein, daher 1
																// addieren.
		return randomNum;
	}
}
