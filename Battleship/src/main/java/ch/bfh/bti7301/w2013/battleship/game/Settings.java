package ch.bfh.bti7301.w2013.battleship.game;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class Settings {
	public static final String PLAYER_NAME = "player.name";
	public static final String SOUNDEFFECTS_LOCAL = "soundeffects.local";
	public static final String SOUNDEFFECTS_OPPONENT = "soundeffects.opponent";
	public static final String BOARD_SIZE = "board.size";

	private List<SettingsListener> listeners = new LinkedList<>();

	private Properties properties = new Properties();
	private static String[] names = { "Captain Cook", "Captain Jack Sparrow",
			"Admiral Nelson", "Duck Dodgers", "James T. Kirk",
			"Jean-Luc Picard", "Hector Barbarossa", "Captain Nemo",
			"Captain Haddock" };

	private GameRule rule;

	public static String randomPlayerName() {
		Random rnd = new Random();
		return names[rnd.nextInt(names.length)];
	}

	public Settings() {
		try (InputStream is = new FileInputStream("battleship.properties")) {
			properties.loadFromXML(is);
		} catch (IOException e) {
			System.out
					.println("Couldn't load properties, using default values");
		}

		rule = new GameRule();
		String boardSizeString = properties.getProperty(BOARD_SIZE);
		try {
			int boardSize = Integer.parseInt(boardSizeString);
			rule.setBoardSize(boardSize);
		} catch (Throwable ignore) {
			// Let's just use the default.
		}
	}

	public String getPlayerName() {
		String name = properties.getProperty(PLAYER_NAME);
		if (name == null) {
			name = randomPlayerName();
			set(PLAYER_NAME, name);
		}
		return name;
	}

	public boolean isOpponentSound() {
		return is(SOUNDEFFECTS_OPPONENT, false);
	}

	public boolean isLocalSound() {
		return is(SOUNDEFFECTS_LOCAL, true);
	}

	public GameRule getRule() {
		return rule;
	}

	public void setBoardSize(int size) {
		getRule().setBoardSize(size);
		set(BOARD_SIZE, size);
	}

	public void set(String key, Object value) {
		String oldValue = get(key, null);
		String newValue = value == null ? null : String.valueOf(value);
		for (SettingsListener l : listeners) {
			l.onChange(key, oldValue, newValue);
		}
		properties.put(key, newValue);
		try (OutputStream os = new FileOutputStream("battleship.properties")) {
			properties.storeToXML(os, "Battleship settings");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean is(String key, boolean def) {
		return "true".equalsIgnoreCase(get(key, String.valueOf(def)).trim());
	}

	public String get(String key, String def) {
		return properties.getProperty(key, String.valueOf(def));
	}

	public void addListener(SettingsListener listener) {
		listeners.add(listener);
	}

	public static interface SettingsListener {
		public void onChange(String key, String oldValue, String newValue);
	}
}