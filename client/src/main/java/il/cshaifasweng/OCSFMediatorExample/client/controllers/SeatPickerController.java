package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.HashSet;
import java.util.Set;

import static il.cshaifasweng.OCSFMediatorExample.client.utils.UiUtil.showSideUI;

public class SeatPickerController {

    @FXML // fx:id="SeatsGrid"
    private GridPane SeatsGrid; // Value injected by FXMLLoader

    @FXML // fx:id="confirmButton"
    private Button confirmButton; // Value injected by FXMLLoader

    @FXML // fx:id="returnBtn"
    private Button returnBtn; // Value injected by FXMLLoader

    private Set<Pane> selectedSeats = new HashSet<>();

    @FXML
    void returnFunction(ActionEvent event) {
        Platform.runLater(()->{
            showSideUI("MovieDetails");
        });
    }

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
        showSideUI("Purchase");
        // Implement the logic to proceed with booking these seats
    }
}
