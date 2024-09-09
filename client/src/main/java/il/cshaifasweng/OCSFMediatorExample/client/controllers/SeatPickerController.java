package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.events.ShowSideUIEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Screening;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Seat;
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
    private Screening screeningData;
    private Movie movieData;

    @FXML
    private GridPane SeatsGrid;

    @FXML
    private Button confirmButton;

    @FXML
    private Button returnBtn;

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
    }

    private void makeSeat(int col, int row, boolean isAvailable) {
        Pane seat = new Pane();
        seat.getStyleClass().add("seat");

        if (!isAvailable) {
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
        showSideUI("Purchase", selectedSeats, screeningData);
    }


    @Subscribe
    public void getMovieDetails(ShowSideUIEvent event) {
        if (!event.getUIName().equals("SeatPicker")) {
            return;
        }
        screeningData = (Screening) event.getSecondObj();
        movieData = (Movie) event.getFirstObj();

        // Clear the grid
        SeatsGrid.getChildren().clear();
        for( Seat seat : screeningData.getSeats() ) {
            makeSeat(seat.getSeatLocationX(), seat.getSeatLocationY(), seat.isAvailable());
        }
    }


}