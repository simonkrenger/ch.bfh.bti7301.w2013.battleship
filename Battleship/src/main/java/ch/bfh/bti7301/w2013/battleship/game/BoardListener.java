package ch.bfh.bti7301.w2013.battleship.game;

/**
 * Class to implement a listener in the observer pattern to listen for changes
 * on the baord.
 * 
 * @author Simon Krenger <simon@krenger.ch>
 * 
 */
public interface BoardListener {

	/**
	 * This method is called whenever a new missile is placed on the board or
	 * the state of an already placed missile is updated
	 * 
	 * @param m
	 *            The missile object that was newly placed or updated
	 */
	void stateChanged(Missile m);
}
