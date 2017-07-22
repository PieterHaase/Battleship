package controller;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.w3c.dom.Text;

import model.Field;
import model.Model;
import model.Ship;
import model.ShipManager;
import network.GameClient;
import network.GameServer;
import network.NetworkService;
import view.ChatPanel;
import view.FieldButton;
import view.GUISettings;
import view.View;

public class Controller {

	private static final int SERVER = 0;
	private static final int CLIENT = 1;
	Model model;
	View view;
	Scanner scanner;
	boolean multiplayer = false;
	private GameServer server;
	private GameClient client;
	private boolean playersTurn = true;
	private boolean gameOver = false;
	private AI computer;
	private NetworkService netService;
	private int networkRole;
	// <<<<<<< HEAD

	private boolean shipPlaced = false;;

	// =======

	// >>>>>>> 6cfda8bd082576052e2c3144e5679fe61e9f2bad
	int lastX = -1;
	int lastY = -1;
	int lastHitX = -1;
	int lastHitY = -1;
	// <<<<<<< HEAD

	int x = 0;
	int y = 0;
	private String orientation = "vertical";

	ArrayList<Ship[]> ships;
	ArrayList<Ship> shipList = new ArrayList<Ship>();

	// public Controller(){
	// =======

	private Text textContent;

	public Controller() {
		// >>>>>>> 6cfda8bd082576052e2c3144e5679fe61e9f2bad
		model = new Model();
		view = new View(model);
		model.addObserver(view);
		model.update();

		// <<<<<<< HEAD

		// =======

		// >>>>>>> 6cfda8bd082576052e2c3144e5679fe61e9f2bad
		ChatPanel chatPanel = view.getChatPanel();
		chatPanel.getSendButton().addActionListener(listener -> {
			sendMessage();
		});

		view.getChatPanel().getTextField().addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					sendMessage();
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
			}
		});
		// <<<<<<< HEAD

		/*
		 * ConsoleIO.write("Enter command:"); String command = ConsoleIO.read();
		 * if (command.matches("server")){ GameServer server = new
		 * GameServer(model); } else if (command.matches("client")){ GameClient
		 * client = new GameClient(model); }
		 */

		// =======

		/*
		 * ConsoleIO.write("Enter command:"); String command = ConsoleIO.read();
		 * if (command.matches("server")){ GameServer server = new
		 * GameServer(model); } else if (command.matches("client")){ GameClient
		 * client = new GameClient(model); }
		 */

		// menuItems (load&save) mit Actionlistener aufrufen
		JMenuItem loadGame = view.getLoadGame();
		loadGame.addActionListener(listener -> loadGame());
		JMenuItem saveGame = view.getSaveGame();
		saveGame.addActionListener(e -> saveGame());
		view.getNewGame().addActionListener(listener -> newGame());
		view.getCreateServer().addActionListener(listener -> createServer());
		view.getJoinGame().addActionListener(listener -> joinGame(""));
		JMenuItem showRules = view.getShowRules();
		//showRules.addActionListener(listener -> new Rules());
		// >>>>>>> 6cfda8bd082576052e2c3144e5679fe61e9f2bad
	}

	private void newSingleplayerGame() {
		computer = new AI(model.getPlayerShips().getGameField());
		for (int x = 0; x < view.getEnemyPanel().getButtonField().length; x++) {
			for (int y = 0; y < view.getEnemyPanel().getButtonField().length; y++) {
				FieldButton button = view.getEnemyPanel().getButtonField()[x][y];
				Field field = model.getEnemyShips().getGameField().getFieldAt(button.getXPos(), button.getYPos());
				button.addActionListener(listener -> {
					if (playersTurn && !gameOver) {
						ConsoleIO.write("Click");
						if (!field.isHit()) {
							field.markAsHit();
							model.update();
							playersTurn = false;
							view.displayPrompt("Wait for " + model.getEnemyName() + "'s turn...");
							if (!multiplayer) {
								computerTurn();
							}
							if (multiplayer) { // DAS MUSS AUSGELAGERT WERDEN
								if (networkRole == SERVER) {
									netService = server.getNetService();
								}
								if (networkRole == CLIENT) {
									netService = client.getNetService();
								}
								ConsoleIO.write("Does this even work?");
								netService.sendHit(field);
							}
							gameOver = model.gameOver();
						}
					}
				});

				button.addMouseListener(new MouseListener() {

					@Override
					public void mouseClicked(MouseEvent e) {

					}

					@Override
					public void mouseEntered(MouseEvent e) {
						button.setBorder(BorderFactory.createLineBorder(Color.darkGray, 2));

					}

					@Override
					public void mouseExited(MouseEvent e) {
						button.setBorder(BorderFactory.createLineBorder(Color.white));

					}

					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub

					}

				});
			}
		}
	}

	private void computerTurn() {
		computer.makeTurn();

		// printFields();
		model.update();
		playersTurn = true;
	}

	private void printFields() {
		model.getPlayerShips().getGameField().printField();
		model.getEnemyShips().getGameField().printField();
	}

	public void sendMessage() {
		ChatPanel chatPanel = view.getChatPanel();
		if (chatPanel.getTextField().getText().equals("server")) {
			createServer();

		} else if (chatPanel.getTextField().getText().contains("client")) {
			String hostIP = chatPanel.getTextField().getText().substring(7);

		} else if (chatPanel.getTextField().getText().contains("client")) {
			String hostIP = "";
			if (chatPanel.getTextField().getText().length() > 6)
				hostIP = chatPanel.getTextField().getText().substring(7);

			chatPanel.displayMessage(hostIP);
			joinGame(hostIP);
		} else {
			chatPanel.displayMessage(model.getPlayerName(), chatPanel.getTextField().getText());
		}
		chatPanel.getTextField().setText("");
	}

	public void newGame() {
		model.getPlayerShips().newGameField();
		model.update();
		ships = model.getPlayerShips().getShipArrayList();
		shipList.clear();
		view.displayMessage("size" + shipList.size());
		for (int k = 0; k < ships.size(); k++) {
			for (int i = 0; i < ships.get(k).length; i++) {
				shipList.add(ships.get(k)[i]);
			}
		}
		view.displayMessage("size" + shipList.size());
		shipPlacement();
		if (!multiplayer)
			newSingleplayerGame();
	}

	public void saveGame() {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Save Game");
		int result = fc.showSaveDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			try {
				FileOutputStream fos = new FileOutputStream(file.getPath());
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(model.getPlayerShips()); // Spielerfeld
				oos.writeObject(model.getEnemyShips()); // Gegnerfeld
				oos.writeObject(playersTurn); // wer dran ist, werden gespeichert
				oos.flush();
				oos.close();
			} catch (Exception e) {
				// Fehlermeldung
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
	}

	public void loadGame() {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Load Game");
		int result = fc.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			try {
				FileInputStream fis = new FileInputStream(file.getPath());
				ObjectInputStream ois = new ObjectInputStream(fis);
				ShipManager playerShips = (ShipManager) ois.readObject();
				ShipManager enemyShips = (ShipManager) ois.readObject();
				boolean playersTurn = (boolean) ois.readObject();
				model.setPlayerShips(playerShips);
				model.setEnemyShips(enemyShips);
				this.playersTurn = playersTurn;
			} catch (FileNotFoundException e) {
				// Fehlermeldung
				JOptionPane.showMessageDialog(null, e.getMessage());
			} catch (IOException e) {
				// Fehlermeldung
				JOptionPane.showMessageDialog(null, e.getMessage());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void createServer() {
		multiplayer = true;
		server = new GameServer(model, view, this);
		server.start();
		networkRole = SERVER;
	}

	public void joinGame(String hostIP) {
		multiplayer = true;
		client = new GameClient(model, view, this, hostIP);
		networkRole = CLIENT;
		playersTurn = false;
		view.displayPrompt("Wait for " + model.getEnemyName() + "'s turn...");
	}

	public View getView() {
		return view;
	}

	public static void main(String[] args) {
		new Controller();
	}

	public void setPlayersTurn(boolean bool) {
		if (bool == true)
			view.displayPrompt("Your turn, " + model.getPlayerName());
		else
			view.displayPrompt("Wait for " + model.getEnemyName() + "'s turn...");
		playersTurn = bool;
	}
	// <<<<<<< HEAD

	public void doNothing(int x) {
		if (x == 20000000) {
			// view.displayMessage("waiting");
			x = 0;
		}
		x++;
	}

	public void placeShip(Ship ship) {
		addActionListeners(ship);
	}

	public void shipPlacement() {

		placeShip(shipList.get(0));

		/*
		 * for(int k=0; k < ships.size(); k++){ for(int i=0; i <
		 * ships.get(k).length; i++){ view.displayMessage("Place " +
		 * ships.get(k)[i].getType() + " " + ships.get(k)[i].getName());
		 * shipPlaced = false; addActionListener(ships.get(k)[i]); int x = 0;
		 * while(!shipPlaced){ if(x == 20000000){ doNothing(x); x = 0; } x++; }
		 * view.displayMessage("placed"); } } view.displayMessage(
		 * "All ships placed"); if(multiplayer) netService.sendPlayerShips();
		 * 
		 * 
		 * 
		 * /*
		 * 
		 * view.displayMessage("Place your Destroyers"); for(int i=0; i <
		 * destroyers.length; i++){ shipPlaced = false; addActionListener(); int
		 * x = 0; while(!shipPlaced){ if(x == 20000000){ doNothing(x); x = 0; }
		 * x++; } view.displayMessage(battleships[i].getType() + " " +
		 * battleships[i].getName() + " placed"); }
		 */
		/*
		 * addActionListener(); =======
		 * 
		 * public void shipPlacement() { >>>>>>>
		 * 6cfda8bd082576052e2c3144e5679fe61e9f2bad FieldButton[][] buttonField
		 * = view.getPlayerPanel().getButtonField(); for (int x = 0; x <
		 * buttonField.length; x++) { for (int y = 0; y < buttonField.length;
		 * y++) { FieldButton button = buttonField[x][y]; <<<<<<< HEAD // Field
		 * field =
		 * model.getPlayerShips().getGameField().getFieldAt(button.getXPos(),
		 * button.getYPos()); button.addMouseListener(new MouseListener(){
		 * 
		 * // Color prevColor = button.getBackground(); ======= Field field =
		 * model.getPlayerShips().getGameField().getFieldAt(button.getXPos(),
		 * button.getYPos()); button.addMouseListener(new MouseListener() {
		 * 
		 * Color prevColor = button.getBackground(); >>>>>>>
		 * 6cfda8bd082576052e2c3144e5679fe61e9f2bad
		 * 
		 * @Override public void mouseClicked(MouseEvent e) { // TODO
		 * Auto-generated method stub
		 * 
		 * }
		 * 
		 * @Override public void mouseEntered(MouseEvent e) { <<<<<<< HEAD
		 * highlight(buttonField, button, 4, "vertical",
		 * GUISettings.cursorColor, 2); =======
		 * button.setBackground(Color.blue);
		 * 
		 * >>>>>>> 6cfda8bd082576052e2c3144e5679fe61e9f2bad }
		 * 
		 * @Override public void mouseExited(MouseEvent e) { <<<<<<< HEAD
		 * highlight(buttonField, button, 4, "vertical", Color.white, 1);
		 * 
		 * ======= button.setBackground(prevColor);
		 * 
		 * >>>>>>> 6cfda8bd082576052e2c3144e5679fe61e9f2bad }
		 * 
		 * @Override public void mousePressed(MouseEvent e) { // TODO
		 * Auto-generated method stub
		 * 
		 * }
		 * 
		 * @Override public void mouseReleased(MouseEvent e) { // TODO
		 * Auto-generated method stub
		 * 
		 * }
		 * 
		 * }); } <<<<<<< HEAD }
		 */
	}

	public void highlight(FieldButton[][] buttonField, FieldButton button, int size, String orientation, Color color,
			int borderSize) {
		button.setBorder(BorderFactory.createLineBorder(color, borderSize));
		if (orientation == "horizontal") {
			if (size > 1) {
				if (button.getXPos() + 1 < buttonField.length)
					buttonField[button.getXPos() + 1][button.getYPos()]
							.setBorder(BorderFactory.createLineBorder(color, borderSize));
				else
					buttonField[button.getXPos() - 1][button.getYPos()]
							.setBorder(BorderFactory.createLineBorder(color, borderSize));
			}
			if (size > 2) {
				if (button.getXPos() + 2 < buttonField.length)
					buttonField[button.getXPos() + 2][button.getYPos()]
							.setBorder(BorderFactory.createLineBorder(color, borderSize));
				if (button.getXPos() + 2 == buttonField.length)
					buttonField[button.getXPos() - 1][button.getYPos()]
							.setBorder(BorderFactory.createLineBorder(color, borderSize));
				if (button.getXPos() + 2 == buttonField.length + 1)
					buttonField[button.getXPos() - 2][button.getYPos()]
							.setBorder(BorderFactory.createLineBorder(color, borderSize));
			}
			if (size > 3) {
				if (button.getXPos() + 3 < buttonField.length)
					buttonField[button.getXPos() + 2][button.getYPos()]
							.setBorder(BorderFactory.createLineBorder(color, borderSize));
				if (button.getXPos() + 3 == buttonField.length)
					buttonField[button.getXPos() - 1][button.getYPos()]
							.setBorder(BorderFactory.createLineBorder(color, borderSize));
				if (button.getXPos() + 3 == buttonField.length + 1)
					buttonField[button.getXPos() - 2][button.getYPos()]
							.setBorder(BorderFactory.createLineBorder(color, borderSize));
				if (button.getXPos() + 3 < buttonField.length)
					buttonField[button.getXPos() + 3][button.getYPos()]
							.setBorder(BorderFactory.createLineBorder(color, borderSize));
				if (button.getXPos() + 3 == buttonField.length + 2)
					buttonField[button.getXPos() - 3][button.getYPos()]
							.setBorder(BorderFactory.createLineBorder(color, borderSize));
			}
		}
		if (orientation == "vertical") {
			if (size > 1) {
				if (button.getYPos() + 1 < buttonField.length)
					buttonField[button.getXPos()][button.getYPos() + 1]
							.setBorder(BorderFactory.createLineBorder(color, borderSize));
				else
					buttonField[button.getXPos()][button.getYPos() - 1]
							.setBorder(BorderFactory.createLineBorder(color, borderSize));
			}
			if (size > 2) {
				if (button.getYPos() + 2 < buttonField.length)
					buttonField[button.getXPos()][button.getYPos() + 2]
							.setBorder(BorderFactory.createLineBorder(color, borderSize));
				if (button.getYPos() + 2 == buttonField.length)
					buttonField[button.getXPos()][button.getYPos() - 1]
							.setBorder(BorderFactory.createLineBorder(color, borderSize));
				if (button.getYPos() + 2 == buttonField.length + 1)
					buttonField[button.getXPos()][button.getYPos() - 2]
							.setBorder(BorderFactory.createLineBorder(color, borderSize));
			}
			if (size > 3) {
				if (button.getYPos() + 3 < buttonField.length)
					buttonField[button.getXPos()][button.getYPos() + 2]
							.setBorder(BorderFactory.createLineBorder(color, borderSize));
				if (button.getYPos() + 3 == buttonField.length)
					buttonField[button.getXPos()][button.getYPos() - 1]
							.setBorder(BorderFactory.createLineBorder(color, borderSize));
				if (button.getYPos() + 3 == buttonField.length + 1)
					buttonField[button.getXPos()][button.getYPos() - 2]
							.setBorder(BorderFactory.createLineBorder(color, borderSize));
				if (button.getYPos() + 3 < buttonField.length)
					buttonField[button.getXPos()][button.getYPos() + 3]
							.setBorder(BorderFactory.createLineBorder(color, borderSize));
				if (button.getYPos() + 3 == buttonField.length + 2)
					buttonField[button.getXPos()][button.getYPos() - 3]
							.setBorder(BorderFactory.createLineBorder(color, borderSize));
			}
		}
	}

	public void addActionListeners(Ship ship) {
		FieldButton[][] buttonField = view.getPlayerPanel().getButtonField();
		for (x = 0; x < buttonField.length; x++) {
			for (y = 0; y < buttonField.length; y++) {
				FieldButton button = buttonField[x][y];
				// Field field =
				// model.getPlayerShips().getGameField().getFieldAt(button.getXPos(),
				// button.getYPos());
				if (button.getMouseListeners().length > 0)
					button.removeMouseListener(button.getMouseListeners()[0]);
				button.addMouseListener(new MouseListener() {

					// Color prevColor = button.getBackground();

					@Override
					public void mouseClicked(MouseEvent e) {
						if (SwingUtilities.isRightMouseButton(e)) {
							highlight(buttonField, button, ship.getLength(), orientation, Color.white, 1);
							if (orientation == "horizontal")
								orientation = "vertical";
							else
								orientation = "horizontal";
							highlight(buttonField, button, ship.getLength(), orientation, GUISettings.cursorColor, 2);
						} else {
							if (model.getPlayerShips().getGameField().placeShip(ship, button.getXPos(),
									button.getYPos(), orientation)) {
								model.update();
								model.getPlayerShips().getGameField().printField();
								if (shipList.size() > 1) {
									highlight(buttonField, button, ship.getLength(), orientation, Color.white, 1);
									shipPlaced = true;
									view.displayMessage("placing ship");
									shipList.remove(0);

									placeShip(shipList.get(0));
								} else {
									removeActionListener();
									highlight(buttonField, button, ship.getLength(), orientation, Color.white, 1);
								}
								view.displayMessage("ArraySize: " + shipList.size());
							} else
								view.displayMessage("not allowed");
							ConsoleIO.write(button.getXPos());
						}
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						highlight(buttonField, button, ship.getLength(), orientation, GUISettings.cursorColor, 2);
					}

					@Override
					public void mouseExited(MouseEvent e) {
						highlight(buttonField, button, ship.getLength(), orientation, Color.white, 1);

					}

					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub

					}

				});
			}
		}
		x = 0;
		y = 0;

	}

	public void removeActionListener() {
		FieldButton[][] buttonField = view.getPlayerPanel().getButtonField();
		for (x = 0; x < buttonField.length; x++) {
			for (y = 0; y < buttonField.length; y++) {
				buttonField[x][y].removeMouseListener(buttonField[x][y].getMouseListeners()[0]);
			}
		}
	}
	// =======
	// }
	// }
	// >>>>>>> 6cfda8bd082576052e2c3144e5679fe61e9f2bad
}
