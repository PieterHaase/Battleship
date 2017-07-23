package controller;

import java.util.ArrayList;

import model.Field;
import model.GameField;
import model.RandomInt;

public class AI {

	private ArrayList<Field> previousHits = new ArrayList<>();
	private GameField gameField;

	public AI(GameField gameField) {
		this.gameField = gameField;
	}

	public void makeTurn() {
		Field field = new Field(0, 0, "");
		;
		if (previousHits.size() == 0) {
			field = pickRandomField();
		}

		else if (previousHits.size() > 1) {
			if (!previousHits.get(0).getParent().isSunk())
				field = pickNextFieldInLine();
		}

		else if (previousHits.size() > 0) {
			if (!previousHits.get(0).getParent().isSunk())
				field = pickRandomNeighbor();
		}

		System.out.println("Computer marked " + field.getXPos() + "," + field.getYPos());
		for (Field fieldX : previousHits)
			System.out.print("[" + fieldX.getXPos() + "," + fieldX.getYPos() + "]");
		System.out.println("");
		field.markAsHit();
		if (field.getContent() != "water" && field.getParent().isSunk()) {
			previousHits.clear();
			System.out
					.println("Computer sunk " + field.getParent().getType() + " '" + field.getParent().getName() + "'");
		}
	}

	private Field pickRandomField() { // wählt ein zufälliges Feld
		System.out.println("Computer picks random field");
		Field field;

		do {
			int x = RandomInt.randInt(0, 9);
			int y = RandomInt.randInt(0, 9);
			field = gameField.getFieldAt(x, y);
			System.out.println("Computer picked " + field.getXPos() + "," + field.getYPos() + " / " + x + "," + y);
		} while (field.isHit());
		if (field.getContent() != "water")
			previousHits.add(field);
		return field;
	}

	private Field pickRandomNeighbor() { // wählt einen zufälligen Nachbarn des
											// zuletzt gewählten Feldes
		System.out.println("Computer picks random neighbor");
		Field field = previousHits.get(0);
		Field newField;
		int x;
		int y;

		do {
			do {
				x = field.getXPos();
				y = field.getYPos();
				int random = RandomInt.randInt(1, 4);

				if (random == 1) {
					y++;
				}
				if (random == 2) {
					y--;
				}
				if (random == 3) {
					x++;
				}
				if (random == 4) {
					x--;
				}

			} while (x < 0 || y < 0 || x > 9 || y > 9);
			newField = gameField.getFieldAt(x, y);
			System.out.println("Computer picked " + newField.getXPos() + "," + newField.getYPos());
		} while (newField.isHit());

		if (newField.getContent() != "water")
			previousHits.add(newField);
		return newField;
	}

	private Field pickNextFieldInLine() { // wählt das nächste Feld, welches in
											// einer Reihe mit den zwei zuletzt
											// gewählten Feldern liegt und ein
											// direkter Nachbar ist
		System.out.println("Computer picks next field in line");
		Field first = previousHits.get(previousHits.size() - 1);
		Field second = previousHits.get(previousHits.size() - 2);
		int x = first.getXPos() - second.getXPos(); // hier muss noch das
													// "umdrehen" implementiert
													// werden
		int y = first.getYPos() - second.getYPos();
		Field field = gameField.getFieldAt(first.getXPos() + x, first.getYPos() + y);

		System.out.println("x:" + x + ", y:" + y);
		System.out.println("xPos:" + first.getXPos() + ", yPos:" + first.getYPos());

		System.out.println("Computer picked " + field.getXPos() + "," + field.getYPos());

		if (field.getContent() != "water")
			previousHits.add(field);
		return field;
	}

}
