package ch.bfh.bti7301.w2013.battleship.network;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionHandler extends Thread {

	private Socket connectionSocket;
	private ObjectInputStream in;
	private ObjectOutputStream out;

	public ConnectionHandler(Connection connection, Socket socket)
			throws IOException {
		setConnectionSocket(socket);
		setOut(new ObjectOutputStream(connectionSocket.getOutputStream()));
		start();
		connection.setConnectionState(ConnectionState.CONNECTED, "connetcion established");
	}

	public void run() {
		try {
			setIn(new ObjectInputStream(connectionSocket.getInputStream()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		while (true) {
			try {
				Object inputObject = in.readObject();
				receiveObject(inputObject);
			} catch (EOFException e) {
				// opponent disconnected
				Connection.getInstance().setConnectionState(
						ConnectionState.INPUTERROR, "an input error ocured while sending ");
				
				break;
				
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
			// TODO Auto-generated catch block todo
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

	public void cleanUp() {
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
