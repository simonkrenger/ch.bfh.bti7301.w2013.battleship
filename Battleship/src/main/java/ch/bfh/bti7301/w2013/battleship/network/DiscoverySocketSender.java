package ch.bfh.bti7301.w2013.battleship.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class DiscoverySocketSender extends Thread {
	public String name;
	public DatagramSocket udpSocket;

	public DiscoverySocketSender(DatagramSocket socket, String name) {

		this.udpSocket = socket;
		this.name = name;
		start();
	}

	public void run() {

		while (!udpSocket.isClosed()) {

			try {

				byte[] buf = new byte[256];
				buf = name.getBytes();

				for (InetAddress bc : NetworkInformation
						.getBroadcastAddresses()) {
					System.out.println("packet sent to BC Add: "  + bc.getHostAddress());
					DatagramPacket packetOut = new DatagramPacket(buf,
							buf.length, bc, Connection.GAMEPORT);

					udpSocket.send(packetOut);
					
					System.out.println("sent packet " + name);
				}

				sleep(5000);

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				udpSocket.close();

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				udpSocket.close();
			}

			udpSocket.close();

		}
	}

}
