package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.events.PurchaseScreeningEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Seat;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MyTickets {

    @FXML
    private VBox TicketsContainer;

    @FXML
    public void initialize() {
        EventBus.getDefault().register(this);

        // Example: Adding some tickets on initialization
        addTicket("12345", "XYZ Movie", "Seat (3, 5)");
        addTicket("67890", "ABC Movie", "Seat (1, 2)");
    }

    public void addTicket(String id, String screening, String seatNumber) {
        AnchorPane ticketPane = new AnchorPane();
        ticketPane.getStyleClass().add("ticket-pane");
        ticketPane.setPrefWidth(300);

        Label idLabel = new Label("ID: " + id);
        idLabel.setLayoutX(14.0);
        idLabel.setLayoutY(14.0);
        idLabel.getStyleClass().add("ticket-label");

        Label screeningLabel = new Label("Screening: " + screening);
        screeningLabel.setLayoutX(14.0);
        screeningLabel.setLayoutY(34.0);
        screeningLabel.getStyleClass().add("ticket-label");

        Label seatNumberLabel = new Label("Seat Number: " + seatNumber);
        seatNumberLabel.setLayoutX(14.0);
        seatNumberLabel.setLayoutY(54.0);
        seatNumberLabel.getStyleClass().add("ticket-label");

        ticketPane.getChildren().addAll(idLabel, screeningLabel, seatNumberLabel);

        TicketsContainer.getChildren().add(ticketPane);
    }

    @Subscribe
    public void onPurchaseScreeningEvent(PurchaseScreeningEvent event) {
        String movieTitle = event.getMovieTitle();

        StringBuilder seatsBuilder = new StringBuilder();
        for (Seat seat : event.getSeats()) {
            seatsBuilder.append(String.format("(%d, %d)", seat.getSeatLocationX(), seat.getSeatLocationY())).append(" ");
        }
        String seatNumbers = seatsBuilder.toString().trim();

        addTicket("Ticket-" + System.currentTimeMillis(), movieTitle, seatNumbers);
    }
}
