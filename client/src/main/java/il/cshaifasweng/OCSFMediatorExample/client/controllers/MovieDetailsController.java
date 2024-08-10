package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.GetMovieEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.ShowSideUIEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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

    @FXML
    public void initialize() {
        EventBus.getDefault().register(this); //TODO: add this to all controllers - please :)
    }

    @Subscribe
    public void setMovieDetails(ShowSideUIEvent event) {

        Movie movie = new Movie().setId(event.getMovieId());

        Message getMovieRequest = new Message(MessageType.GET_MOVIE_REQUEST)
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey())
                .setDataObject(movie);

        SimpleClient.getClient().sendToServer(getMovieRequest);
    }

    @Subscribe
    public void setMovieDetails(GetMovieEvent event) {
        Platform.runLater(() -> {
            titleLabel.setText(event.getMovie().getTitle());
            genreLabel.setText(event.getMovie().getGenre().toString());
            releaseDateLabel.setText(event.getMovie().getReleaseDate().toString());
            durationLabel.setText(event.getMovie().getDurationInMinutes() + " minutes");
            // TODO :    fix :)
            ratingLabel.setText(event.getMovie().getId() + "/10");

        });
    }

    //TODO: request the screening times from the server
    //TODO: display the screening times in the ListView

}
