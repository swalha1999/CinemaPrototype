package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.Client;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.ShowSideUIEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.MovieTicket;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Screening;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Seat;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static il.cshaifasweng.OCSFMediatorExample.client.utils.PaymentUtil.isValidCard;
import static il.cshaifasweng.OCSFMediatorExample.client.utils.UiUtil.showNotification;

public class PurchaseBundle {

    @FXML
    private TextField CVV_Txt;
    @FXML
    private Label OrderIdLabel;

    @FXML
    public void initialize() {
        EventBus.getDefault().register(this);
    }

    @FXML
    void ConfirmPurchase(ActionEvent event) {
        // Validate the credit card using the CVV input
        if (!isValidCard(CVV_Txt.getText())) {
            showNotification("The Card Number is Not Valid, Please Try Again", false);
            return;
        }

        showNotification("Purchase Successful , Check The Information In Ur Inbox", true);
        CVV_Txt.clear();
            Message request = new Message(MessageType.PURCHASE_TICKETS_BUNDLE_REQUEST)
                    .setDataObject(1)
                    .setSessionKey(SessionKeysStorage.getInstance().getSessionKey());

            Client.getClient().sendToServer(request);
    }

    @Subscribe
    public void onShowSideUI(ShowSideUIEvent event) {
        if (!event.getUIName().equals("PurchaseBundle")) {
            return;
        }
    }
}
