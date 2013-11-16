package ch.bfh.bti7301.w2013.battleship.network;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import ch.bfh.bti7301.w2013.battleship.game.Game;
import ch.bfh.bti7301.w2013.battleship.game.Missile;
import ch.bfh.bti7301.w2013.battleship.game.players.GenericPlayer.PlayerState;

public class Connection extends Thread {

	final static int GAMEPORT = 49768;

	private static Connection instance;

	private ConnectionState connectionState;

	private ConnectionStateListener connectionStateListener;
	private ConnectionListener listener;
	private ConnectionHandler handler;

	private static Game game = Game.getInstance();

	private Connection() {
		listener = new ConnectionListener(this);
		listener.start();
		GameState.getInstance();

	}

	public static Connection getInstance() {
		if (instance == null) {
			instance = new Connection();
		}
		return instance;
	}

	public void acceptOpponent(Socket socket) {

		if (getConnectionState() == ConnectionState.CONNECTED) {
			setConnectionState(ConnectionState.CONNECTED, "already connected!");
		}
		try {
			handler = new ConnectionHandler(this, socket);
		} catch (IOException e) {
			e.printStackTrace();
			setConnectionState(ConnectionState.CONNECTIONERROR,
					"couldn't create connectionHandler");
			// TODO: Reestablish Connection
		}

	}

	public void connectOpponent(String Ip) {

		if (getConnectionState() == ConnectionState.CONNECTED) {
			setConnectionState(ConnectionState.CONNECTED, "already connected!");
		}

		try {
			Socket socket = new Socket(Ip, GAMEPORT);
			handler = new ConnectionHandler(this, socket);
			listener.closeListener();

		} catch (IOException e) {
			e.printStackTrace();
			setConnectionState(ConnectionState.CONNECTIONERROR,
					"couldn't connect, please try again");
			// TODO: GUI Should show this Error and ask the user to connect
			// again.
		}
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

		if (object instanceof PlayerState) {
			PlayerState received = (PlayerState) object;
			switch (received) {
			case READY:
				if (game.getLocalPlayer().getPlayerState() == PlayerState.READY) {
					game.getLocalPlayer().setPlayerState(PlayerState.PLAYING);
					game.getOpponent().setPlayerState(PlayerState.WAITING);
				} else {
					game.getOpponent().setPlayerState(PlayerState.READY);
				}
				break;
			case WAITING:
				game.getOpponent().setPlayerState(PlayerState.WAITING);
				game.getLocalPlayer().setPlayerState(PlayerState.PLAYING);
				break;
			case GAME_LOST:
				game.getOpponent().setPlayerState(PlayerState.GAME_LOST);
				game.getLocalPlayer().setPlayerState(PlayerState.GAME_WON);
				break;
			case GAME_WON:
				game.getOpponent().setPlayerState(PlayerState.GAME_WON);
				game.getLocalPlayer().setPlayerState(PlayerState.GAME_LOST);
				break;
			case GAME_STARTED:
				// TODO
				break;
			case PLAYING:
				// TODO
				break;
			default:
				throw new RuntimeException("Invalid PlayerState received:"
						+ received);
			}

		} else if (object instanceof Missile) {
			Missile received = (Missile) object;
			instance.handleMissile(received);
		}
	}

	private void handleMissile(Missile missile) {

		switch (missile.getMissileState()) {
		case FIRED:
			Missile feedback = game.getLocalPlayer().placeMissile(missile);
			sendMissile(feedback);
			break;
		case MISS:
			game.getOpponent().setPlayerState(PlayerState.PLAYING);
			game.getLocalPlayer().setPlayerState(PlayerState.WAITING);
			game.getOpponent().getBoard().updateMissile(missile);
			break;
		case HIT:
		case SUNK:
			game.getOpponent().setPlayerState(PlayerState.WAITING);
			game.getLocalPlayer().setPlayerState(PlayerState.PLAYING);
			game.getOpponent().getBoard().updateMissile(missile);
			break;
		case GAME_WON:
			game.getOpponent().setPlayerState(PlayerState.GAME_LOST);
			game.getLocalPlayer().setPlayerState(PlayerState.GAME_WON);
			game.getOpponent().getBoard().updateMissile(missile);
			break;
		}
	}

	public ConnectionState getConnectionState() {
		return connectionState;
	}

	public void setConnectionState(ConnectionState connectionState, String msg) {
		this.connectionState = connectionState;
		if (connectionStateListener != null) {
			connectionStateListener.stateChanged(connectionState, msg);
		}
	}

	public void setConnectionStateListener(
			ConnectionStateListener connectionStateListener) {
		this.connectionStateListener = connectionStateListener;
	}

	public void closeConnection() {
		instance.cleanUp();
	}

	public void reestablishConnection(ConnectionState local, Object lastSent,
			Object lastReceived) {
		getInstance().cleanUp();
		instance = null;
		Connection.getInstance();
		setConnectionState(local, "reestablished connection");

		// FIX THIS
		if (lastSent != null) {
			handler.sendObject(lastSent);
		}
		if (lastReceived != null) {
			// Do Something
		}

	}

	public ArrayList<String> findOpponents() {
		// ToDO

		return null;

	}

	private void cleanUp() {
		// TODO: think about the whole closing game thing
		handler.cleanUp();
		setConnectionState(ConnectionState.CLOSED, "the connection was closed");
		instance = null;
	}
}
