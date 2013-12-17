package ch.bfh.bti7301.w2013.battleship.network;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import javax.sound.sampled.ReverbType;

public class ConnectionHandler extends Thread {

	private Socket connectionSocket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	@SuppressWarnings("unused")
	private ConnectionKeepalive keepalive;

	public ConnectionHandler(Connection connection, Socket socket)
			throws IOException {
		
		setConnectionSocket(socket);
		
		startKeepalive();
		setOut(new ObjectOutputStream(connectionSocket.getOutputStream()));
		start();
	}

	public void run() {
		try {
			setIn(new ObjectInputStream(connectionSocket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
			Connection.getInstance().catchAndReestablish(ConnectionState.INPUTERROR, "cannot set the input stream");
		}

		while (true) {
			try {
				Object inputObject = in.readObject();
				receiveObject(inputObject);
				
			} catch (EOFException e) {
				e.printStackTrace();
				Connection.getInstance().catchAndReestablish(ConnectionState.INPUTERROR, "opponent disconnected");
				break;

			} catch (IOException e) {
				e.printStackTrace();
				Connection.getInstance().catchAndReestablish(ConnectionState.INPUTERROR, "someting with the input stream went wrong");

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				Connection.getInstance().catchAndReestablish(ConnectionState.INPUTERROR, "there is no input stream");
			}
		}
	}

	public void sendObject(Object outgoingObject) {
		try {
			out.writeObject(outgoingObject);

		} catch (IOException e) {
			e.printStackTrace();
			Connection.getInstance().catchAndReestablish(ConnectionState.OUTPUTEROR, "somtehing went wrong while sending an Object to your opponent");
		}

	}

	
	public void receiveObject(Object receivedObject) {
		String ka = new String ("keepalive");
		if (!receivedObject.equals(ka)){
		Connection.receiveObjectToGame(receivedObject);
		System.out.println("Dropped Keepalive: " + receivedObject );
		
		}
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
	
	public String getLocalIp(){
		return connectionSocket.getLocalAddress().getHostAddress();
	}
	
	public String getOpponentIp(){
		return connectionSocket.getInetAddress().getHostAddress();
	}
	
	public void startKeepalive() throws SocketException{
		this.connectionSocket.setSoTimeout(6000);
		this.keepalive = new ConnectionKeepalive();
	}

	public void closeHandler() {
		if (!connectionSocket.isClosed()) {
			try {
				keepalive.interrupt();
				in.close();
				out.close();
				connectionSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
				Connection.getInstance().setConnectionState(
						ConnectionState.CONNECTIONERROR,
						"the connection is stuck");
				//TODO: Some GUI Interaction is asked for.
			}
		}
	}
	
}
