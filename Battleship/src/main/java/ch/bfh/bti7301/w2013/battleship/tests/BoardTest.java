package ch.bfh.bti7301.w2013.battleship.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import ch.bfh.bti7301.w2013.battleship.game.Board;
import ch.bfh.bti7301.w2013.battleship.game.Board.BoardSetup;
import ch.bfh.bti7301.w2013.battleship.game.Board.Direction;
import ch.bfh.bti7301.w2013.battleship.game.BoardListener;
import ch.bfh.bti7301.w2013.battleship.game.Coordinates;
import ch.bfh.bti7301.w2013.battleship.game.Game;
import ch.bfh.bti7301.w2013.battleship.game.Missile;
import ch.bfh.bti7301.w2013.battleship.game.Ship;
import ch.bfh.bti7301.w2013.battleship.game.ships.AircraftCarrier;
import ch.bfh.bti7301.w2013.battleship.game.ships.PatrolBoat;

public class BoardTest {

	@Test
	public final void testBoardInt() {
		Board b = new Board(99);
		assertEquals(99, b.getBoardSize());
	}
	
	@Test
	public final void testGetBoardSize() {
		Board b = new Board(99);
		assertEquals(99, b.getBoardSize());
	}

	@Test
	public final void testPlaceMissile() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testUpdateMissile() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetPlacedShips() {
		Board b = new Board(10);
		BoardSetup bs = b.getBoardSetup();
		
		AircraftCarrier ac = new AircraftCarrier(new Coordinates(2, 2), Direction.SOUTH);
		bs.placeShip(ac);
		bs.done();
		
		ArrayList<Ship> ships = b.getPlacedShips();
		assertEquals(ships.get(0), ac);
	}

	@Test
	public final void testWithinBoard() {
		Board b = new Board(10);
		
		// Test some coordinates within the board
		assertTrue(b.withinBoard(new Coordinates(1, 1)));
		assertTrue(b.withinBoard(new Coordinates(1, 10)));
		assertTrue(b.withinBoard(new Coordinates(10, 1)));
		assertTrue(b.withinBoard(new Coordinates(10, 10)));

		// Test some coordinates not within the board
		assertFalse(b.withinBoard(new Coordinates(0, 0)));
		assertFalse(b.withinBoard(new Coordinates(-1, -1)));
		assertFalse(b.withinBoard(new Coordinates(11, 11)));
		assertFalse(b.withinBoard(new Coordinates(Integer.MAX_VALUE, Integer.MAX_VALUE)));
	}

	@Test
	public final void testCheckAllShipsSunk() {
		Board b = new Board(10);
		
		BoardSetup bs = b.getBoardSetup();
		
		// Place PatrolBoat (Coordinates [2,2] and [2,3])
		PatrolBoat p = new PatrolBoat(new Coordinates(2, 2), Direction.SOUTH);
		bs.placeShip(p);
		bs.done();

		assertFalse(b.checkAllShipsSunk());
		
		p.setDamage(new Coordinates(2, 2));
		assertFalse(b.checkAllShipsSunk());
		
		p.setDamage(new Coordinates(2, 3));
		assertTrue(b.checkAllShipsSunk());
	}

}
