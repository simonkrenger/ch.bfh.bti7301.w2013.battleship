package ch.bfh.bti7301.w2013.battleship.network;

import java.io.*;
import java.net.*;
import java.util.Date;

import ch.bfh.bti7301.w2013.battleship.game.Missile;

public class Connection {

	final static int GAMEPORT = 42423;
	public Socket connection;

	public Connection() {
		connection = createServerSocket(GAMEPORT);
	}

	public Connection(String opponentIP) throws UnknownHostException {

		try {
			Socket player = new Socket(opponentIP, GAMEPORT);
			connection = player;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	private static Socket createServerSocket(int gameport) {

		ServerSocket listener = null;
		try {
			listener = new ServerSocket(gameport);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while (true) {
			try {
				Socket commSocket = listener.accept();
				listener.close();
				return commSocket;		
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 * private static Socket createClientSocket(String opponentIP) {
	 * 
	 * Socket bsSocket = null;
	 * 
	 * try { bsSocket = new Socket(opponentIP, GAMEPORT);
	 * 
	 * } catch (UnknownHostException e) {
	 * System.err.println("Don't know about host: " + opponentIP);
	 * System.exit(1); }
	 * 
	 * return bsSocket; }
	 */
	
	

	private static boolean sendObject(Socket socket, Object missile) {
		// checkNetwork connection

		return false;

	}

	private static boolean receiveObject(Socket socket) {

		return false;

	}

	/**
	 * 
	 * @param missile
	 * @return
	 */

	public Missile placeShot(Missile missile) {
		sendObject(connection, missile);
		return null;
	}



	/**
	 * private static ObjectInputStream getInputStream(Socket socket){
	 * 
	 * ObjectOutputStream out = null; ObjectInputStream in = null;
	 * 
	 * //out = new ObjectOutputStream(bsClientSocket.getOutputStream(),) //out =
	 * new PrintWriter(echoSocket.getOutputStream(), true); //in = new
	 * BufferedReader(new InputStreamReader(
	 * 
	 * BufferedReader stdIn = new BufferedReader( new
	 * InputStreamReader(System.in)); String userInput;
	 * 
	 * while ((userInput = stdIn.readLine()) != null) { out.println(userInput);
	 * System.out.println("echo: " + in.readLine()); }
	 * 
	 * out.close(); in.close(); stdIn.close(); echoSocket.close(); }
	 */

	private static ObjectOutputStream getOutputStream(Socket socket) {
		return null;

	}

}
