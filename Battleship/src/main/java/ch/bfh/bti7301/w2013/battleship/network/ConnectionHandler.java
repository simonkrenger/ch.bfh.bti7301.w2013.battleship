package ch.bfh.bti7301.w2013.battleship.network;

import java.io.*;
import java.net.*;


public class ConnectionHandler implements Runnable {

		
	private Socket connectionSocket;
	private ObjectInputStream in;
	private ObjectOutputStream out;


	public ConnectionHandler(Connection connection, Socket socket) throws IOException {
		setConnectionSocket(socket);
		setIn(new ObjectInputStream(connectionSocket.getInputStream()));
		setOut(new ObjectOutputStream(connectionSocket.getOutputStream()));
		run();
		connection.setConnectionState(ConnectionState.CONNECTED);
		
	}
	

	public void run() {

		while (true) {
			try {
				Object inputObject = in.readObject();
				receiveObject(inputObject);

			} catch (IOException e) {
				e.printStackTrace();

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public void sendObject(Object outgoingObject) {
		try {
			out.writeObject(outgoingObject);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void receiveObject(Object receivedObject) {
		Connection.receiveObjectToGame(receivedObject);	
	}
	
	public void setIn(ObjectInputStream in) {
		this.in = in;
	}
	
	public void setOut(ObjectOutputStream out) {
		this.out = out;
	}
	

	public void setConnectionSocket(Socket connectionSocket) {
		this.connectionSocket = connectionSocket;
	}
	
	public void cleanUp(){
		try {
			in.close();
			out.close();
			connectionSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
