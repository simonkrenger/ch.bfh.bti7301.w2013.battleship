package ch.bfh.bti7301.w2013.battleship.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.bfh.bti7301.w2013.battleship.game.Board;
import ch.bfh.bti7301.w2013.battleship.game.Board.Direction;
import ch.bfh.bti7301.w2013.battleship.game.Coordinates;
import ch.bfh.bti7301.w2013.battleship.game.Missile;
import ch.bfh.bti7301.w2013.battleship.game.Board.BoardSetup;
import ch.bfh.bti7301.w2013.battleship.game.players.LocalPlayer;
import ch.bfh.bti7301.w2013.battleship.game.ships.AircraftCarrier;

public class PlayerTest {

	/*
	 * Generic Player tests
	 */

	@Test
	public final void testGetBoard() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetPlayerState() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSetPlayerState() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testAddPlayerStateListener() {
		fail("Not yet implemented"); // TODO
	}

	/*
	 * Tests for LocalPlayer
	 */
	@Test
	public final void testPlaceMissile() {
		LocalPlayer lp = new LocalPlayer();
		Board localBoard = lp.getBoard();

		localBoard.getBoardSetup().placeShip(
				new AircraftCarrier(new Coordinates(3, 3), Direction.SOUTH));
		localBoard.getBoardSetup().done();
		
		

		Missile m = new Missile(new Coordinates(5, 5));
		lp.placeMissile(m);
	}

	/*
	 * Tests for NetworkPlayer
	 */
	@Test
	public final void testSendMissile() {
		fail("Not yet implemented"); // TODO
	}

}
