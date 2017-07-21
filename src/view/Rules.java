package view;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
/**
 * Zeige ein JDialog mit den Regeln an.
 * Für die Rules Actionlisteners schreiben.
 * Actionlisteners muss ein neues Objekt der Klasse Rules erzeugen.
 * 
 * JDialog soll Regeln so anzeigen wie die HTML-Seite
 * @author naqib
 *
 */

public class Rules extends JDialog {

	private static String rules = 
			  "Ziel des Spiels:"
			+ "\n "
			+ "\nJeder Spieler versteckt eine kleine Flotte von Schiffen "
			+ "vor seinem Gegner. \nDerjenige, der zuerst alle Schiffe des "
			+ "Gegners komplett getroffen und versenkt hat, gewinnt."
			+ "\n"
			+ "\nBeide Flotten bestehen aus folgenden 5 Schiffen:"
			+ "\n"
			+ "\n1 Schlachtschiff (5 Treffer nötig zum Versenken)"
			+ "\n1 Kreuzer (4 Treffer nötig zum Versenken)"
			+ "\n2 Fregatten (3 Treffer nötig zum Versenken)"
            + "\n1 Minensucher (2 Treffer nötig zum Versenken)"
			+ "\n"
            + "\n"
			+ "Das Einsetzen der Schiffe"
            + "\n"
			+ "\n"
			+ "Vor dem ersten Zug muss jeder seine Schiffe einsetzen."
			+ " Die Schiffe müssen so eingesetzt werden, "
			+ "\ndass sie sich weder horizontal noch vertikal "
			+ "berühren - sie dürfen also nicht an einer Kante angrenzen, "
			+ "\nDigonales Berühren über Eck ist zulässig."
			+ "\nWähle ein Schiff durch Anklicken, drehe es gegebenfalls"
			+ "durch den entsprechenden Button."
			+ "\nSchiebe es über das Brett bis die Position stimmt "
			+ "und klicke dann, um es abzusetzten."
			+ "\nWenn du ein Schiff wieder entfernen willst, kannst "
			+ "du es durch Anklicken wieder zurücknehmen."
			+ "\nSind alle Schiffe eingesetzt, bestätige und das Spiel kann starten."
			+ "\n"
			+ "\n"
			+ "Auf Felder schießen"
			+ "\n"
			+ "\n"
			+ "Von jetzt an siehst du immer zwei Spielbretter:"
			+ "\n"
			+ "\n"
			+ " * Im rechten Brett kannst du deine eigenen Schüsse "
			+ "und Erfolge sehen"
			+ "\n"
			+ " * Im linken Brett siehst du deine Schiffe und "
			+ "die Treffer deines Gegners"
			+ "\n"
			+ "\n"
			+ "Feuere auf ein bestimmtes Feld, in dem du im rechten "
			+ "Brett auf das gewünschte Feld klickst. "
			+ "\nBestätige und du siehst sofort, ob du getroffen hast."
			+ "\nBesondere Regel auf Brettspielnetz: Wenn du triffst, "
			+ "\nbist du sofort nochmal an der Reihe"
			+ "- auch mehrmals hintereinander."
			+ "\n"
			+ "\n"
			+ "Sieger"
			+ "\n"
			+ "\n"
			+ "Wer als erstes alle gegnerischen Schiffe versenkt, ist Sieger.";

	public Rules(JFrame parent) {
		super(parent, rules, false);
		
	}
	
	public static void main(String[] args) {
		System.out.println(rules);
		/*JFrame f = new JFrame();
		f.add(new JLabel(rules));
		f.setVisible(true);
		new Rules(f);*/
	}

}
