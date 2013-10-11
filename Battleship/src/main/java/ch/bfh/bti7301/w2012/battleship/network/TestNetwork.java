package ch.bfh.bti7301.w2012.battleship.network;

import java.net.*;
import java.util.*;
import java.io.*;

public class TestNetwork {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Enumeration<NetworkInterface> allInterfaces = null;
		try {
			allInterfaces = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (NetworkInterface interfaces : Collections.list(allInterfaces)) {

			System.out.println(interfaces.getDisplayName() + '\n');

			Enumeration<InetAddress> inetAddresses = interfaces
					.getInetAddresses();

			for (InetAddress inetAddress : Collections.list(inetAddresses)) {
				System.out.println(inetAddress.getHostAddress() + '\n');

				byte[] raw = inetAddress.getAddress();
				if (raw.length == 4) {

					System.out.println("Bytes::");

					for (Byte ip : raw) {
						System.out.println(ip.byteValue() + ".");
					}
				}

			}
			System.out.println('\n');
		}
	}

}
