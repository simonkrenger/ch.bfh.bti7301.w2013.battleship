package ch.bfh.bti7301.w2013.battleship.gui;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.effect.SepiaTone;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import ch.bfh.bti7301.w2013.battleship.game.Board;
import ch.bfh.bti7301.w2013.battleship.game.BoardListener;
import ch.bfh.bti7301.w2013.battleship.game.Coordinates;
import ch.bfh.bti7301.w2013.battleship.game.Missile;
import ch.bfh.bti7301.w2013.battleship.game.Ship;

public class BoardView extends Parent {
	public static final int SIZE = 40;

	private Map<Coordinates, MissileView> missileViews = new HashMap<>();

	public BoardView(final Board board) {
		final int rows, columns;
		rows = columns = board.getBoardSize();

		getChildren().add(getWater(rows, columns));
		for (int i = 0; i <= rows; i++) {
			getChildren().add(getLine(i * SIZE, 0, i * SIZE, SIZE * columns));
		}
		for (int i = 0; i <= columns; i++) {
			getChildren().add(getLine(0, i * SIZE, SIZE * rows, i * SIZE));
		}

		for (Ship ship : board.getPlacedShips()) {
			addShip(ship);
		}

		for (Missile missile : board.getPlacedMissiles()) {
			drawMissile(missile);
		}

		// This is for the opponent's board. This has to move somewhere else
		// later, I think
		setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				Missile m = new Missile(getCoordinates(e.getX(), e.getY()));
				drawMissile(m);
				try {
					board.placeMissile(m);
				} catch (RuntimeException r) {
					missileViews.get(m.getCoordinates()).setVisible(false);
				}
			}
		});

		board.addBoardListener(new BoardListener() {
			@Override
			public void stateChanged(final Missile m) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						// javaFX operations should go here
						MissileView mv = missileViews.get(m.getCoordinates());
						if (mv != null)
							mv.update(m);
						else
							drawMissile(m);
					}
				});
			}
		});
	}

	public void addShip(Ship ship) {
		ShipView sv = new ShipView(ship);
		sv.relocate(getX(ship.getStartCoordinates()),
				getY(ship.getStartCoordinates()));
		getChildren().add(sv);
	}

	public Coordinates getCoordinates(double x, double y) {
		return new Coordinates((int) (x / SIZE) + 1, (int) (y / SIZE) + 1);
	}

	private void drawMissile(Missile missile) {
		MissileView mv = new MissileView(missile);
		mv.relocate(getX(missile.getCoordinates()),
				getY(missile.getCoordinates()));
		getChildren().add(mv);
		missileViews.put(missile.getCoordinates(), mv);
	}

	private double getX(Coordinates c) {
		return SIZE * (c.x - 1);
	}

	private double getY(Coordinates c) {
		return SIZE * (c.y - 1);
	}

	private Shape getWater(int rows, int columns) {
		Rectangle water = new Rectangle(columns * SIZE, rows * SIZE);
		water.setFill(Color.LIGHTBLUE);
		return water;
	}

	private Line getLine(double x1, double y1, double x2, double y2) {
		Line line = new Line(x1, y1, x2, y2);
		line.setStrokeWidth(0.1);
		return line;
	}
}
