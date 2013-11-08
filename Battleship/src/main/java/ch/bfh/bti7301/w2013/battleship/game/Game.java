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

	/**
	 * GameID, a unique identifier for the current game TODO: Implement method
	 * to set the same game ID for both players
	 */
	private int gameID;

	/**
	 * Singleton pattern
	 */
	private static Game instance = null;

	private GameRule rule = new GameRule();

	/**
	 * The local player
	 */
	private Player localPlayer;

	/**
	 * The opponent player
	 */
	private Player opponentPlayer;

	@SuppressWarnings("unused")
	private Connection connection;

	/**
	 * Private constructor for the Singleton pattern
	 */
	private Game() {
		// private
	}

	/**
	 * Singleton pattern, returns the only instance of the Game class.
	 * 
	 * @return The only instance of this Singleton
	 */
	public static Game getInstance() {
		if (instance == null) {
			instance = new Game();
			instance.localPlayer = new LocalPlayer();
			instance.opponentPlayer = new NetworkPlayer();
			instance.connection = Connection.getInstance();
		}
		return instance;
	}

	/**
	 * Returns the local player (human)
	 * 
	 * @return A Player object representing the local human player
	 */
	public Player getLocalPlayer() {
		return localPlayer;
	}

	/**
	 * Gets the opponent player (connected via network)
	 * 
	 * @return A Player object representing the opponent
	 */
	public Player getOpponent() {
		return opponentPlayer;
	}

	/**
	 * Returns the active player. Note that this method may return NULL if no
	 * player is currently active.
	 * 
	 * @return The player that has the State "PLAYING". May return NULL if the
	 *         game is not running!
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

	public GameRule getRule() {
		return rule;
	}

	/**
	 * Method to get the ID of this game
	 * 
	 * @return Returns the unique ID of this game
	 */
	public int getGameID() {
		return gameID;
	}

	/**
	 * Method to set the game ID for this game
	 * 
	 * @param gameID
	 *            The new unique ID to set for this game
	 */
	public void setGameID(int gameID) {
		this.gameID = gameID;
	}
}
