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

import model.Field;
import model.Model;
import model.Ship;
import model.ShipManager;
import network.GameClient;
import network.GameServer;
import network.Message;
import network.NetworkService;
import view.ChatPanel;
import view.ClientWindow;
import view.FieldButton;
import view.GUISettings;
import view.Rules;
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

	private boolean shipPlaced = false;
	private boolean allShipsPlaced = false;
	private boolean enemyShipsReceived = false;

	private int lastX = -1;
	private int lastY = -1;
	private int lastHitX = -1;
	private int lastHitY = -1;

	private int x = 0;
	private int y = 0;

	private String orientation = "vertical";

	private ArrayList<Ship[]> ships;
	private ArrayList<Ship> shipList = new ArrayList<Ship>();

	public Controller() {
		model = new Model();
		view = new View(model);
		model.addObserver(view);
		model.update();
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

		JMenuItem loadGame = view.getLoadGame();
		loadGame.addActionListener(listener -> loadGame());
		JMenuItem saveGame = view.getSaveGame();
		saveGame.addActionListener(e -> saveGame());
		view.getNewGame().addActionListener(listener -> {
			if (netService != null)
				netService.sendMessage("~NEWGAME");
			newGame();
		});
		view.getCreateServer().addActionListener(listener -> createServer());
		view.getJoinGame().addActionListener(listener -> joinGame(""));

		view.getPlaceRandom().addActionListener(listener -> {
			model.getPlayerShips().placeRandomShips();
			model.update();
			shipList.clear();
			removeActionListener();
			if (multiplayer)
				multiplayerGame();
			else
				newSingleplayerGame();
		});

		JMenuItem showRules = view.getShowRules();
		showRules.addActionListener(listener -> new Rules());
	}

	private void newSingleplayerGame() {
		setPlayersTurn(true);
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
			if (multiplayer) {
				netService.sendMessage(new Message(model.getPlayerName(), chatPanel.getTextField().getText()));
			}

		}
		chatPanel.getTextField().setText("");
	}

	public void newGame() {
		model.reset();
		view.getPlaceRandom().setEnabled(true);
		ships = model.getPlayerShips().getShipArrayList();
		shipList.clear();
		for (int k = 0; k < ships.size(); k++) {
			for (int i = 0; i < ships.get(k).length; i++) {
				shipList.add(ships.get(k)[i]);
			}
		}
		if (!allShipsPlaced)
			shipPlacement();
		if (!multiplayer) {
			model.getEnemyShips().placeRandomShips();
			model.update();
		}

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
				oos.writeObject(playersTurn); // wer dran ist, werden
												// gespeichert
				oos.flush();
				oos.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage()); // Fehlermeldung
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
				allShipsPlaced = true;
				newGame();
				FileInputStream fis = new FileInputStream(file.getPath());
				ObjectInputStream ois = new ObjectInputStream(fis);
				ShipManager playerShips = (ShipManager) ois.readObject();
				ShipManager enemyShips = (ShipManager) ois.readObject();
				boolean playersTurn = (boolean) ois.readObject();
				model.setPlayerShips(playerShips);
				model.setEnemyShips(enemyShips);
				this.playersTurn = playersTurn;
				if (netService != null) {
					netService.sendMessage("~NEWGAME");
					netService.sendEnemyShips();
					netService.sendPlayerShips();
				}
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, e.getMessage()); // Fehlermeldung
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage()); // Fehlermeldung
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public void createServer() {
		multiplayer = true;
		server = new GameServer(model, view, this);
		server.start();
		// netService = server.getNetService();
		networkRole = SERVER;
	}

	public void joinGame(String hostIP) {
		multiplayer = true;
		client = new GameClient(model, view, this, hostIP);
		ClientWindow clientWindow = new ClientWindow(this, client);
		networkRole = CLIENT;
		playersTurn = false;
	}

	public void shipPlacement() {
		placeShip(shipList.get(0));
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

	public void placeShip(Ship ship) {
		view.displayPrompt("Place your " + ship.getType() + " '" + ship.getName() + "'");
		FieldButton[][] buttonField = view.getPlayerPanel().getButtonField();
		for (int x = 0; x < buttonField.length; x++) {
			for (int y = 0; y < buttonField.length; y++) {
				FieldButton button = buttonField[x][y];
				if (button.getMouseListeners().length > 0)
					button.removeMouseListener(button.getMouseListeners()[0]);
				button.addMouseListener(new MouseListener() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (SwingUtilities.isRightMouseButton(e)) {
							highlight(buttonField, button, ship.getLength(), orientation, GUISettings.gridColor, 1);
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
									highlight(buttonField, button, ship.getLength(), orientation, GUISettings.gridColor,
											1);
									shipPlaced = true;
									shipList.remove(0);

									placeShip(shipList.get(0));
								} else {
									removeActionListener();
									highlight(buttonField, button, ship.getLength(), orientation, GUISettings.gridColor,
											1);
									if (multiplayer) {
										netService.sendPlayerShips();
										allShipsPlaced = true;
									} else
										newSingleplayerGame();
								}
							} else
								ConsoleIO.write(button.getXPos());
						}
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						highlight(buttonField, button, ship.getLength(), orientation, GUISettings.cursorColor, 2);
					}

					@Override
					public void mouseExited(MouseEvent e) {
						highlight(buttonField, button, ship.getLength(), orientation, GUISettings.gridColor, 1);
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

	public void removeActionListener() {
		FieldButton[][] buttonField = view.getPlayerPanel().getButtonField();
		for (int x = 0; x < buttonField.length; x++) {
			for (int y = 0; y < buttonField.length; y++) {
				buttonField[x][y].removeMouseListener(buttonField[x][y].getMouseListeners()[0]);
			}
		}
	}

	private void testMethod() {
		netService = server.getNetService();
		netService.sendPlayerShips();
	}

	public void multiplayerGame() {
		if (playersTurn)
			view.displayPrompt("Your turn, " + model.getPlayerName());
		else
			view.displayPrompt("Wait for " + model.getEnemyName() + "'s turn...");
		for (int x = 0; x < view.getEnemyPanel().getButtonField().length; x++) {
			for (int y = 0; y < view.getEnemyPanel().getButtonField().length; y++) {
				FieldButton button = view.getEnemyPanel().getButtonField()[x][y];
				Field field = model.getEnemyShips().getGameField().getFieldAt(x, y);
				button.addActionListener(listener -> {
					if (playersTurn && !gameOver) {
						if (!field.isHit()) {
							field.markAsHit();
							netService.sendHit(field);
							model.update();
							setPlayersTurn(false);
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
						button.setBorder(BorderFactory.createLineBorder(GUISettings.gridColor));

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

	public Model getModel() {
		return model;
	}

	public View getView() {
		return view;
	}

	public NetworkService getNetService() {
		return netService;
	}

	public boolean enemyShipsReceived() {
		return enemyShipsReceived;
	}

	public boolean getPlayersTurn() {
		return playersTurn;
	}

	public void setAllShipsPlaced(boolean b) {
		allShipsPlaced = b;
	}

	public void setPlayersTurn(boolean bool) {
		if (bool == true)
			view.displayPrompt("Your turn, " + model.getPlayerName());
		else
			view.displayPrompt("Wait for " + model.getEnemyName() + "'s turn...");
		playersTurn = bool;
	}
	
	public void setNetService(NetworkService netService) {
		this.netService = netService;
	}

	public void setEnemyShipsReceived(boolean received) {
		enemyShipsReceived = received;
	}

	public static void main(String[] args) {
		new Controller();
	}

}
