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

import ch.bfh.bti7301.w2013.battleship.game.Board;
import ch.bfh.bti7301.w2013.battleship.game.Board.Coordinates;
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
		this.startCoordinates = start;
		this.endCoordinates = end;
		this.size = size;
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

}
