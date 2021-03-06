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
package ch.bfh.bti7301.w2013.battleship.game.players;

import ch.bfh.bti7301.w2013.battleship.game.Coordinates;
import ch.bfh.bti7301.w2013.battleship.game.Missile.MissileState;
import ch.bfh.bti7301.w2013.battleship.game.Game;
import ch.bfh.bti7301.w2013.battleship.game.Missile;
import ch.bfh.bti7301.w2013.battleship.game.Player;
import ch.bfh.bti7301.w2013.battleship.game.Ship;
import ch.bfh.bti7301.w2013.battleship.network.Connection;

/**
 * @author simon
 * 
 */
public class LocalPlayer extends GenericPlayer {

	@Override
	public Missile placeMissile(Missile m) {

		// Do some sanity checks
		if (!playerBoard.withinBoard(m.getCoordinates())) {
			throw new RuntimeException(
					"Coordinates of missile not within board!");
		}

		if (m.getMissileState() != MissileState.FIRED) {
			throw new RuntimeException("Missle has state "
					+ m.getMissileState() + ", needs to be FIRED!");
		}

		for (Ship s : playerBoard.getPlacedShips()) {
			for (Coordinates c : s.getCoordinatesForShip()) {
				if (c.equals(m.getCoordinates())) {
					// It's a hit!
					s.setDamage(m.getCoordinates());
					if (s.isSunk()) {
						m.setMissileState(MissileState.SUNK);
						m.setSunkShip(s);
						if (playerBoard.checkAllShipsSunk()) {
							m.setMissileState(MissileState.GAME_WON);
						}
					} else {
						m.setMissileState(MissileState.HIT);
					}
					getBoard().placeMissile(m);
					return m;
				}
			}
		}
		m.setMissileState(MissileState.MISS);
		getBoard().placeMissile(m);
		return m;
	}

	@Override
	public void setPlayerState(PlayerState status) {
		// Prevent infinite loops:
		if (status == getPlayerState()
				// Also, for test cases where we connect the client to itself:
				|| (status == PlayerState.GAME_WON && getPlayerState() == PlayerState.GAME_LOST)
				|| (status == PlayerState.GAME_LOST && getPlayerState() == PlayerState.GAME_WON))
			return;

		// This occurs when the player presses "ready" and the opponents is already "ready"
		Player opponent = Game.getInstance().getOpponent();
		if (status == PlayerState.READY
				&& opponent.getPlayerState() == PlayerState.READY) {
			status = PlayerState.WAITING;
			opponent.setPlayerState(PlayerState.PLAYING);
		}

		super.setPlayerState(status);
		Connection.getInstance().sendStatus(status);
	}
}
