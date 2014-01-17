package ch.bfh.bti7301.w2013.battleship.gui;

import static ch.bfh.bti7301.w2013.battleship.utils.GameUtils.getString;
import static ch.bfh.bti7301.w2013.battleship.game.Settings.*;
import ch.bfh.bti7301.w2013.battleship.game.Settings;
import ch.bfh.bti7301.w2013.battleship.network.Connection;
import ch.bfh.bti7301.w2013.battleship.network.ConnectionState;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class SettingsPanel extends VBox {
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

		final TitledPane rulePane = new TitledPane();
		rulePane.setText(getString("gamerules"));
		GridPane ruleBox = new GridPane();
		rulePane.setContent(ruleBox);
		final Slider boardSizeSlider = new Slider(10, 30, settings.getRule()
				.getBoardSize());
		boardSizeSlider.setShowTickLabels(true);
		boardSizeSlider.setShowTickMarks(true);
		boardSizeSlider.setMajorTickUnit(10);
		boardSizeSlider.setMinorTickCount(10);
		boardSizeSlider.setBlockIncrement(1);
		boardSizeSlider.valueProperty().addListener(
				new ChangeListener<Number>() {
					@Override
					public void changed(
							ObservableValue<? extends Number> observable,
							Number oldValue, Number newValue) {
						settings.setBoardSize(newValue.intValue());
					}
				});
		ruleBox.addRow(0, new Label(getString("boardsize")), boardSizeSlider);
		rulePane.setCollapsible(false);
		getChildren().add(rulePane);
		Connection.getInstance().addConnectionStateListener(
				new GuiConnectionStateListenerAdapter() {
					@Override
					public void doStateChanged(ConnectionState newState,
							String msg) {
						if (newState == ConnectionState.CONNECTED)
							rulePane.setDisable(true);
					}
				});
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
}
