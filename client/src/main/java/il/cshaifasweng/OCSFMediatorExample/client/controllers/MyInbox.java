package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.events.PurchaseMovieEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MyInbox {

    @FXML
    private VBox MessageContainer;

    @FXML
    public void initialize() {
        // Register the EventBus
        EventBus.getDefault().register(this);

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

    @Subscribe
    public void MoviePurchasse(PurchaseMovieEvent event) {
        String movieTitle = event.getMovieTitle();
        String startTime = event.getStartTime().toString(); // Assuming startTime is LocalDateTime
        String endTime = event.getEndTime().toString(); // Assuming endTime is LocalDateTime

        String messageContent = String.format("Your purchase for the movie '%s' is confirmed. \n" +
                "Available from: %s to %s", movieTitle, startTime, endTime);

        addMessage("Cinema System", messageContent);
    }
}
