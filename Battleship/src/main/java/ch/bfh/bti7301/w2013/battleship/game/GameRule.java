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
public class GameRule implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int boardSize = 12;
	private int totalTime = 0;
	private GameType gameType = GameType.DEFAULT;
	private Map<Class<? extends Ship>, Integer> shipList = new HashMap<Class<? extends Ship>, Integer>();

	/**
	 * Constructor for default rules
	 */
	public GameRule() {
		shipList.put(AircraftCarrier.class, 1);
		shipList.put(Battleship.class, 2);
		shipList.put(Submarine.class, 1);
		shipList.put(Destroyer.class, 2);
		shipList.put(PatrolBoat.class, 4);
	}

	public GameRule(int boardSize, int time,
			HashMap<Class<? extends Ship>, Integer> list) {
		this.boardSize = boardSize;
		this.totalTime = time;
		this.shipList = list;
	}

	public int getBoardSize() {
		return boardSize;
	}

	void setBoardSize(int boardSize) {
		this.boardSize = boardSize;
	}

	public GameType getGameType() {
		return gameType;
	}

	void setGameType(GameType gameType) {
		this.gameType = gameType;
	}

	public int getTotalTime() {
		return totalTime;
	}

	void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}

	public Map<Class<? extends Ship>, Integer> getShipList() {
		return shipList;
	}

	void setShipList(Map<Class<? extends Ship>, Integer> shipList) {
		this.shipList = shipList;
	}

	public static enum GameType {
		/**
		 * Default rule: player can shoot again when hit, not time limit.
		 */
		DEFAULT;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + boardSize;
		result = prime * result
				+ ((gameType == null) ? 0 : gameType.hashCode());
		result = prime * result
				+ ((shipList == null) ? 0 : shipList.hashCode());
		result = prime * result + totalTime;
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
		GameRule other = (GameRule) obj;
		if (boardSize != other.boardSize)
			return false;
		if (gameType != other.gameType)
			return false;
		if (shipList == null) {
			if (other.shipList != null)
				return false;
		} else if (!shipList.equals(other.shipList))
			return false;
		if (totalTime != other.totalTime)
			return false;
		return true;
	}

}
