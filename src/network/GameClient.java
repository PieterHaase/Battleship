package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import controller.ConsoleIO;
import controller.Controller;
import model.*;
import view.View;

public class GameClient extends Thread{
	
	private String hostIP;
	private int port = 12345;
	private Socket socket;
	private ObjectInputStream inStream;
	private ObjectOutputStream outStream;
	private Model model;
	private View view;
	private Controller controller;
	private NetworkService netService;
	private boolean running = true;
	
	
	public GameClient(Model model, View view, Controller controller, String hostIP) {
//		this.hostIP = hostIP;
		this.model = model;
		this.view = view;
		this.controller = controller;
		this.hostIP = hostIP;
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
			if(hostIP.equals(""))
				hostIP = InetAddress.getLocalHost().getHostAddress();
			socket = new Socket(InetAddress.getByName(hostIP), port);
			view.displayMessage("Connected to: " + hostIP);
			view.setTitle(view.getTitle() + " - Client");
			inStream = new ObjectInputStream(socket.getInputStream());
			outStream = new ObjectOutputStream(socket.getOutputStream());
			netService = new NetworkService(model, inStream, outStream);
			netService.receiveEnemyShips();
			netService.sendPlayerShips();
			
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
