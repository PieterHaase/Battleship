package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import model.Field;
import model.GameField;

public class GameFieldPanel extends JPanel {

	private Color windowColor = GUISettings.windowColor;
	private Color buttonColor = GUISettings.gameFieldColor;
	private Color gridColor = GUISettings.gridColor;
	private int fieldSize;
	private FieldButton[][] buttonField;
	private GameField gameField;
	private TitledBorder title;
	private JLabel fieldOwner = new JLabel("");
	private JPanel grid = new JPanel(new GridLayout(10, 10, 0, 0));
	private ImageIcon sub = new ImageIcon("src/sub.png");
	private ImageIcon cruiser0h = new ImageIcon("src/cruiser0h.png");
	private ImageIcon cruiser1h = new ImageIcon("src/cruiser1h.png");
	private ImageIcon cruiser0v = new ImageIcon("src/cruiser0v.png");
	private ImageIcon cruiser1v = new ImageIcon("src/cruiser1v.png");
	private ImageIcon destroyer0h = new ImageIcon("src/destroyer0h.png");
	private ImageIcon destroyer1h = new ImageIcon("src/destroyer1h.png");
	private ImageIcon destroyer2h = new ImageIcon("src/destroyer2h.png");
	private ImageIcon destroyer0v = new ImageIcon("src/destroyer0v.png");
	private ImageIcon destroyer1v = new ImageIcon("src/destroyer1v.png");
	private ImageIcon destroyer2v = new ImageIcon("src/destroyer2v.png");
	private ImageIcon battleship0h = new ImageIcon("src/battleship0h.png");
	private ImageIcon battleship1h = new ImageIcon("src/battleship1h.png");
	private ImageIcon battleship2h = new ImageIcon("src/battleship2h.png");
	private ImageIcon battleship3h = new ImageIcon("src/battleship3h.png");
	private ImageIcon battleship0v = new ImageIcon("src/battleship0v.png");
	private ImageIcon battleship1v = new ImageIcon("src/battleship1v.png");
	private ImageIcon battleship2v = new ImageIcon("src/battleship2v.png");
	private ImageIcon battleship3v = new ImageIcon("src/battleship3v.png");

	public GameFieldPanel(GameField gameField) {
		this.gameField = gameField;
		fieldOwner.setText(gameField.getOwner() + "'s Ships");
		fieldOwner.setHorizontalAlignment(SwingConstants.CENTER);
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setBackground(windowColor);

		fieldSize = gameField.getSize();
		buttonField = new FieldButton[fieldSize][fieldSize];

		for (int x = 0; x < fieldSize; x++) {
			for (int y = 0; y < fieldSize; y++) {
				buttonField[x][y] = new FieldButton(x, y);
				buttonField[x][y].setOpaque(true);
				buttonField[x][y].setFocusable(false);
				buttonField[x][y].setBackground(buttonColor);
				buttonField[x][y].setBorder(new LineBorder(gridColor));
			}
		}

		for (int y = 0; y < fieldSize; y++) {
			for (int x = 0; x < fieldSize; x++) {
				grid.add(buttonField[x][y]);
			}
		}
		add(fieldOwner, BorderLayout.NORTH);
		add(grid, BorderLayout.CENTER);

	}

	public FieldButton[][] getButtonField() {
		return buttonField;
	}

	public void update(GameField gameField) {

		fieldOwner.setText(gameField.getOwner() + "'s Ships");

		for (int y = 0; y < fieldSize; y++) { // Nummerierung der Zeilen und
												// Spalten
			for (int x = 0; x < fieldSize; x++) {
				Field field = gameField.getFieldAt(x, y);
				String content = field.getContent();
				String labelText = "";
				buttonField[x][y].setBackground(buttonColor);

				buttonField[x][y].setIcon(null);
				
				if (content.contains("Submarine")){
					buttonField[x][y].setIcon(sub);
				}

				if (field.getParent() != null) {
					if (field.getParent().getOrientation() == "horizontal") {
						if (content.contains("Cruiser0")) {
							buttonField[x][y].setIcon(cruiser0h);
						}
						if (content.contains("Cruiser1")) {
							buttonField[x][y].setIcon(cruiser1h);
						}
						if (content.contains("Destroyer0")) {
							buttonField[x][y].setIcon(destroyer0h);
						}
						if (content.contains("Destroyer1")) {
							buttonField[x][y].setIcon(destroyer1h);
						}
						if (content.contains("Destroyer2")) {
							buttonField[x][y].setIcon(destroyer2h);
						}
						if (content.contains("Battleship0")) {
							buttonField[x][y].setIcon(battleship0h);
						}
						if (content.contains("Battleship1")) {
							buttonField[x][y].setIcon(battleship1h);
						}
						if (content.contains("Battleship2")) {
							buttonField[x][y].setIcon(battleship2h);
						}
						if (content.contains("Battleship3")) {
							buttonField[x][y].setIcon(battleship3h);
						}

					} else {
						if (content.contains("Cruiser0")) {
							buttonField[x][y].setIcon(cruiser0v);
						}
						if (content.contains("Cruiser1")) {
							buttonField[x][y].setIcon(cruiser1v);
						}
						if (content.contains("Destroyer0")) {
							buttonField[x][y].setIcon(destroyer0v);
						}
						if (content.contains("Destroyer1")) {
							buttonField[x][y].setIcon(destroyer1v);
						}
						if (content.contains("Destroyer2")) {
							buttonField[x][y].setIcon(destroyer2v);
						}
						if (content.contains("Battleship0")) {
							buttonField[x][y].setIcon(battleship0v);
						}
						if (content.contains("Battleship1")) {
							buttonField[x][y].setIcon(battleship1v);
						}
						if (content.contains("Battleship2")) {
							buttonField[x][y].setIcon(battleship2v);
						}
						if (content.contains("Battleship3")) {
							buttonField[x][y].setIcon(battleship3v);
						}
					}
				}
				// if (content.contains("Battleship"))
				// labelText = "B";
				// if (content.contains("Destroyer"))
				// labelText = "D";
				// if (content.contains("Submarine")){
				//// labelText = "S";
				// buttonField[x][y].setIcon(sub);
				// buttonField[x][y].setBackground(buttonColor);
				// }
				if (field.isHit()) {

					if (content.equals("water"))
						buttonField[x][y].setBackground(GUISettings.waterColor);
					else
						buttonField[x][y].setBackground(Color.red);
				}

				// labelText = field.getXPos() + "," + field.getYPos();
				// labelText = field.getContent().substring(0,1) +
				// Boolean.toString(field.isHit()).substring(0, 1);
				// labelText = Boolean.toString(field.isHit());
				// if(field.getParent() != null)
				// labelText = field.getParent().getOrientation();

				buttonField[x][y].setText(labelText);
			}
		}

	}

}
