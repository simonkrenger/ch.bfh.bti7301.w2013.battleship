package ch.bfh.bti7301.w2013.battleship.gui;

import static ch.bfh.bti7301.w2013.battleship.gui.BoardView.SIZE;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Scale;
import ch.bfh.bti7301.w2013.battleship.game.Missile;

public class MissileView extends Parent {
	private ImageView image;

	public MissileView(Missile missile) {
		image = new ImageView(new Image(getClass().getClassLoader()
				.getResourceAsStream("Fire.png")));
		image.getTransforms().add(new Scale(SIZE / 200.0, SIZE / 200.0));
		update(missile);
		getChildren().add(image);
	}

	public void update(Missile missile) {
		switch (missile.getMissileState()) {
		case HIT:
			image.setImage(new Image(getClass().getClassLoader()
					.getResourceAsStream("Fire.png")));
			break;
		case SUNK:
		case GAME_WON:
			image.setImage(new Image(getClass().getClassLoader()
					.getResourceAsStream("Fire.png")));
			break;
		case MISS:
			image.setImage(new Image(getClass().getClassLoader()
					.getResourceAsStream("Splash.png")));
			break;
		case FIRED:
			image.setImage(new Image(getClass().getClassLoader()
					.getResourceAsStream("Incoming.png")));
			break;
		}
	}
}
