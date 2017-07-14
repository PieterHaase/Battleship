package view;
import model.*;
import network.GameClient;
import network.GameServer;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * 
 * @author Pieter Haase
 * @version 1.0
 */

public class View extends JFrame implements Observer{

	private static final long serialVersionUID = 1L;
	
	private Model model;

	private JPanel mainPanel = new JPanel(new GridLayout(1,2,5,0));
	private JPanel middlePanel = new JPanel(new GridLayout(1,2,5,0));
	private JPanel leftPanel = new JPanel();
	private JPanel rightPanel = new JPanel();
	private JPanel playerGameField = new JPanel(new GridLayout(10,10,0,0));
	private JPanel enemyGameField = new JPanel(new GridLayout(10,10,0,0));
	private ChatPanel chatPanel = new ChatPanel();
	private JLabel statusLbl = new JLabel("Player 1, your turn! Click on a field to fire at enemy ship.");
	
	private ArrayList<JPanel> gameFields = new ArrayList<>();
	private ArrayList<FieldButton[][]> buttonFields = new ArrayList<>();
//	private ArrayList<JPanel> panels = new ArrayList<>();
	//private JButton[][] leftButtonArray;
	//private JButton[][] rightButtonArray;
	
	private JLabel playerShips = new JLabel("Player ships");
	private JLabel enemyShips = new JLabel("Enemy ships");
	private int fieldSize;
	private FieldButton[][] playerButtons;
	private FieldButton[][] enemyButtons;
	
	private int gameFieldPanelSize = 450;
	private Color windowColor = GUISettings.windowColor;
	GameFieldPanel playerPanel;
	GameFieldPanel enemyPanel;
	GameField playerField;
	GameField enemyField;
	private JMenuBar menuBar = new JMenuBar();
	private JMenu gameMenu = new JMenu("Game");
	private JMenuItem newGame = new JMenuItem("New Game");
	private JMenuItem saveGame = new JMenuItem("Save Game");
	private JMenuItem loadGame = new JMenuItem("Load Game");
	private JMenu networkMenu = new JMenu("Network");
	private JMenuItem createServer = new JMenuItem("Create Server");
	private JMenuItem joinGame = new JMenuItem("Join Game");

	
	public View (Model model){
		this.model = model;
		setJMenuBar(menuBar);
		gameMenu.add(newGame);
		gameMenu.add(saveGame);
		gameMenu.add(loadGame);
		menuBar.add(gameMenu);
		networkMenu.add(createServer);	//controller.createServer
		networkMenu.add(joinGame);		//controller.joinGame
		menuBar.add(networkMenu);

		/*
		fieldSize = model.getPlayerShips().getGameFieldSize();
		playerButtons = new FieldButton[fieldSize][fieldSize];
		enemyButtons = new FieldButton[fieldSize][fieldSize];
//		leftButtonArray = new JButton[fieldSize][fieldSize];
//		leftButtonArray = new JButton[fieldSize][fieldSize];
		
		gameFields.add(playerGameField);
		gameFields.add(enemyGameField);
		
		buttonFields.add(playerButtons);
		buttonFields.add(enemyButtons);
		
//		panels.add(leftPanel);
//		panels.add(rightPanel);
		
		for (FieldButton[][] buttonField: buttonFields){
			for (int x=0; x < fieldSize; x++){
				for (int y=0; y < fieldSize; y++){
				buttonField[x][y] = new FieldButton(x,y);
				buttonField[x][y].setOpaque(true);
				buttonField[x][y].setBackground(Color.lightGray);
				}
			}
		}
		
		for (int y=0; y < fieldSize; y++){
			for (int x=0; x < fieldSize; x++){
			playerGameField.add(playerButtons[x][y]);
			enemyGameField.add(enemyButtons[x][y]);
			}
		}
		
		for (JPanel gameField: gameFields){
			
			//gameField.setBackground(Color.gray);
			gameField.setPreferredSize(new Dimension(450,450));
		}
		/*
		for (JPanel panel: panels){
			panel.setBorder(new EmptyBorder(10, 10, 20, 10));
			panel.setBackground(Color.gray);
		}
		
		leftPanel.add(playerShips, BorderLayout.NORTH);
		leftPanel.add(playerGameField);
		
		rightPanel.add(enemyShips, BorderLayout.NORTH);
		rightPanel.add(enemyGameField);
		
		
		//rightPanel.setBackground(Color.gray);
		middlePanel.add(leftPanel);
		middlePanel.add(rightPanel);
		//middlePanel.add(button);
		mainPanel.add(middlePanel, BorderLayout.CENTER);
		
		mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		add(mainPanel);
		*/
		playerField = model.getPlayerShips().getGameField(); 
		playerPanel = new GameFieldPanel(playerField);
		playerPanel.setPreferredSize(new Dimension(gameFieldPanelSize,gameFieldPanelSize));
		
		enemyField = model.getEnemyShips().getGameField();
		enemyPanel = new GameFieldPanel(enemyField);
		enemyPanel.setPreferredSize(new Dimension(gameFieldPanelSize,gameFieldPanelSize));
		
//		setSize(1000, 200);
		Dimension dim = new Dimension(this.getWidth(), 150);
		chatPanel.setPreferredSize(dim);
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.setBackground(Color.red);
		panel.add(chatPanel);
		
		mainPanel.add(playerPanel);
		mainPanel.add(enemyPanel);
		mainPanel.setBorder(new EmptyBorder(10,10,0,10));
		mainPanel.setBackground(windowColor);
		add(mainPanel, BorderLayout.NORTH);
		add(chatPanel, BorderLayout.CENTER);
		statusLbl.setBorder(new EmptyBorder(0,15,10,15));
		add(statusLbl, BorderLayout.SOUTH);
		setTitle("Battleship");
		pack();

		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
//		setVisible(false);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		
		playerPanel.update(model.getPlayerShips().getGameField());
		enemyPanel.update(model.getEnemyShips().getGameField());
		
//		updateButtonField(playerPanel.getButtonField(), model.getPlayerShips().getGameField());
//		updateButtonField(enemyPanel.getButtonField(), model.getEnemyShips().getGameField());
		
	}
	
