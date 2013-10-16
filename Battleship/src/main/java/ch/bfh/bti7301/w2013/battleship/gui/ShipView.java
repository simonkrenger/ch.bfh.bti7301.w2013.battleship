package ch.bfh.bti7301.w2013.battleship.gui;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import ch.bfh.bti7301.w2013.battleship.game.Board.Direction;
import ch.bfh.bti7301.w2013.battleship.game.Ship;

import static ch.bfh.bti7301.w2013.battleship.gui.BoardView.SIZE;

public class ShipView extends Parent {
	private Class<? extends Ship> shipType;

	public ShipView(Ship ship) {
		shipType = ship.getClass();

		Circle c1 = new Circle(SIZE / 2);
		set(c1);
		getChildren().add(c1);
		Rectangle r = new Rectangle(SIZE, SIZE * (ship.getSize() - 1));
		r.setTranslateX(-SIZE / 2);
		set(r);
		getChildren().add(r);
		Circle c2 = new Circle(SIZE / 2);
		c2.setTranslateY(SIZE * (ship.getSize() - 1));
		set(c2);
		getChildren().add(c2);
		rotateShip(ship.getDirection());
	}

	private Shape set(Shape node) {
		node.setFill(Color.DARKGRAY);
		return node;
	}

	private void rotateShip(Direction direction) {
		switch (direction) {
		case NORTH:
			getTransforms().add(new Rotate(180));
			break;
		case EAST:
			getTransforms().add(new Rotate(-90));
			break;
		case WEST:
			getTransforms().add(new Rotate(90));
			break;
		case SOUTH:
			break;
		}
	}

	public Class<? extends Ship> getShipType() {
		return shipType;
	}
}
