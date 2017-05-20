package model;

public class Destroyer extends Ship{
	
	private static String type = "Destroyer";
	private static int length = 3;

	public Destroyer(String name) {
		super(type, length, name);
	}
}
