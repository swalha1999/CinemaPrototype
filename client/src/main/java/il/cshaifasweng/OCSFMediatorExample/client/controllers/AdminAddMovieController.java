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
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static il.cshaifasweng.OCSFMediatorExample.client.utils.UiUtil.getImage;
import static il.cshaifasweng.OCSFMediatorExample.client.utils.UiUtil.getImageFromBytes;

public class AdminAddMovieController {

    @FXML
    private Button clearButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TableColumn<MovieView, ?> descriptionColumn;
    @FXML
    private TextField descriptionField;
    @FXML
    private TableColumn<MovieView, ?> genreColumn;
    @FXML
    private ComboBox<MovieGenre> genreComboBox;
    @FXML
    private Button importButton;
    @FXML
    private Button insertButton;
    @FXML
    private ImageView movieImageView;
    @FXML
    private TableView<MovieView> moviesTable;
    @FXML
    private TextField producerField;
    @FXML
    private TableColumn<MovieView, ?> titleEnglishColumn;
    @FXML
    private TextField titleEnglishField;
    @FXML
    private TableColumn<MovieView, ?> titleHebrewColumn;
    @FXML
    private TextField titleHebrewField;

    private Movie movieToSend =new Movie();

    @FXML
    public void initialize() throws IOException {
        EventBus.getDefault().register(this); //TODO: add this to all controllers - please :)

        Message message = new Message(MessageType.GET_ALL_MOVIES_REQUEST)
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey());

        Client.getClient().sendToServer(message);

        for (MovieGenre genre : MovieGenre.values()) {
            genreComboBox.getItems().add(genre);
        }

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
                    if (moviesTable.getSelectionModel().getSelectedItem().getImageUrl() != null && !moviesTable.getSelectionModel().getSelectedItem().getImageUrl().isEmpty()) {
                        updateImageView(moviesTable.getSelectionModel().getSelectedItem().getImageUrl());
                    }else if(moviesTable.getSelectionModel().getSelectedItem().getMovie().getImageBytes() != null){
                        movieImageView.setImage(getImageFromBytes(moviesTable.getSelectionModel().getSelectedItem().getMovie().getImageBytes()));
                    }else{
                        updateImageView(null);
                    }

                });
            }
        });
    }

    @FXML
    void Clear(ActionEvent event){
        titleEnglishField.setText("");
        titleHebrewField.setText("");
        genreComboBox.getSelectionModel().clearSelection();
        descriptionField.setText("");
        producerField.setText("");
    }

    @FXML
    void Delete(ActionEvent event) {
        Movie movieToRemove = moviesTable.getSelectionModel().getSelectedItem().getMovie();

        Message removeMovieRequest = new Message(MessageType.REMOVE_MOVIE_REQUEST)
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey())
                .setDataObject(movieToRemove);

        Client.getClient().sendToServer(removeMovieRequest);
    }

    @FXML
    void Insert(ActionEvent event) throws IOException {
        movieToSend
                .setEnglishTitle(titleEnglishField.getText())
                .setHebrewTitle(titleHebrewField.getText())
                .setGenre(genreComboBox.getSelectionModel().getSelectedItem())
                .setDescription(descriptionField.getText());

        Message addMovieRequest = new Message(MessageType.ADD_MOVIE_REQUEST)
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey())
                .setDataObject(movieToSend);

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
                    .setImageBytes(movieToSend.getImageBytes())
                    .setProducer(producerField.getText());


            Message UpdateMovieRequest = new Message(MessageType.UPDATE_MOVIE_REQUEST)
                    .setSessionKey(SessionKeysStorage.getInstance().getSessionKey())
                    .setDataObject(movieToUpdate);

            Client.getClient().sendToServer(UpdateMovieRequest);
        }
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
        movieImageView.setImage(getImage(event));
    }

    public void importImage(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        File selectedFile = fileChooser.showOpenDialog(CinemaMain.cinemaStage);

        if (selectedFile != null) {
            System.out.println("File selected: " + selectedFile.getAbsolutePath());
            byte[] imageBytes = Files.readAllBytes(Paths.get(selectedFile.getAbsolutePath()));
            movieImageView.setImage(getImageFromBytes(imageBytes));
            movieToSend.setImageBytes(imageBytes);

        } else {
            System.out.println("File selection cancelled.");
        }
    }
}
