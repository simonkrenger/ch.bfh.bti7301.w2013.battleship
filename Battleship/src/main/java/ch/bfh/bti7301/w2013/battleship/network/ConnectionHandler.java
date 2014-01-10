package ch.bfh.bti7301.w2013.battleship.network;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import org.hamcrest.core.IsInstanceOf;


public class ConnectionHandler extends Thread {

	private Socket connectionSocket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private ConnectionKeepalive keepalive;

	public ConnectionHandler(Connection connection, Socket socket)
			throws IOException {
		
		setConnectionSocket(socket);
		
		setOut(new ObjectOutputStream(connectionSocket.getOutputStream()));
		start();
		startKeepalive();
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
				System.out.println("RECEIVED OBJECT: " + inputObject);
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

	public synchronized void sendObject(Object outgoingObject) {
		try {
			out.writeObject(outgoingObject);
			
		} catch (IOException e) {
			e.printStackTrace();
			Connection.getInstance().catchAndReestablish(ConnectionState.OUTPUTEROR, "somtehing went wrong while sending an Object to your opponent");
		}

	}

	
	public void receiveObject(Object receivedObject) {
		if (!(receivedObject instanceof Integer)){
		Connection.receiveObjectToGame(receivedObject);
		}
		else if (!((Integer)receivedObject == -1)){
		Connection.receiveObjectToGame(receivedObject);
		}
	}

	public void setIn(ObjectInputStream in) {
		this.in = in;
	}

	public void setOut(ObjectOutputStream out) {
		this.out = out;
	}

	public void setConnectionSocket(Socket connectionSocket) throws SocketException {
		this.connectionSocket = connectionSocket;
		this.connectionSocket.setSoTimeout(6000);
	}
	
	public String getLocalIp(){
		return connectionSocket.getLocalAddress().getHostAddress();
	}
	
	public String getOpponentIp(){
		return connectionSocket.getInetAddress().getHostAddress();
	}
	
	public void startKeepalive() throws SocketException{
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
