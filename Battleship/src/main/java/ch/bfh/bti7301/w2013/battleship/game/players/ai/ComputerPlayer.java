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
import ch.bfh.bti7301.w2013.battleship.game.Board.BoardSetup;
import ch.bfh.bti7301.w2013.battleship.game.Board;
import ch.bfh.bti7301.w2013.battleship.game.BoardListener;
import ch.bfh.bti7301.w2013.battleship.game.Game;
import ch.bfh.bti7301.w2013.battleship.game.Missile;
import ch.bfh.bti7301.w2013.battleship.game.Player;
import ch.bfh.bti7301.w2013.battleship.game.PlayerStateListener;
import ch.bfh.bti7301.w2013.battleship.game.players.NetworkPlayer;

/**
 * @author simon
 * 
 */
public class ComputerPlayer extends NetworkPlayer implements
		PlayerStateListener, BoardListener {

	StrategyStack strategy;

	Board ownBoard;
	Board playerBoard;

	public ComputerPlayer() {
		this("Computer");
	}

	public ComputerPlayer(String name) {
		super(name);

		// Setup environment
		ownBoard = new Board(Game.getInstance().getRule().getBoardSize());
		playerBoard = new Board(Game.getInstance().getRule().getBoardSize());

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
		//Connection.getInstance().sendMissile(m);
		// Here, we receive a missile from the player
		
		// TODO: Evaluate on board
		// TODO: Set active player
		// TODO: Repeat if necessary
	}
	
	// Player state changes
	@Override
	public void stateChanged(Player p, PlayerState s) {
		Player active = Game.getInstance().getActivePlayer();
		if (this == active) {
			// Ok, this means we're active
		}
	}

	// Board state changes
	@Override
	public void stateChanged(Missile m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Board getBoard() {
		return ownBoard;
	}

}
