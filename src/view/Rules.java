package view;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class Rules extends JDialog {

	private static String rules = "Ziel des Spiels:" + "\n "
			+ "\nJeder Spieler versteckt eine kleine Flotte von Schiffen "
			+ "vor seinem Gegner. \nDerjenige, der zuerst alle Schiffe des "
			+ "Gegners komplett getroffen und versenkt hat, gewinnt." + "\n"
			+ "\nBeide Flotten bestehen aus folgenden 10 Schiffen:" + "\n"
			+ "\n1 Schlachtschiff (4 Treffer n�tig zum Versenken)" + "\n2 Zerst�rer (3 Treffer n�tig zum Versenken)"
			+ "\n3 Kreuzer (2 Treffer n�tig zum Versenken)" + "\n4 U-Boot (1 Treffer n�tig zum Versenken)" + "\n" + "\n"
			+ "Das Einsetzen der Schiffe" + "\n" + "\n" + "Vor dem ersten Zug muss jeder seine Schiffe einsetzen."
			+ " Die Schiffe m�ssen so eingesetzt werden, "
			+ "\ndass sie sich weder horizontal noch vertikal �berschneiden. "
			+ "\nDie Schiffe werden durch Anklicken, gegebenfalls durch den entsprechenden Button, platziert."
			+ "\nSchiebe es �ber das Spielfeld bis die Position stimmt " + "und klicke dann, um es abzusetzten."
			+ "\nWenn du ein Schiff wieder entfernen willst, kannst " + "du es durch Anklicken wieder zur�cknehmen."
			+ "\nSind alle Schiffe eingesetzt, kann das Spiel starten." + "\n" + "\n" + "Auf Felder schie�en" + "\n"
			+ "\n" + "Von jetzt an siehst du immer zwei Spielfelder:" + "\n" + "\n"
			+ " * Im rechten Spielfeld kannst du deine eigenen Sch�sse " + "und Erfolge sehen" + "\n"
			+ " * Im linken Spielfeld siehst du deine Schiffe und " + "die Treffer deines Gegners" + "\n" + "\n"
			+ "Feuere auf ein bestimmtes Feld, in dem du im rechten " + "Spielfeld auf das gew�nschte Feld klickst. "
			+ "\nDu siehst sofort, ob du getroffen hast." + "\nWenn du triffst, bist du sofort nochmal an der Reihe"
			+ "- auch mehrmals hintereinander." + "\n" + "\n" + "Sieger" + "\n" + "\n"
			+ "Wer als erstes alle gegnerischen Schiffe versenkt, ist Sieger.";

	public Rules() {
		JOptionPane.showMessageDialog(getContentPane(), rules, "Regeln", JOptionPane.INFORMATION_MESSAGE);
	}

}
