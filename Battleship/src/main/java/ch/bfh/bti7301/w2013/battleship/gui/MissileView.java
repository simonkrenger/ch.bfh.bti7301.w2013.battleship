package ch.bfh.bti7301.w2013.battleship.gui;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;
import ch.bfh.bti7301.w2013.battleship.game.Missile;

public class MissileView extends Parent {
	public MissileView(Missile missile) {
		Shape shape = new Ellipse(BoardView.SIZE, BoardView.SIZE);
		switch (missile.getMissileState()) {
		case HIT:
		case SUNK:
		case GAME_WON:
			shape.setFill(Color.RED);
			break;
		case MISS:
			shape.setFill(Color.BLUE);
			break;
		}
		getChildren().add(shape);
	}
}
