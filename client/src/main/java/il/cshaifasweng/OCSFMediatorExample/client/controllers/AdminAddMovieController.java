/**
 * Sample Skeleton for 'AdminAddMovie.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.CinemaMain;
import il.cshaifasweng.OCSFMediatorExample.client.Client;
import il.cshaifasweng.OCSFMediatorExample.client.data.MovieView;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.AddMoviesEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.GetAllMoviesEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.RemoveMovieEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.UpdateMovieEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.MovieGenre;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
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

    @FXML // fx:id="genreComboBox"
    private ComboBox<MovieGenre> genreComboBox; // Value injected by FXMLLoader

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

    @FXML // fx:id="imageUrlField"
    private TextField imageUrlField; // Value injected by FXMLLoader

    @FXML
    public void initialize() throws IOException {
        EventBus.getDefault().register(this); //TODO: add this to all controllers - please :)

        Message message = new Message(MessageType.GET_ALL_MOVIES_REQUEST)
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey());

        Client.getClient().sendToServer(message);

        for (MovieGenre genre : MovieGenre.values()) {
            genreComboBox.getItems().add(genre);
        }

        imageUrlField.textProperty().addListener((observable, oldValue, newValue) -> updateImageView(newValue));

        titleEnglishColumn.setCellValueFactory(new PropertyValueFactory<>("englishTitle"));
        titleHebrewColumn.setCellValueFactory(new PropertyValueFactory<>("hebrewTitle"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        moviesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Platform.runLater(()->{
                    titleEnglishField.setText(moviesTable.getSelectionModel().getSelectedItem().getEnglishTitle());
                    titleHebrewField.setText(moviesTable.getSelectionModel().getSelectedItem().getHebrewTitle());
                    genreComboBox.getSelectionModel().select(moviesTable.getSelectionModel().getSelectedItem().getGenre());
                    descriptionField.setText(moviesTable.getSelectionModel().getSelectedItem().getDescription());
                    imageUrlField.setText(moviesTable.getSelectionModel().getSelectedItem().getImageUrl());
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
        imageUrlField.setText("");
        titleEnglishField.setText("");
        titleHebrewField.setText("");
        genreComboBox.getSelectionModel().clearSelection();
        descriptionField.setText("");
        producerField.setText("");
    }

    @FXML
    void Delete(ActionEvent event) {

        Movie movieToRemove = new Movie().setId(moviesTable.getSelectionModel().getSelectedItem().getId());

        Message removeMovieRequest = new Message(MessageType.REMOVE_MOVIE_REQUEST)
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey())
                .setDataObject(movieToRemove);

        Client.getClient().sendToServer(removeMovieRequest);
    }

    @FXML
    void Insert(ActionEvent event) throws IOException {
        Movie movieToAdd = new Movie()
                .setEnglishTitle(titleEnglishField.getText())
                .setHebrewTitle(titleHebrewField.getText())
                .setGenre(genreComboBox.getSelectionModel().getSelectedItem())
                .setDescription(descriptionField.getText())
                .setImageUrl(imageUrlField.getText());

        Message addMovieRequest = new Message(MessageType.ADD_MOVIE_REQUEST)
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey())
                .setDataObject(movieToAdd);

        Client.getClient().sendToServer(addMovieRequest);

    }

    @FXML
    void Update(ActionEvent event) {
        MovieView selectedMovie = moviesTable.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            Movie movieToUpdate = new Movie().setId(moviesTable.getSelectionModel().getSelectedItem().getId())
                    .setEnglishTitle(titleEnglishField.getText())
                    .setHebrewTitle(titleHebrewField.getText())
                    .setGenre(genreComboBox.getSelectionModel().getSelectedItem())
                    .setDescription(descriptionField.getText())
                    .setProducer(producerField.getText())
                    .setImageUrl(imageUrlField.getText());

            Message UpdateMovieRequest = new Message(MessageType.UPDATE_MOVIE_REQUEST)
                    .setSessionKey(SessionKeysStorage.getInstance().getSessionKey())
                    .setDataObject(movieToUpdate);

            Client.getClient().sendToServer(UpdateMovieRequest);
        }
    }

    @FXML
    void importImage(ActionEvent event) {

    }

    @Subscribe
    public void onAddMovieEvent(AddMoviesEvent event){
        Platform.runLater(()->{
            moviesTable.getItems().add(new MovieView(event.getMovie()));
        });
    }

    @Subscribe
    public void onRemoveMovieEvent(RemoveMovieEvent event){
        Platform.runLater(()->{
            for (MovieView movieView : moviesTable.getItems()) {
                if(movieView.getId() == event.getMovie().getId()){
                    moviesTable.getItems().remove(movieView);
                    break;
                }
            }
        });
    }

    @Subscribe
    public void onUpdateMovieEvent(UpdateMovieEvent event){
        Platform.runLater(()->{
            for (MovieView movieView : moviesTable.getItems()) {
                if(movieView.getId() == event.getMovie().getId()){
                    movieView.copy(event.getMovie());
                }
            }
            moviesTable.refresh();
        });
    }

    public void updateImageView(String event) {
        System.out.println("Changed movie view");

        movieImageView.setImage(new Image(CinemaMain.class.getResourceAsStream(event)));
    }
}