	public void displayMessage(String message){
		chatPanel.displayMessage(message);
	}
	
	public void displayMessage(String source, String message){
		chatPanel.displayMessage(source, message);
	}
	
	public void displayPrompt(String Message){
		statusLbl.setText(Message);
	}
	
	public ChatPanel getChatPanel(){
		return chatPanel;
	}
	
	public GameFieldPanel getPlayerPanel() {
		return playerPanel;
	}
	
	public GameFieldPanel getEnemyPanel() {
		return enemyPanel;
	}
	
	public FieldButton[][] getPlayerButtons() {
		return playerButtons;
	}
	
	public FieldButton[][] getEnemyButtons() {
		return enemyButtons;
	}
	/*
	public void updateButtonField(FieldButton[][] buttonField, GameField gameField){
		for (int x=0; x < fieldSize; x++){
			for (int y=0; y < fieldSize; y++){
				Field field = gameField.getFieldAt(x, y);
				String content = field.getContent();
				String labelText = "";
				
				if (content != "water")
					buttonField[x][y].setBackground(Color.gray);
				if (content == "water")
					labelText = "";
				if (content.contains("Battleship"))
					labelText = "B";
				if (content.contains("Destroyer"))
					labelText = "D";
				if (content.contains("Cruiser"))
					labelText = "C";
				if (content.contains("Submarine"))
					labelText = "S";	
				if (content == "water" && field.isHit())
					buttonField[x][y].setBackground(Color.cyan);
				if (content != "water" && field.isHit())
					buttonField[x][y].setBackground(Color.black);
				buttonField[x][y].setText(labelText);
			}
		}
	}*/
}