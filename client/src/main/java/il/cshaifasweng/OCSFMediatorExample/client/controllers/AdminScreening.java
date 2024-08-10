package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.client.data.ScreeningView;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.GetAllMoviesDetailsEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.MovieDetails;
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
import java.util.List;

public class AdminScreening {

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
    private TableView<ScreeningView> MoviesTable;

    @FXML
    private TableColumn<ScreeningView, String> ScreeningDate_Col;

    @FXML
    private TableColumn<ScreeningView, Integer> StartTime_Col;

    @FXML
    private TableColumn<ScreeningView, String> EndTime_Col;

    @FXML
    public void initialize() throws IOException {

        AvailableSeats_Col.setCellValueFactory(new PropertyValueFactory<>("AvailableSeats"));
        Cinema_Col.setCellValueFactory(new PropertyValueFactory<>("Cinema"));
        Hall_Col.setCellValueFactory(new PropertyValueFactory<>("Hall"));
        MovieId_Col.setCellValueFactory(new PropertyValueFactory<>("MovieId"));
        ScreeningDate_Col.setCellValueFactory(new PropertyValueFactory<>("ScreeningDate"));
        StartTime_Col.setCellValueFactory(new PropertyValueFactory<>("StartTime"));
        BookedSeats_Col.setCellValueFactory(new PropertyValueFactory<>("BookedSeats"));
        EndTime_Col.setCellValueFactory(new PropertyValueFactory<>("EndTime"));

    }

    //TODO: hey omar this is swalha this function is not working the event type is not correct - please fix :)
//    @Subscribe
    @Subscribe
    public void onGetAllMoviesEvent(GetAllMoviesDetailsEvent response) {
        Platform.runLater(() -> {
            List<MovieDetails> movies = response.getMoviesDetails();
            MoviesTable.getItems().clear();
            for (MovieDetails movie : movies) {
                MoviesTable.getItems().add(new ScreeningView(movie));
            }
        });
    }
}
