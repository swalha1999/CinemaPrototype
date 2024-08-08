package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class MyTickets {

    @FXML
    private VBox TicketsContainer;

    @FXML
    public void initialize() {
        // Example: Adding some tickets on initialization
        addTicket("12345", "XYZ Movie", "A1");
        addTicket("67890", "ABC Movie", "B2");
        addTicket("54321", "DEF Movie", "C3");
        addTicket("09876", "GHI Movie", "D4");
        addTicket("12345", "XYZ Movie", "A1");
        addTicket("67890", "ABC Movie", "B2");
        addTicket("54321", "DEF Movie", "C3");
        addTicket("09876", "GHI Movie", "D4");
        addTicket("12345", "XYZ Movie", "A1");
    }

    public void addTicket(String id, String screening, String seatNumber) {
        AnchorPane ticketPane = new AnchorPane();
        ticketPane.getStyleClass().add("ticket-pane");
        ticketPane.setPrefWidth(300); // Set the desired width of the ticket

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
}
