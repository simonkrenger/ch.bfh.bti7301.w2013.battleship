package ch.bfh.bti7301.w2013.battleship.network;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Connection implements Runnable {

	private static Connection instance = null;
	final static int GAMEPORT = 42423, POOLSIZE = 1;
	private final ServerSocket listener;
	private final ExecutorService pool;
	private final String opponentIP;

	/**
	 * 
	 * @throws IOException
	 */

	private Connection() throws IOException {
		listener = new ServerSocket(GAMEPORT);
		pool = Executors.newFixedThreadPool(POOLSIZE);
		opponentIP = null;

	}

	/**
	 * 
	 * @param opponentIP
	 */

	private Connection(String opponentIP) {
		listener = null;
		this.opponentIP = opponentIP;
		pool = Executors.newFixedThreadPool(POOLSIZE);
	}

	/**
 * 
 */
	public void run() { // run the service
		try {
			for (;;) {
				if (opponentIP == null) {
					pool.execute(new ConnectionHandler(listener.accept()));
				} else {
					pool.execute(new ConnectionHandler(new Socket(opponentIP,
							GAMEPORT)));
				}
			}
		} catch (IOException ex) {
			pool.shutdown();
		}
	}

	/**
	 * 
	 * @param opponentIP
	 * @return
	 * @throws IOException
	 */

	public Connection getInstance(String opponentIP) throws IOException {

		if (instance != null) {
			return instance;
		} else if (opponentIP == null) {
			instance = new Connection();
			return instance;
		} else {
			instance = new Connection(opponentIP);
			return instance;
		}
	}

}
