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

import ch.bfh.bti7301.w2013.battleship.game.players.LocalPlayer;
import ch.bfh.bti7301.w2013.battleship.game.players.NetworkPlayer;
import ch.bfh.bti7301.w2013.battleship.game.players.GenericPlayer.PlayerState;
import ch.bfh.bti7301.w2013.battleship.network.Connection;

/**
 * @author simon
 * 
 */
public class Game {

	private int gameID;

	private static Game instance = null;

	private Player localPlayer;
	private Player opponentPlayer;

	@SuppressWarnings("unused")
	private Connection connection;
	


	private Game() {
		this.localPlayer = new LocalPlayer();
		this.opponentPlayer = new NetworkPlayer();
	}

	public static Game getInstance() {
		if (instance == null) {
			instance = new Game();
			instance.connection = Connection.getInstance();
		}
		return instance;
	}

	public Player getLocalPlayer() {
		return localPlayer;
	}

	public Player getOpponent() {
		return opponentPlayer;
	}

	/**
	 * Returns the active player. Note that this method may return NULL if no
	 * player is currently active.
	 * 
	 * @return The player that has the State "PLAYING". May return NULL if the game is not running!
	 */
	public Player getActivePlayer() {
		if (localPlayer.getPlayerState() == PlayerState.PLAYING
				&& opponentPlayer.getPlayerState() == PlayerState.WAITING) {
			return localPlayer;
		} else if (opponentPlayer.getPlayerState() == PlayerState.PLAYING
				&& localPlayer.getPlayerState() == PlayerState.WAITING) {
			return opponentPlayer;
		}
		return null;
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}
}
