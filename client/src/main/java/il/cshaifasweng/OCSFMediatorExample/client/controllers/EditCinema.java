/**
 * Sample Skeleton for 'EditCinema.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.Client;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.ShowSideUIEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Cinema;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.greenrobot.eventbus.Subscribe;

import java.util.Objects;

import static il.cshaifasweng.OCSFMediatorExample.client.utils.UiUtil.showSideUI;

public class EditCinema {

    @FXML // fx:id="addressField"
    private TextField addressField; // Value injected by FXMLLoader

    @FXML // fx:id="cityField"
    private TextField cityField; // Value injected by FXMLLoader

    @FXML // fx:id="emailField"
    private TextField emailField; // Value injected by FXMLLoader

    @FXML // fx:id="nameField"
    private TextField nameField; // Value injected by FXMLLoader

    @FXML // fx:id="phoneField"
    private TextField phoneField; // Value injected by FXMLLoader

    private Cinema cinema;

    @FXML
    void cancelEdit(ActionEvent event) {
        Platform.runLater(() -> {
            showSideUI("CinemaInfo");
        });
    }

    @FXML
    void saveCinema(ActionEvent event) {
        cinema.setAddress(addressField.getText());
        cinema.setCity(cityField.getText());
        cinema.setEmail(emailField.getText());
        cinema.setName(nameField.getText());
        cinema.setPhoneNumber(phoneField.getText());

        Message message = new Message( MessageType.UPDATE_CINEMA_REQUEST)
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey())
                .setDataObject(cinema);
        Client.getClient().sendToServer(message);

        Platform.runLater(() -> {
            showSideUI("CinemaInfo");
        });
    }

    @Subscribe
    public void onSideUiChange (ShowSideUIEvent event){
        if(!Objects.equals(event.getUIName(), "EditCinema")){
            return;
        }
        cinema = (Cinema) event.getFirstObj();
        addressField.setText(cinema.getAddress());
        cityField.setText(cinema.getCity());
        emailField.setText(cinema.getEmail());
        nameField.setText(cinema.getName());
        phoneField.setText(cinema.getPhoneNumber());

    }

}
