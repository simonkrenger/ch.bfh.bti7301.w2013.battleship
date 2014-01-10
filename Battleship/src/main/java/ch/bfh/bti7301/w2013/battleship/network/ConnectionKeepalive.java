package ch.bfh.bti7301.w2013.battleship.network;


public class ConnectionKeepalive extends Thread {

	public ConnectionKeepalive() {
		start();
	}

	private static Object KEEP_ALIVE = new Integer(-1);

	public void run() {
		while (true) {
			
			Connection.getInstance().getHandler().sendObject(KEEP_ALIVE);
			try {
				sleep(5000);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("SLEEP IN CONNETION KEEPALIVE WAS INTERRUPTED");
				e.printStackTrace();
			}
		}
	}

}
