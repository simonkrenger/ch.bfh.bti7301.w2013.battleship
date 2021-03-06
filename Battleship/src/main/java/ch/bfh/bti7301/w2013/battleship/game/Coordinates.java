package ch.bfh.bti7301.w2013.battleship.game;

import java.io.Serializable;

import ch.bfh.bti7301.w2013.battleship.game.Board.Direction;

/**
 * Class to store coordinates (X and Y). Note that coordinates in this
 * implementation begin at (1,1).
 * 
 * @author Simon Krenger <simon@krenger.ch>
 * 
 */
public class Coordinates implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * X coordinates
	 */
	public int x;

	/**
	 * Y coordinates
	 */
	public int y;

	/**
	 * Constructor for the class, takes the X and Y coordinates as arguments.
	 * Note that the coordinates begin at (1,1) and NOT at (0,0)!
	 * 
	 * @param x
	 *            X coordinates (a value of 1 means leftmost coordinates)
	 * @param y
	 *            Y coordinates (a value of 1 means topmost coordinates)
	 */
	public Coordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Coordinates getNext(Direction d) {
		switch (d) {
		case NORTH:
			return new Coordinates(x, y - 1);
		case EAST:
			return new Coordinates(x + 1, y);
		case SOUTH:
			return new Coordinates(x, y + 1);
		case WEST:
			return new Coordinates(x - 1, y);
		default:
			throw new RuntimeException("Illegal direction: " + d);
		}
	}

	@Override
	public String toString() {
		// Stolen from here:
		// http://stackoverflow.com/questions/10813154/converting-number-to-letter
		String alpha = x > 0 && x < 27 ? String.valueOf((char) (x + 'A' - 1))
				: null;
		return alpha + y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinates other = (Coordinates) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}