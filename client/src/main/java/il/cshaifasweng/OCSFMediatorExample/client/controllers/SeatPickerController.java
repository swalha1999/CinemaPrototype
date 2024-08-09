package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.HashSet;
import java.util.Set;

public class SeatPickerController {

    @FXML
    private GridPane SeatsGrid;

    @FXML
    private Button confirmButton;

    private Set<Pane> selectedSeats = new HashSet<>();

    @FXML
    public void initialize() {
        // Example: Creating a 5x5 grid of seats
        Platform.runLater(() -> {
            int rows = 10;
            int columns = 10;
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < columns; col++) {
                   makeSeat(row, col);
                }
            }
        });
    }

 private void makeSeat(int col, int row){
     Pane seat = new Pane();
     seat.getStyleClass().add("seat");

     // Example: Mark some seats as unavailable
     if ((row == 2 && col == 2) || (row == 3 && col == 4)) {
         seat.getStyleClass().add("unavailable");
         seat.setDisable(true);
     } else {
         seat.setOnMouseClicked(event -> toggleSeatSelection(seat));
     }
     SeatsGrid.add(seat, col, row);
 }

    private void toggleSeatSelection(Pane seat) {
        if (selectedSeats.contains(seat)) {
            selectedSeats.remove(seat);
            seat.getStyleClass().remove("selected");
        } else {
            selectedSeats.add(seat);
            seat.getStyleClass().add("selected");
        }
    }

    @FXML
    private void confirmSelection() {
        // Handle the confirmation of selected seats
        System.out.println("Selected seats: " + selectedSeats.size());
        // Implement the logic to proceed with booking these seats
    }
}
