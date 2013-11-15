package ch.bfh.bti7301.w2013.battleship.network;

import java.io.*;
import java.net.*;

public class NetworkListener extends Thread {

	public void run() {

		Socket udpListener = new Socket();
		try {
			ObjectInputStream in = new ObjectInputStream(
					udpListener.getInputStream());
			ObjectOutputStream out = new ObjectOutputStream(
					udpListener.getOutputStream());

			while (true) {
				readUdpSocket((String) in.readObject());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			if (!udpListener.isClosed())
				udpListener.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void readUdpSocket(String in) {

	}

	private void sendUdpMessage() {

	}

}

//
//
// }
//
//
// try (
// Socket echoSocket = new Socket(hostName, portNumber);
// PrintWriter out =
// new PrintWriter(echoSocket.getOutputStream(), true);
// BufferedReader in =
// new BufferedReader(
// new InputStreamReader(echoSocket.getInputStream()));
