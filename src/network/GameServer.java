package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import controller.Controller;
import model.Model;
import view.ServerWindow;
import view.View;

public class GameServer extends Thread {

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
	private boolean shipsReceived = false;

	public GameServer(Model model, View view, Controller controller) {
		this.model = model;
		this.view = view;
		this.controller = controller;
		controller.newGame();
	}

	public NetworkService getNetService() {
		return netService;
	}

	public Socket getSocket() {
		return socket;
	}

	@Override
	public void run() {
		try {
			server = new ServerSocket(port);
			ServerWindow serverWindow = new ServerWindow(controller, this, InetAddress.getLocalHost().getHostAddress());
			view.displayMessage("Server started, IP: " + InetAddress.getLocalHost().getHostAddress());
			view.setTitle(view.getTitle() + " - Server");
			socket = server.accept();
			serverWindow.connectionReceived();
			view.setEnabled(true);
			view.displayMessage("Connection received from: " + socket.getInetAddress().getHostName());

			outStream = new ObjectOutputStream(socket.getOutputStream());
			inStream = new ObjectInputStream(socket.getInputStream());
			netService = new NetworkService(controller, inStream, outStream);
			controller.setNetService(netService);

			while (running) {
				if (netService.receive()) {

				}
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
