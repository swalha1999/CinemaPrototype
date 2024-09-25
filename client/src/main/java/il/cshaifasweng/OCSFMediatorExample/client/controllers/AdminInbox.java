package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.Client;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
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

    @FXML // fx:id="acceptBtn"
    private Button acceptBtn; // Value injected by FXMLLoader

    @FXML // fx:id="rejectBtn"
    private Button rejectBtn; // Value injected by FXMLLoader

    public void initialize() {
        // Register the EventBus
        EventBus.getDefault().register(this);

        // Send a request to get all support tickets
        Message message = new Message(MessageType.GET_ALL_SUPPORT_TICKETS_REQUEST)
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey());
        Client.getClient().sendToServer(message);
    }

    @FXML
    void onAccept(ActionEvent event) {
        // Handle accept button click from the UI
    }

    @FXML
    void onReject(ActionEvent event) {
        // Handle reject button click from the UI
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

//        Button acceptButton = new Button("Accept");
//        acceptButton.getStyleClass().add("ticket-button");
//        acceptButton.setOnAction(event -> onAccept(ticket));
//
//        Button rejectButton = new Button("Reject");
//        rejectButton.getStyleClass().add("ticket-button");
//        rejectButton.setOnAction(event -> onReject(ticket));
//
//        buttonBox.getChildren().addAll(acceptButton, rejectButton);
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

    // Handling acceptance of support tickets
    void onAccept(SupportTicket ticket) {
        // Handle acceptance logic for the ticket
        // e.g., send a message to the server or update the status of the ticket
    }

    // Handling rejection of support tickets
    void onReject(SupportTicket ticket) {
        // Handle rejection logic for the ticket
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
