package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import controller.ConsoleIO;
import model.Model;

public class GameServer {

	private int port = 12345;
	private ServerSocket server;
	private Socket socket;
	private ObjectOutputStream outStream;
	private ObjectInputStream inStream;
	private Model model;
	
	public GameServer(Model model) {
		this.model = model;
		try {
			server = new ServerSocket(port);
			System.out.println("Server started, IP: " + InetAddress.getLocalHost().getHostAddress());
			socket = server.accept();
			System.out.println("Connection received from \n" + socket.getInetAddress().getHostName());
			
			outStream = new ObjectOutputStream(socket.getOutputStream());
			inStream = new ObjectInputStream(socket.getInputStream());
			NetworkService netService = new NetworkService(model, inStream, outStream);
			netService.sendPlayerShips();
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
