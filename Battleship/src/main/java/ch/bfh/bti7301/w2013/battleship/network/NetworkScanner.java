package ch.bfh.bti7301.w2013.battleship.network;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ch.bfh.bti7301.w2013.battleship.gui.SettingsPanel;

public class NetworkScanner {
	private static NetworkScanner instance;
	MulticastSocket udpSocket;
	private static DiscoverySocketListener discoveryListener;
	private static DiscoverySocketSender discoverySender;

	private NetworkScanner() throws IOException {
		udpSocket = new MulticastSocket(Connection.GAMEPORT);
		udpSocket.setReuseAddress(true);
		udpSocket.joinGroup(NetworkInformation.MULTICAST_GROUP);
		discoveryListener = new DiscoverySocketListener(udpSocket);
		
		
		discoverySender = new DiscoverySocketSender(udpSocket);
		
	}
	

	public static NetworkScanner getInstance() {
		if (instance == null) {
			try {
				instance = new NetworkScanner();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return instance;
	}


	private String matchIp(String str) {

		String IPADDRESS_PATTERN = "(([2]([0-4][0-9]|[5][0-5])|[0-1]?[0-9]?[0-9])[.]){3}(([2]([0-4][0-9]|[5][0-5])|[0-1]?[0-9]?[0-9]))";

		Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
		Matcher matcher = pattern.matcher(str);
		if (matcher.find()) {
			return matcher.group();
		} else {
			return "0.0.0.0";
		}

	}

	private String matchName(String str) {

		String NAME_PATTERN = "-\\w*$";

		Pattern pattern = Pattern.compile(NAME_PATTERN);
		Matcher matcher = pattern.matcher(str);
		if (matcher.find()) {
			return matcher.group();
		} else {
			return "no name";
		}

	}

	private boolean matchMsg(String str) {

		String MSG_PATTERN = "(BSG-)(([2]([0-4][0-9]|[5][0-5])|[0-1]?[0-9]?[0-9])[.]){3}(([2]([0-4][0-9]|[5][0-5])|[0-1]?[0-9]?[0-9]))(-\\w*)$";

		Pattern pattern = Pattern.compile(MSG_PATTERN);
		Matcher matcher = pattern.matcher(str);
		return matcher.find();

	}

	public void readUdpSocket(String ip,String name) {
			Connection.getInstance().foundOpponent(ip, name);

	}


	public void cleanUp() {
		// TODO: close all open sockets.
	}

}
