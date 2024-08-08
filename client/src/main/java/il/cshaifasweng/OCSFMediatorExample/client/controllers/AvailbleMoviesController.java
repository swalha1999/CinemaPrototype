/**
 * Sample Skeleton for 'AvailbleMovies.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.client.data.MovieView;
import il.cshaifasweng.OCSFMediatorExample.client.data.UserView;
import il.cshaifasweng.OCSFMediatorExample.client.events.GetAllMoviesEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.MovieGenre;
import il.cshaifasweng.OCSFMediatorExample.entities.User;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AvailbleMoviesController {

    @FXML // fx:id="Description_Col"
    private TableColumn<MovieView, String> Description_Col; // Value injected by FXMLLoader

    @FXML // fx:id="Genre_Col"
    private TableColumn<MovieView, MovieGenre> Genre_Col; // Value injected by FXMLLoader

    @FXML // fx:id="MovieName_Col"
    private TableColumn<MovieView, String> MovieName_Col; // Value injected by FXMLLoader

    @FXML // fx:id="MoviesTable"
    private TableView<MovieView> MoviesTable; // Value injected by FXMLLoader

    @FXML // fx:id="Picture_Col"
    private TableColumn<MovieView, String> Picture_Col; // Value injected by FXMLLoader

    @FXML // fx:id="ScreeningTime_Col"
    private TableColumn<MovieView, String> ScreeningTime_Col; // Value injected by FXMLLoader

    @FXML
    public void initialize() {
        EventBus.getDefault().register(this);
        Description_Col.setCellValueFactory(new PropertyValueFactory<>("Description"));
        Genre_Col.setCellValueFactory(new PropertyValueFactory<>("Genre"));
        MovieName_Col.setCellValueFactory(new PropertyValueFactory<>("MovieName"));
        Picture_Col.setCellValueFactory(new PropertyValueFactory<>("Picture"));
        ScreeningTime_Col.setCellValueFactory(new PropertyValueFactory<>("ScreeningTime"));

        try {
            SimpleClient.getClient().sendToServer(new Message(1, "get all movies"));
        } catch (IOException e) {
            System.err.println("Error sending message to server to get all the movies: " + e.getMessage());
        }
    }

    @Subscribe
    public void onGetAllMoviesEvent(GetAllMoviesEvent response){
        Platform.runLater(()->{
            List<Movie> movies = response.getMovies();
            MoviesTable.getItems().clear();
            for (Movie movie : movies) {
                MoviesTable.getItems().add(new MovieView(movie));
            }
        });
    }

}
