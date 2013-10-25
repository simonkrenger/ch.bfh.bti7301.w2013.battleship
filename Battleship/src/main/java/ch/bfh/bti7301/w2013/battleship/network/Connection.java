package ch.bfh.bti7301.w2013.battleship.network;

import java.io.*;
import java.net.*;

import ch.bfh.bti7301.w2013.battleship.game.Game;
import ch.bfh.bti7301.w2013.battleship.game.Missile;
import ch.bfh.bti7301.w2013.battleship.game.players.GenericPlayer.PlayerState;

public class Connection extends Thread {

	final static int GAMEPORT = 42423;

	private ConnectionState connectionState;
	private ConnectionStateListener connectionStateListener;
	private static Connection instance;

	private ConnectionHandler handler;
	private static Game game = Game.getInstance();

	private Connection() throws IOException {
		new ConnectionListener(this).start();
	}

	public void acceptOpponent(Socket socket) {

		if (getConnectionState() == ConnectionState.CONNECTED) {
			throw new RuntimeException("Already connected");
		}
		try {
			handler = new ConnectionHandler(this, socket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void connectOpponet(String Ip) {

		if (getConnectionState() == ConnectionState.CONNECTED) {
			throw new RuntimeException("Already connected");
		}

		try {
			Socket socket = new Socket(Ip, GAMEPORT);
			handler = new ConnectionHandler(this, socket);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Connection getInstance() {
		if (instance == null) {
			try {
				instance = new Connection();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return instance;
	}

	public void sendMissile(Missile missile) {
		if (instance.connectionState != ConnectionState.CONNECTED) {
			throw new RuntimeException("Cannot shoot an imaginary Opponent");
		}
		handler.sendObject(missile);
	}

	public void sendStatus(PlayerState state) {

		if (instance.connectionState != ConnectionState.CONNECTED) {
			throw new RuntimeException("No Conncetion yet");
		}
		handler.sendObject(state);
	}

	public static void receiveObjectToGame(Object object) {

		if (object == PlayerState.READY) {
			game.getOpponent().setPlayerState(PlayerState.READY);
			game.getLocalPlayer().setPlayerState(PlayerState.WAITING);
		}

		else if (object == PlayerState.WAITING) {
			game.getOpponent().setPlayerState(PlayerState.WAITING);
			game.getLocalPlayer().setPlayerState(PlayerState.PLAYING);
		}

		else if (object == PlayerState.WAITING) {
			game.getOpponent().setPlayerState(PlayerState.WAITING);
			game.getLocalPlayer().setPlayerState(PlayerState.PLAYING);
		}

		else if (object == PlayerState.GAME_LOST) {
			game.getOpponent().setPlayerState(PlayerState.GAME_LOST);
			game.getLocalPlayer().setPlayerState(PlayerState.GAME_WON);
		}

		else if (object == PlayerState.GAME_WON) {
			game.getOpponent().setPlayerState(PlayerState.GAME_WON);
			game.getLocalPlayer().setPlayerState(PlayerState.GAME_LOST);
		}

		else {

			instance.handleMissile((Missile) object);

		}
	}

	private void handleMissile(Missile missile) {
		if (missile.getMissileState() == Missile.MissileState.FIRED) {

			Missile feedback = game.getLocalPlayer().placeMissile(missile);
			sendMissile(feedback);
		}

		else {
			game.getOpponent().getBoard().updateMissile(missile);
		}

	}

	public ConnectionState getConnectionState() {
		return connectionState;
	}

	public void setConnectionState(ConnectionState connectionState) {
		this.connectionState = connectionState;
		if (connectionStateListener != null) {
			connectionStateListener.stateChanged(connectionState);
		}
	}

	public void setConnectionStateListener(
			ConnectionStateListener connectionStateListener) {
		this.connectionStateListener = connectionStateListener;
	}

	public void closeConnection() {
		instance.cleanUp();
	}

	private void cleanUp() {
		handler.cleanUp();
		setConnectionState(ConnectionState.CLOSED);
	}
}
