package ch.bfh.bti7301.w2012.battleship.network;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.JOptionPane;

public class client {

    public static void main(String[] args) throws IOException {
    	
        String serverAddress = JOptionPane.showInputDialog(
        	"Your IP Is: " + findLocalhost() +
            "Enter IP Address of a machine that is\n" +
            "running the date service on port 9090:");
        Socket s = new Socket(serverAddress, 9090);
        BufferedReader input =
            new BufferedReader(new InputStreamReader(s.getInputStream()));
        String answer = input.readLine();
        JOptionPane.showMessageDialog(null, answer);
        System.exit(0);
    }

    
    
    public static InetAddress findLocalhost(){

     InetAddress ip;
    		  try {
    	 
    			return ip = InetAddress.getLocalHost();
    	 
    		  } catch (UnknownHostException e) {
    	 
    			e.printStackTrace();
    			return null;
    		  }
    	 
    		}
    
    
	
    
    
    	
    
   
    	}

