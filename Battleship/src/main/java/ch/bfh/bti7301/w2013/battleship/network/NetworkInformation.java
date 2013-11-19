package ch.bfh.bti7301.w2013.battleship.network;

import java.net.*;
import java.util.*;

/**
 * Class to check and find the users network settings
 * 
 * @author corradi
 * @version 2013-10-04 v0.1
 */

public class NetworkInformation {

	/**
	 * Method to get all IP Addresses of Up IPv4, non loopback and non virtual
	 * interfaces. uses getUpInterfaces() to find all UP Interfaces.
	 * 
	 * @return An Array List With the Interfaces names and Ip Addresses.
	 */

	public static ArrayList<InterfaceAddress> getIpAddresses() {

		ArrayList<InterfaceAddress> ipAddresses = new ArrayList<InterfaceAddress>();
		for (NetworkInterface adapter : getUpInterfaces()) {
			for (InterfaceAddress interfaceAddress : adapter
					.getInterfaceAddresses()) {
				ipAddresses.add(interfaceAddress);

			}

		}

		return ipAddresses;
	}

	public static ArrayList<String> getIntAddresses() {

		ArrayList<String> ipv4Interfaces = new ArrayList<String>();

		/**
		 * From each element in the allInterfaces Enum (has to be changed to
		 * list to iterate) get "UP" interfaces with IPv4 Addresses
		 */

		for (InterfaceAddress address : getIpAddresses()) {

			// hier vergleich für 0-255 , Byte vergleichen mit dezimalzahl.
			if (address.getAddress().getAddress().length == 4) {
				ipv4Interfaces.add(address.getAddress().getHostAddress());
			}

		}

		return ipv4Interfaces;
	}

	/**
	 * Check if there are any Network interfaces that are up.
	 * 
	 * @return
	 */
	public static boolean hasRunningInterface() {

		return !getUpInterfaces().isEmpty();
	}

	/**
	 * Returns an ArrayList with all the Interfaces that are up.
	 * @return
	 */
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

	public static ArrayList<InetAddress> getBroadcastAddresses() {
		
		ArrayList<InetAddress> broadcastAddresses = new ArrayList<InetAddress>();
		
		for (InterfaceAddress broadcast : getIpAddresses()){
			InetAddress bc = broadcast.getBroadcast();
			if (bc != null ){
			broadcastAddresses.add(bc);
			}
		}

		return broadcastAddresses;

	}

	/**
	 * FOR TEST ONLY!!!
	 */
	public static void main(String[] args) {

		System.out.println(getIntAddresses());
		System.out.println(getIpAddresses());
		System.out.println(getBroadcastAddresses());

	}

}
