package ch.bfh.bti7301.w2013.battleship.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class TestDatagram extends Thread {

	static InetAddress localIp, remoteIp, soIp;
	static int port, soPort;
	static DatagramSocket socket;
	static byte[] buf;
	static String msg;
	static DatagramPacket packetIn, packetOut;

	public TestDatagram() {
		try {
			localIp = InetAddress.getByName("147.87.46.74");
			remoteIp = InetAddress.getByName("147.87.47.255");
			port = 4406;
			socket = new DatagramSocket(port, localIp);
			socket.setBroadcast(true);
			soPort = socket.getLocalPort();
			soIp = socket.getLocalAddress();

			buf = new byte[256];
			msg = "HELLO WORLD";
			buf = msg.getBytes();

			packetOut = new DatagramPacket(buf, buf.length,remoteIp,port);
			packetIn = new DatagramPacket(buf, buf.length);

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void run() {
		int i = 0;

		while (i < 10) {
			try {
				
				socket.send(packetOut);
				sleep(5000);
				
				socket.receive(packetIn);
				System.out.println();
				System.out.println("----------------");

		        String received = new String(packetIn.getData());
		        System.out.println("MSG: " + received);
				System.out.println(packetIn.getAddress().getHostAddress());
				System.out.println(packetIn.getPort());
				System.out.println(packetIn.getLength());


			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			i++;
			System.out.println();
			System.out.println(i);
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		TestDatagram test = new TestDatagram();

		System.out.println();
		System.out.println("----------------");
		System.out.println("socket was generated on address: "
				+ socket.getLocalSocketAddress());
		System.out.println();

		System.out.println();
		System.out.println("----------------");
		System.out.println("values from Socket: " + soPort + " IP: " + soIp);
		System.out.println();

		System.out.println("packet to send:");
		System.out.println("----------------");
		//System.out.println(packetOut.getAddress().getHostAddress());
		//System.out.println(packetOut.getPort());
		System.out.println(packetOut.getLength());

		test.start();

	}

}
