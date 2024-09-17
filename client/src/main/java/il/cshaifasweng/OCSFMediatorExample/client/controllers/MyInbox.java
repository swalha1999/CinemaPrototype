package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.Client;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.GetMyScreeningsEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.HourTillMovieEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.PurchaseScreeningEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Screening;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyInbox {

    @FXML
    private VBox MessageContainer;

    // Store unique screenings already displayed
    private Set<Integer> displayedScreeningIds = new HashSet<>();

    @FXML
    public void initialize() {
        // Register the EventBus
        EventBus.getDefault().register(this);

        // Example: Adding some static messages on initialization
        addMessage("John Doe", "Hello! How are you?");
        addMessage("Jane Smith", "Meeting at 3 PM.");
        addMessage("Admin", "Your account has been updated.");
        addMessage("Support", "Your issue has been resolved.");
        addMessage("John Doe", "Let's catch up later.");

        // Send a request to get all screenings for the user
        Message message = new Message(MessageType.GET_MY_SCREENINGS_REQUEST)
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey());
        Client.getClient().sendToServer(message);
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
        String movieTitle = event.getTicket().getScreening().getMovie().getTitle();
        LocalDateTime startTime = event.getTicket().getScreening().getStartingAt();
        int durationInMinutes = event.getTicket().getScreening().getMovie().getDurationInMinutes();

        // Calculate end time by adding the duration to the start time
        LocalDateTime endTime = startTime.plusMinutes(durationInMinutes);

        // Format date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedStartTime = startTime.format(formatter);
        String formattedEndTime = endTime.format(formatter);

        String messageContent = String.format("Your purchase for the movie '%s' is confirmed. \n" +
                "Available from: %s to %s.", movieTitle, formattedStartTime, formattedEndTime);

        addMessage("Cinema System", messageContent);
    }

    @Subscribe
    public void OnHourTillMovieEvent(HourTillMovieEvent event) {
        String messageContent = event.getMessage();
        addMessage("Cinema System", messageContent);
        System.out.println(" it should print the movie ");
    }

    // Handle GetMyScreeningsEvent and avoid duplicating screenings in the inbox
    @Subscribe
    public void onGetMyScreeningsEvent(GetMyScreeningsEvent event) {
        if (event != null && event.getScreenings() != null) {
            List<Screening> screeningSet = event.getScreenings();

            // Loop through the set of screenings and add them to the inbox
            for (Screening screening : screeningSet) {
                int screeningId = screening.getId();

                // Check if the screening was already displayed
                if (!displayedScreeningIds.contains(screeningId)) {
                    String movieTitle = screening.getMovie().getTitle();
                    LocalDateTime startTime = screening.getStartingAt();
                    int durationInMinutes = screening.getMovie().getDurationInMinutes();

                    // Calculate end time by adding the duration to the start time
                    LocalDateTime endTime = startTime.plusMinutes(durationInMinutes);

                    // Format date and time
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    String formattedStartTime = startTime.format(formatter);
                    String formattedEndTime = endTime.format(formatter);

                    // Create the message content
                    String messageContent = String.format("Screening for the movie '%s'. \n" +
                            "From: %s to %s.", movieTitle, formattedStartTime, formattedEndTime);

                    // Add the message to the inbox
                    addMessage("Cinema System", messageContent);

                    // Mark the screening as displayed
                    displayedScreeningIds.add(screeningId);
                }
            }
        } else {
            System.err.println("GetMyScreeningsEvent or Screening data is null.");
        }
    }
}
