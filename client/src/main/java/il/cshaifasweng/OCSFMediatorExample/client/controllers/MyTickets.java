package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.Client;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.GetMyTicketsEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.MovieTicket;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Seat;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import javafx.application.Platform;
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
    private VBox TicketsContainer;

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

    }

    @FXML
    public void RemoveTicketBtn(javafx.event.ActionEvent actionEvent) {
        if (selectedTicket != null) {
            System.out.println("Removing ticket: " + selectedTicket);

            Message message = new Message(MessageType.REMOVE_TICKET_REQUEST)
                    .setSessionKey(SessionKeysStorage.getInstance().getSessionKey())
                    .setDataObject(selectedTicket);  // Send the selected ticket object to the server

            Client.getClient().sendToServer(message);
        } else {
            System.err.println("No ticket selected for removal.");
        }
    }

    public void addTicket(MovieTicket ticket) {
        // Add the ticket object to the set
        tickets.add(ticket);

        // Create a new AnchorPane for the ticket
        AnchorPane ticketPane = new AnchorPane();
        ticketPane.getStyleClass().add("ticket-pane");
        ticketPane.setPrefWidth(300);

        // Create labels for ticket details
        Label idLabel = new Label("ID: " + ticket.getId());
        idLabel.setLayoutX(14.0);
        idLabel.setLayoutY(14.0);
        idLabel.getStyleClass().add("ticket-label");

        Label movieLabel = new Label("Movie: " + ticket.getScreening().getMovie().getTitle());
        movieLabel.setLayoutX(14.0);
        movieLabel.setLayoutY(34.0);
        movieLabel.getStyleClass().add("ticket-label");

        Seat seat = ticket.getSeat();
        String seatNumber = String.format("(%d, %d)", seat.getSeatLocationX(), seat.getSeatLocationY());
        Label seatNumberLabel = new Label("Seat Number: " + seatNumber);
        seatNumberLabel.setLayoutX(14.0);
        seatNumberLabel.setLayoutY(54.0);
        seatNumberLabel.getStyleClass().add("ticket-label");

        // Add labels to the ticket pane
        ticketPane.getChildren().addAll(idLabel, movieLabel, seatNumberLabel);

        // Add click event to select the ticket
        ticketPane.setOnMouseClicked(event -> selectTicket(ticketPane, String.valueOf(ticket.getId())));

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

        // Fetch the corresponding ticket object for the selected ID
        selectedTicket = fetchTicketById(ticketId);
    }

    private MovieTicket fetchTicketById(String ticketId) {
        for (MovieTicket ticket : tickets) {
            if (String.valueOf(ticket.getId()).equals(ticketId)) {
                return ticket;
            }
        }
        return null;  // Return null if no ticket is found with the given ID
    }


    @Subscribe
    public void onGetMyTicketsEvent(GetMyTicketsEvent event) {
        if (event != null && event.getTickets() != null) {
            // Ensure this runs on the JavaFX Application thread
            Platform.runLater(() -> {
                // Clear the container and ticket set to remove old tickets
                TicketsContainer.getChildren().clear();
                tickets.clear();

                // Add the updated list of tickets
                List<MovieTicket> ticketList = event.getTickets();

                for (MovieTicket ticket : ticketList) {
                    addTicket(ticket);
                }
            });
        } else {
            System.err.println("GetMyTicketsEvent or Ticket data is null.");
        }
    }


}
