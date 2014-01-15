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
import ch.bfh.bti7301.w2013.battleship.game.players.ai.ComputerPlayer;
import ch.bfh.bti7301.w2013.battleship.network.Connection;
import ch.bfh.bti7301.w2013.battleship.network.ConnectionState;
import ch.bfh.bti7301.w2013.battleship.network.ConnectionStateListener;

/**
 * @author simon
 * 
 */
public class Game implements ConnectionStateListener {

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
	
	/**
	 * Computer player
	 */
	private ComputerPlayer computerPlayer;

	@SuppressWarnings("unused")
	private Connection connection;
	@SuppressWarnings("unused")
	private GameState gameState;

	/**
	 * Private constructor for the Singleton pattern
	 */
	private Game() {

	}

	/**
	 * Singleton pattern, returns the only instance of the Game class.
	 * 
	 * @return The only instance of this Singleton
	 */
	public static Game getInstance() {
		if (instance == null) {
			instance = new Game();

			// Add players
			instance.localPlayer = new LocalPlayer();
			instance.opponentPlayer = new NetworkPlayer();
			
			// Add computer player
			instance.computerPlayer = new ComputerPlayer();

			// Add connection state listener to handle GameRule validation
			instance.connection = Connection.getInstance();
			Connection.getInstance().addConnectionStateListener(instance);

			instance.gameState = GameState.getInstance();

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
	
	public ComputerPlayer getComputerPlayer() {
		return computerPlayer;
	}
	
	public void setComputerPlayer(ComputerPlayer c) {
		opponentPlayer = c;
	}

	/**
	 * Returns the active player. Note that this method may return NULL if no
	 * player is currently active.
	 * 
	 * @return The player that has the State "PLAYING". May return NULL if the
	 *         game is not running!
	 */
	public Player getActivePlayer() {
		if (getLocalPlayer().getPlayerState() == PlayerState.PLAYING
				&& getOpponent().getPlayerState() == PlayerState.WAITING) {
			return getLocalPlayer();
		} else if (getOpponent().getPlayerState() == PlayerState.PLAYING
				&& getLocalPlayer().getPlayerState() == PlayerState.WAITING) {
			return getOpponent();
		}
		return null;
	}

	public void setActivePlayer(Player p) {
		if (p == getOpponent()) {
			getOpponent().setPlayerState(PlayerState.PLAYING);
			getLocalPlayer().setPlayerState(PlayerState.WAITING);
		} else if (p == getLocalPlayer()) {
			getOpponent().setPlayerState(PlayerState.WAITING);
			getLocalPlayer().setPlayerState(PlayerState.PLAYING);
		} else {
			throw new RuntimeException("setActivePlayer() failed, player" + p
					+ " not recognized!");
		}
	}
	
	public void setWinningPlayer(Player p) {
		if (p == getOpponent()) {
			
		} else if(p == getLocalPlayer()) {
			getOpponent().setPlayerState(PlayerState.GAME_LOST);
			getLocalPlayer().setPlayerState(PlayerState.GAME_WON);
		} else {
			throw new RuntimeException("setWinningPlayer() failed, player" + p
					+ " not recognized!");
		}
	}

	/**
	 * Method to get the gamerules of this instance of Game
	 * 
	 * @return The active gamerules
	 */
	public GameRule getRule() {
		return rule;
	}

	@Override
	public void stateChanged(ConnectionState newState, String msg) {
		// As soon as the connection changes to "CONNECTED", send the GameRule
		// to be checked
		if (newState == ConnectionState.CONNECTED) {
			Connection.getInstance().sendGameRule(this.rule);
		}
	}

	/**
	 * Method to check game rules
	 * 
	 * @param g
	 */
	public void checkGameRule(GameRule g) {
		if (g.equals(this.rule)) {
			// Everything is ok
		} else {
			// TODO: Notify user that he has different GameRules
			// if( User wants to replace his gamerules )
			// { // TODO: Replace this.rule and reload GUI }
			// else { // TODO: To be discussed (disconnect?) }
		}
	}

	public void handleMissile(Missile m) {
		// This method is called when a Missile arrives from the opponent
		switch (m.getMissileState()) {
		case FIRED:
			// "FIRED" means the missile was fired and needs to be sent back
			// with a new status
			Missile feedback = getLocalPlayer().placeMissile(m);
			if(Game.getInstance().getOpponent() instanceof ComputerPlayer) {
				// Send to computer player
				ComputerPlayer comp = (ComputerPlayer) Game.getInstance().getOpponent();
				comp.sendMissile(feedback);
			} else {
				// Send to network player
				Connection.getInstance().sendMissile(feedback);
			}
			break;
		case MISS:
			setActivePlayer(getOpponent());
			getOpponent().getBoard().updateMissile(m);
			break;
		case HIT:
		case SUNK:
			setActivePlayer(getLocalPlayer());
			getOpponent().getBoard().updateMissile(m);
			break;
		case GAME_WON:
			setWinningPlayer(getLocalPlayer());
			getOpponent().getBoard().updateMissile(m);
			break;
		}
	}

	public void handlePlayerState(PlayerState ps) {
		// This method is called when a PlayerState arrives from the opponent.

		switch (ps) {
		case READY:
			if (getLocalPlayer().getPlayerState() == PlayerState.READY) {
				setActivePlayer(getLocalPlayer());
			} else {
				getOpponent().setPlayerState(PlayerState.READY);
			}
			break;
		case WAITING:
			setActivePlayer(getLocalPlayer());
			break;
		case GAME_LOST:
			setWinningPlayer(getLocalPlayer());
			break;
		case GAME_WON:
			setWinningPlayer(getOpponent());
			break;
		case GAME_STARTED:
		case PLAYING:
			// TODO
			break;
		default:
			throw new RuntimeException("Invalid PlayerState received:" + ps);
		}
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
