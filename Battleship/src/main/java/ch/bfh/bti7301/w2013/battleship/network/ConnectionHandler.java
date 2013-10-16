package ch.bfh.bti7301.w2013.battleship.network;

import java.io.*;
import java.net.*;

import ch.bfh.bti7301.w2013.battleship.game.Missile;

public class ConnectionHandler implements Runnable {
	
	private static ObjectInputStream input;
	private static Connection conn;

    
	public ConnectionHandler(ObjectInputStream in, Connection connection) {
		input = in;
		conn = connection;
		run();
	}

	



	public void run() {

		while (true) {
			try {
				Object inputObject = input.readObject();
				receiveObject(inputObject);

			} catch (IOException e) {
				e.printStackTrace();

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}



	public static void sendObject(ObjectOutputStream out, Object outgoingObject) {
		try {
			//if (Protocoll.checkOutput(outgoingObject)){
			out.writeObject(outgoingObject);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Object receiveObject(Object receivedObject) {
		conn.receiveObjectToGame(receivedObject);
		return false;

	}

}
