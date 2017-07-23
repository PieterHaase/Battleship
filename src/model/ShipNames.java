package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Diese Klasse repräsentiert eine Liste von Schiffsnamen.
 * 
 * @author Pieter Haase, Naqib Faizy
 * @version 1.0.
 *
 */
public class ShipNames {

	private File saveFile = new File("src\\model\\shipnames.txt");
	private static ArrayList<String> names = new ArrayList<String>();

	/**
	 * Erstellt eine Liste mit Schiffsnamen, die aus einer Textdatei eingelesen wird. 
	 */
	public ShipNames() {

		String inText = "";

		if (saveFile.exists()) {
			try {
				FileReader fReader = new FileReader(saveFile);
				BufferedReader bReader = new BufferedReader(fReader);
				while (inText != null) {
					inText = bReader.readLine();
					if (inText != null)
						names.add(inText);
				}
				bReader.close();
				fReader.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Gibt einen zufälligen Namen aus der Namensliste zurück.
	 * @return name - Wenn ein Name aus der Liste gewählt wurde
	 * @return "Unknown" - Wenn kein Name aus der Liste gewählt werden konnte
	 */
	public static String randomName() {
		if (names.size() > 0) {
			int index = RandomInt.randInt(0, names.size() - 1);
			String name = names.get(index);
			names.remove(index); // damit kein Name doppelt verwendet wird
			return name;
		} else {
			return "Unknown";
		}

	}

}
