/**
 * Sample Skeleton for 'Host.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.Client;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

import static il.cshaifasweng.OCSFMediatorExample.client.CinemaMain.setRoot;
import static il.cshaifasweng.OCSFMediatorExample.client.Client.host;
import static il.cshaifasweng.OCSFMediatorExample.client.Client.port6;

public class Host {

    @FXML // fx:id="ipAdd"
    private TextField ipAdd; // Value injected by FXMLLoader

    @FXML // fx:id="port"
    private TextField port; // Value injected by FXMLLoader

    public void initialize() {
        port.setText("3000");
        ipAdd.setText("cinema.swalha1999.com");
    }

    @FXML
    void updatePort(ActionEvent event) throws IOException {
        host=ipAdd.getText();
        port6= Integer.parseInt(port.getText());
        Client client = Client.getClient();
        client.openConnection();

        Platform.runLater(()->{
            setRoot("Login");
        });
    }

    public void localPort(ActionEvent actionEvent) throws IOException {
        host ="localhost";
        port6= Integer.parseInt(port.getText());
        Client client = Client.getClient();
        client.openConnection();

        Platform.runLater(()->{
            setRoot("Login");
        });
    }
}
