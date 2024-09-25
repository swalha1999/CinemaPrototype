package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.Client;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.GetSeatsForScreeningEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.ShowSideUIEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Screening;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Seat;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.UserRole;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
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

import static il.cshaifasweng.OCSFMediatorExample.client.utils.UiUtil.showNotification;
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
        Platform.runLater(() -> showSideUI("MovieDetails"));  // Ensures return to MovieDetails
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
        // If the seat is already selected, deselect it
        if (selectedSeats.contains(seatContainer.getSeat())) {
            selectedSeats.remove(seatContainer.getSeat());
            seatContainer.getSeatPane().getStyleClass().remove("selected");
        } else {
            // Select the new seat without clearing the previous selection
            selectedSeats.add(seatContainer.getSeat());
            seatContainer.getSeatPane().getStyleClass().add("selected");
        }
    }


    @FXML
    private void confirmSelection() {
        showSideUI("Purchase", selectedSeats, screeningData);  // Pass the selected seats and screening data
    }

    @Subscribe
    public void getUIChange(ShowSideUIEvent event) {
        if (!event.getUIName().equals("SeatPicker") || event.getFirstObj() == null || event.getSecondObj() == null) {
            return;
        }

        selectedSeats.clear();
        seatLocations.clear();
        SeatsGrid.getChildren().clear();

        screeningData = (Screening) event.getSecondObj();
        movieData = (Movie) event.getFirstObj();

        // Request server to get the seats for the screening
        Message request = new Message(MessageType.GET_SEATS_FOR_SCREENING_REQUEST)
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey())
                .setDataObject(screeningData);

        Client.getClient().sendToServer(request);
    }

    @Subscribe
    public void onGetSeatsForScreeningEvent(GetSeatsForScreeningEvent event) {
        Platform.runLater(() -> {
            List<Seat> seats = event.getSeats();
            for (Seat seat : seats) {
                makeSeatTile(seat);
            }
        });
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
