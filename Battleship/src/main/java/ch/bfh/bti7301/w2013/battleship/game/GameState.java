package ch.bfh.bti7301.w2013.battleship.game;

/**
 * 
 * @author fueenzi
 *
 */

public enum GameState {
	
	//not connected yet
	INITIAL,
	
	//waiting for Opponent
	READY,
	
	//this players turn to shoot
	ACTIVE,
	
	//opponents turn to shoot
	WAITING,
	
	//gameOver
	END

}
