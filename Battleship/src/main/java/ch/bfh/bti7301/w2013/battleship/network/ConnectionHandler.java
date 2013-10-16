package ch.bfh.bti7301.w2013.battleship.network;

import java.io.*;
import java.net.*;

import ch.bfh.bti7301.w2013.battleship.game.Missile;

public class ConnectionHandler implements Runnable {

	private final Socket connection;
	public ObjectInputStream in;
	public ObjectOutputStream out;

	ConnectionHandler(Socket socket) {
		this.connection = socket;
		try {
			out = new ObjectOutputStream(connection.getOutputStream());
			in = new ObjectInputStream(connection.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		run();
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

	public Object placeShot(Missile missile) {
		return sendObject(missile);
	}

	public Object sendReady(String ready) {
		return sendObject(ready);

	}

	public Object sendStart(String start) {
		return sendObject(start);
	}

	private Object sendObject(Object outgoingObject) {

		try {
			out.writeObject(outgoingObject);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;

	}

	private static boolean receiveObject(Object receivedObject) {

		return false;

	}

	private ObjectInputStream getInputStream() {
		return in;
	}

	private ObjectOutputStream getOutputStream() {
		return out;
	}
}
