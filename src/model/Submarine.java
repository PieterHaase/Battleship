package model;

public class Submarine extends Ship{

	private static String type = "Submarine";
	private static int length = 1;
	
	public Submarine(String name) {
		super(type, length, name);
	}
}
