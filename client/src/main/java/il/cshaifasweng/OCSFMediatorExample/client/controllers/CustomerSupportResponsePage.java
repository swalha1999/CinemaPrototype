package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.Client;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.ShowSideUIEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.SupportTicket;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.User;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.time.LocalDateTime;

import static il.cshaifasweng.OCSFMediatorExample.client.utils.UiUtil.showNotification;

public class CustomerSupportResponsePage {

    private User user;

    private  SupportTicket ticket;
    @FXML // fx:id="replyBtn"
    private Button replyBtn; // Value injected by FXMLLoader

    @FXML // fx:id="replyDescription"
    private TextArea replyDescription; // Value injected by FXMLLoader

    @FXML // fx:id="userNameLabel"
    private Label userNameLabel; // Value injected by FXMLLoader

    @FXML
    private void initialize() {
        String currentUsername = SessionKeysStorage.getInstance().getUsername();
        EventBus.getDefault().register(this);
    }

    @FXML
    void handleReply(ActionEvent event) {
        try {
            // Check if the client is connected to the server
            if (!Client.getClient().isConnected()) {
                showNotification("Failed to submit the request. Server connection lost.", false);
                return;
            }

            // Create a new SupportTicket and populate its fields
            SupportTicket supportTicket = new SupportTicket();
            supportTicket.setDescription(replyDescription.getText());
            // TODO: we need to set the user name of the user to the ticket then send it , and we need to delete the ticket from the inbox after responding
           supportTicket.setUser(ticket.getUser());
            supportTicket.setEmail(SessionKeysStorage.getInstance().getEmail());
            supportTicket.setCreatedDate(LocalDateTime.now());

            // Create the message with the SupportTicket object
            Message message = new Message(MessageType.REPLY_SUPPORT_TICKET_REQUEST)
                    .setDataObject(supportTicket)  // Send the SupportTicket object
                    .setSessionKey(SessionKeysStorage.getInstance().getSessionKey());

            // Send the message to the server
            Client.getClient().sendToServer(message);

            // Show notification and clear the text area
            showNotification("Reply has been sent.", true);
        } catch (Exception e) {
            e.printStackTrace(); // Log any exception that occurs during the submission
            showNotification("An error occurred while submitting the request.", false);
        }
    }

    @Subscribe
    public void onUIShow(ShowSideUIEvent event) {
        if (!event.getUIName().equals("CustomerSupportResponsePage")) {
            return;
        }
        ticket = (SupportTicket) event.getFirstObj();
        userNameLabel.setText("Response to " + ticket.getUser().getUsername());
    }
}
