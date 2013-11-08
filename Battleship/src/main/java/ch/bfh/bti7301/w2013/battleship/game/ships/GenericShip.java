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
package ch.bfh.bti7301.w2013.battleship.game.ships;

import java.util.ArrayList;

import ch.bfh.bti7301.w2013.battleship.game.Board.BoardSetup;
import ch.bfh.bti7301.w2013.battleship.game.Board.Direction;
import ch.bfh.bti7301.w2013.battleship.game.Coordinates;
import ch.bfh.bti7301.w2013.battleship.game.Ship;

/**
 * @author Simon Krenger <simon@krenger.ch>
 * 
 *         Generic class to represent a ship.
 * 
 */
public class GenericShip implements Ship {

	/**
	 * Start coordinates for the ship
	 */
	protected Coordinates startCoordinates;

	/**
	 * End coordinates for the ship
	 */
	protected Coordinates endCoordinates;

	/**
	 * Size of the ship
	 */
	protected int size;

	/**
	 * ArrayList containing all the coordinates where the ship was damaged
	 */
	protected ArrayList<Coordinates> damage = new ArrayList<Coordinates>();

	protected GenericShip(Coordinates start, Coordinates end, int size) {
		this.startCoordinates = start;
		this.endCoordinates = end;
		this.size = size;

		// Now we've set the private variables, cross-check them
		// If there is an error, an Exception will be thrown (this kills the
		// constructor)
		if (!checkSize()) {
			throw new RuntimeException("Coordinates and size do not match!");
		}
	}

	protected GenericShip(Coordinates start, Direction direction, int size) {

		this.size = size;

		this.startCoordinates = start;
		this.endCoordinates = getEndCoordinatesForShip(start, direction);

		// Now we've set the private variables, cross-check them
		// If there is an error, an Exception will be thrown (this kills the
		// constructor)
		if (!checkSize()) {
			throw new RuntimeException("Coordinates and size do not match!");
		}
	}

	/**
	 * Method to perform a crosscheck of the coordinates and the size of the
	 * ship. This method checks if the ship was placed horizontally or
	 * vertically (and not diagonally) and if the size and coordinates match.
	 * 
	 * @return Returns 'true' if the size and coordinates are valid and 'false'
	 *         if there was a mismatch
	 */
	private boolean checkSize() {
		// Note that this method indirectly checks for invalid coordinates such
		// as ([2,2],[2,2]), where the ship would have size of 0

		if (startCoordinates.x == endCoordinates.x) {
			if (startCoordinates.y > endCoordinates.y) {
				// Ship faces north
				if (!((startCoordinates.y - endCoordinates.y + 1) == size)) {
					return false;
				}
				return true;
			} else {
				// Ship faces south
				if (!((endCoordinates.y - startCoordinates.y + 1) == size)) {
					return false;
				}
				return true;
			}
		} else if (startCoordinates.y == endCoordinates.y) {
			if (startCoordinates.x > endCoordinates.x) {
				// Ship faces west
				if (!((startCoordinates.x - endCoordinates.x + 1) == size)) {
					return false;
				}
				return true;
			} else {
				// Ship faces east
				if (!((endCoordinates.x - startCoordinates.x + 1) == size)) {
					return false;
				}
				return true;
			}
		} else {
			// Diagonal, throw exception!
			return false;
		}
	}

	/**
	 * Helper method to calculate the end coordinates for given start
	 * coordinates and a direction
	 * 
	 * @param start
	 *            Start coordinates to use
	 * @param d
	 *            Direction to use
	 * @return The newly calculated end coordinates
	 */
	public Coordinates getEndCoordinatesForShip(Coordinates start, Direction d) {
		Coordinates newEndCoordinates = null;

		switch (d) {
		case NORTH:
			newEndCoordinates = new Coordinates(start.x, start.y - (size - 1));
			break;
		case SOUTH:
			newEndCoordinates = new Coordinates(start.x, start.y + (size - 1));
			break;
		case WEST:
			newEndCoordinates = new Coordinates(start.x - (size - 1), start.y);
			break;
		case EAST:
			newEndCoordinates = new Coordinates(start.x + (size - 1), start.y);
			break;
		}
		return newEndCoordinates;
	}

	@Override
	public Coordinates getStartCoordinates() {
		return startCoordinates;
	}

	@Override
	public Coordinates getEndCoordinates() {
		return endCoordinates;
	}

