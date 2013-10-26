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

/**
 * @author simon
 * 
 */
public class Missile implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum MissileState {
		/**
		 * Initial state of the missile
		 */
		FIRED,

		/**
		 * The missile coordinates mark a ship
		 */
		HIT,

		/**
		 * The missile coordinates do not mark a ship
		 */
		MISS,

		/**
		 * This state is returned when the missile coordinates mark a ship and
		 * the ship was sunk as a result of that hit. This will then indicate
		 * the user that this ship was sunk.
		 */
		SUNK,

		/**
		 * This state is returned when the missile coordinates mark a ship, that
		 * ship was sunk as a result of that hit and the game is won.
		 */
		GAME_WON
	}

	private Coordinates coordinates = null;

	private MissileState status = null;

	public Missile(Coordinates c) {
		this.status = MissileState.FIRED;
		this.coordinates = c;
	}

	/**
	 * Returns the state of the missile. Caution, this function may return NULL
	 * if the missile does not contain an explicit status.
	 * 
	 * @return The state of this missile
	 */
	public MissileState getMissileState() {
		return this.status;
	}

	public void setMissileState(MissileState state) {
		this.status = state;
	}

	public Coordinates getCoordinates() {
		return this.coordinates;
	}
	
	@Override
	public String toString() {
		return "Missile [coordinates=" + coordinates + ", status=" + status
				+ "]";
	}
}
