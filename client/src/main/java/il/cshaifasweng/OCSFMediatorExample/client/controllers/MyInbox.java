package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.events.PurchaseScreeningEvent;
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

    @Subscribe
    public void onPurchaseScreeningEvent(PurchaseScreeningEvent event) {
        String movieTitle = event.getMovieTitle();
        String startTime = event.getStartTime().toString();
        String endTime = event.getEndTime().toString();
        String userEmail = event.getUserEmail();
System.out.println(movieTitle + " " + startTime + " " + endTime + " " + userEmail + "hahahahahahahah");
       //TODO : fix the email its null
        String messageContent = String.format("Your purchase for the movie '%s' is confirmed. \n" +
                "Available from: %s to %s.\n" +
                "Confirmation sent to: %s", movieTitle, startTime, endTime, userEmail);

        addMessage("Cinema System", messageContent);
    }
}
