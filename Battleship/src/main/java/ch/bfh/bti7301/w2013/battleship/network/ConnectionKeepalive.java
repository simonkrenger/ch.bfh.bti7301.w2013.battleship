package ch.bfh.bti7301.w2013.battleship.network;

import java.io.Serializable;

public class ConnectionKeepalive extends Thread {

	public ConnectionKeepalive() {
		start();
	}

	private static Object KEEP_ALIVE = new Integer(42);

	public void run() {
		while (true) {
			
			Connection.getInstance().getHandler().sendObject(KEEP_ALIVE);
			try {
				sleep(5000);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	static class KeepAlive implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	}
}
