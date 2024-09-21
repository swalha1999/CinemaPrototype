package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.Client;
import il.cshaifasweng.OCSFMediatorExample.client.data.CinemaView;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.GetAllCinemasEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.GetAllSupportTicketsEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Cinema;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.SupportTicket;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.User;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ComboBox;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.stream.Collectors;

import static il.cshaifasweng.OCSFMediatorExample.client.utils.UiUtil.showNotification;

public class SupportPage {

    @FXML
    private TextArea issueDescription;

    @FXML
    private Button submitButton;

    @FXML
    private Label welcomeMessage;

    @FXML
    private ComboBox<CinemaView> ComplainsLocation;

    private User currentUser;

    private CinemaView OnlineCinema;

    @FXML
    public void initialize() {
        // Register the EventBus to listen to events
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void handleGetAllCinemasEvent(GetAllCinemasEvent event) {
        // Handle the event when all support tickets are retrieved (if needed)
        Platform.runLater(() -> {
            List<Cinema> cinemaList = event.getCinemas();
            List<CinemaView> cinemaViewList = cinemaList.stream()
                    .map(CinemaView::new)
                    .collect(Collectors.toList());

            OnlineCinema = new CinemaView(null, -1, "All Locations", "", "", "", "", "");
            cinemaViewList.add(0, OnlineCinema);

            ComplainsLocation.setItems(FXCollections.observableArrayList(cinemaViewList));
            if (!cinemaViewList.isEmpty()) {
                ComplainsLocation.getSelectionModel().selectFirst();
            }
        });
    }

    @FXML
    void handleSubmit(ActionEvent event) {
        try {
            // Check if the client is connected to the server
            if (!Client.getClient().isConnected()) {
                showNotification("Failed to submit the request. Server connection lost.", false);
                return;
            }

            // Create a new SupportTicket and populate its fields
            SupportTicket supportTicket = new SupportTicket();
            supportTicket.setDescription(issueDescription.getText());

            // Optionally set other details like subject, email, or user info (if available)
            if (currentUser != null) {
                supportTicket.setName(currentUser.getUsername());
                supportTicket.setEmail(currentUser.getEmail());
            } else {
                // Handle case when currentUser is null (optional)
                supportTicket.setName("Anonymous");
                supportTicket.setEmail("anonymous@support.com");
            }

            // Create the message with the SupportTicket object
            Message message = new Message(MessageType.SEND_SUPPORT_TICKET_REQUEST)
                    .setDataObject(supportTicket)  // Send the SupportTicket object
                    .setSessionKey(SessionKeysStorage.getInstance().getSessionKey());

            // Send the message to the server
            Client.getClient().sendToServer(message);

            // Show notification and clear the text area
            showNotification("Thank you for your message! Our team will respond within 24 hours.", true);
            issueDescription.clear();
        } catch (Exception e) {
            e.printStackTrace(); // Log any exception that occurs during the submission
            showNotification("An error occurred while submitting the request.", false);
        }
    }

    @FXML
    private void PickLocation(ActionEvent event) {
        // Get the selected cinema from the ComboBox or other selection control
        CinemaView selectedCinema = ComplainsLocation.getSelectionModel().getSelectedItem();

        // Check if a cinema was selected and it's not the "All Locations" cinema (e.g., OnlineCinema)
        if (selectedCinema != null && !selectedCinema.equals(OnlineCinema)) {
            // Get the session key
            String sessionKey = SessionKeysStorage.getInstance().getSessionKey();

            // Get the cinema ID directly
            Integer selectedCinemaId = selectedCinema.getId().getValue();

            // Create a message object to send the cinema info request
            Message message = new Message(MessageType.GET_ALL_CINEMAS_REQUEST)
                    .setSessionKey(sessionKey)
                    .setDataObject(selectedCinemaId); // Pass the cinema ID as the data object

            // Get the client instance
            Client client = Client.getClient();

            // Send the message to the server if the client is initialized
            if (client != null) {
                client.sendToServer(message);
            } else {
                System.out.println("Client is not initialized.");
            }
        } else {
            // Handle case where no cinema is selected or "All Locations" is selected
            System.out.println("Selected cinema is null or is 'OnlineCinema'.");
        }
    }





    // Optionally, you can implement a method to unsubscribe from the EventBus when this controller is no longer active.
    public void cleanup() {
        EventBus.getDefault().unregister(this);
    }
}
