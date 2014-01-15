package ch.bfh.bti7301.w2013.battleship.game;


import ch.bfh.bti7301.w2013.battleship.game.players.GenericPlayer;
import ch.bfh.bti7301.w2013.battleship.game.players.GenericPlayer.PlayerState;
import ch.bfh.bti7301.w2013.battleship.network.Connection;

public class GameState {

    private static GameState instance;
	private Object lastIn;
	private Object lastOut;
	private int counter;
	private PlayerState localPlayerState;
	private PlayerState opponentPlayerState;
	private String localIp;
	private String opponentIp;
    private int reestablishCount;
	public String getLocalIp() {
		return localIp;
	}

	public void setLocalIp(String localIp) {
		this.localIp = localIp;
	}

	public String getOpponentIp() {
		return opponentIp;
	}

	public void setOpponentIp(String opponentIp) {
		this.opponentIp = opponentIp;
	}

	public GameState() {
		setCounter(0);
		setLastIn(null);
		setLastOut(null);
		setLocalPlayerState(GenericPlayer.PlayerState.GAME_STARTED);
		setOpponentPlayerState(null);
		setReestablishCount(0);
	}

	public void restoreGame(Object opponentCounter) {
		System.out.println("METHOD GAMESTATE.RESTOREGAME WAS CALLED");
		int oct = (int) opponentCounter;
		if (this.counter == oct) {
			Game.getInstance().getLocalPlayer().setPlayerState(localPlayerState);
			
		} else if (this.counter > oct) {
			if (lastOut instanceof PlayerState) {
				Connection.getInstance().sendStatus((PlayerState) lastOut);
				if (lastOut instanceof Missile) {
					Connection.getInstance().sendMissile((Missile) lastOut);
				}
			} else if (this.counter < oct) {
				Connection.getInstance().sendCounter(counter);
			}
		}
	}

	public void resumeGame() {
		setReestablishCount(0);
		// TODO
	}
	
	public static GameState getInstance(){
			if (instance == null) {
				instance = new GameState();
			}
			return instance;
	}

	public Object getLastIn() {
		return lastIn;
	}

	public void setLastIn(Object lastIn) {
		this.lastIn = lastIn;
	}

	public Object getLastOut() {
		return lastOut;
	}

	public void setLastOut(Object lastOut) {
		this.lastOut = lastOut;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public void addCounter() {
		this.counter++;
	}

	public PlayerState getLocalPlayerState() {
		return localPlayerState;
	}

	public void setLocalPlayerState(PlayerState localPlayerState) {
		this.localPlayerState = localPlayerState;
	}

	public PlayerState getOpponentPlayerState() {
		return opponentPlayerState;
	}

	public void setOpponentPlayerState(PlayerState networkPlayerState) {
		this.opponentPlayerState = networkPlayerState;
	}

	public void setReestablishCount(int reestablishCount) {
		this.reestablishCount = reestablishCount;
	}
	
	public void addReestablishCount(){
		this.reestablishCount++;
	}
	public int getReestablishCount() {
		return reestablishCount;
	}
}
