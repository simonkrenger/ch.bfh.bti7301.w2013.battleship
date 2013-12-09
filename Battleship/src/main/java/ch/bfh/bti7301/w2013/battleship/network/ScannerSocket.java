package ch.bfh.bti7301.w2013.battleship.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class ScannerSocket extends Thread {

	public InetAddress ip;
	public static DatagramSocket udpSocket;
	
	public static ObjectOutputStream out;
	public static ObjectInputStream in;
	
	ScannerSocket(InetAddress ip){
		this.ip = ip;
		
		
		try {
			udpSocket = new DatagramSocket(Connection.GAMEPORT);
			
			//in = new ObjectInputStream(udpSocket.getInputStream());
			//out = new ObjectOutputStream(udpSocket.getOutputStream());
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		start();
		
	}
	
	public void run() {

				while (!udpSocket.isClosed()) {
					try {

						NetworkScanner.getInstance().readUdpSocket((String)in.readObject());
						//Test
						System.out.println("a udp Socket was generated an its listener is started" );
					} catch (ClassNotFoundException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println("there was a problem with the udp listener");
					}
					
				}
				
				try {
					udpSocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	
	
	
	public void sendMsg(String msg) throws IOException{
	 out.writeObject(msg);
	 System.out.println("a message has been sent over the udp Socket: " + this.ip );
	}
	

	
}


