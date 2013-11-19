package ch.bfh.bti7301.w2013.battleship.gui;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ch.bfh.bti7301.w2013.battleship.network.Connection;
import ch.bfh.bti7301.w2013.battleship.network.ConnectionState;
import ch.bfh.bti7301.w2013.battleship.network.NetworkInformation;

import com.sun.javafx.collections.ObservableListWrapper;

public class NetworkPanel extends VBox {
	public NetworkPanel() {
		getChildren().add(getIpBox());
		ListView<String> ips = new ListView<>();
		ips.setItems(new ObservableListWrapper<>(NetworkInformation
				.getIntAddresses()));
		getChildren().add(ips);
	}

	private HBox getIpBox() {
		// Temporary input field to enter the opponent's
		final HBox ipBox = new HBox();
		final TextField ipAddress = new TextField();

		Matcher m = Pattern.compile("\\d+\\.\\d+\\.\\d+\\.").matcher(
				NetworkInformation.getIntAddresses().toString());
		if (m.find())
			ipAddress.setText(m.group());

		ipBox.getChildren().add(ipAddress);
		final Button connect = new Button(ResourceBundle.getBundle(
				"translations").getString("connect"));
		Connection.getInstance().setConnectionStateListener(
				new GuiConnectionStateListenerAdapter() {
					@Override
					public void doStateChanged(ConnectionState newState,
							String msg) {
						switch (newState) {
						case CLOSED:
							break;
						case LISTENING:
							ipBox.setVisible(true);
							break;
						case CONNECTED:
							ipBox.setVisible(false);
							break;
						case CONNECTIONERROR:
							break;
						case INPUTERROR:
							break;
						case LISTENERERROR:
							break;
						case OUTPUTEROR:
							break;
						default:
							break;
						}
					}
				});
		connect.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Connection.getInstance().connectOpponent(ipAddress.getText());
				System.out.println(ipAddress.getText());
			}
		});
		ipBox.getChildren().add(connect);
		return ipBox;
	}
}
