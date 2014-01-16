/**
 * This is free and unencumbered software released into the public domain.
 * 
 * Anyone is free to copy, modify, publish, use, compile, sell, or distribute
 * this software, either in source code form or as a compiled binary, for any
 * purpose, commercial or non-commercial, and by any means.
 * 
 * In jurisdictions that recognize copyright laws, the author or authors of this
 * software dedicate any and all copyright interest in the software to the
 * public domain. We make this dedication for the benefit of the public at large
 * and to the detriment of our heirs and successors. We intend this dedication
 * to be an overt act of relinquishment in perpetuity of all present and future
 * rights to this software under copyright law.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * For more information, please refer to [http://unlicense.org]
 */
package ch.bfh.bti7301.w2013.battleship.game;

import static ch.bfh.bti7301.w2013.battleship.utils.GameUtils.rnd;

import java.util.ArrayList;
import java.util.List;

import ch.bfh.bti7301.w2013.battleship.game.players.GenericPlayer.PlayerState;
import ch.bfh.bti7301.w2013.battleship.game.players.NetworkPlayer;
import ch.bfh.bti7301.w2013.battleship.game.players.ai.ComputerPlayer;

/**
 * Class to represent a gameboard. Features methods to place and update
 * missiles, to validate coordinates and generally keep the game together.
 * 
 * @author Simon Krenger <simon@krenger.ch>
 * 
 */
public class Board {
	/**
	 * Size of the board (n*n)
	 */
	private int size;

	/**
	 * List of placed ships on this board
	 */
	private ArrayList<Ship> placedShips = new ArrayList<Ship>();

	/**
	 * List of placed missiles on this board
	 */
	private ArrayList<Missile> placedMissiles = new ArrayList<Missile>();

	/**
	 * Setup object. Needed for Board setup
	 */
	private BoardSetup setup = new BoardSetup();

	/**
	 * List of BoardListeners to notify when a missile is newly placed or has
	 * its status updated.
	 */
	private ArrayList<BoardListener> listeners = new ArrayList<BoardListener>();

	/**
	 * Constructor for the class, takes the board size n as an argument, where
	 * (n * n) is then the size of the board.
	 * 
	 * @param size
	 *            Size of one side of the rectangle
	 */
	public Board(int size) {
		this.size = size;
	}

	/**
	 * Get the size of one side of the board
	 * 
	 * @return Board size
	 */
	public int getBoardSize() {
		return this.size;
	}

	/**
	 * Function to place a missile on the opponents board. This method can only
	 * be called on the opponents board and only if the local player has the
	 * state "PLAYING". This method then triggers a call to the opponents board
	 * and calls the method "sendMissile()" on the opponent.
	 * 
	 * All BoardListeners registered to this board will be notified of the
	 * change.
	 * 
	 * @param m
	 *            Missile to be placed on the opponents board
	 */
	public void placeMissile(Missile m) {

		// This operation can only be made on the opponents board
		if (Game.getInstance().getLocalPlayer().getBoard() != this) {
			// Check if its the players turn
			PlayerState playerState = Game.getInstance().getLocalPlayer()
					.getPlayerState();
			if (playerState == PlayerState.PLAYING) {

				// Check if coordinates of missile were already used
				for (Missile placed : placedMissiles) {
					if (placed.getCoordinates().equals(m.getCoordinates())) {
						if(Game.getInstance().getComputerPlayer().getBoard() == this) {
							return;
						}
						throw new RuntimeException(
								"Missile coordinates were already used!");
					}
				}
				placedMissiles.add(m);
				Player opponent = Game.getInstance().getOpponent();
				if(opponent instanceof ComputerPlayer) {
					ComputerPlayer comp = (ComputerPlayer) opponent;
					comp.placeMissile(m);
				} else {
					opponent.sendMissile(m);
				}
			} else {
				throw new RuntimeException("Local player is in state "
						+ playerState + ", cannot place missile just yet!");
			}
		}
		// Notify listeners
		for (BoardListener l : listeners) {
			l.stateChanged(m);
		}
	}

