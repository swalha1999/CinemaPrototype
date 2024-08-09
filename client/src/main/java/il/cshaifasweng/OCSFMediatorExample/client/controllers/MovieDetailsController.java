package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.SimpleChatClient;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.GetMovieEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.ShowMovieDetailsEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.requests.GetMovieRequest;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.greenrobot.eventbus.Subscribe;

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

    @Subscribe
    public void setMovieDetails(ShowMovieDetailsEvent event) {
        GetMovieRequest getMovieRequest = new GetMovieRequest()
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey())
                .setUsername(SessionKeysStorage.getInstance().getUsername())
                .setMovieId(event.getMovieId());

        try {
            SimpleClient.getClient().sendToServer(new Message(getMovieRequest, MessageType.GET_MOVIE_REQUEST));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void setMovieDetails(GetMovieEvent event) {
        titleLabel.setText(event.getMovie().getTitle());
        genreLabel.setText(event.getMovie().getGenre().toString());
//        durationLabel.setText(event.getMovie().getDuration() + " minutes");
        releaseDateLabel.setText(event.getMovie().getReleaseDate().toString());
//        ratingLabel.setText(event.getMovie().getRating() + "/10");


    }

    //TODO: request the screening times from the server
    //TODO: display the screening times in the ListView

}
