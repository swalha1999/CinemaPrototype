package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.Client;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.EditPriceRequestEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.GetPriceChangesEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.ShowSideUIEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.PriceChangeRequest;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Screening;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Objects;

import static il.cshaifasweng.OCSFMediatorExample.client.utils.UiUtil.showNotification;
import static il.cshaifasweng.OCSFMediatorExample.client.utils.UiUtil.showSideUI;

public class EditPrice {

    @FXML public Button confirmButton;
    @FXML private TextField PriceText;
    @FXML private Button returnBtn;

    private Screening screening;

    @FXML
    public void initialize() {
        EventBus.getDefault().register(this);

    }

    @FXML
    void onReturn(ActionEvent event) {
        Platform.runLater(() -> {
            showSideUI("CinemaInfo");
        });
    }

    @Subscribe
    public void onSideUiChange (ShowSideUIEvent event){
        if(!event.getUIName().equals("EditPrice")){
            return;
        }
        System.out.println("We are here");
        screening = (Screening) event.getFirstObj();
    }

    @FXML
    public void onConfirmButton(ActionEvent actionEvent) {
        Message request = new Message(MessageType.ADD_PRICE_CHANGE_REQUEST);

        if (PriceText.getText().isEmpty()) {
            return;
        }

        if (screening == null) {
            showNotification("Error: you must select a screening", false);
            return;
        }

        PriceChangeRequest priceChangeRequest = new PriceChangeRequest();
        priceChangeRequest.setScreening(screening);

        try {
            priceChangeRequest.setNewPrice(Integer.parseInt(PriceText.getText()));
        } catch (Exception e) {
            showNotification("Error: you mush privode a hole number", false);
        }


        request.setSessionKey(SessionKeysStorage.getInstance().getSessionKey()).setDataObject(priceChangeRequest);
        Client.getClient().sendToServer(request);


    }
}