package ch.bfh.bti7301.w2013.battleship.gui;

import static ch.bfh.bti7301.w2013.battleship.utils.GameUtils.getString;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Random;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import ch.bfh.bti7301.w2013.battleship.game.GameRule;

public class SettingsPanel extends VBox {
	private static final String PLAYER_NAME = "player.name";
	private static final String SOUNDEFFECTS_LOCAL = "soundeffects.local";
	private static final String SOUNDEFFECTS_OPPONENT = "soundeffects.opponent";
	private static final String BOARD_SIZE = "board.size";

	private static final Settings settings = new Settings();

	public SettingsPanel() {
		GridPane nameBox = new GridPane();
		TextField nameField = new TextField(settings.getPlayerName());
		nameField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				settings.set(PLAYER_NAME, newValue);
			}
		});
		nameBox.addRow(0, new Label(getString("player.name")), nameField);
		getChildren().add(nameBox);

		TitledPane soundPane = new TitledPane();
		soundPane.setText(getString("soundeffects"));
		VBox soundBox = new VBox();
		soundPane.setContent(soundBox);
		soundBox.getChildren()
				.add(createCheckBox(SOUNDEFFECTS_OPPONENT, false));
		soundBox.getChildren().add(createCheckBox(SOUNDEFFECTS_LOCAL, true));
		soundPane.setCollapsible(false);
		getChildren().add(soundPane);

		TitledPane rulePane = new TitledPane();
		rulePane.setText(getString("gamerules"));
		GridPane ruleBox = new GridPane();
		rulePane.setContent(ruleBox);
		final TextField boardSizeField = new TextField(String.valueOf( //
				settings.getRule().getBoardSize()));
		boardSizeField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				try {
					int bs = Integer.parseInt(newValue);
					settings.getRule().setBoardSize(bs);
					settings.set(BOARD_SIZE, bs);
				} catch (Throwable e) {
					boardSizeField.setText(oldValue);
				}
			}
		});
		ruleBox.addRow(0, new Label(getString("boardsize")), boardSizeField);
		rulePane.setCollapsible(false);
		getChildren().add(rulePane);
	}

	private CheckBox createCheckBox(final String id, boolean def) {
		CheckBox cb = new CheckBox(getString(id));
		cb.setSelected(settings.is(id, def));
		cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> ov,
					Boolean oldval, Boolean newval) {
				settings.set(id, newval);
			}
		});
		return cb;
	}

	public static Settings getSettings() {
		return settings;
	}

	public static class Settings {
		private Properties properties = new Properties();
		private String[] names = { "Captain Cook", "Captain Jack Sparrow",
				"Admiral Nelson", "Duck Dodgers", "James T. Kirk",
				"Jean-Luc Picard", "Hector Barbarossa", "Captain Nemo",
				"Captain Haddock" };

		private GameRule rule;

		private String randomPlayerName() {
			Random rnd = new Random();
			return names[rnd.nextInt(names.length)];
		}

		private Settings() {
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

		private void set(String key, Object value) {
			properties.put(key, String.valueOf(value));
			try (OutputStream os = new FileOutputStream("battleship.properties")) {
				properties.storeToXML(os, "Battleship settings");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		private boolean is(String key, boolean def) {
			return "true".equalsIgnoreCase(properties.getProperty(key,
					String.valueOf(def)).trim());
		}
	}
}
