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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    private Set<Seat> selectedSeats = new HashSet<>();
    private List<seatContainer> seatLocations = new ArrayList<>();

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

    private void makeSeatTile(Seat seat) {
        Pane seatPane = new Pane();
        seatContainer seatContainer = new seatContainer(seat, seatPane);
        seatPane.getStyleClass().add("seat");

        if (!seat.isAvailable()) {
            seatPane.getStyleClass().add("unavailable");
            seatPane.setDisable(true);
        } else {
            seatPane.setOnMouseClicked(event -> toggleSeatSelection(seatContainer));
        }

        SeatsGrid.add(seatPane, seat.getSeatLocationX(), seat.getSeatLocationY());
        seatLocations.add(new seatContainer(seat, seatPane));
    }

    private void toggleSeatSelection(seatContainer seatContainer) {
        if (selectedSeats.contains(seatContainer.getSeat())) {
            selectedSeats.remove(seatContainer.getSeat());
            seatContainer.getSeatPane().getStyleClass().remove("selected");
        } else {
            selectedSeats.add(seatContainer.getSeat());
            seatContainer.getSeatPane().getStyleClass().add("selected");
        }
    }

    @FXML
    private void confirmSelection() {
        showSideUI("Purchase", selectedSeats, screeningData);
    }


    @Subscribe
    public void getMovieDetails(ShowSideUIEvent event) {
        if (!event.getUIName().equals("SeatPicker") || event.getFirstObj() == null || event.getSecondObj() == null) {
            return;
        }

        screeningData = (Screening) event.getSecondObj();
        movieData = (Movie) event.getFirstObj();

        // Clear the grid
        SeatsGrid.getChildren().clear();
        seatLocations.clear();
        for( Seat seat : screeningData.getSeats() ) {
            makeSeatTile(seat);
        }
    }


    private static class seatContainer {
        Seat seat;
        Pane seatPane;

        public seatContainer(Seat seat, Pane seatPane) {
            this.seat = seat;
            this.seatPane = seatPane;
        }

        public Seat getSeat() {
            return seat;
        }

        public Pane getSeatPane() {
            return seatPane;
        }

    }


}