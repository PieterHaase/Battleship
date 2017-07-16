package view;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;

import model.Field;
import model.GameField;

public class GameFieldPanel extends JPanel{
	
	private Color windowColor = GUISettings.windowColor;
	private Color buttonColor = GUISettings.gameFieldColor;
	private int fieldSize;
	private FieldButton[][] buttonField;
	private GameField gameField;
	
	public GameFieldPanel(GameField gameField) {
		this.gameField = gameField;
		
		TitledBorder title;
		title = BorderFactory.createTitledBorder(new EmptyBorder(0,0,0,0), gameField.getOwner() + " Ships");
		title.setTitleJustification(TitledBorder.CENTER);
		
		setLayout(new GridLayout(10,10,0,0));
		setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(0,0,0,0), title));
		this.setBackground(windowColor);

		fieldSize = gameField.getSize();
		buttonField = new FieldButton[fieldSize][fieldSize];

		for (int x=0; x < fieldSize; x++){
			for (int y=0; y < fieldSize; y++){
				buttonField[x][y] = new FieldButton(x,y);
				buttonField[x][y].setOpaque(true);
				buttonField[x][y].setBackground(buttonColor);
				buttonField[x][y].setBorder(new LineBorder(windowColor));
			}
		}
	
		for (int y=0; y < fieldSize; y++){
			for (int x=0; x < fieldSize; x++){
			this.add(buttonField[x][y]);
			}
		}
	}
	
	public FieldButton[][] getButtonField(){
		return buttonField;
	}
	
	public void update(GameField gameField){
		for (int x=0; x < fieldSize; x++){
			for (int y=0; y < fieldSize; y++){
				Field field = gameField.getFieldAt(x, y);
				String content = field.getContent();
				String labelText = "";
//				System.out.println(content);

				if (content.contains("Battleship")){
					labelText = "B";
//					buttonField[x][y].setBackground(Color.gray);
				}
				if (content.contains("Destroyer"))
					labelText = "D";
				if (content.contains("Cruiser"))
					labelText = "C";
				if (content.contains("Submarine"))
					labelText = "S";	
				if (content == "water" && field.isHit())
					buttonField[x][y].setBackground(GUISettings.waterColor);
				if (content != "water" && field.isHit())
					buttonField[x][y].setBackground(Color.black);
				
				if (!content.contains("water"))
					buttonField[x][y].setBackground(Color.gray);
				if (content.contains("water")){
					labelText = "";
					buttonField[x][y].setBackground(buttonColor);
				}
				buttonField[x][y].setText(labelText);
			}
		}
	}

}
