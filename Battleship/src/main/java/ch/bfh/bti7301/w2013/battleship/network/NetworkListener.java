//package ch.bfh.bti7301.w2013.battleship.network;
//
//import java.io.*;
//import java.net.*;
//
//public class NetworkListener extends Thread {
//	
//	private static NetworkListener instance;
//	private Socket udpListener;
//	
//	private NetworkListener() {
//		
//		
//		setUdpListener(new Socket(bc, Connection.GAMEPORT));
//	}
//	
//	public void setUdpListener(Socket udpListener) {
//		this.udpListener = udpListener;
//	}
//	
//	
//	public static NetworkListener getInstance() {
//		if (instance == null) {
//			instance = new NetworkListener();
//		}
//		return instance;
//	}
//	
//
//	public void run() {
//
//		for (InetAddress bc : NetworkInformation.getBroadcastAddresses()) {
//
//			Socket udpListener;
//			try {
//				udpListener = new Socket(bc, Connection.GAMEPORT);
//				
//
//				while (!udpListener.isClosed()) {
//					
//					ObjectInputStream in = new ObjectInputStream(
//							udpListener.getInputStream());
//					ObjectOutputStream out = new ObjectOutputStream(
//							udpListener.getOutputStream());
//					
//					readUdpSocket((String)in.readObject());
//					
//				}
//				
//
//			} catch (IOException | ClassNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		}
//
//	}
//
//	
//
//	private void readUdpSocket(String in) {
//
//	}
//
//	private void sendUdpMessage() {
//
//	}
//
//}
//
