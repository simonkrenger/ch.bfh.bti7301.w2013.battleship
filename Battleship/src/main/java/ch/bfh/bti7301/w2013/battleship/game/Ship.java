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

import java.io.Serializable;
import java.util.ArrayList;

import ch.bfh.bti7301.w2013.battleship.game.Board.BoardSetup;
import ch.bfh.bti7301.w2013.battleship.game.Board.Direction;

/**
 * 
 * @author simon
 * 
 */
public interface Ship extends Serializable {

	/**
	 * Get the start coordinates for the ship
	 * 
	 * @return Returns the starting coordinates of the ship
	 */
	public Coordinates getStartCoordinates();

	/**
	 * Get the end coordinates for the ship
	 * 
	 * @return Returns the end coordinates of the ship
	 */
	public Coordinates getEndCoordinates();

	/**
	 * Method to set the coordinates of the ship to new values
	 * 
	 * @param b
	 *            BoardSetup required to move the ship. THe ship cannot be moved
	 *            without a valid BoardSetup
	 * @param start
	 *            New start coordinates
	 * @param d
	 *            New direction
	 */
	public void setCoordinates(BoardSetup b, Coordinates start, Direction d);

	public void setCoordinates(BoardSetup b, Coordinates start, Coordinates end);

	/**
	 * Get the direction for the ship
	 * 
	 * @return Returns the direction the ship is facing (north, south, east or
	 *         west)
	 */
	public Board.Direction getDirection();

	/**
	 * Gets the size of the ship (unit is "boxes")
	 * 
	 * @return Number of boxes occupied by the ship
	 */
	public int getSize();

	/**
	 * Get the human-readable name for the ship. For example "Aircraft carrier"
	 * 
	 * @return Returns a string containing the name of the ship
	 */
	public String getName();

	/**
	 * Gets the number of boxes damaged for this ship
	 * 
	 * @return Number of boxes damaged
	 */
	public int getDamage();

	/**
	 * Checks if the ship was sunk (all boxes of the ship damaged)
	 * 
	 * @return Returns TRUE if the ship was sunk
	 */
	public boolean isSunk();

	/**
	 * Returns a list of all coordinates occupied by this ship
	 * 
	 * @return List of coordinates occupied by this ship
	 */
	public ArrayList<Coordinates> getCoordinatesForShip();
	
	/**
	 * 
	 * @param start
	 * @param d
	 * @return
	 */
	public Coordinates getEndCoordinatesForShip(Coordinates start, Direction d);

	/**
	 * 
	 * @return
	 */
	public ArrayList<Coordinates> getCoordinatesForDamage();

	/**
	 * 
	 * @return
	 */
	public ArrayList<Coordinates> getExtrapolatedCoordinates();

	/**
	 * 
	 * @param c
	 */
	public void setDamage(Coordinates c);

}
