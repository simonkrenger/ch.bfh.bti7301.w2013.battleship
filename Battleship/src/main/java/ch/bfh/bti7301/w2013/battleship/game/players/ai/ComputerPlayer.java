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
import ch.bfh.bti7301.w2013.battleship.game.Board;
import ch.bfh.bti7301.w2013.battleship.game.Coordinates;
import ch.bfh.bti7301.w2013.battleship.game.Game;
import ch.bfh.bti7301.w2013.battleship.game.Missile;
import ch.bfh.bti7301.w2013.battleship.game.Player;
import ch.bfh.bti7301.w2013.battleship.game.PlayerStateListener;
import ch.bfh.bti7301.w2013.battleship.game.players.LocalPlayer;

/**
 * @author simon
 * 
 */
public class ComputerPlayer extends LocalPlayer implements
		PlayerStateListener {

	StrategyStack strategy;
	
	ArrayList<Coordinates> shotsTaken = new ArrayList<Coordinates>();

	ComputerBoard computerBoard;
	ComputerBoard playerBoard;

	public ComputerPlayer() {
		this("Computer");
	}

	public ComputerPlayer(String name) {
		super();

		// Setup environment
		computerBoard = new ComputerBoard(Game.getInstance().getRule().getBoardSize());
		playerBoard = new ComputerBoard(Game.getInstance().getRule().getBoardSize());

		// Generate 5 random steps
		strategy = new StrategyStack(10);

		// Random setup
		BoardSetup setup = this.getBoard().getBoardSetup();
		setup.randomPlacement(getAvailableShips(Game.getInstance().getRule()));
		setup.done();
		
		this.setPlayerState(PlayerState.READY);
	}
	
	public void activate() {
		this.addPlayerStateListener(this);
		Game.getInstance().getLocalPlayer().addPlayerStateListener(this);
	}

	@Override
	public void sendMissile(Missile m) {
		// This method is called when a Missile arrives from the opponent
		Game g = Game.getInstance();
        switch (m.getMissileState()) {
        case FIRED:
        		System.out.println("What is that? A bird?");
        		// Call placeMissile on GenericPlayer(ComputerPlayer)
                Missile f = super.placeMissile(m);
                if(this.getBoard().checkAllShipsSunk()) {
                        // ComputerPlayer lost, dang it!
                        System.out.println("Rats, this game was lost!");
                }
                Game.getInstance().handleMissile(f);
                break;
        case MISS:
                g.setActivePlayer(g.getLocalPlayer());
                break;
        case HIT:
        case SUNK:
        		// Insult player
        		System.out.println("Argh, you rat!");
                g.setActivePlayer(g.getComputerPlayer());
                break;
        case GAME_WON:
        		System.out.println("Haha, I win!");
                g.setWinningPlayer(Game.getInstance().getOpponent());
                break;
        }
	}
	
	@Override
	public void setPlayerState(PlayerState status) {
		this.status = status;
	}
	
	// Player state changes
	@Override
	public void stateChanged(Player p, PlayerState s) {        
        Player active = Game.getInstance().getActivePlayer();
        if (this == active) {
                // Ok, this means we're active
                fireMissile();
        }
	}
	
	private void fireMissile() {
        Coordinates c = strategy.getNextStep();
        while(shotsTaken.contains(c)) {
                c = strategy.getNextStep();
        }
        shotsTaken.add(c);
        Game.getInstance().handleMissile(new Missile(c));
	}

	@Override
	public Board getBoard() {
		return computerBoard;
	}

}