	@Override
	public void setCoordinates(BoardSetup b, Coordinates start, Direction d) {
		if (b == null) {
			throw new RuntimeException("BoardSetup not valid (was null)!");
		}

		Coordinates oldStartCoordinates = getStartCoordinates();
		Coordinates oldEndCoordinates = getEndCoordinates();

		this.startCoordinates = start;
		this.endCoordinates = getEndCoordinatesForShip(start, d);

		if (!checkSize()) {
			// Roll back changes
			this.startCoordinates = oldStartCoordinates;
			this.endCoordinates = oldEndCoordinates;

			throw new RuntimeException(
					"setCoordinates(): New coordinates not valid!");
		}
	}

	@Override
	public void setCoordinates(BoardSetup b, Coordinates start, Coordinates end) {
		if (b == null) {
			throw new RuntimeException("BoardSetup not valid (was null)!");
		}

		Coordinates oldStartCoordinates = getStartCoordinates();
		Coordinates oldEndCoordinates = getEndCoordinates();

		this.startCoordinates = start;
		this.endCoordinates = end;

		if (!checkSize()) {
			// Roll back changes
			this.startCoordinates = oldStartCoordinates;
			this.endCoordinates = oldEndCoordinates;

			throw new RuntimeException(
					"setCoordinates(): New coordinates not valid!");
		}
	}

	@Override
	public int getSize() {
		return this.size;
	}

	@Override
	public String getName() {
		return "Generic ship";
	}

	@Override
	public int getDamage() {
		return damage.size();
	}

	@Override
	public void setDamage(Coordinates c) {
		if (getCoordinatesForShip().contains(c)) {
			if (!damage.contains(c)) {
				damage.add(c);
			} else {
				throw new RuntimeException("Coordinates " + c
						+ " are already damaged!");
			}
		} else {
			throw new RuntimeException("Coordinates " + c
					+ " don't match with ship coordinates!");
		}
	}

	@Override
	public boolean isSunk() {
		return (damage.size() == size);
	}

	@Override
	public Direction getDirection() {
		// Note that we don't need to check for diagonal here, the ship was
		// already constructed and therefore has valid coordinates

		if (startCoordinates.x == endCoordinates.x) {
			if (startCoordinates.y > endCoordinates.y) {
				return Direction.NORTH;
			} else {
				return Direction.SOUTH;
			}
		} else {
			if (startCoordinates.x > endCoordinates.x) {
				return Direction.WEST;
			} else {
				return Direction.EAST;
			}
		}
	}

	@Override
	public ArrayList<Coordinates> getCoordinatesForShip() {
		ArrayList<Coordinates> coords = new ArrayList<Coordinates>();
		if (startCoordinates.x == endCoordinates.x) {
			if (startCoordinates.y > endCoordinates.y) {
				// Ship faces north
				for (int i = endCoordinates.y; i <= startCoordinates.y; i++) {
					coords.add(new Coordinates(startCoordinates.x, i));
				}
			} else {
				// Ship faces south
				for (int i = startCoordinates.y; i <= endCoordinates.y; i++) {
					coords.add(new Coordinates(startCoordinates.x, i));
				}
			}
		} else {
			if (startCoordinates.x > endCoordinates.x) {
				// Ship faces west
				for (int i = endCoordinates.x; i <= startCoordinates.x; i++) {
					coords.add(new Coordinates(i, startCoordinates.y));
				}
			} else {
				// Ship faces east
				for (int i = startCoordinates.x; i <= endCoordinates.x; i++) {
					coords.add(new Coordinates(i, startCoordinates.y));
				}
			}
		}
		return coords;
	}

	@Override
	public ArrayList<Coordinates> getExtrapolatedCoordinates() {
		ArrayList<Coordinates> extrapolated = new ArrayList<Coordinates>();
		// Add the coordinates for the ship itself
		extrapolated.addAll(getCoordinatesForShip());

		// Calculate border of 1 around the ship
		for (Coordinates c : getCoordinatesForShip()) {
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					Coordinates temp = new Coordinates(c.x + j, c.y + i);
					// Do not allow duplicates
					if (!extrapolated.contains(temp)) {
						extrapolated.add(temp);
					}
				}
			}
		}
		return extrapolated;
	}

	@Override
	public ArrayList<Coordinates> getCoordinatesForDamage() {
		return damage;
	}

	@Override
	public String toString() {
		return getName() + " [startCoordinates=" + startCoordinates
				+ ", endCoordinates=" + endCoordinates + ", size=" + size
				+ ", getDamage()=" + getDamage() + ", isSunk()=" + isSunk()
				+ ", getDirection()=" + getDirection()
				+ ", getCoordinatesForShip()=" + getCoordinatesForShip()
				+ ", getExtrapolatedCoordinates()="
				+ getExtrapolatedCoordinates() + ", getCoordinatesForDamage()="
				+ getCoordinatesForDamage() + "]";
	}
}
