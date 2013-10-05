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

import java.awt.List;
import java.util.ArrayList;

/**
 * @author simon
 *
 */
public class Board {

	private int boardSize = 10;
	private ArrayList<Ship> placedShips = new ArrayList<Ship>();
	private ArrayList<Missile> placedMissiles = new ArrayList<Missile>();
	
	public Board() {
		
	}
	
	public Board(int size) {
		this.boardSize = size;
	}
	
	public int getBoardSize() {
		return this.boardSize;
	}
	
	public void placeShip(Ship s) {
		
	}
	
	public boolean withinBoard(Coordinates c) {
		return (c.x <= boardSize) && (c.y <= boardSize);
	}
	
	public class Coordinates {
		
		public int x;
		public int y;
		
		public Coordinates(int x, int y) {
			this.x = x;
			this.y = y;
		}		
	}
}
