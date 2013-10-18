package ch.bfh.bti7301.w2013.battleship.network;

/**
 * This listener is called when the connection state changes, e.g. the client
 * gets connected or the connection is interrupted.
 * 
 * @author Christian Meyer <chrigu.meyer@gmail.com>
 * 
 */
public interface ConnectionStateListener {
	void stateChanged(ConnectionState newState);
}
