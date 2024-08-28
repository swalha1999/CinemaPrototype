/**
 * Sample Skeleton for 'AddCinema.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.controllers;
import il.cshaifasweng.OCSFMediatorExample.client.Client;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Cinema;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.User;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import static il.cshaifasweng.OCSFMediatorExample.client.CinemaMain.setRoot;
import static il.cshaifasweng.OCSFMediatorExample.client.utils.UiUtil.showSideUI;

public class AddCinema {

    @FXML // fx:id="cityField"
    private TextField cityField; // Value injected by FXMLLoader

    @FXML // fx:id="managerField"
    private TextField managerField; // Value injected by FXMLLoader

    @FXML
    void handleBack(ActionEvent event) {
        Platform.runLater(() -> {
            showSideUI("CinemaInfo");
        });
    }

    @FXML
    void handleConfirm(ActionEvent event) {
        Message msg = new Message(MessageType.ADD_CINEMA_REQUEST).setSessionKey(SessionKeysStorage.getInstance().getSessionKey());

        User manager = new User().setUsername(managerField.getText());

        Cinema cinemaToAdd = new Cinema().setManager(manager);
        cinemaToAdd.setCity(cityField.getText());
        // TODO: add cinema address, phone number, email

        msg.setDataObject(cinemaToAdd);
        Client.getClient().sendToServer(msg);

        Platform.runLater(() -> {
            showSideUI("CinemaInfo");
        });
    }

}
