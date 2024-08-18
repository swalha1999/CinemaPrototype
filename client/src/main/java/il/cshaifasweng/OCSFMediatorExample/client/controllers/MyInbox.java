package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class MyInbox {

    @FXML
    private VBox MessageContainer;

    @FXML
    public void initialize() {
        // Example: Adding some messages on initialization
        addMessage("John Doe", "Hello! How are you?");
        addMessage("Jane Smith", "Meeting at 3 PM.");
        addMessage("Admin", "Your account has been updated.");
        addMessage("Support", "Your issue has been resolved.");
        addMessage("John Doe", "Let's catch up later.");
    }

    public void addMessage(String sender, String content) {
        AnchorPane messagePane = new AnchorPane();
        messagePane.getStyleClass().add("ticket-pane");
        messagePane.setPrefWidth(300); // Set the desired width of the ticket

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
}
