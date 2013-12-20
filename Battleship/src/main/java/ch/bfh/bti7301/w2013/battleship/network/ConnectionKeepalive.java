package ch.bfh.bti7301.w2013.battleship.network;



public class ConnectionKeepalive extends Thread{
	
	public ConnectionKeepalive(){
		start();
	}

public void run (){
	while(true){
	Connection.getInstance().getHandler().sendObject("keepalive");
	try {
		sleep(5000);
	
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		System.out.println("ConnectionKeepalive: exception sleep");
		e.printStackTrace();
	}
	}
}
	
}
