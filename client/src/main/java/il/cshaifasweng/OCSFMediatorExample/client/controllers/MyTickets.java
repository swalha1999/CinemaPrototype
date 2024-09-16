package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.Client;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.GetMyTicketsEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.PurchaseScreeningEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.MovieTicket;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Seat;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyTickets {

    @FXML
    private VBox TicketsContainer;  // Make sure this is correctly linked in FXML.

    @FXML
    private Button returnTicketButton;

    private AnchorPane selectedTicketPane = null;  // Store the currently selected ticket
    private MovieTicket selectedTicket = null;     // Store the selected ticket data
    private Set<MovieTicket> tickets = new HashSet<>();

    @FXML
    public void initialize() {
        // Register to EventBus
        EventBus.getDefault().register(this);

        // Disable the return ticket button initially
        returnTicketButton.setDisable(true);

        // Send request to server to fetch all tickets
        Message message = new Message(MessageType.GET_MY_TICKETS_REQUEST)
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey());
        Client.getClient().sendToServer(message);
    }

    public void addTicket(String id, String movieTitle, String seatNumber) {
        // Create a new AnchorPane for the ticket
        AnchorPane ticketPane = new AnchorPane();
        ticketPane.getStyleClass().add("ticket-pane");
        ticketPane.setPrefWidth(300);

        // Create labels for ticket details
        Label idLabel = new Label("ID: " + id);
        idLabel.setLayoutX(14.0);
        idLabel.setLayoutY(14.0);
        idLabel.getStyleClass().add("ticket-label");

        Label movieLabel = new Label("Movie: " + movieTitle);
        movieLabel.setLayoutX(14.0);
        movieLabel.setLayoutY(34.0);
        movieLabel.getStyleClass().add("ticket-label");

        Label seatNumberLabel = new Label("Seat Number: " + seatNumber);
        seatNumberLabel.setLayoutX(14.0);
        seatNumberLabel.setLayoutY(54.0);
        seatNumberLabel.getStyleClass().add("ticket-label");

        // Add labels to the ticket pane
        ticketPane.getChildren().addAll(idLabel, movieLabel, seatNumberLabel);

        // Add click event to select the ticket
        ticketPane.setOnMouseClicked(event -> selectTicket(ticketPane, id));

        // Add the ticket pane to the VBox container
        TicketsContainer.getChildren().add(ticketPane);
    }

    private void selectTicket(AnchorPane ticketPane, String ticketId) {
        // If there is already a selected ticket, reset its style
        if (selectedTicketPane != null) {
            selectedTicketPane.getStyleClass().remove("selected-ticket");
        }

        // Highlight the selected ticket
        selectedTicketPane = ticketPane;
        ticketPane.getStyleClass().add("selected-ticket");

        // Enable the return ticket button
        returnTicketButton.setDisable(false);

        // Fetch the corresponding ticket object for the selected ID (In a real app, this would come from your data)
        selectedTicket = fetchTicketById(ticketId); // Implement this method to get the actual MovieTicket
    }

    private MovieTicket fetchTicketById(String ticketId) {
        // Placeholder: Fetch the ticket based on the ticketId (from your backend or stored data)
        return new MovieTicket(); // Replace with actual logic
    }

    @Subscribe
    public void onPurchaseScreeningEvent(PurchaseScreeningEvent event) {
        if (event != null && event.getTicket() != null) {
            // Debugging: Print event details
            System.out.println("Received PurchaseScreeningEvent");

            MovieTicket ticket = event.getTicket();
            String movieTitle = ticket.getScreening().getMovie().getTitle();
            Seat seat = ticket.getSeat();

            // Build seat number
            String seatNumber = String.format("(%d, %d)", seat.getSeatLocationX(), seat.getSeatLocationY());

            // Add ticket with movie title and seat number
            addTicket(String.valueOf(ticket.getId()), movieTitle, seatNumber);
        } else {
            System.err.println("PurchaseScreeningEvent or Ticket data is null.");
        }
    }

    // Handle GetMyTicketsEvent
    @Subscribe
    public void onGetMyTicketsEvent(GetMyTicketsEvent event) {
        if (event != null && event.getTickets() != null) {
            List<MovieTicket> ticketSet = event.getTickets();

            // Loop through the set of tickets and add them to the UI
            for (MovieTicket ticket : ticketSet) {
                String movieTitle = ticket.getScreening().getMovie().getTitle();
                Seat seat = ticket.getSeat();
                String seatNumber = String.format("(%d, %d)", seat.getSeatLocationX(), seat.getSeatLocationY());

                addTicket(String.valueOf(ticket.getId()), movieTitle, seatNumber);
            }
        } else {
            System.err.println("GetMyTicketsEvent or Ticket data is null.");
        }
    }

    // Unregister from the EventBus when the controller is no longer in use
    public void cleanup() {
        EventBus.getDefault().unregister(this);
    }
}
