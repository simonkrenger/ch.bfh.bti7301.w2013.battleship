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
package ch.bfh.bti7301.w2013.battleship;

import static ch.bfh.bti7301.w2013.battleship.utils.GameUtils.getAvailableShips;
import static ch.bfh.bti7301.w2013.battleship.utils.GameUtils.getFont;
import static ch.bfh.bti7301.w2013.battleship.utils.GameUtils.getString;
import static ch.bfh.bti7301.w2013.battleship.utils.GameUtils.rnd;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.sound.sampled.AudioFileFormat.Type;
import javax.sound.sampled.AudioSystem;

import ch.bfh.bti7301.w2013.battleship.game.Board.BoardSetup;
import ch.bfh.bti7301.w2013.battleship.game.Board.Direction;
import ch.bfh.bti7301.w2013.battleship.game.BoardListener;
import ch.bfh.bti7301.w2013.battleship.game.Coordinates;
import ch.bfh.bti7301.w2013.battleship.game.Game;
import ch.bfh.bti7301.w2013.battleship.game.Missile;
import ch.bfh.bti7301.w2013.battleship.game.Player;
import ch.bfh.bti7301.w2013.battleship.game.Ship;
import ch.bfh.bti7301.w2013.battleship.game.players.GenericPlayer.PlayerState;
import ch.bfh.bti7301.w2013.battleship.gui.BoardView;
import ch.bfh.bti7301.w2013.battleship.gui.BoardView.BoardType;
import ch.bfh.bti7301.w2013.battleship.gui.GuiPlayerStateListenerAdapter;
import ch.bfh.bti7301.w2013.battleship.gui.NetworkPanel;
import ch.bfh.bti7301.w2013.battleship.gui.ReadyButton;
import ch.bfh.bti7301.w2013.battleship.network.NetworkInformation;
import ch.bfh.bti7301.w2013.battleship.sounds.SoundEffects;

/**
 * @author Christian Meyer <chrigu.meyer@gmail.com>
 * 
 */
public class Main extends Application {

	private Game game;

