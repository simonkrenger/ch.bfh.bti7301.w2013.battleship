package ch.bfh.bti7301.w2013.battleship.game;

import ch.bfh.bti7301.w2013.battleship.game.players.GenericPlayer.PlayerState;

/**
 * 
 * @author simon
 *
 */
public interface PlayerStateListener {

	/**
	 * This method is called whenever the state of a player has changed.
	 * 
	 * @param p
	 *            The player whose state has changed
	 * @param s
	 *            The new state of the player
	 */
	void stateChanged(Player p, PlayerState s);
}
