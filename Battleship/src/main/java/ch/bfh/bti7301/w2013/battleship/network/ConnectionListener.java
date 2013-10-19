package ch.bfh.bti7301.w2013.battleship.network;

import java.io.IOException;
import java.net.ServerSocket;

public class ConnectionListener extends Thread {

	private ServerSocket listener;
	private Connection connection;

	public ConnectionListener(Connection connection) throws IOException {
		listener = new ServerSocket(Connection.GAMEPORT);
		// opponentIP = null;
		this.connection = connection;
	}

	public void run() { // run the service
		try {
			while (true) {
				connection.acceptOpponent(listener.accept());
				listener.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
