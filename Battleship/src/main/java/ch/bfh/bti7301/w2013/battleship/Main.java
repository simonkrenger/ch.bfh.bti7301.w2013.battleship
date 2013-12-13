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

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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
import ch.bfh.bti7301.w2013.battleship.game.Board.BoardSetup;
import ch.bfh.bti7301.w2013.battleship.game.Board.Direction;
import ch.bfh.bti7301.w2013.battleship.game.Coordinates;
import ch.bfh.bti7301.w2013.battleship.game.Game;
import ch.bfh.bti7301.w2013.battleship.game.Player;
import ch.bfh.bti7301.w2013.battleship.game.Ship;
import ch.bfh.bti7301.w2013.battleship.game.players.GenericPlayer.PlayerState;
import ch.bfh.bti7301.w2013.battleship.gui.BoardView;
import ch.bfh.bti7301.w2013.battleship.gui.BoardView.BoardType;
import ch.bfh.bti7301.w2013.battleship.gui.GuiPlayerStateListenerAdapter;
import ch.bfh.bti7301.w2013.battleship.gui.NetworkPanel;
import ch.bfh.bti7301.w2013.battleship.gui.ReadyButton;
import ch.bfh.bti7301.w2013.battleship.gui.SettingsPanel;
import ch.bfh.bti7301.w2013.battleship.sounds.SoundEffectsBoardListener;

/**
 * @author Christian Meyer <chrigu.meyer@gmail.com>
 * 
 */
public class Main extends Application {

	private Game game;

	public Main() throws Exception {
		game = Game.getInstance();
		game.getOpponent()
				.getBoard()
				.addBoardListener(
						new SoundEffectsBoardListener(BoardType.OPPONENT));
		game.getLocalPlayer()
				.getBoard()
				.addBoardListener(
						new SoundEffectsBoardListener(BoardType.LOCAL));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle(getString("title"));

		game.getLocalPlayer().getBoard().getBoardSetup()
				.randomPlacement(getAvailableShips(game.getRule()));

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

		TabPane tabs = new TabPane();
		guiBox.getChildren().add(tabs);
		tabs.getTabs().add(createTab(new NetworkPanel(), "network"));
		tabs.getTabs().add(createTab(new SettingsPanel(), "settings"));

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
		final double width = baseWidth * 1.5 + 40;
		final double height = baseHeight + 20;
		final Scale st = new Scale(scale, scale);
		root.getTransforms().add(st);
		final Scene scene = new Scene(root, width * scale, height * scale,
				Color.WHITE);

		scene.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(
					ObservableValue<? extends Number> observableValue,
					Number oldWidth, Number newWidth) {
				double sw = newWidth.doubleValue() / width;
				double sh = scene.getHeight() / height;
				double s = Math.min(sw, sh);
				st.setX(s);
				st.setY(s);
				Bounds rb = root.getBoundsInParent();
				double tx = (scene.getWidth() - rb.getWidth() - s * 4) / 2;
				double ty = (scene.getHeight() - rb.getHeight() - s * 4) / 2;
				root.relocate(tx, ty);
				System.out.println("Width: " + newWidth);
			}
		});
		scene.heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(
					ObservableValue<? extends Number> observableValue,
					Number oldHeight, Number newHeight) {
				double sw = scene.getWidth() / width;
				double sh = newHeight.doubleValue() / height;
				double s = Math.min(sw, sh);
				st.setX(s);
				st.setY(s);
				Bounds rb = root.getBoundsInParent();
				double tx = (scene.getWidth() - rb.getWidth() - s * 4) / 2;
				double ty = (scene.getHeight() - rb.getHeight() - s * 4) / 2;
				root.relocate(tx, ty);
				System.out.println("Height: " + newHeight);
			}
		});

		primaryStage.setScene(scene);
		primaryStage.setMinHeight(height);
		primaryStage.setMinWidth(width);
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

	private Tab createTab(Node content, String name) {
		Tab tab = new Tab(getString(name));
		tab.setClosable(false);
		tab.setContent(content);
		return tab;
	}

}
