package model;

public class Battleship extends Ship{

	private static final String type = "Battleship";
	private static int length = 4;
	
	public Battleship(String name) {
		super(type, length, name);
	}
}
