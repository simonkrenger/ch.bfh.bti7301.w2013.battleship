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

import java.util.ArrayList;

import ch.bfh.bti7301.w2013.battleship.game.players.GenericPlayer.PlayerState;

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
		if (Game.getInstance().getOpponent().getBoard() == this) {
			// Check if its the players turn
			PlayerState playerState = Game.getInstance().getLocalPlayer()
					.getPlayerState();
			if (playerState == PlayerState.PLAYING) {

				// Check if coordinates of missile were already used
				for (Missile placed : placedMissiles) {
					if (placed.getCoordinates().equals(m.getCoordinates())) {
						throw new RuntimeException(
								"Missile coordinates were already used!");
					}
				}
				placedMissiles.add(m);
				Game.getInstance().getOpponent().sendMissile(m);
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

		private boolean checkCollisions(Ship s) {

			for (Ship placed : placedShips) {
				for (Coordinates d : s.getExtrapolatedCoordinates()) {
					for (Coordinates c : placed.getCoordinatesForShip()) {
						if (c.equals(d)) {
							return false;
						}
					}
				}
			}
			return true;
		}

		public void moveShip(Ship s, Coordinates newStartCoordinates,
				Direction d) {
			if (withinBoard(newStartCoordinates)
					&& withinBoard(s.getEndCoordinatesForShip(
							newStartCoordinates, d))) {

				Coordinates oldStartCoordinates = s.getStartCoordinates();
				Coordinates oldEndCoordinates = s.getEndCoordinates();

				s.setCoordinates(this, newStartCoordinates, d);

				if (!checkCollisions(s)) {
					// Collision with other ships, roll back changes!
					s.setCoordinates(this, oldStartCoordinates,
							oldEndCoordinates);

					throw new RuntimeException(
							"Collision with an already placed ship!");
				}
			}
		}

		public void moveShip(Ship s, Coordinates newStartCoordinates,
				Coordinates newEndCoordinates) {
			if (withinBoard(newStartCoordinates)
					&& withinBoard(newEndCoordinates)) {
				
				Coordinates oldStartCoordinates = s.getStartCoordinates();
				Coordinates oldEndCoordinates = s.getEndCoordinates();

				s.setCoordinates(this, newStartCoordinates, newEndCoordinates);

				if (!checkCollisions(s)) {
					// Collision with other ships, roll back changes!
					s.setCoordinates(this, oldStartCoordinates,
							oldEndCoordinates);

					throw new RuntimeException(
							"Collision with an already placed ship!");
				}
			}
		}

		public void placeShip(Ship s) {

			// Check boundaries of board
			if (withinBoard(s.getStartCoordinates())
					&& withinBoard(s.getEndCoordinates())) {

				if (checkCollisions(s)) {
					placedShips.add(s);
				} else {
					throw new RuntimeException(
							"Collision with an already placed ship!");
				}
			} else {
				throw new RuntimeException("Coordinates not within board!");
			}
		}

		public void done() {
			setup = null;
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
	}
}
