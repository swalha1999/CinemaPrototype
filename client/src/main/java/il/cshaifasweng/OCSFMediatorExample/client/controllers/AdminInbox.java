package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.Client;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.EditPriceRequestEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.GetAllSupportTicketsEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.GetPriceChangesEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.PriceChangeRequest;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.SupportTicket;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class AdminInbox {
    @FXML
    private VBox MessageContainer;

    public void initialize() {
        // Register the EventBus
        EventBus.getDefault().register(this);

        // Send a request to get all support tickets
        Message getSupportTicketsMessage = new Message(MessageType.GET_ALL_SUPPORT_TICKETS_REQUEST)
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey());
        Client.getClient().sendToServer(getSupportTicketsMessage);

        Message priceChangeRequestMessage = new Message(MessageType.GET_PRICE_CHANGES_REQUEST)
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey());
        Client.getClient().sendToServer(priceChangeRequestMessage);
    }

    // Add message to the GUI dynamically for Support Tickets
    public void addMessage(SupportTicket ticket) {
        AnchorPane messagePane = new AnchorPane();
        messagePane.getStyleClass().add("ticket-pane");
        messagePane.setPrefWidth(700);

        Label senderLabel = new Label("Sender: " + ticket.getUser().getUsername());
        senderLabel.setLayoutX(14.0);
        senderLabel.setLayoutY(14.0);
        senderLabel.getStyleClass().add("ticket-label");

        Label contentLabel = new Label("Message: " + ticket.getDescription());
        contentLabel.setLayoutX(14.0);
        contentLabel.setLayoutY(34.0);
        contentLabel.getStyleClass().add("ticket-label");

        HBox buttonBox = new HBox(10);
        buttonBox.setLayoutX(14.0);
        buttonBox.setLayoutY(70.0);

        messagePane.getChildren().addAll(senderLabel, contentLabel, buttonBox);

        MessageContainer.getChildren().add(messagePane);
    }

    // Subscribe to the event to receive and display support tickets
    @Subscribe
    public void onGetSupportTicketRequest(GetAllSupportTicketsEvent event) {
        List<SupportTicket> supportTickets = event.getSupportTickets();

        Platform.runLater(() -> {
            MessageContainer.getChildren().clear();  // Clear any previous messages

            for (SupportTicket ticket : supportTickets) {
                addMessage(ticket);
            }
        });
    }

    // Method to add a price change request to the GUI dynamically
    public void addPriceChangeRequest(PriceChangeRequest priceChangeRequest) {
        AnchorPane messagePane = new AnchorPane();
        messagePane.getStyleClass().add("ticket-pane");
        messagePane.setPrefWidth(700);

        Label senderLabel = new Label("Sender: " + priceChangeRequest.getContentManager().getUsername());
        senderLabel.setLayoutX(14.0);
        senderLabel.setLayoutY(14.0);
        senderLabel.getStyleClass().add("ticket-label");

        Label contentLabel = new Label("Message: " + "Request to change the price for this screening - "
                + priceChangeRequest.getScreening() + " to this price: " + priceChangeRequest.getNewPrice());
        contentLabel.setLayoutX(14.0);
        contentLabel.setLayoutY(34.0);
        contentLabel.getStyleClass().add("ticket-label");

        HBox buttonBox = new HBox(10);
        buttonBox.setLayoutX(14.0);
        buttonBox.setLayoutY(70.0);

        Button acceptButton = new Button("Accept");
        acceptButton.getStyleClass().add("ticket-button");
        acceptButton.setOnAction(event -> onAccept(priceChangeRequest));

        Button rejectButton = new Button("Reject");
        rejectButton.getStyleClass().add("ticket-button");
        rejectButton.setOnAction(event -> onReject(priceChangeRequest));

        buttonBox.getChildren().addAll(acceptButton, rejectButton);
        messagePane.getChildren().addAll(senderLabel, contentLabel, buttonBox);

        MessageContainer.getChildren().add(messagePane);
    }

    @Subscribe
    public void onGetPriceChangesRequest(GetPriceChangesEvent event) {
        List<PriceChangeRequest> priceChangeRequests = event.getPriceChangeRequests();

        Platform.runLater(() -> {
            MessageContainer.getChildren().clear();

            for (PriceChangeRequest priceChangeRequest : priceChangeRequests) {
                addPriceChangeRequest(priceChangeRequest);
            }
        });
    }

    // Handling acceptance of price change requests
    void onAccept(PriceChangeRequest priceChangeRequest) {
        // Handle acceptance logic for the price change request
    }

    // Handling rejection of price change requests
    void onReject(PriceChangeRequest priceChangeRequest) {
        // Handle rejection logic for the price change request
    }
}
