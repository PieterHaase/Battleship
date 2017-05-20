package model;

import java.util.ArrayList;

public class ShipNames {

	private static ArrayList<String> names = new ArrayList<String>();
	
	public ShipNames() {
		names.add("Centurion");
		names.add("Vega");
		names.add("Nova");
		names.add("October");
		names.add("Hercules");
	}
	
	public static String randomName() {
		int index = RandomInt.randInt(0,names.size());
		String name = names.get(index);
		names.remove(index);					//damit kein Name doppelt verwendet wird
		return name;
	}
	
}
