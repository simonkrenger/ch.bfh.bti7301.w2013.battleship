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

import ch.bfh.bti7301.w2013.battleship.game.Board;
import ch.bfh.bti7301.w2013.battleship.game.Board.Coordinates;
import ch.bfh.bti7301.w2013.battleship.game.Board.Direction;
import ch.bfh.bti7301.w2013.battleship.game.Ship;

/**
 * @author simon
 * 
 */
public class GenericShip implements Ship {

	protected Board.Coordinates startCoordinates;
	protected Board.Coordinates endCoordinates;

	protected int size;

	protected GenericShip(Board.Coordinates start, Board.Coordinates end,
			int size) {
		// TODO: Check if coordinates and size match
		this.startCoordinates = start;
		this.endCoordinates = end;
		this.size = size;
		checkSize();
	}

	protected GenericShip(Board.Coordinates start, Direction direction, int size) {
		this.startCoordinates = start;
		this.size = size;
		switch (direction) {
		case NORTH:
			this.endCoordinates = new Coordinates(start.x, start.y + (size - 1));
			break;
		case SOUTH:
			this.endCoordinates = new Coordinates(start.x, start.y - (size - 1));
			break;
		case WEST:
			this.endCoordinates = new Coordinates(start.x - (size - 1), start.y);
			break;
		case EAST:
			this.endCoordinates = new Coordinates(start.x + (size - 1), start.y);
			break;
		}
		checkSize();
	}

	private void checkSize() throws RuntimeException {
		if (startCoordinates.x == endCoordinates.x) {
			if (startCoordinates.y > endCoordinates.y) {
				// Ship faces north
				if (!((startCoordinates.y - endCoordinates.y + 1) == size)) {
					throw new RuntimeException(
							"Coordinates and size do not match!");
				}
			} else {
				// Ship faces south
				if (!((endCoordinates.y - startCoordinates.y + 1) == size)) {
					throw new RuntimeException(
							"Coordinates and size do not match!");
				}
			}
		} else if (startCoordinates.y == endCoordinates.y) {
			if (startCoordinates.x > endCoordinates.x) {
				// Ship faces west
				if (!((startCoordinates.x - endCoordinates.x + 1) == size)) {
					throw new RuntimeException(
							"Coordinates and size do not match!");
				}
			} else {
				// Ship faces east
				if (!((endCoordinates.x - startCoordinates.x + 1) == size)) {
					throw new RuntimeException(
							"Coordinates and size do not match!");
				}
			}
		} else {
			// Diagonal, throw exception!
			throw new RuntimeException("Diagonal ships not allowed!");
		}
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
	public int getSize() {
		return this.size;
	}

	@Override
	public String getName() {
		return "Generic ship";
	}

	@Override
	public int getDamage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isSunk() {
		// TODO Auto-generated method stub
		return false;
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
		//ArrayList<Coordinates> coords = new ArrayList<Coordinates>();
		return null;
	}

	@Override
	public ArrayList<Coordinates> getCoordinatesForDamage() {
		// TODO Auto-generated method stub
		return null;
	}
}
