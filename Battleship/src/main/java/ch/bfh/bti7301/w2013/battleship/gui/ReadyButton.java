package ch.bfh.bti7301.w2013.battleship.gui;

import java.util.ResourceBundle;

import javafx.animation.ParallelTransitionBuilder;
import javafx.animation.ScaleTransitionBuilder;
import javafx.animation.TranslateTransitionBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.util.Duration;
import ch.bfh.bti7301.w2013.battleship.game.Game;
import ch.bfh.bti7301.w2013.battleship.game.players.GenericPlayer.PlayerState;
import ch.bfh.bti7301.w2013.battleship.utils.GameUtils;

public class ReadyButton extends Button {

	public ReadyButton(final Game game, final BoardView pbv,
			final BoardView obv, final Node guiNode) {
		super(ResourceBundle.getBundle("translations").getString("ready"));
		setPrefWidth(obv.getBoundsInParent().getWidth());
		setFont(GameUtils.getFont("fonts/overhaul.ttf", 40.0));
		setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				game.getLocalPlayer().setPlayerState(PlayerState.READY);
				ParallelTransitionBuilder
						.create()
						.children(
								ScaleTransitionBuilder.create().node(pbv)
										.duration(Duration.seconds(1)).toX(0.5)
										.toY(0.5).build(),
								TranslateTransitionBuilder
										.create()
										.node(pbv)
										.duration(Duration.seconds(1))
										.toX(-pbv.getBoundsInParent()
												.getWidth()
												/ (4 * pbv.getScaleFactor()))
										.toY(-pbv.getBoundsInParent()
												.getHeight()
												/ (4 * pbv.getScaleFactor()))
										.build(),
								ScaleTransitionBuilder.create().node(obv)
										.duration(Duration.seconds(1)).toX(2)
										.toY(2).build(),
								TranslateTransitionBuilder
										.create()
										.node(obv)
										.duration(Duration.seconds(1))
										.toX(10
												+ pbv.getBoundsInParent()
														.getWidth()
												* (1 - pbv.getScaleFactor())
												/ 2)
										.toY(obv.getBoundsInParent()
												.getHeight()
												/ obv.getScaleFactor()).build(),
								TranslateTransitionBuilder
										.create()
										.node(guiNode)
										.duration(Duration.seconds(1))
										.toX(-obv.getBoundsInParent().getMinX() + 10)
										.build()//
						).build().play();
				setVisible(false);
			}
		});
	}
}
