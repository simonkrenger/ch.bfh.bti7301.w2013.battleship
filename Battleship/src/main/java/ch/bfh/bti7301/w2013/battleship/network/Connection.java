package ch.bfh.bti7301.w2013.battleship.network;

import java.io.*;
import java.net.*;

import ch.bfh.bti7301.w2013.battleship.game.Missile;

public class Connection implements Runnable {

	final static int GAMEPORT = 42423;

	public ConnectionState connectionState;

	private static Connection instance;
	private ServerSocket listener;
	private String opponentIP;
	private Socket connection;
	private ObjectInputStream in;
	private ObjectOutputStream out;

	private ConnectionHandler handler;

	/**
	 * 
	 * @throws IOException
	 */
	private Connection() throws IOException {
		listener = new ServerSocket(GAMEPORT);
		opponentIP = null;
		run();

	}

	/**
	 * 
	 * @param opponentIP
	 */
	private Connection(String opponentIP) {
		listener = null;
		this.opponentIP = opponentIP;
		run();
	}

	/**
	 * Try to connect with an opponent.
	 */
	public void run() { // run the service
		try {
			for (;;) {
				if (opponentIP == null) {
					connection = listener.accept();
					opponentIP = connection.getInetAddress().getHostAddress();
					connectionState = ConnectionState.LISTENING;

				} else {
					connection = new Socket(opponentIP, GAMEPORT);
				}

				out = new ObjectOutputStream(connection.getOutputStream());
				in = new ObjectInputStream(connection.getInputStream());
				handler = new ConnectionHandler(in, instance);
				connectionState = ConnectionState.CONNECTED;
				listener.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
			cleanUp();
		}
	}

	/**
	 * 
	 * @param opponentIP
	 * @return
	 * @throws IOException
	 */
	public static Connection getInstance() throws IOException {

		if (instance != null) {
			return instance;
		} else {
			instance = new Connection();
			return instance;
//		} else {
//			instance = new Connection(opponentIP);
//			return instance;
		}
	}

	/**
	 * 
	 * @param missile
	 */
	public void placeShot(Missile missile) {
		ConnectionHandler.sendObject(out, missile);
	}

	/**
	 * 
	 * @param ready
	 */

	public void sendReady(String ready) {
		ConnectionHandler.sendObject(out, ready);

	}

	/**
	 * 
	 * @param end
	 */
	public void sendEnd(String end) {
		ConnectionHandler.sendObject(out, end);

	}

	/**
	 * 
	 * @param start
	 */
	public void sendStart(String start) {
		ConnectionHandler.sendObject(out, start);
	}

	/**
	 * 
	 * @return
	 */
	public Object receiveObjectToGame(Object object) {

		// simon: wohin damit?
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public ConnectionState getConnectionState() {
		return connectionState;
	}

	/**
	 * 
	 */
	private void cleanUp() {
		if (connection != null && !connection.isClosed()) {
			// Make sure that the socket, if any, is closed.
			try {
				connection.close();
			} catch (IOException e) {
			}
		}
		connectionState = ConnectionState.CLOSED;
		instance = null;
		listener = null;
		opponentIP = null;
		connection = null;
		in = null;
		out = null;

	}
}
