/**
 * Sample Skeleton for 'host.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

import static il.cshaifasweng.OCSFMediatorExample.client.SimpleChatClient.setRoot;
import static il.cshaifasweng.OCSFMediatorExample.client.SimpleClient.host;
import static il.cshaifasweng.OCSFMediatorExample.client.SimpleClient.port6;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;

public class Host {

    @FXML // fx:id="ipAdd"
    private TextField ipAdd; // Value injected by FXMLLoader

    @FXML // fx:id="port"
    private TextField port; // Value injected by FXMLLoader

    @FXML
    void updatePort(ActionEvent event) throws IOException {
        host=ipAdd.getText();
        port6= Integer.parseInt(port.getText());
        SimpleClient client = SimpleClient.getClient();
        client.openConnection();
        try {
            Message message = new Message(0, "add client");
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Platform.runLater(()->{
            try {
                setRoot("primary");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
