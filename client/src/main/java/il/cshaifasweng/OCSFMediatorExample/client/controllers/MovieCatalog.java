package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.SimpleChatClient;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import java.util.Objects;

import static il.cshaifasweng.OCSFMediatorExample.client.utils.UiUtil.getLabelWidth;

public class MovieCatalog {

    @FXML
    private GridPane MoviesGrid;

    @FXML
    public void initialize() {
        // Example: Adding some movies on initialization
        for (int i = 0; i < 12; i++) {
            addMovie("Movie " + (i + 1), "images\\movie1.jpg");
        }
    }

    public void addMovie(String title, String imagePath) {
        float scaleFac = 0.19f;
        float imageWidth = scaleFac * 878.0f;
        float imageHeight = scaleFac * 1390.0f;

        AnchorPane moviePane = new AnchorPane();
        moviePane.getStyleClass().add("movie-pane");
        moviePane.setPrefWidth(imageWidth + 25);

        ImageView movieImage = new ImageView(new Image(Objects.requireNonNull(SimpleChatClient.class.getResourceAsStream(imagePath))));
        movieImage.setFitHeight(imageHeight);
        movieImage.setFitWidth(imageWidth);
        movieImage.setLayoutX( (moviePane.getPrefWidth() / 2) - (imageWidth / 2) + 5);
        movieImage.setLayoutY(10.0);

        Label movieTitle = new Label(title);
        movieTitle.getStyleClass().add("movie-title");
        movieTitle.setLayoutX( (moviePane.getPrefWidth() / 2) - (getLabelWidth(movieTitle) / 2));
        movieTitle.setLayoutY(imageHeight + 20);
        moviePane.getChildren().addAll(movieImage, movieTitle);

        moviePane.setOnMouseClicked(event -> {
            System.out.println("Movie clicked: " + title);
        });

        int index = MoviesGrid.getChildren().size();
        int row = index / 4;
        int col = index % 4;

        MoviesGrid.add(moviePane, col, row);
    }
}
