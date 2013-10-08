package ch.bfh.bti7301.w2013.battleship.gui;

import javafx.scene.Parent;
import javafx.scene.shape.Line;

public class BoardView extends Parent {
	public static final int SIZE = 40;

	// private int rows, columns;

	public BoardView(int rows, int columns) {
		// this.rows = rows;
		// this.columns = columns;

		for (int i = 0; i <= rows; i++) {
			getChildren().add(getLine(i * SIZE, 0, i * SIZE, SIZE * columns));
		}
		for (int i = 0; i <= columns; i++) {
			getChildren().add(getLine(0, i * SIZE, SIZE * rows, i * SIZE));
		}
	}

	private Line getLine(double x1, double y1, double x2, double y2) {
		Line line = new Line(x1, y1, x2, y2);
		line.setStrokeWidth(0.1);
		return line;
	}
}
