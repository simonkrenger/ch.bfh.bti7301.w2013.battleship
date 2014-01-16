package ch.bfh.bti7301.w2013.battleship.game.players.ai;

import ch.bfh.bti7301.w2013.battleship.game.Board;
import ch.bfh.bti7301.w2013.battleship.game.Game;
import ch.bfh.bti7301.w2013.battleship.game.Missile;

public class ComputerBoard extends Board {

	public ComputerBoard(int boardSize) {
		super(boardSize);
	}

	@Override
	public void placeMissile(Missile m) {
		placedMissiles.add(m);
		Game.getInstance().getComputerPlayer().sendMissile(m);
	}
}
