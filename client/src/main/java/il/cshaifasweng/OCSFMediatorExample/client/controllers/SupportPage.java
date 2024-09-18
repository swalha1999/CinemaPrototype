package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import static il.cshaifasweng.OCSFMediatorExample.client.utils.UiUtil.showNotification;

public class SupportPage {

    @FXML
    private TextArea issueDescription;

    @FXML
    private Button submitButton;

    @FXML
    private Label welcomeMessage;

    @FXML
    void handleSubmit(ActionEvent event) {
        Message message = new Message(MessageType.SEND_SUPPORT_TICKET_REQUEST)
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey())
                .setDataObject(issueDescription.getText());  // Send the selected ticket object to the server
        showNotification(" Thank For Trying To Get The Best Experience , The Team Will Respond In less Than 24 Hours",true);
        issueDescription.clear();
    }

}
