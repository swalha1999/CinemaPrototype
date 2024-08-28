/**
 * Sample Skeleton for 'EditHall.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.Client;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.ShowSideUIEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Cinema;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Hall;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Objects;

import static il.cshaifasweng.OCSFMediatorExample.client.utils.UiUtil.showSideUI;

public class EditHall {

    @FXML // fx:id="hallNameField"
    private TextField hallNameField; // Value injected by FXMLLoader

    @FXML // fx:id="seatsNumberField"
    private TextField seatsNumberField; // Value injected by FXMLLoader

    private Hall hall;

    @FXML
    public void initialize() {
        EventBus.getDefault().register(this); //TODO: add this to all controllers - please :)
    }

    @FXML
    void handleCancel(ActionEvent event) {
        Platform.runLater(() -> {
            showSideUI("CinemaInfo");
        });
    }

    @FXML
    void saveHall(ActionEvent event) {
        hall.setName(hallNameField.getText());
        hall.setSeatsNum(Integer.parseInt(seatsNumberField.getText()));

        Message message = new Message(MessageType.UPDATE_HALL_REQUEST)
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey())
                .setDataObject(hall);
        Client.getClient().sendToServer(message);

        Platform.runLater(() -> {
            showSideUI("CinemaInfo");
        });
    }

    @Subscribe
    public void onSideUiChange (ShowSideUIEvent event){
        System.out.println("i GOT HERE!!!!");
        if(!Objects.equals(event.getUIName(), "EditHall")){
            return;
        }
        hall = (Hall) event.getFirstObj();

        hallNameField.setText(hall.getName());
        seatsNumberField.setText(Integer.toString(hall.getSeatsNum()));

    }

}
