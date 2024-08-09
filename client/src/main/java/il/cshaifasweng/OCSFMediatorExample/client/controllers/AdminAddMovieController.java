/**
 * Sample Skeleton for 'AdminAddMovie.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.client.data.MovieView;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.data.UserView;
import il.cshaifasweng.OCSFMediatorExample.client.events.GetAllMoviesEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.GetAllUsersEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.User;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.requests.GetAllMoviesRequest;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.requests.GetAllUsersRequest;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;

public class AdminAddMovieController {

    @FXML // fx:id="clearButton"
    private Button clearButton; // Value injected by FXMLLoader

    @FXML // fx:id="deleteButton"
    private Button deleteButton; // Value injected by FXMLLoader

    @FXML // fx:id="durationColumn"
    private TableColumn<MovieView, ?> durationColumn; // Value injected by FXMLLoader

    @FXML // fx:id="durationField"
    private TextField durationField; // Value injected by FXMLLoader

    @FXML // fx:id="genreColumn"
    private TableColumn<MovieView, ?> genreColumn; // Value injected by FXMLLoader

    @FXML // fx:id="genreField"
    private TextField genreField; // Value injected by FXMLLoader

    @FXML // fx:id="importButton"
    private Button importButton; // Value injected by FXMLLoader

    @FXML // fx:id="insertButton"
    private Button insertButton; // Value injected by FXMLLoader

    @FXML // fx:id="movieImageView"
    private ImageView movieImageView; // Value injected by FXMLLoader

    @FXML // fx:id="moviesTable"
    private TableView<MovieView> moviesTable; // Value injected by FXMLLoader

    @FXML // fx:id="showingDateColumn"
    private TableColumn<MovieView, ?> showingDateColumn; // Value injected by FXMLLoader

    @FXML // fx:id="showingDatePicker"
    private DatePicker showingDatePicker; // Value injected by FXMLLoader

    @FXML // fx:id="titleColumn"
    private TableColumn<MovieView, ?> titleColumn; // Value injected by FXMLLoader

    @FXML // fx:id="titleField"
    private TextField titleField; // Value injected by FXMLLoader

    @FXML // fx:id="updateButton"
    private Button updateButton; // Value injected by FXMLLoader

    @FXML
    public void initialize() throws IOException {
        EventBus.getDefault().register(this); //TODO: add this to all controllers - please :)
        
        GetAllMoviesRequest getAllMoviesRequest = new GetAllMoviesRequest(SessionKeysStorage.getInstance().getSessionKey());
        SimpleClient.getClient().sendToServer(new Message(getAllMoviesRequest, MessageType.GET_ALL_MOVIES_REQUEST));

        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        showingDateColumn.setCellValueFactory(new PropertyValueFactory<>("showingDate"));
    }

    @Subscribe
    public void onGetAllMoviesEvent(GetAllMoviesEvent response){
        Platform.runLater(()->{
            List<Movie> movies = response.getMovies();
            moviesTable.getItems().clear();
            for (Movie movie : movies) {
                moviesTable.getItems().add(new MovieView(movie));
            }
        });
    }

    @FXML
    void Clear(ActionEvent event) {

    }

    @FXML
    void Delete(ActionEvent event) {

    }

    @FXML
    void Insert(ActionEvent event) {

    }

    @FXML
    void Update(ActionEvent event) {

    }

    @FXML
    void importImage(ActionEvent event) {

    }

}
