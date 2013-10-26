package ch.bfh.bti7301.w2013.battleship.game;

import java.io.Serializable;

/**
 * Class to store coordinates (X and Y)
 * 
 * @author simon
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

	public Coordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		// Stolen from here:
		// http://stackoverflow.com/questions/10813154/converting-number-to-letter
		String alpha = x > 0 && x < 27 ? String
				.valueOf((char) (x + 'A' - 1)) : null;
		return alpha + y;
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