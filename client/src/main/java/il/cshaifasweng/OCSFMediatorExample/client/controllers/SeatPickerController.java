package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.events.ShowSideUIEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Screening;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashSet;
import java.util.Set;

import static il.cshaifasweng.OCSFMediatorExample.client.utils.UiUtil.showSideUI;

public class SeatPickerController {
     private Screening screeningData = new Screening();
    @FXML // fx:id="SeatsGrid"
    private GridPane SeatsGrid; // Value injected by FXMLLoader

    @FXML // fx:id="confirmButton"
    private Button confirmButton; // Value injected by FXMLLoader

    @FXML // fx:id="returnBtn"
    private Button returnBtn; // Value injected by FXMLLoader

    private Set<Pane> selectedSeats = new HashSet<>();

    @FXML
    void returnFunction(ActionEvent event) {
        Platform.runLater(() -> {
            showSideUI("MovieDetails"); // Ensure this is the correct screen name
        });
    }

    @FXML
    public void initialize() {
        EventBus.getDefault().register(this);
        // Example: Creating a 10x10 grid of seats
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

    private void makeSeat(int col, int row) {
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
        // Send the movie data and selected seats to the Purchase screen
        System.out.println("Selected seats: " + selectedSeats.size());
        System.out.println("Movie received: " + screeningData.getMovie().getTitle());
        showSideUI("Purchase", selectedSeats, screeningData);  // Pass selected seats and Movie data
    }


    @Subscribe
    public void getMovieDetails(ShowSideUIEvent event) {
        if (event.getUIName().equals("SeatPicker") && event.getFirstObj() instanceof Movie) {
            screeningData.setMovie((Movie)event.getFirstObj());
            System.out.println("Movie received: " + screeningData.getMovie().getTitle());
        }
    }


}