package ch.bfh.bti7301.w2013.battleship.network;

import java.io.*;
import java.net.*;
import java.util.Date;

public class Connection {

	final static int GAMEPORT = 42423;
	public Socket connection;

	public Connection() {
		createServerSocket(GAMEPORT);
	}

	public Connection(String opponentIP) {
		// Create a Client Socket
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
				return commSocket;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static boolean sendObject(Socket socket) {

		return false;

	}

	public static boolean receiveObject(Socket socket) {
		return false;

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
