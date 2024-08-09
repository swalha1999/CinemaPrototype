/**
 * Sample Skeleton for 'AdminAddMovie.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.client.data.MovieView;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.data.UserView;
import il.cshaifasweng.OCSFMediatorExample.client.events.AddMoviesEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.GetAllMoviesEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.RemoveUserEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.MovieGenre;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.requests.AddMovieRequest;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.requests.GetAllMoviesRequest;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

    @FXML // fx:id="descriptionColumn"
    private TableColumn<MovieView, ?> descriptionColumn; // Value injected by FXMLLoader

    @FXML // fx:id="descriptionField"
    private TextField descriptionField; // Value injected by FXMLLoader

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

    @FXML // fx:id="producerField"
    private TextField producerField; // Value injected by FXMLLoader

    @FXML // fx:id="titleEnglishColumn"
    private TableColumn<MovieView, ?> titleEnglishColumn; // Value injected by FXMLLoader

    @FXML // fx:id="titleEnglishField"
    private TextField titleEnglishField; // Value injected by FXMLLoader

    @FXML // fx:id="titleHebrewColumn"
    private TableColumn<MovieView, ?> titleHebrewColumn; // Value injected by FXMLLoader

    @FXML // fx:id="titleHebrewField"
    private TextField titleHebrewField; // Value injected by FXMLLoader

    @FXML // fx:id="updateButton"
    private Button updateButton; // Value injected by FXMLLoader

    @FXML
    public void initialize() throws IOException {
        EventBus.getDefault().register(this); //TODO: add this to all controllers - please :)

        GetAllMoviesRequest getAllMoviesRequest = new GetAllMoviesRequest(SessionKeysStorage.getInstance().getSessionKey());
        SimpleClient.getClient().sendToServer(new Message(getAllMoviesRequest, MessageType.GET_ALL_MOVIES_REQUEST));

        titleEnglishColumn.setCellValueFactory(new PropertyValueFactory<>("englishTitle"));
        titleHebrewColumn.setCellValueFactory(new PropertyValueFactory<>("hebrewTitle"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        moviesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Platform.runLater(()->{
                    titleEnglishField.setText("i am the problem number 1");
                    titleHebrewField.setText("i am the problem number 2");
                    genreField.setText(moviesTable.getSelectionModel().getSelectedItem().getEnglishTitle());
                    descriptionField.setText(moviesTable.getSelectionModel().getSelectedItem().getGenre());
                });
            }
        });
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
    void Insert(ActionEvent event) throws IOException {
        AddMovieRequest addMovieRequest = new AddMovieRequest()
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey())
                .setEnglishTitle(titleEnglishField.toString())
                .setHebrewTitle(titleHebrewColumn.toString())
                .setGenre(MovieGenre.ALL)
                .setDescription(descriptionField.toString());

        SimpleClient.getClient().sendToServer(new Message(addMovieRequest, MessageType.ADD_MOVIE_REQUEST));
    }

    @Subscribe
    public void onAddMovieEvent(AddMoviesEvent event){
        Platform.runLater(()->{
            moviesTable.getItems().add(new MovieView(event.getMovie()));
        });
    }

    @FXML
    void Update(ActionEvent event) {
        MovieView selectedMovie = moviesTable.getSelectionModel().getSelectedItem();
        if (selectedMovie == null) {
            return;
        }

    }

    @FXML
    void importImage(ActionEvent event) {

    }

}
