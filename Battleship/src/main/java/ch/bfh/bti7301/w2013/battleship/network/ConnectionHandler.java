package ch.bfh.bti7301.w2013.battleship.network;

import java.io.*;
import java.net.*;

import ch.bfh.bti7301.w2013.battleship.game.Missile;

public class ConnectionHandler implements Runnable {

	private final Socket connection;

	ConnectionHandler(Socket socket) {
		this.connection = socket;
	}

	public void run() {

		// connection.getInputStream()
		// connection.set

	}

	public Missile placeShot(Missile missile) {
		sendObject(connection, missile);
		return null;
	}

	private static boolean sendObject(Socket socket, Object missile) {

		// ObjectOutputStream out =

		return false;

	}

	private static boolean receiveObject(Socket socket) {

		// connectio

		// ObjectInputStream = new ObjectInputStream(connection);

		return false;

	}

	private ObjectInputStream getInputStream(Socket socket) {
		return null;
	}

	private static ObjectOutputStream getOutputStream(Socket socket) {
		return null;

	}
}
