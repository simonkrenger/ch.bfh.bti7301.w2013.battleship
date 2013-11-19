package ch.bfh.bti7301.w2013.battleship.gui;

import javafx.application.Platform;
import ch.bfh.bti7301.w2013.battleship.network.ConnectionState;
import ch.bfh.bti7301.w2013.battleship.network.ConnectionStateListener;

public abstract class GuiConnectionStateListenerAdapter implements
		ConnectionStateListener {
	@Override
	public final void stateChanged(final ConnectionState newState,
			final String msg) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				doStateChanged(newState, msg);
			}
		});
	}

	public abstract void doStateChanged(ConnectionState newState, String msg);
}
