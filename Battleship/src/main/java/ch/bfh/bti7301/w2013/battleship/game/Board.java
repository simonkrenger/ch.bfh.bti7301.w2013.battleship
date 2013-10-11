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


/**
 * @author simon
 * 
 */
public class Board {

	private static int DEFAULT_BOARD_SIZE = 10;
	
	private int size;
	
	private ArrayList<Ship> placedShips = new ArrayList<Ship>();
	private ArrayList<Missile> placedMissiles = new ArrayList<Missile>();
	
	private BoardSetup setup = new BoardSetup();

	public Board() {
		this(DEFAULT_BOARD_SIZE);
	}

	public Board(int size) {
		this.size = size;
	}

	public int getBoardSize() {
		return this.size;
	}

	public void placeShip(Ship s) {
		placedShips.add(s);
		// TODO: Do some sanity checks, throw exception if placement is not possible

		// First, check if ship can be placed (game state)
		// Check if number of ships is allowed
		
		// Check boundaries of board
		if(withinBoard(s.getStartCoordinates()) && withinBoard(s.getEndCoordinates())) {
			
		} else {
			// Start or end coordinates are not within board!
		}
		
		// Check 
	}
	
	public void placeMissile(Missile m) {
		//TODO
		
		//TODO: Check if its the players turn?
		//TODO: Check if coordinates of missile were already used
		//TODO: Observer pattern
	}
	
	public ArrayList<Ship> getPlacedShips() {
		return this.placedShips;
	}

	public ArrayList<Missile> getPlacedMissiles() {
		return this.placedMissiles;
	}

	public boolean withinBoard(Coordinates c) {
		return (c.x <= size) && (c.y <= size);
	}
	
	public BoardSetup getBoardSetup() {
		return this.setup;
	}
	
	public class BoardSetup {
		private BoardSetup() {
			
		}
		
		public void moveShip(Ship s, Coordinates newStartCoordinates, Direction d) {
			
		}
		
		public void moveShip(Ship s, Coordinates newStartCoordinates, Coordinates newEndCoordinates) {
			
		}
		
		public void done() {
			setup = null;
		}
	}

	public static class Coordinates {

		public int x;
		public int y;

		public Coordinates(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			// http://stackoverflow.com/questions/10813154/converting-number-to-letterhttp://stackoverflow.com/questions/10813154/converting-number-to-letter
			String alpha = x > 0 && x < 27 ? String
					.valueOf((char) (x + 'A' - 1)) : null;
			return alpha + y;
		}
	}
	
	public enum Direction {
		NORTH, SOUTH, WEST, EAST
	}
}
