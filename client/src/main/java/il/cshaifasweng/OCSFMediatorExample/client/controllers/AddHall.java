/**
 * Sample Skeleton for 'AddHall.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.Client;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.ShowSideUIEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Cinema;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Hall;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.User;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.greenrobot.eventbus.Subscribe;

import static il.cshaifasweng.OCSFMediatorExample.client.CinemaMain.setRoot;
import static il.cshaifasweng.OCSFMediatorExample.client.utils.UiUtil.showSideUI;

public class AddHall {

    @FXML // fx:id="hallNameField"
    private TextField hallNameField; // Value injected by FXMLLoader

    @FXML // fx:id="seatsNumberField"
    private TextField seatsNumberField; // Value injected by FXMLLoader

    Cinema cinema; // this is the cinema that the hall will be added to

    @FXML
    void handleBack(ActionEvent event) {
        Platform.runLater(() -> {
            showSideUI("CinemaInfo");
        });
    }

    @FXML
    void handleConfirm(ActionEvent event) {
        Message msg = new Message(MessageType.ADD_HALL_REQUEST).setSessionKey(SessionKeysStorage.getInstance().getSessionKey());
        Hall hallToAdd = new Hall();
        hallToAdd.setName(hallNameField.getText());
        hallToAdd.setSeatsNum(Integer.parseInt(seatsNumberField.getText()));
        hallToAdd.setCinema(cinema);

        msg.setDataObject(hallToAdd);
        Client.getClient().sendToServer(msg);
    }

    @Subscribe
    public void onSideUiEvent(ShowSideUIEvent event) {
        if (event.getUIName().equals("AddHall")) {
            cinema = ((Cinema) event.getFirstObj()).deepCopy();
            System.out.println("Hello Cinema: " + cinema);
        }
    }

}
