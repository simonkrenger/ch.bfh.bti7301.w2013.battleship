package ch.bfh.bti7301.w2013.battleship.network;

import java.io.Serializable;

public class ConnectionKeepalive extends Thread {

	public ConnectionKeepalive() {
		start();
	}

	private static KeepAlive KEEP_ALIVE = new KeepAlive();

	public void run() {
		while (true) {
			Connection.getInstance().getHandler().sendObject(KEEP_ALIVE);
			try {
				sleep(5000);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("ConnectionKeepalive: exception sleep");
				e.printStackTrace();
			}
		}
	}

	static class KeepAlive implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 6337911690510859715L;
	}
}
