package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import controller.ConsoleIO;
import model.*;

public class GameClient {
	
	private String hostIP = "141.22.95.51";
	private int port = 12345;
	private Socket socket;
	private ObjectInputStream inStream;
	private ObjectOutputStream outStream;
	private Model model;
	
	
	public GameClient(Model model/*String hostIP*/) {
//		this.hostIP = hostIP;
		this.model = model;
		try {
			//hostIP = InetAddress.getLocalHost().toString();
			socket = new Socket(InetAddress.getByName(hostIP), port);			
			inStream = new ObjectInputStream(socket.getInputStream());
			outStream = new ObjectOutputStream(socket.getOutputStream());
			NetworkService netService = new NetworkService(model, inStream, outStream);
			netService.receiveEnemyShips();
			
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
