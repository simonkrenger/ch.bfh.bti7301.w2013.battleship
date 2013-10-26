package ch.bfh.bti7301.w2013.battleship.game;

import ch.bfh.bti7301.w2013.battleship.game.players.GenericPlayer.PlayerState;

public interface PlayerStateListener {
	void stateChanged(Player p, PlayerState s);
}
