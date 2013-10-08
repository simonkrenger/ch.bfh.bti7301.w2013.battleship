package ch.bfh.bti7301.w2013.battleship.gui;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class ShipView extends Parent {
	public ShipView(int size) {
		getChildren().add(
				set(new Rectangle(BoardView.SIZE, BoardView.SIZE * size)));
	}

	private Shape set(Shape node) {
		node.setFill(Color.BLUE);
		return node;
	}
}
