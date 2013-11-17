package ch.bfh.bti7301.w2013.battleship.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ch.bfh.bti7301.w2013.battleship.game.Game;
import ch.bfh.bti7301.w2013.battleship.game.Player;
import ch.bfh.bti7301.w2013.battleship.game.players.GenericPlayer.PlayerState;
import ch.bfh.bti7301.w2013.battleship.network.Connection;

public class NetworkTest {
	/**
	 * A simple test for the connection, connecting the game to itself (somewhat
	 * unfortunately) and then testing if the receiving code set the player
	 * state to {@link PlayerState#READY}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testConnection() throws Exception {
		Connection c = Connection.getInstance();

		c.connectOpponent("127.0.0.1");
		Player opp = Game.getInstance().getOpponent();
		assertEquals(PlayerState.GAME_STARTED, opp.getPlayerState());
		c.sendStatus(PlayerState.READY);
		long time = System.currentTimeMillis();
		// Wait until the 'other end' reacted, but no more than 10 seconds
		while (opp.getPlayerState() == PlayerState.GAME_STARTED
				&& System.currentTimeMillis() - time < 10000) {
			Thread.sleep(100);
		}
		assertEquals(PlayerState.READY, opp.getPlayerState());
	}
}
