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

import java.util.HashMap;
import java.util.Map;

import ch.bfh.bti7301.w2013.battleship.game.ships.AircraftCarrier;
import ch.bfh.bti7301.w2013.battleship.game.ships.Battleship;
import ch.bfh.bti7301.w2013.battleship.game.ships.Destroyer;
import ch.bfh.bti7301.w2013.battleship.game.ships.PatrolBoat;
import ch.bfh.bti7301.w2013.battleship.game.ships.Submarine;

/**
 * @author simon
 *
 */
public class GameRule {

	private int boardSize = 10;
	private int totalTime = 0;
	private Map <Class<? extends Ship>, Integer> shipList = new HashMap<Class<? extends Ship>, Integer>();
	
	/**
	 * Constructor for default rules
	 */
	public GameRule() {
		shipList.put(AircraftCarrier.class, 1);
		shipList.put(Battleship.class, 2);
		shipList.put(Submarine.class, 3);
		shipList.put(Destroyer.class, 4);
		shipList.put(PatrolBoat.class, 5);
	}
	
	public GameRule(int boardSize, int time, HashMap<Class<? extends Ship>, Integer> list) {
		this.boardSize = boardSize;
		this.totalTime = time;
		this.shipList = list;
	}

	public int getBoardSize() {
		return boardSize;
	}

	public void setBoardSize(int boardSize) {
		this.boardSize = boardSize;
	}

	public int getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}

	public Map<Class<? extends Ship>, Integer> getShipList() {
		return shipList;
	}

	public void setShipList(Map<Class<? extends Ship>, Integer> shipList) {
		this.shipList = shipList;
	}
	
	
}
