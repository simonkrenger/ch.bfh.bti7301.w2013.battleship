package ch.bfh.bti7301.w2013.battleship.game;

/**
 * 
 * @author simon
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
