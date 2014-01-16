package ch.bfh.bti7301.w2013.battleship.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ConfirmationDialog extends VBox {
	private Boolean result;

	public ConfirmationDialog(String message, String yes, String no) {
		setPadding(new Insets(20));
		setSpacing(8);
		setStyle("-fx-background-color: #336699;");
		Label l = new Label(message);
		l.setTextFill(Color.WHITE);
		l.setFont(new Font(20));
		getChildren().add(l);
		getChildren().add(getButton(yes, true));
		getChildren().add(getButton(no, false));
	}

	private Button getButton(String label, final boolean result) {
		Button b = new Button(label);
		b.setMinWidth(150);
		b.setMaxWidth(200);
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				ConfirmationDialog.this.result = result;
			}
		});
		return b;
	}

	public static boolean waitForConfirmation(final Group root,
			final String message, final String yes, final String no) {
		final ConfirmationDialog[] dialog = { null };
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ConfirmationDialog d = new ConfirmationDialog(message, yes, no);
				Bounds rootBounds = root.getBoundsInLocal();
				d.setMinWidth(rootBounds.getWidth());
				d.setMaxWidth(rootBounds.getWidth());
				d.setAlignment(Pos.CENTER);
				d.relocate(rootBounds.getMinX(), (rootBounds.getHeight()
						- d.getBoundsInParent().getHeight() - 100) / 2);
				dialog[0] = d;
				root.getChildren().add(d);
			}
		});
		while (dialog[0] == null || dialog[0].result == null) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// Ignore
			}
		}
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				root.getChildren().remove(dialog[0]);
			}
		});
		return dialog[0].result;
	}
}
