package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.SimpleChatClient;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.GetAllMoviesEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.requests.GetAllMoviesRequest;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Objects;

import static il.cshaifasweng.OCSFMediatorExample.client.utils.UiUtil.getLabelWidth;

public class MovieCatalog {

    @FXML
    private GridPane MoviesGrid;

    @FXML
    public void initialize() {
        EventBus.getDefault().register(this); //TODO: add this to all controllers - please :)

        GetAllMoviesRequest getAllMoviesRequest = new GetAllMoviesRequest()
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey())
                .setUsername(SessionKeysStorage.getInstance().getUsername());
        try {
            SimpleClient.getClient().sendToServer(new Message(getAllMoviesRequest, MessageType.GET_ALL_MOVIES_REQUEST));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addMovie(String title, String imagePath) {
        float scaleFac = 0.19f;
        float imageWidth = scaleFac * 878.0f;
        float imageHeight = scaleFac * 1390.0f;

        AnchorPane moviePane = new AnchorPane();
        moviePane.getStyleClass().add("movie-pane");
        moviePane.setPrefWidth(400);

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
        int row = index / 2;
        int col = index % 2;

        MoviesGrid.add(moviePane, col, row);
    }

    @Subscribe
    public void onGetAllMoviesEvent(GetAllMoviesEvent event) {
        Platform.runLater(() -> {
            MoviesGrid.getChildren().clear();
            event.getMovies().forEach(movie -> addMovie(movie.getTitle(), "images\\movie1.jpg"));
        });
    }

}
