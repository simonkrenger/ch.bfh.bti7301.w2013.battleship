package ch.bfh.bti7301.w2013.battleship.gui;

import static ch.bfh.bti7301.w2013.battleship.utils.GameUtils.getString;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import ch.bfh.bti7301.w2013.battleship.game.Game;
import ch.bfh.bti7301.w2013.battleship.game.GameRule;

public class SettingsPanel extends VBox {
	private static final String SOUNDEFFECTS_LOCAL = "soundeffects.local";
	private static final String SOUNDEFFECTS_OPPONENT = "soundeffects.opponent";

	private static final Settings settings = new Settings();

	public SettingsPanel() {
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
		ruleBox.addRow(0, new Label(getString("boardsize")), new TextField());
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

		private Settings() {
			try (InputStream is = new FileInputStream("battleship.properties")) {
				properties.loadFromXML(is);
			} catch (IOException e) {
				System.out
						.println("Couldn't load properties, using default values");
			}
		}

		public boolean isOpponentSound() {
			return is(SOUNDEFFECTS_OPPONENT, false);
		}

		public boolean isLocalSound() {
			return is(SOUNDEFFECTS_LOCAL, true);
		}

		public GameRule getRule() {
			return Game.getInstance().getRule();
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
