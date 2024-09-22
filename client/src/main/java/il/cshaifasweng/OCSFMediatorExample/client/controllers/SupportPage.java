package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.Client;
import il.cshaifasweng.OCSFMediatorExample.client.data.CinemaView;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.GetAllCinemasEvent;
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

import java.time.Instant;
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
    private ComboBox<CinemaView> locationComboBox; // Renamed for clarity

    private CinemaView allLocations;

    @Subscribe
    public void onGetLocation(GetAllCinemasEvent event) {
        Platform.runLater(() -> {
            List<Cinema> cinemaList = event.getCinemas();
            List<CinemaView> cinemaViewList = cinemaList.stream()
                    .map(CinemaView::new)
                    .collect(Collectors.toList());

            allLocations = new CinemaView(null, -1, "All Locations", "", "", "", "", "");
            cinemaViewList.add(0, allLocations);

            locationComboBox.setItems(FXCollections.observableArrayList(cinemaViewList));
            if (!cinemaViewList.isEmpty()) {
                locationComboBox.getSelectionModel().selectFirst();
            }
        });
    }

    @FXML
    private void initialize() {
        String currentUsername = SessionKeysStorage.getInstance().getUsername();

        // Set the welcome message, replacing "[Username]" with the actual username
        welcomeMessage.setText("Welcome " + currentUsername + "! , \n"+
                "We value your feedback and are here to assist with any concerns you may have. " +
                "Please feel free to share your experience, and we'll work to resolve the issue as quickly as possible.");
        EventBus.getDefault().register(this);
        fetchCinemas();
    }

    private void fetchCinemas() {
        Message message = new Message(MessageType.GET_ALL_CINEMAS_REQUEST)
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey());

        Client.getClient().sendToServer(message);
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

            // Set the selected cinema
            CinemaView selectedCinema = locationComboBox.getSelectionModel().getSelectedItem();
            if (selectedCinema != null && !selectedCinema.equals(allLocations)) {
                Cinema cinema = new Cinema();
                cinema.setId(selectedCinema.getId().getValue());
                supportTicket.setCinema(cinema);
            } else {
                // Handle case when no cinema is selected or "All Locations" is selected
                showNotification("Please select a valid cinema.", false);
                return;
            }

            // Optionally set other details like subject, email, or user info (if available)

            supportTicket.setName(SessionKeysStorage.getInstance().getUsername());
            supportTicket.setEmail(SessionKeysStorage.getInstance().getEmail() != null ? SessionKeysStorage.getInstance().getEmail() : "N/A");

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

    // Optionally, you can implement a method to unsubscribe from the EventBus when this controller is no longer active.
    public void cleanup() {
        EventBus.getDefault().unregister(this);
    }
}
