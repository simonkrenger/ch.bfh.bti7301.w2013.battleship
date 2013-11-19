package ch.bfh.bti7301.w2013.battleship.utils;

import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Map.Entry;
import java.util.Random;

import javafx.scene.text.Font;

import ch.bfh.bti7301.w2013.battleship.game.Board.Direction;
import ch.bfh.bti7301.w2013.battleship.game.Coordinates;
import ch.bfh.bti7301.w2013.battleship.game.GameRule;
import ch.bfh.bti7301.w2013.battleship.game.Ship;

public class GameUtils {
	public static final Random RANDOM = new Random();

	public static List<Ship> getAvailableShips(GameRule rule) {
		List<Ship> availableShips = new LinkedList<>();
		Coordinates dc = new Coordinates(0, 0);
		Direction dd = Direction.SOUTH;

		for (Entry<Class<? extends Ship>, Integer> e : rule.getShipList()
				.entrySet()) {
			for (int i = 0; i < e.getValue(); i++)
				availableShips.add(buildShip(e.getKey(), dc, dd));
		}
		return availableShips;
	}

	private static Ship buildShip(Class<? extends Ship> type, Coordinates c,
			Direction d) {
		try {
			return type.getConstructor(Coordinates.class, Direction.class)
					.newInstance(c, d);
		} catch (Exception e) {
			throw new RuntimeException(
					"Error while creating ships through reflection", e);
		}
	}

	public static Font getFont(String res, double size) {
		return Font.loadFont(GameUtils.class.getClassLoader()
				.getResourceAsStream(res), size);
	}

	public static String getString(String key) {
		return ResourceBundle.getBundle("translations").getString(key);
	}

	public static <E> E rnd(E[] choices) {
		return choices[RANDOM.nextInt(choices.length)];
	}

	public static <E> E rnd(List<E> choices) {
		return choices.get(RANDOM.nextInt(choices.size()));
	}
}
