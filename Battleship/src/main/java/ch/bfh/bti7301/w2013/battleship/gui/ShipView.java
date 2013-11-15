package ch.bfh.bti7301.w2013.battleship.gui;

import static ch.bfh.bti7301.w2013.battleship.gui.BoardView.SIZE;

import java.io.InputStream;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import ch.bfh.bti7301.w2013.battleship.game.Board.Direction;
import ch.bfh.bti7301.w2013.battleship.game.Ship;

public class ShipView extends Parent {
	private static final double HALF_SIZE = SIZE / 2;
	private static final double QUART_SIZE = SIZE / 4;
	private Ship ship;
	private Rotate rotation;

	public ShipView(Ship ship) {
		this.ship = ship;

		rotation = new Rotate();
		InputStream is = getClass().getClassLoader().getResourceAsStream(
				ship.getClass().getSimpleName() + ".png");
		if (is == null) {
			Circle c1 = new Circle(HALF_SIZE);
			set(c1);
			getChildren().add(c1);
			Rectangle r = new Rectangle(SIZE, SIZE * (ship.getSize() - 1));
			r.setTranslateX(-HALF_SIZE);
			set(r);
			getChildren().add(r);
			Circle c2 = new Circle(HALF_SIZE);
			c2.setTranslateY(SIZE * (ship.getSize() - 1));
			set(c2);
			getChildren().add(c2);
			getTransforms().add(rotation);
		} else {
			Image img = new Image(is);
			ImageView iv = new ImageView(img);
			double scale = SIZE / img.getHeight();
			iv.getTransforms().add(new Scale(scale, scale));
			double correction = QUART_SIZE / scale;
			iv.getTransforms().add(new Translate(correction, -correction));
			iv.getTransforms().add(new Rotate(90));
			iv.getTransforms().add(new Translate(-correction, -correction));
			getChildren().add(iv);
			getTransforms().add(rotation);
		}

		rotateShip(ship.getDirection());
	}

	private Shape set(Shape node) {
		node.setFill(Color.DARKGRAY);
		return node;
	}

	public void update() {
		rotateShip(ship.getDirection());
	}

	private void rotateShip(Direction direction) {
		switch (direction) {
		case NORTH:
			rotation.setAngle(180);
			break;
		case EAST:
			rotation.setAngle(-90);
			break;
		case WEST:
			rotation.setAngle(90);
			break;
		case SOUTH:
			rotation.setAngle(0);
			break;
		}
	}

	public Ship getShip() {
		return ship;
	}

	public Class<? extends Ship> getShipType() {
		return ship.getClass();
	}
}
