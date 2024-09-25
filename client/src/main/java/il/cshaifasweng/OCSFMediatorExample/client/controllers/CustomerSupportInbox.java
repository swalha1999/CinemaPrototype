package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.Client;
import il.cshaifasweng.OCSFMediatorExample.client.data.CinemaView;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.GetAllSupportTicketsEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.ShowSideUIEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Cinema;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.SupportTicket;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import static il.cshaifasweng.OCSFMediatorExample.client.CinemaMain.loadFXMLPane;
import static il.cshaifasweng.OCSFMediatorExample.client.utils.UiUtil.showSideUI;

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

        Button acceptButton = new Button("Answer");
        acceptButton.getStyleClass().add("ticket-button");
        acceptButton.setOnAction(event -> onAnswer(ticket));

        Button rejectButton = new Button("Reject");
        rejectButton.getStyleClass().add("ticket-button");
        rejectButton.setOnAction(event -> onReject(ticket));

        buttonBox.getChildren().addAll(acceptButton, rejectButton);
        messagePane.getChildren().addAll(senderLabel, contentLabel, buttonBox);

        MessageContainer.getChildren().add(messagePane);
    }

    private void onReject(SupportTicket ticket) {
        System.out.println("Rejected: " + ticket.getUser().getUsername());
    }

    private void onAnswer(SupportTicket ticket) {
        showSideUI("CustomerSupportResponsePage",ticket);
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
                addMessage(ticket);
            }
        });
    }



}
