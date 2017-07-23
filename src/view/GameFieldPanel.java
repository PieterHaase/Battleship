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
	private ImageIcon cruiser1 = new ImageIcon("src/cruiser1.png");
	private ImageIcon cruiser2 = new ImageIcon("src/cruiser2.png");

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
				if (!content.equals("water")) {
					buttonField[x][y].setBackground(Color.gray);
				}
				if (content.contains("Battleship"))
					labelText = "B";
				if (content.contains("Destroyer"))
					labelText = "D";
				if (content.contains("Cruiser"))
					labelText = "C";
				if (content.contains("Submarine")) {
					// labelText = "S";
					buttonField[x][y].setIcon(sub);
					buttonField[x][y].setBackground(buttonColor);
				}
				if (field.isHit()) {
					if (content.equals("water"))
						buttonField[x][y].setBackground(GUISettings.waterColor);
					else
						buttonField[x][y].setBackground(Color.red);
				}
				buttonField[x][y].setText(labelText);
			}
		}

	}

}