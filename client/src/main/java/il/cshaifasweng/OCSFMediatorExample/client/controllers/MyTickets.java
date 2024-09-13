package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.events.PurchaseScreeningEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.MovieTicket;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Seat;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MyTickets {

    @FXML
    private VBox TicketsContainer;  // Make sure this is correctly linked in FXML.

    @FXML
    public void initialize() {
        // Register to EventBus
        EventBus.getDefault().register(this);

        // Example: Adding some tickets on initialization (For testing purposes)
        addTicket("12345", "XYZ Movie", "Seat (3, 5)");
        addTicket("67890", "ABC Movie", "Seat (1, 2)");
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

        // Add the ticket pane to the VBox container
        TicketsContainer.getChildren().add(ticketPane);
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

    // Unregister from the EventBus when the controller is no longer in use
    public void cleanup() {
        EventBus.getDefault().unregister(this);
    }
}
