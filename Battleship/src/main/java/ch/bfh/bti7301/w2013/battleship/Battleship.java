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

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import ch.bfh.bti7301.w2013.battleship.game.Board;
import ch.bfh.bti7301.w2013.battleship.game.Board.Coordinates;
import ch.bfh.bti7301.w2013.battleship.game.Board.Direction;
import ch.bfh.bti7301.w2013.battleship.game.Game;
import ch.bfh.bti7301.w2013.battleship.game.GameRule;
import ch.bfh.bti7301.w2013.battleship.game.Missile;
import ch.bfh.bti7301.w2013.battleship.game.Ship;
import ch.bfh.bti7301.w2013.battleship.game.ships.AircraftCarrier;
import ch.bfh.bti7301.w2013.battleship.gui.BoardView;
import ch.bfh.bti7301.w2013.battleship.gui.ShipView;
import static ch.bfh.bti7301.w2013.battleship.gui.BoardView.SIZE;

/**
 * @author Christian Meyer <chrigu.meyer@gmail.com>
 * 
 */
public class Battleship extends Application {
	private ResourceBundle labels;

	private Game game;
	private GameRule rule;

	public Battleship() {
		labels = ResourceBundle.getBundle("translations");
		game = new Game();
		rule = new GameRule();

		game.getLocalPlayer()
				.getBoard()
				.placeShip(
						new AircraftCarrier(new Coordinates(2, 2),
								Direction.SOUTH));
		game.getLocalPlayer()
				.getBoard()
				.placeShip(
						new AircraftCarrier(new Coordinates(4, 2),
								Direction.EAST));

		game.getOpponent().getBoard()
				.placeMissile(new Missile(new Coordinates(1, 1)));
		game.getOpponent().getBoard()
				.placeMissile(new Missile(new Coordinates(3, 4)));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle(labels.getString("title"));

		final Group root = new Group();
		final Scene scene = new Scene(root, 800, 600, Color.WHITE);
		primaryStage.setScene(scene);

		final Board playerBoard = game.getLocalPlayer().getBoard();
		final BoardView pbv = new BoardView(playerBoard);
		pbv.relocate(10, 10);
		root.getChildren().add(pbv);

		Board opponentBoard = game.getOpponent().getBoard();
		BoardView obv = new BoardView(opponentBoard);
		obv.getTransforms().add(new Scale(0.5, 0.5, 0, 0));

		obv.relocate(pbv.getBoundsInParent().getMaxX() + 20, 10);
		root.getChildren().add(obv);

		int offset = 0;
		for (Ship s : getAvailableShips()) {
			final ShipView sv = new ShipView(s);
			sv.relocate(obv.getBoundsInParent().getMinX() + offset, pbv
					.getBoundsInParent().getMaxY() - 40);
			root.getChildren().add(sv);
			offset += SIZE * 1.1;

			sv.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent me) {
					initX = sv.getTranslateX();
					initY = sv.getTranslateY();
					dragAnchor = new Point2D(me.getSceneX(), me.getSceneY());
				}
			});
			sv.setOnMouseDragged(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent me) {
					double dragX = me.getSceneX() - dragAnchor.getX();
					double dragY = me.getSceneY() - dragAnchor.getY();
					// calculate new position of the circle
					double newXPosition = initX + dragX;
					double newYPosition = initY + dragY;
					sv.setTranslateX(newXPosition);
					sv.setTranslateY(newYPosition);
				}
			});
			sv.setOnMouseReleased(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent me) {
					if (pbv.contains(me.getSceneX(), me.getSceneY())) {
						// if on board, snap & add to it
						Coordinates c = pbv.getCoordinates(
								me.getSceneX() - pbv.getLayoutX() + me.getX(),
								me.getSceneY() - pbv.getLayoutY() + me.getY());
						Ship ship = buildShip(sv.getShipType(), c,
								Direction.NORTH);
						playerBoard.placeShip(ship);
						// TODO: handle illegal ship placement
						root.getChildren().remove(sv);
						pbv.addShip(ship);
					} else {
						// snap back
						sv.setTranslateX(initX);
						sv.setTranslateY(initY);
					}
				}
			});
		}

		primaryStage.show();
	}

	private List<Ship> getAvailableShips() {
		List<Ship> availableShips = new LinkedList<>();
		Coordinates dc = new Coordinates(0, 0);
		Direction dd = Direction.NORTH;

		for (Entry<Class<? extends Ship>, Integer> e : rule.getShipList()
				.entrySet()) {
			Ship ship = buildShip(e.getKey(), dc, dd);
			for (int i = 0; i < e.getValue(); i++)
				availableShips.add(ship);
		}
		return availableShips;
	}

	private Ship buildShip(Class<? extends Ship> type, Coordinates c,
			Direction d) {
		try {
			return type.getConstructor(Coordinates.class, Direction.class)
					.newInstance(c, d);
		} catch (Exception e) {
			throw new RuntimeException(
					"Error while creating ships through reflection", e);
		}
	}

	private double initX;
	private double initY;
	private Point2D dragAnchor;
}
