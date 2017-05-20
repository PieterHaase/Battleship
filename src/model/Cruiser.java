package model;

public class Cruiser extends Ship{
	
	private static String type = "Cruiser";
	private static int length = 2;
	
	public Cruiser(String name) {
		super(type, length, name);
	}
}