	/**
	 * Update an already placed missile on this board. This method examines the
	 * coordinates of the Missile provided and updates the missile found at the
	 * coordinates examined with the new status of the provided missile.
	 * 
	 * All BoardListeners registered to this board will be notified of the
	 * change.
	 * 
	 * @param m
	 *            The missile to be updated
	 */
	public void updateMissile(Missile m) {
		for (Missile placed : placedMissiles) {
			if (placed.getCoordinates().equals(m.getCoordinates())) {
				placed.setMissileState(m.getMissileState());

				// Notify listeners
				for (BoardListener l : listeners) {
					l.stateChanged(m);
				}
				return;
			}
		}
		throw new RuntimeException("Missile " + m
				+ " not found on board! placedMissiles: " + placedMissiles);
	}

	public ArrayList<Ship> getPlacedShips() {
		return this.placedShips;
	}

	public ArrayList<Missile> getPlacedMissiles() {
		return this.placedMissiles;
	}

	public boolean withinBoard(Coordinates c) {
		return ((c.x <= size) && (c.y <= size)) && ((c.x > 0) && (c.y > 0));
	}

	public boolean checkAllShipsSunk() {
		for (Ship s : getPlacedShips()) {
			if (!s.isSunk()) {
				return false;
			}
		}
		return true;
	}

	public BoardSetup getBoardSetup() {
		if (this.setup == null) {
			throw new RuntimeException(
					"You called done(), so no more BoardSetup for you!");
		}
		return this.setup;
	}

	public class BoardSetup {
		private BoardSetup() {

		}

		/**
		 * Method to perform a collision detection between a given ship and all
		 * already placed ships.
		 * 
		 * @param s
		 *            This ship is newly placed and needs to be checked for
		 *            collisions
		 * @throws RuntimeException
		 *             Throws an exception when there is a collision with an
		 *             already placed ship
		 */
		private void checkForCollisions(Ship s) throws RuntimeException {

			for (Ship placed : placedShips) {
				for (Coordinates c : s.getCoordinatesForShip()) {
					for (Coordinates d : placed.getExtrapolatedCoordinates()) {
						// Check if coordinates match
						// Also check if we do not check on ourself
						if (c.equals(d) && s != placed) {
							throw new RuntimeException("Collision detected at "
									+ d + ", between ships '" + s.getName()
									+ "' and '" + placed.getName()
									+ "' (there may be other collisions)");
						}
					}
				}
			}
		}

		public void moveShip(Ship s, Coordinates newStartCoordinates,
				Direction d) {
			if (withinBoard(newStartCoordinates)
					&& withinBoard(s.getEndCoordinatesForShip(
							newStartCoordinates, d))) {

				// Save old values for rollback if necessary
				Coordinates oldStartCoordinates = s.getStartCoordinates();
				Coordinates oldEndCoordinates = s.getEndCoordinates();

				s.setCoordinates(this, newStartCoordinates, d);

				try {
					checkForCollisions(s);
				} catch (RuntimeException ex) {
					// Collision with other ships, roll back changes!
					s.setCoordinates(this, oldStartCoordinates,
							oldEndCoordinates);
					throw ex;
				}
			} else {
				throw new RuntimeException(
						"moveShip: New coordinates not within board!");
			}
		}

		public void moveShip(Ship s, Coordinates newStartCoordinates,
				Coordinates newEndCoordinates) {
			if (withinBoard(newStartCoordinates)
					&& withinBoard(newEndCoordinates)) {

				// Save old values for rollback if necessary
				Coordinates oldStartCoordinates = s.getStartCoordinates();
				Coordinates oldEndCoordinates = s.getEndCoordinates();

				s.setCoordinates(this, newStartCoordinates, newEndCoordinates);

				try {
					checkForCollisions(s);
				} catch (RuntimeException ex) {
					// Collision with other ships, roll back changes!
					s.setCoordinates(this, oldStartCoordinates,
							oldEndCoordinates);
					throw ex;
				}
			} else {
				throw new RuntimeException(
						"moveShip: New coordinates not within board!");
			}
		}

