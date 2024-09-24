package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.Client;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.GetAllSupportTicketsEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.SupportTicket;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class CustomerSupportInbox {
    @FXML
    private VBox MessageContainer;

    public void initialize() {
        // Register the EventBus
        EventBus.getDefault().register(this);

        // Send a request to get all support tickets
        Message message = new Message(MessageType.GET_ALL_SUPPORT_TICKETS_REQUEST)
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey());
        Client.getClient().sendToServer(message);
    }

    // Add message to the GUI dynamically
    public void addMessage(String sender, String content) {
        AnchorPane messagePane = new AnchorPane();
        messagePane.getStyleClass().add("ticket-pane");
        messagePane.setPrefWidth(300);

        Label senderLabel = new Label("Sender: " + sender);
        senderLabel.setLayoutX(14.0);
        senderLabel.setLayoutY(14.0);
        senderLabel.getStyleClass().add("ticket-label");

        Label contentLabel = new Label("Message: " + content);
        contentLabel.setLayoutX(14.0);
        contentLabel.setLayoutY(34.0);
        contentLabel.getStyleClass().add("ticket-label");

        messagePane.getChildren().addAll(senderLabel, contentLabel);

        MessageContainer.getChildren().add(messagePane);
    }

    // Subscribe to the event to receive and display support tickets
    @Subscribe
    public void onGetSupportTicketRequest(GetAllSupportTicketsEvent event) {
        // Get the list of support tickets from the event
        List<SupportTicket> supportTickets = event.getSupportTickets();

        // Use Platform.runLater to make sure GUI updates are done on the JavaFX thread
        Platform.runLater(() -> {
            MessageContainer.getChildren().clear();  // Clear any previous messages

            // Iterate through the support tickets and add them to the GUI
            for (SupportTicket ticket : supportTickets) {
                String sender = ticket.getUser().getUsername();  // Assuming SupportTicket has a user field
                String content = ticket.getDescription();  // Assuming SupportTicket has a content field
                addMessage(sender, content);
            }
        });
    }
}
