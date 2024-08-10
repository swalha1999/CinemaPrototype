package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.SimpleChatClient;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.AddMoviesEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.GetAllMoviesEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.RemoveMovieEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.ShowSideUIEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.MovieGenre;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static il.cshaifasweng.OCSFMediatorExample.client.utils.UiUtil.getLabelWidth;

public class MovieCatalog {

    @FXML
    private GridPane MoviesGrid;

    @FXML
    private TextField searchField;

    @FXML
    private Button allButton, actionButton, comedyButton, dramaButton, sciFiButton;

    private List<Movie> allMovies = new ArrayList<>();

    @FXML
    public void initialize() {
        EventBus.getDefault().register(this);

        // Send request to get all movies
        Message message = new Message(MessageType.GET_ALL_MOVIES_REQUEST)
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey());

        SimpleClient.getClient().sendToServer(message);

        // Add listener to search field
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterMoviesBySearch(newValue));

        // Add listeners to genre buttons
        allButton.setOnAction(event -> filterMoviesByGenre(MovieGenre.ALL));
        actionButton.setOnAction(event -> filterMoviesByGenre(MovieGenre.ACTION));
        comedyButton.setOnAction(event -> filterMoviesByGenre(MovieGenre.COMEDY));
        dramaButton.setOnAction(event -> filterMoviesByGenre(MovieGenre.DRAMA));
        sciFiButton.setOnAction(event -> filterMoviesByGenre(MovieGenre.SCI_FI));
    }

    public void addMovie(int id, String title, MovieGenre genre, String imagePath) {
        Movie movie = new Movie(id, title, genre, imagePath);
        allMovies.add(movie);
        renderMovies(allMovies);
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

        ImageView movieImage = new ImageView(new Image(Objects.requireNonNull(SimpleChatClient.class.getResourceAsStream(movie.getImagePath()))));
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
            System.out.println("Movie clicked: " + movie.getTitle());
            EventBus.getDefault().post(new ShowSideUIEvent("MovieDetails", movie.getId()));
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
            event.getMovies().forEach(movie -> addMovie(movie.getId() ,movie.getTitle(), movie.getGenre(), "images\\movie1.jpg"));
        });
    }

    @Subscribe
    public void onAddMoviesEvent(AddMoviesEvent event) {
        Platform.runLater(() -> {
            addMovie(event.getMovie().getId(), event.getMovie().getTitle(), event.getMovie().getGenre(), "images\\movie1.jpg");
        });
    }

    @Subscribe
    public void onRemoveMoviesEvent(RemoveMovieEvent event) {
        Platform.runLater(() -> {
            removeMovie(event.getMovie().getId());
        });
    }

    // Inner class to represent a Movie
    private static class Movie {
        private final int id;
        private final String title;
        private final MovieGenre genre ;
        private final String imagePath;

        public Movie(int id, String title, MovieGenre genre, String imagePath) {
            this.id = id;
            this.title = title;
            this.genre = genre;
            this.imagePath = imagePath;
        }

        public String getTitle() {
            return title;
        }

        public MovieGenre getGenre() {
            return genre == null ? MovieGenre.ALL : genre;

        }

        public String getImagePath() {
            return imagePath;
        }

        public int getId() {
            return id;
        }
    }
}
