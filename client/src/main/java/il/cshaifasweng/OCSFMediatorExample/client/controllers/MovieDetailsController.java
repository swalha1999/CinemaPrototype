package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.SimpleChatClient;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.Objects;

public class MovieDetailsController {

    @FXML
    private ImageView movieImageView;

    @FXML
    private Label titleLabel;

    @FXML
    private Label genreLabel;

    @FXML
    private Label durationLabel;

    @FXML
    private Label releaseDateLabel;

    @FXML
    private Label ratingLabel;

    @FXML
    private ListView<String> screeningTimesListView;

    public void setMovieDetails(String title, String genre, String duration, String releaseDate, String rating, String imagePath, List<String> screeningTimes) {
        titleLabel.setText(title);
        genreLabel.setText("Genre: " + genre);
        durationLabel.setText("Duration: " + duration);
        releaseDateLabel.setText("Release Date: " + releaseDate);
        ratingLabel.setText("Rating: " + rating);

        movieImageView.setImage(new Image(Objects.requireNonNull(SimpleChatClient.class.getResourceAsStream(imagePath))));

        screeningTimesListView.getItems().clear();
        screeningTimesListView.getItems().addAll(screeningTimes);
    }
}