		public void placeShip(Ship s) {

			// Check boundaries of board
			if (withinBoard(s.getStartCoordinates())
					&& withinBoard(s.getEndCoordinates())) {

				try {
					checkForCollisions(s);
					placedShips.add(s);
				} catch (RuntimeException ex) {
					// Nothing to rollback, throw it further
					throw ex;
				}
			} else {
				throw new RuntimeException(
						"placeShip: Coordinates of ship not within board!");
			}
		}

		public void done() {
			setup = null;
		}

		public void randomPlacement(List<Ship> ships) {
			long time = System.currentTimeMillis();
			int size = Game.getInstance().getRule().getBoardSize();
			List<Coordinates> free = new ArrayList<>(size * size);

			for (int i = 1; i <= size; i++)
				for (int j = 1; j <= size; j++)
					free.add(new Coordinates(i, j));

			for (Ship ship : ships) {
				boolean successful = false;
				Coordinates c;
				Direction d = rnd(Direction.values());
				do {
					c = rnd(free);
					for (int i = 0; i < Direction.values().length; i++) {
						try {
							ship.setCoordinates(setup, c, d);
							setup.placeShip(ship);
							successful = true;
							break;
						} catch (RuntimeException ignore) {
							d = d.rotateCW();
						}
					}
					if (System.currentTimeMillis() - time > 1000) {
						throw new RuntimeException(
								"Random placement took too long!");
					}
				} while (!successful);

				Coordinates c1 = ship.getStartCoordinates().getNext(
						ship.getDirection().getOpposite());
				Coordinates c2 = c1.getNext(ship.getDirection().rotateCW());
				Coordinates c3 = c1.getNext(ship.getDirection().rotateCCW());
				for (int i = 0; i < ship.getSize() + 2; i++) {
					free.remove(c1);
					free.remove(c2);
					free.remove(c3);
					c1 = c1.getNext(ship.getDirection());
					c2 = c2.getNext(ship.getDirection());
					c3 = c3.getNext(ship.getDirection());
				}
			}
		}
	}

	public void addBoardListener(BoardListener bl) {
		listeners.add(bl);
	}

	@Override
	public String toString() {
		return "Board [size=" + size + ", setup=" + setup
				+ ", getPlacedShips()=" + getPlacedShips()
				+ ", getPlacedMissiles()=" + getPlacedMissiles()
				+ ", checkAllShipsSunk()=" + checkAllShipsSunk() + "]";
	}

	/**
	 * Enumeration for direction. This direction can be used to place ships
	 * 
	 * @author Simon Krenger <simon@krenger.ch>
	 * 
	 */
	public static enum Direction {
		NORTH, SOUTH, WEST, EAST;

		/**
		 * Rotate clockwise
		 * 
		 * @return
		 */
		public Direction rotateCW() {
			switch (this) {
			case NORTH:
				return EAST;
			case WEST:
				return NORTH;
			case SOUTH:
				return WEST;
			case EAST:
				return SOUTH;
			}
			throw new RuntimeException();
		}

		/**
		 * Rotate counter-clockwise
		 * 
		 * @return
		 */
		public Direction rotateCCW() {
			switch (this) {
			case NORTH:
				return WEST;
			case WEST:
				return SOUTH;
			case SOUTH:
				return EAST;
			case EAST:
				return NORTH;
			}
			throw new RuntimeException();
		}

		/**
		 * Opposite direction
		 * 
		 * @return
		 */
		public Direction getOpposite() {
			switch (this) {
			case NORTH:
				return SOUTH;
			case WEST:
				return EAST;
			case SOUTH:
				return NORTH;
			case EAST:
				return WEST;
			}
			throw new RuntimeException();
		}
	}
}
