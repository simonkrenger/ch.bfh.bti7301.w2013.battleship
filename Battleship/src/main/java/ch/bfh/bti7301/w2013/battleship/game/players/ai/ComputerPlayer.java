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
package ch.bfh.bti7301.w2013.battleship.game.players.ai;

import static ch.bfh.bti7301.w2013.battleship.utils.GameUtils.getAvailableShips;

import java.util.ArrayList;

import ch.bfh.bti7301.w2013.battleship.game.Board.BoardSetup;
import ch.bfh.bti7301.w2013.battleship.game.Coordinates;
import ch.bfh.bti7301.w2013.battleship.game.Missile.MissileState;
import ch.bfh.bti7301.w2013.battleship.game.Board;
import ch.bfh.bti7301.w2013.battleship.game.BoardListener;
import ch.bfh.bti7301.w2013.battleship.game.Game;
import ch.bfh.bti7301.w2013.battleship.game.Missile;
import ch.bfh.bti7301.w2013.battleship.game.Player;
import ch.bfh.bti7301.w2013.battleship.game.PlayerStateListener;
import ch.bfh.bti7301.w2013.battleship.game.players.LocalPlayer;
import ch.bfh.bti7301.w2013.battleship.game.players.NetworkPlayer;
import ch.bfh.bti7301.w2013.battleship.game.players.GenericPlayer.PlayerState;

/**
 * @author simon
 * 
 */
public class ComputerPlayer extends LocalPlayer implements
		PlayerStateListener {

	StrategyStack strategy;
	
	ArrayList<Coordinates> shotsTaken = new ArrayList<Coordinates>();

	Board computerBoard;

	public ComputerPlayer() {
		this("Computer");
	}

	public ComputerPlayer(String name) {
		super();

		// Setup environment
		computerBoard = new Board(Game.getInstance().getRule().getBoardSize());

		// Generate 5 random steps
		strategy = new StrategyStack(10);
		strategy.addRandom(5);

		// Random setup
		BoardSetup setup = this.getBoard().getBoardSetup();
		setup.randomPlacement(getAvailableShips(Game.getInstance().getRule()));
		setup.done();

		this.setPlayerState(PlayerState.READY);
	}

	@Override
	public void sendMissile(Missile m) {
		if(m.getMissileState() == MissileState.FIRED) {
			Missile f = super.placeMissile(m);
			if(this.getBoard().checkAllShipsSunk()) {
				// ComputerPlayer lost, dang it!
				System.out.println("Rats, this game was lost!");
			}
			Game.getInstance().handleMissile(f);
		} else {
			// Is a feedback
			if(m.getMissileState() == MissileState.GAME_WON) {
				System.out.println("Yay, I won!");
			} else {
				switch(m.getMissileState()) {
				case HIT:
				case SUNK:
					// Insult the player
					System.out.println("Take that you rat!");
					fireMissile();
					break;
				case MISS:
					// Insult the player
					System.out.println("Argh, I swear it just was there!");
					break;
				default:
					break;
				
				}
			}
		}

	}
	
	// Player state changes
	@Override
	public void stateChanged(Player p, PlayerState s) {
		System.out.println("Computer player noticed that the player " + p + " is now " + s);
		
		
		if(p == Game.getInstance().getLocalPlayer() && s == PlayerState.WAITING) {
			p.setPlayerState(PlayerState.WAITING);
		}
		
		Player active = Game.getInstance().getActivePlayer();
		if (this == active) {
			// Ok, this means we're active
			System.out.println("Computer player is active!");
			fireMissile();
		}
	}
	
	@Override
	public void setPlayerState(PlayerState status) {
		this.status = status;
	}
	
	private void fireMissile() {
		Coordinates c = strategy.pop();
		while(shotsTaken.contains(c)) {
			c = strategy.pop();
		}
		shotsTaken.add(c);
		Game.getInstance().handleMissile(new Missile(c));
	}

	@Override
	public Board getBoard() {
		return computerBoard;
	}

}
