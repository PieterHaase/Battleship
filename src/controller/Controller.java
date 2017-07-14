package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

import javax.swing.KeyStroke;

import model.*;
import view.*;
import network.*;


public class Controller {
	
	Model model;
	View view;
	Scanner scanner;
	boolean multiplayer = false;
	GameServer server;
	GameClient client;
	boolean playersTurn = true;
	boolean gameOver = false;
	private AI computer;
	
	int lastX = -1;
	int lastY = -1;
	int lastHitX = -1;
	int lastHitY = -1;
	
	public Controller(){
		model = new Model();
		view = new View(model);
		model.addObserver(view);
		model.update();
		
		
		computer = new AI(model.getPlayerShips().getGameField());
		for (int x = 0; x < view.getEnemyPanel().getButtonField().length; x++) {
			for (int y = 0; y < view.getEnemyPanel().getButtonField().length; y++) {
				FieldButton button = view.getEnemyPanel().getButtonField()[x][y]; 
				Field field = model.getEnemyShips().getGameField().getFieldAt(button.getXPos(), button.getYPos()); 
				button.addActionListener(listener -> {
					if (playersTurn && !gameOver){
						ConsoleIO.write("Click");
						if (!field.isHit()){
							field.markAsHit();
							model.update();
							playersTurn = false;
							if (!multiplayer){
								computerTurn();
							}
							if(multiplayer){
								server.getNetService().sendHit(field);
							}
							gameOver = model.gameOver();
						}
					}
				});
			}
		}	
		
		
		ChatPanel chatPanel = view.getChatPanel();
		chatPanel.getSendButton().addActionListener(listener -> {
			sendMessage();
		});
		
		view.getChatPanel().getTextField().addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
					sendMessage();
				}
			}
			@Override
			public void keyReleased(KeyEvent arg0) {}
			@Override
			public void keyTyped(KeyEvent arg0) {}	
		});
		
/*		ConsoleIO.write("Enter command:");
		String command = ConsoleIO.read(); 
		if (command.matches("server")){
			GameServer server = new GameServer(model);
		}
		else if (command.matches("client")){
			GameClient client = new GameClient(model);
		}*/
	}
	
	private void computerTurn(){
		computer.makeTurn();
			
//		printFields();
		model.update();
		playersTurn = true;
	}
	
	private void printFields(){
		model.getPlayerShips().getGameField().printField();
		model.getEnemyShips().getGameField().printField();
	}
	
	public void sendMessage(){
		ChatPanel chatPanel = view.getChatPanel();
		if(chatPanel.getTextField().getText().equals("server")){
			createServer();
			multiplayer = true;
		}
		else if(chatPanel.getTextField().getText().equals("client")){
			joinGame("hostIP not set");
			multiplayer = true;
		}
		else{
		chatPanel.displayMessage(model.getPlayerName(), chatPanel.getTextField().getText());
		}
		chatPanel.getTextField().setText("");
	}
	
	public void createServer(){
		server = new GameServer(model, view);
		server.start();
	}
	public void joinGame(String hostIP){
		client = new GameClient(model, view, hostIP);
		client.start();
	}
	
	public static void main(String[] args) {
		new Controller();
	}
	
}
