package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.SimpleChatClient;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.Objects;

public class MovieCatalogController {

    @FXML
    private GridPane MoviesGrid;

    @FXML
    public void initialize() {
        // Example: Adding some movies on initialization
        for (int i = 0; i < 24; i++) {
            addMovie("Movie " + (i + 1), "images\\movie1.jpg");
        }


    }

    public void addMovie(String title, String imagePath) {
        AnchorPane moviePane = new AnchorPane();
        moviePane.getStyleClass().add("movie-pane");
        moviePane.setPrefWidth(300); // Set the desired width of the movie pane

        ImageView movieImage = new ImageView(new Image(Objects.requireNonNull(SimpleChatClient.class.getResourceAsStream(imagePath))));

        movieImage.setFitHeight(150.0);
        movieImage.setFitWidth(100.0);
        movieImage.setLayoutX(100.0);
        movieImage.setLayoutY(10.0);

        Label movieTitle = new Label(title);
        movieTitle.setLayoutX(100.0);
        movieTitle.setLayoutY(170.0);
        movieTitle.getStyleClass().add("movie-title");

        moviePane.getChildren().addAll(movieImage, movieTitle);

        int index = MoviesGrid.getChildren().size();
        int row = index / 3;
        int col = index % 3;

        MoviesGrid.add(moviePane, col, row);
    }
}
