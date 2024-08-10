/**
 * Sample Skeleton for 'Host.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

import static il.cshaifasweng.OCSFMediatorExample.client.SimpleChatClient.setRoot;
import static il.cshaifasweng.OCSFMediatorExample.client.SimpleClient.host;
import static il.cshaifasweng.OCSFMediatorExample.client.SimpleClient.port6;

public class Host {

    @FXML // fx:id="ipAdd"
    private TextField ipAdd; // Value injected by FXMLLoader

    @FXML // fx:id="port"
    private TextField port; // Value injected by FXMLLoader

    public void initialize() {
        port.setText("3000");
        ipAdd.setText("localhost");
    }

    @FXML
    void updatePort(ActionEvent event) throws IOException {
        host=ipAdd.getText();
        port6= Integer.parseInt(port.getText());
        SimpleClient client = SimpleClient.getClient();
        client.openConnection();

        Platform.runLater(()->{
            setRoot("Login");
        });
    }

}
