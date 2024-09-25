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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Objects;

public class EditPrice {

    Screening screening;
    @FXML
    private Button ConfirmButton;

    @FXML
    private TextField PriceText;

    @FXML
    void EditPrice(ActionEvent event) {
        PriceChangeRequest req = new PriceChangeRequest();
        String priceText = PriceText.getText();
        req.setScreening(screening);
        if (priceText.isEmpty()) {
            System.out.println("Price field is empty");
            return;
        }

        int newPrice = 0;
        try {
            newPrice = Integer.parseInt(priceText);
            req.setNewPrice(newPrice);
            // Further processing after setting the new price
        } catch (NumberFormatException e) {
            // Handle the case where the input is not a valid integer
            System.out.println("Please enter a valid number");
        }

        Message message = new Message(MessageType.ADD_PRICE_CHANGE_REQUEST)
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey())
                .setDataObject(req);
        Client.getClient().sendToServer(message);
    }


    @FXML
    public void initialize() {
        EventBus.getDefault().register(this); //TODO: add this to all controllers - please :)
    }

    @Subscribe
    public void onSideUiChange (ShowSideUIEvent event){
        if(!Objects.equals(event.getUIName(), "EditPrice")){
            return;
        }
    screening =(Screening) event.getFirstObj();
    }

}