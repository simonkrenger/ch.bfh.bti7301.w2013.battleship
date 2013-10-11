package ch.bfh.bti7301.w2013.battleship.network;

import java.net.*;
import java.util.*;
import java.io.*;

/**
 * Class to check and find the users network settings
 * @author corradi
 * @version 2013-10-04 v0.1
 */

public class NetworkInformation {
	
	/**
	 * Method to get all IP Addresses of Up IPv4, non Loopback and non virtual interfaces.
	 * uses  getUpInterfaces() to find all UP Interfaces. 
	 * 
	 * @return      An Array List With the Interfaces names and Ip Addresses.
	 */

	public static ArrayList<String> getIntAddresses() {

		ArrayList<String> ipv4Interfaces = new ArrayList<String>();

		/** Get all Network interface information */
		Enumeration<NetworkInterface> allInterfaces = null;
		try {
			allInterfaces = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/**
		 * From each element in the allInterfaces Enum (has to be changed to
		 * list to iterate) get "UP" interfaces with IPv4 Addresses
		 */
		for (NetworkInterface interfaces : getUpInterfaces()) {

			ipv4Interfaces.add("Interface:" + interfaces.getDisplayName());

			Enumeration<InetAddress> inetAddresses = interfaces
					.getInetAddresses();

			for (InetAddress ifAddress : Collections.list(inetAddresses)) {

				//hier vergleich für 0-255 , Byte vergleichen mit dezimalzahl.
				if (ifAddress.getAddress().length == 4) {
					ipv4Interfaces.add(" IP:" + ifAddress.getHostAddress()
							+ " ");
				}
			}
		}

		return ipv4Interfaces;
	}

	public static boolean hasRunningInterface() {

		return !getUpInterfaces().isEmpty();
	}

	private static ArrayList<NetworkInterface> getUpInterfaces() {

		ArrayList<NetworkInterface> upIpv4Interfaces = new ArrayList<NetworkInterface>();

		Enumeration<NetworkInterface> allInterfaces = null;
		try {
			allInterfaces = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (NetworkInterface adapter : Collections.list(allInterfaces)) {

			try {
				if (adapter.isUp() && !adapter.isLoopback()
						&& !adapter.isVirtual()) {

					upIpv4Interfaces.add(adapter);

				}

			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return upIpv4Interfaces;
	}
	
	/**
	FOR TEST ONLY!!!
	 */
	public static void main(String[] args) {

		System.out.println(hasRunningInterface());
		System.out.println(getIntAddresses());
	}
}
