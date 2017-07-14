package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import controller.ConsoleIO;
import controller.Controller;
import model.Model;
import view.View;

public class GameServer extends Thread{

	private int port = 12345;
	private ServerSocket server;
	private Socket socket;
	private ObjectOutputStream outStream;
	private ObjectInputStream inStream;
	private Model model;
	private View view;
	private Controller controller;
	private boolean running = true;
	private NetworkService netService;
	
	public GameServer(Model model, View view, Controller controller) {
		this.model = model;
		this.view = view;
		this.controller = controller;
	}
	
	public NetworkService getNetService(){
		return netService;
	}
	
	public Socket getSocket(){
		return socket;
	}
	
	@Override
	public void run(){
		try {
			server = new ServerSocket(port);
			view.displayMessage("Server started, IP: " + InetAddress.getLocalHost().getHostAddress());
			view.setTitle(view.getTitle() + " - Server");
//			System.out.println("Server started, IP: " + InetAddress.getLocalHost().getHostAddress());
			socket = server.accept();
			view.displayMessage("Connection received from: " + socket.getInetAddress().getHostName());
			view.displayPrompt("Your turn, " + model.getPlayerName());
//			System.out.println("Connection received from \n" + socket.getInetAddress().getHostName());
			
			outStream = new ObjectOutputStream(socket.getOutputStream());
			inStream = new ObjectInputStream(socket.getInputStream());
			netService = new NetworkService(model, inStream, outStream);
			netService.sendPlayerShips();
			netService.receiveEnemyShips();
			
			while (running){
				if(netService.receiveHit()){
					controller.setPlayersTurn(true);
				}
			}
			
//			readerThread = new ReaderThread(socket, gui);	
//			readerThread.start();
			
//			writerThread = new WriterThread(socket);
//			writerThread.start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
