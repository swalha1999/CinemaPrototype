package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.Client;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.*;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.MovieGenre;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import static il.cshaifasweng.OCSFMediatorExample.client.utils.UiUtil.*;

public class MovieCatalog {

    @FXML
    private GridPane MoviesGrid;

    @FXML
    private TextField searchField;

    @FXML
    private Button allButton, actionButton, comedyButton, dramaButton, sciFiButton;

    int index = 0;

    private List<Movie> allMovies = new ArrayList<>();

    @FXML
    public void initialize() {
        EventBus.getDefault().register(this);

        // Add listener to search field
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterMoviesBySearch(newValue));

        // Add listeners to genre buttons
        allButton.setOnAction(event -> filterMoviesByGenre(MovieGenre.ALL));
        actionButton.setOnAction(event -> filterMoviesByGenre(MovieGenre.ACTION));
        comedyButton.setOnAction(event -> filterMoviesByGenre(MovieGenre.COMEDY));
        dramaButton.setOnAction(event -> filterMoviesByGenre(MovieGenre.DRAMA));
        sciFiButton.setOnAction(event -> filterMoviesByGenre(MovieGenre.SCI_FI));
    }

    public void addMovie(Movie movie) {
        if(!movie.isComingSoon()&&!movie.isOnlineMovie()){
            allMovies.add(movie);
            renderMovies(allMovies);
        }
    }

    public void removeMovie(int id) {
        allMovies.removeIf(movie -> movie.getId() == id);
        renderMovies(allMovies);
    }

    private void renderMovies(List<Movie> movies) {
        MoviesGrid.getChildren().clear();
        int index = 0;
        for (Movie movie : movies) {
            AnchorPane moviePane = createMoviePane(movie);
            int row = index / 2;
            int col = index % 2;
            MoviesGrid.add(moviePane, col, row);
            index++;
        }
    }

    private AnchorPane createMoviePane(Movie movie) {
        float scaleFac = 0.19f;
        float imageWidth = scaleFac * 878.0f;
        float imageHeight = scaleFac * 1390.0f;

        AnchorPane moviePane = new AnchorPane();
        moviePane.getStyleClass().add("movie-pane");
        moviePane.setPrefWidth(400);

        ImageView movieImage;
        if(movie.getImageBytes() == null){
            movieImage = new ImageView(getImage(movie.getImageUrl()));
        }else{
            movieImage = new ImageView(getImageFromBytes(movie.getImageBytes()));
        }

        movieImage.setFitHeight(imageHeight);
        movieImage.setFitWidth(imageWidth);
        movieImage.setLayoutX((moviePane.getPrefWidth() / 2) - (imageWidth / 2) + 5);
        movieImage.setLayoutY(10.0);

        Label movieTitle = new Label(movie.getTitle());
        movieTitle.getStyleClass().add("movie-title");
        movieTitle.setLayoutX((moviePane.getPrefWidth() / 2) - (getLabelWidth(movieTitle) / 2));
        movieTitle.setLayoutY(imageHeight + 20);

        moviePane.getChildren().addAll(movieImage, movieTitle);

        moviePane.setOnMouseClicked(event -> {
            showSideUI("MovieDetails", movie);
        });

        moviePane.setOnMouseEntered(event -> {
            moviePane.setCursor(Cursor.HAND);  // Change to hand cursor
        });

        moviePane.setOnMouseExited(event -> {
            moviePane.setCursor(Cursor.DEFAULT);  // Change back to default cursor
        });



        return moviePane;
    }

    private void filterMoviesBySearch(String query) {
        List<Movie> filteredMovies = new ArrayList<>();
        for (Movie movie : allMovies) {
            if (movie.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredMovies.add(movie);
            }
        }
        renderMovies(filteredMovies);
    }

    private void filterMoviesByGenre(MovieGenre genre) {
        List<Movie> filteredMovies = new ArrayList<>();
        for (Movie movie : allMovies) {
            if (genre.equals(MovieGenre.ALL) || movie.getGenre().equals(genre)) {
                filteredMovies.add(movie);
            }
        }
        renderMovies(filteredMovies);
    }

    @Subscribe
    public void onGetAllMoviesEvent(GetAllMoviesEvent event) {
        Platform.runLater(() -> {
            allMovies.clear();
            event.getMovies().forEach(this::addMovie);
        });
    }

    @Subscribe
    public void onAddMoviesEvent(AddMoviesEvent event) {
        Platform.runLater(() -> {
            addMovie(event.getMovie());
        });
    }

    @Subscribe
    public void onRemoveMoviesEvent(RemoveMovieEvent event) {
        Platform.runLater(() -> {
            removeMovie(event.getMovie().getId());
        });
    }

    @Subscribe
    public void onUpdateMoviesEvent(UpdateMovieEvent event) {
        Platform.runLater(() -> {
            for (Movie movie : allMovies) {
                if (movie.getId() == event.getMovie().getId()) {
                    movie.copy(event.getMovie());
                    break;
                }
            }
            renderMovies(allMovies);
        });
    }
}
