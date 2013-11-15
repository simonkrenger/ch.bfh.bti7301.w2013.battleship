package ch.bfh.bti7301.w2013.battleship.gui;

import javafx.application.Platform;
import ch.bfh.bti7301.w2013.battleship.game.Player;
import ch.bfh.bti7301.w2013.battleship.game.PlayerStateListener;
import ch.bfh.bti7301.w2013.battleship.game.players.GenericPlayer.PlayerState;

public abstract class GuiPlayerStateListenerAdapter implements
		PlayerStateListener {

	@Override
	public final void stateChanged(final Player p, final PlayerState s) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				doStateChanged(p, s);
			}
		});
	}

	public abstract void doStateChanged(Player p, PlayerState s);
}
