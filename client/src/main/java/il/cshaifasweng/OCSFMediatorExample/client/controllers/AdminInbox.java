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

    }

    @FXML
    void onReject(ActionEvent event) {

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
                String sender = ticket.getUser().getUsername();
                String content = ticket.getDescription();
                addMessage(sender, content);
            }
        });
    }

    public void addPriceChangeRequest(PriceChangeRequest priceChangeRequest) {
        AnchorPane messagePane = new AnchorPane();
        messagePane.getStyleClass().add("ticket-pane");
        messagePane.setPrefWidth(300);

        Label senderLabel = new Label("Sender: " + priceChangeRequest.getContentManager().getUsername();
        senderLabel.setLayoutX(14.0);
        senderLabel.setLayoutY(14.0);
        senderLabel.getStyleClass().add("ticket-label");

        Label contentLabel = new Label("Message: " + "Request to change the price for this screening - " + priceChangeRequest.getScreening()
                + "to this price:" + priceChangeRequest.getNewPrice());
        contentLabel.setLayoutX(14.0);
        contentLabel.setLayoutY(34.0);
        contentLabel.getStyleClass().add("ticket-label");

        messagePane.getChildren().addAll(senderLabel, contentLabel);

        MessageContainer.getChildren().add(messagePane);
    }

    @Subscribe
    public void onGetPriceChangesRequest(GetPriceChangesEvent event) {

        List<PriceChangeRequest> priceChangeRequests = event.getPriceChangeRequests();

        Platform.runLater(() -> {
            MessageContainer.getChildren().clear();

            // Iterate through the support tickets and add them to the GUI
            for (PriceChangeRequest priceChangeRequest : priceChangeRequests) {
                addPriceChangeRequest(priceChangeRequest);
            }

        });

    }
}
