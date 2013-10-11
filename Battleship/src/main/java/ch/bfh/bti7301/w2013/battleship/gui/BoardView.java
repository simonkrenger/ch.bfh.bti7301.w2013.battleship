package ch.bfh.bti7301.w2013.battleship.gui;

import java.util.LinkedList;
import java.util.List;

import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import ch.bfh.bti7301.w2013.battleship.game.Board;
import ch.bfh.bti7301.w2013.battleship.game.Board.Coordinates;
import ch.bfh.bti7301.w2013.battleship.game.Ship;
import ch.bfh.bti7301.w2013.battleship.game.ships.AircraftCarrier;

public class BoardView extends Parent {
	public static final int SIZE = 40;

	public BoardView(Board board) {
		int rows, columns;
		rows = columns = board.getBoardSize();

		getChildren().add(getWater(rows, columns));
		for (int i = 0; i <= rows; i++) {
			getChildren().add(getLine(i * SIZE, 0, i * SIZE, SIZE * columns));
		}
		for (int i = 0; i <= columns; i++) {
			getChildren().add(getLine(0, i * SIZE, SIZE * rows, i * SIZE));
		}

		List<Ship> ships = new LinkedList<>();
		ships.add(new AircraftCarrier( //
				new Coordinates(2, 2), new Coordinates(2, 7)));
		ships.add(new AircraftCarrier( //
				new Coordinates(4, 2), new Coordinates(9, 2)));
		for (Ship ship : ships) {
			ShipView sv = new ShipView(ship.getSize());
			if (ship.getStartCoordinates().x != ship.getEndCoordinates().x) {
				Bounds b = sv.getBoundsInLocal();
				sv.setTranslateY(-(b.getHeight() - SIZE) / 2);
				sv.setRotate(90);
				sv.setTranslateX((b.getHeight() - SIZE) / 2);
			}
			sv.relocate(BoardView.SIZE * ship.getStartCoordinates().x,
					BoardView.SIZE * ship.getStartCoordinates().y);
			getChildren().add(sv);
		}
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
