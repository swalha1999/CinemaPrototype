package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.client.data.ScreeningView;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.GetAllScreeningsEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Screening;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class AdminScreening {

    @FXML
    private TableColumn<ScreeningView, String> MovieName_Col;

    @FXML
    private TableColumn<ScreeningView, Integer> AvailableSeats_Col;

    @FXML
    private TableColumn<ScreeningView, Integer> BookedSeats_Col;

    @FXML
    private TableColumn<ScreeningView, String> Cinema_Col;

    @FXML
    private TableColumn<ScreeningView, String> Hall_Col;

    @FXML
    private TableColumn<ScreeningView, String> MovieId_Col;

    @FXML
    private TableView<ScreeningView> ScreeningTable;

    @FXML
    private TableColumn<ScreeningView, String> ScreeningDate_Col;

    @FXML
    private TableColumn<ScreeningView, LocalDateTime> StartTime_Col;

    @FXML
    private TableColumn<ScreeningView, String> EndTime_Col;

    @FXML
    public void initialize() throws IOException {
        EventBus.getDefault().register(this);
        AvailableSeats_Col.setCellValueFactory(new PropertyValueFactory<>("availableSeats"));
        Cinema_Col.setCellValueFactory(new PropertyValueFactory<>("cinema"));
        Hall_Col.setCellValueFactory(new PropertyValueFactory<>("hall"));
        MovieId_Col.setCellValueFactory(new PropertyValueFactory<>("movieId"));
        ScreeningDate_Col.setCellValueFactory(new PropertyValueFactory<>("screeningDate"));
        BookedSeats_Col.setCellValueFactory(new PropertyValueFactory<>("bookedSeats"));
        StartTime_Col.setCellValueFactory(new PropertyValueFactory<>("screeningDate"));


        Message message = new Message(MessageType.GET_ALL_SCREENINGS_REQUEST)
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey());
        SimpleClient.getClient().sendToServer(message);

    }


    @Subscribe
    public void onGetAllScreenings(GetAllScreeningsEvent response) {
        Platform.runLater(() -> {
            List<Screening> screenings = response.getScreenings();
            for (Screening screening : screenings) {
                ScreeningTable.getItems().add(new ScreeningView(screening));
            }
            ScreeningTable.refresh();
        });
    }
}