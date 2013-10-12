package ch.bfh.bti7301.w2013.battleship.gui;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import ch.bfh.bti7301.w2013.battleship.game.Board;
import ch.bfh.bti7301.w2013.battleship.game.Board.Coordinates;
import ch.bfh.bti7301.w2013.battleship.game.Missile;
import ch.bfh.bti7301.w2013.battleship.game.Ship;

public class BoardView extends Parent {
	public static final int SIZE = 40;

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
			ShipView sv = new ShipView(ship.getSize());
			switch (ship.getDirection()) {
			case NORTH:
				sv.getTransforms().add(new Rotate(180, SIZE / 2, SIZE / 2));
				break;
			case EAST:
				sv.getTransforms().add(new Rotate(-90, SIZE / 2, SIZE / 2));
				break;
			case WEST:
				sv.getTransforms().add(new Rotate(90, SIZE / 2, SIZE / 2));
				break;
			case SOUTH:
				break;
			}
			sv.relocate(SIZE * ship.getStartCoordinates().x,
					SIZE * ship.getStartCoordinates().y);
			getChildren().add(sv);
		}

		for (Missile missile : board.getPlacedMissiles()) {
			drawMissile(missile);
		}

		// This is for the opponent's board. This has to move somewhere else
		// later, I think
		setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				int c = (int) (e.getX() / SIZE);
				int r = (int) (e.getY() / SIZE);
				Missile m = new Missile(new Coordinates(c, r));
				board.placeMissile(m);
				drawMissile(m);
			}
		});
	}

	private void drawMissile(Missile missile) {
		MissileView mv = new MissileView(missile);
		mv.relocate(SIZE * missile.getCoordinates().x,
				SIZE * missile.getCoordinates().y);
		getChildren().add(mv);
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
