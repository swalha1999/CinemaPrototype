/**
 * Sample Skeleton for 'AdminScreening.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.controllers;

<<<<<<< HEAD
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.client.data.ScreeningView;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.GetAllMoviesDetailsEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.MovieDetails;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import javafx.application.Platform;
=======
>>>>>>> f784a9508229c5781e8eb43efbc5c51ac6c5c04e
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AdminScreening {

<<<<<<< HEAD
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
=======
    @FXML // fx:id="AvailableSeats_Col"
    private TableColumn<?, ?> AvailableSeats_Col; // Value injected by FXMLLoader

    @FXML // fx:id="BookedSeats_Col"
    private TableColumn<?, ?> BookedSeats_Col; // Value injected by FXMLLoader

    @FXML // fx:id="Cinema_Col"
    private TableColumn<?, ?> Cinema_Col; // Value injected by FXMLLoader

    @FXML // fx:id="Hall_Col"
    private TableColumn<?, ?> Hall_Col; // Value injected by FXMLLoader

    @FXML // fx:id="MovieId_Col"
    private TableColumn<?, ?> MovieId_Col; // Value injected by FXMLLoader

    @FXML // fx:id="MoviesTable"
    private TableView<?> MoviesTable; // Value injected by FXMLLoader

    @FXML // fx:id="ScreeningDate_Col"
    private TableColumn<?, ?> ScreeningDate_Col; // Value injected by FXMLLoader

    @FXML // fx:id="StartTime_Col"
    private TableColumn<?, ?> StartTime_Col; // Value injected by FXMLLoader

>>>>>>> f784a9508229c5781e8eb43efbc5c51ac6c5c04e
}