	public Main() throws Exception {
		game = Game.getInstance();
		game.getOpponent().getBoard().addBoardListener(new BoardListener() {
			@Override
			public void stateChanged(final Missile m) {
				switch (m.getMissileState()) {
				case HIT:
					SoundEffects.playHit();
					break;
				case SUNK:
				case GAME_WON:
					SoundEffects.playSunk();
					if (new Random().nextBoolean())
						SoundEffects.playSOS();
					else
						SoundEffects.playWilhelmScream();
				default:
					break;
				}
			}
		});
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Output this for debugging and testing
		System.out.println(NetworkInformation.getIntAddresses());
		for (Type t : AudioSystem.getAudioFileTypes())
			System.out.println(t.getExtension());
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle(getString("title"));

		randomPlacement(getAvailableShips(game.getRule()), game
				.getLocalPlayer().getBoard().getBoardSetup());

		final Group root = new Group();

		final BoardView pbv = new BoardView(game, BoardType.LOCAL);
		pbv.relocate(10, 10);
		root.getChildren().add(pbv);

		final BoardView obv = new BoardView(game, BoardType.OPPONENT);
		obv.getTransforms().add(new Scale(0.5, 0.5, 0, 0));

		obv.relocate(pbv.getBoundsInParent().getMaxX() + 20, 10);
		root.getChildren().add(obv);

		VBox guiBox = new VBox(20);
		guiBox.relocate(obv.getBoundsInParent().getMinX(), obv
				.getBoundsInParent().getMaxY() + 8);
		guiBox.setPrefWidth(obv.getBoundsInParent().getWidth());
		guiBox.setPrefHeight(obv.getBoundsInParent().getHeight() - 10);
		root.getChildren().add(guiBox);

		final Button ready = new ReadyButton(game, pbv, obv, guiBox);
		guiBox.getChildren().add(ready);

		NetworkPanel networkPanel = new NetworkPanel();
		guiBox.getChildren().add(networkPanel);

		game.getOpponent().addPlayerStateListener(
				new GuiPlayerStateListenerAdapter() {
					@Override
					public void doStateChanged(Player p, PlayerState s) {
						switch (s) {
						case READY:
							ready.setText(getString("start"));
							break;
						case PLAYING:
							ColorAdjust ca = new ColorAdjust();
							ca.setSaturation(-0.7);
							obv.setEffect(ca);
							break;
						default:
							break;
						}
					}
				});
		game.getLocalPlayer().addPlayerStateListener(
				new GuiPlayerStateListenerAdapter() {
					@Override
					public void doStateChanged(Player p, PlayerState s) {
						switch (s) {
						case PLAYING:
							obv.setEffect(null);
							break;
						case GAME_WON:
						case GAME_LOST:
							pbv.setDisable(true);
							obv.setDisable(true);
							displayEnd(root, s);
							break;
						default:
							break;
						}
					}
				});

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				// There will be something more to do, but this should fix the
				// problem that the program doesn't quit properly.
				System.exit(0);
			}
		});

		double scale = 1.2;
		double baseWidth = pbv.getBoundsInParent().getWidth();
		double baseHeight = pbv.getBoundsInParent().getHeight();
		double width = baseWidth * 1.5 + 40;
		double height = baseHeight + 20;
		root.getTransforms().add(new Scale(scale, scale));
		final Scene scene = new Scene(root, width * scale, height * scale,
				Color.WHITE);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	private void displayEnd(Group root, PlayerState state) {
		Label t = new Label();

		InnerShadow is = new InnerShadow();
		Font f = new Font(40);
		switch (state) {
		case GAME_WON:
			t.setText(getString("game.won"));
			t.setTextFill(Color.ALICEBLUE);
			is.setColor(Color.AQUA);
			f = getFont("fonts/overhaul.ttf", 40);
			break;
		case GAME_LOST:
			t.setText(getString("game.lost"));
			t.setTextFill(Color.DEEPPINK);
			is.setColor(Color.DARKRED);
			f = getFont("fonts/thin-pencil-handwriting.ttf", 40);
		default:
			break;
		}
		t.setFont(f);
		t.setWrapText(true);

		DropShadow ds = new DropShadow();
		ds.setInput(is);
		ds.setOffsetY(5.0);
		ds.setOffsetX(5.0);
		ds.setColor(Color.GREY);

		t.setEffect(ds);

		Bounds rootBounds = root.getBoundsInLocal();
		t.setMinWidth(rootBounds.getWidth());
		t.setMaxWidth(rootBounds.getWidth());
		t.setAlignment(Pos.CENTER);
		t.setTextAlignment(TextAlignment.CENTER);
		t.relocate(0, (rootBounds.getHeight() - t.getBoundsInParent()
				.getHeight()) / 2);
		root.getChildren().add(t);
	}

	private void randomPlacement(List<Ship> ships, BoardSetup setup) {
		long time = System.currentTimeMillis();
		int size = game.getRule().getBoardSize();
		List<Coordinates> free = new ArrayList<>(size * size);

		for (int i = 1; i <= size; i++)
			for (int j = 1; j <= size; j++)
				free.add(new Coordinates(i, j));

		for (Ship ship : ships) {
			boolean successful = false;
			Coordinates c;
			Direction d = rnd(Direction.values());
			do {
				c = rnd(free);
				for (int i = 0; i < Direction.values().length; i++) {
					try {
						ship.setCoordinates(setup, c, d);
						setup.placeShip(ship);
						successful = true;
						break;
					} catch (RuntimeException ignore) {
						d = d.rotateCW();
					}
				}
				if (System.currentTimeMillis() - time > 1000) {
					throw new RuntimeException(
							"Random placement took too long!");
				}
			} while (!successful);

			Coordinates c1 = ship.getStartCoordinates().getNext(
					ship.getDirection().getOpposite());
			Coordinates c2 = c1.getNext(ship.getDirection().rotateCW());
			Coordinates c3 = c1.getNext(ship.getDirection().rotateCCW());
			for (int i = 0; i < ship.getSize() + 2; i++) {
				free.remove(c1);
				free.remove(c2);
				free.remove(c3);
				c1 = c1.getNext(ship.getDirection());
				c2 = c2.getNext(ship.getDirection());
				c3 = c3.getNext(ship.getDirection());
			}
		}
	}
}
