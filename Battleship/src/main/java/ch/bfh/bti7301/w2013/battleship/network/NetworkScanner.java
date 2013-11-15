package ch.bfh.bti7301.w2013.battleship.network;


import java.io.*;
import java.net.*;
import java.util.*;

public class NetworkScanner extends Thread{

	
	private Socket udpSocket;
	private InputStreamReader in;
	private OutputStreamWriter out;
	private ArrayList<String> opponents;
	
	
	public NetworkScanner() throws IOException{
		   setUdpSocket(new Socket());
		   setIn(new InputStreamReader(udpSocket.getInputStream()));
		   setOut(new OutputStreamWriter(udpSocket.getOutputStream()));
		   start();
	   }
		
	   public void run(){
		   while (true){
			   try {
				in.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			   NetworkInformation.getBroadcastAddresses();
			   
		   }
	   }
	   

	public void setUdpSocket(Socket udpSocket) {
		this.udpSocket = udpSocket;
	}


	public void setIn(InputStreamReader inputStreamReader) {
		this.in = inputStreamReader;
	}

	public void setOut(OutputStreamWriter outputStreamWriter) {
		this.out = outputStreamWriter;
	}


	
	
	
}
