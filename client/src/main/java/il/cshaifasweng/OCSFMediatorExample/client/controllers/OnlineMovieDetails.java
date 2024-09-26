/**
 * Sample Skeleton for 'OnlineMovieDetails.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.Client;
import il.cshaifasweng.OCSFMediatorExample.client.data.ScreeningView;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.GetOnlineScreeningForMovieEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.GetScreeningForMovieEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.ShowSideUIEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Screening;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import static il.cshaifasweng.OCSFMediatorExample.client.utils.UiUtil.*;

public class OnlineMovieDetails {

    @FXML private TableColumn<ScreeningView, ?> AvailableSeat_Col;
    @FXML private TableColumn<ScreeningView, ?> Cinema_Col;
    @FXML private TableColumn<ScreeningView, ?> Hall_Col;
    @FXML private TableColumn<ScreeningView, ?> Price_Col;
    @FXML private TableColumn<ScreeningView, ?> Screening_Col;
    @FXML private Label actorsLabel;
    @FXML private Label durationLabel;
    @FXML private Label genreLabel;
    @FXML private ImageView movieImageView;
    @FXML private Label producerLabel;
    @FXML private Label releaseDateLabel;
    @FXML private Button returnBtn;
    @FXML private Label summaryLabel;
    @FXML private Label titleLabel;
    @FXML private TableView<ScreeningView> screeningTable;

    private Movie movieToDisplay;

    @FXML
    public void initialize() {
        EventBus.getDefault().register(this); //TODO: add this to all controllers - please :)

        Screening_Col.setCellValueFactory(new PropertyValueFactory<>("screeningDate"));
        Cinema_Col.setCellValueFactory(new PropertyValueFactory<>("cinema"));
        Hall_Col.setCellValueFactory(new PropertyValueFactory<>("hall"));
        Price_Col.setCellValueFactory(new PropertyValueFactory<>("price"));
        screeningTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Platform.runLater(()->{
                    showSideUI("SeatPicker",movieToDisplay, screeningTable.getSelectionModel().getSelectedItem().getScreening());
                });
            }
        });
    }

    //TODO add the movie actors and the producer to the gui
    //TODO change the way we display the image in the movie details page

    @Subscribe
    public void getMovieDetails(ShowSideUIEvent event) {
        // this line is to make sure that the event is for this controller only and if we expect data from the event
        if (!event.getUIName().equals("MovieDetails") || event.getFirstObj() == null) {
            return;
        }
        showNotification("We are in movie Details", false);

        Movie movie = (Movie) event.getFirstObj();
        movieToDisplay = movie;
        if (movie == null) {
            showNotification("ERROR: we expected to get data with the UI change with the Type Movie", false);
            return;
        }
        showNotification("We are in movie Details", false);
        Message message = new Message( MessageType.GET_ONLINE_SCREENING_FOR_MOVIE_REQUEST)
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey())
                .setDataObject(movie);

        Client.getClient().sendToServer(message);

        Platform.runLater(() -> {
            titleLabel.setText("Title: "+movie.getTitle());
            genreLabel.setText("Genre: "+movie.getGenre().toString());
            releaseDateLabel.setText("Release Date: " + (movie.getReleaseDate() == null ? "N/A" : movie.getReleaseDate().toString()));
            durationLabel.setText("Duration: " + movie.getDurationInMinutes() + " minutes");
            if (movie.getImageBytes() != null) {
                movieImageView.setImage(getImageFromBytes(movie.getImageBytes()));
            }
            summaryLabel.setText("Summary: "+movie.getDescription());
            producerLabel.setText("Producer: "+movie.getProducer());
            actorsLabel.setText("Actors: "+ movie.getActors());
        });
    }

    @Subscribe
    public void onGetOnlineScreeningForMovieEvent(GetOnlineScreeningForMovieEvent event){
        Platform.runLater(() -> {
            screeningTable.getItems().clear();
            List<Screening> screenings = event.getScreenings();
            for (Screening screening : screenings) {
                screeningTable.getItems().add(new ScreeningView(screening));
            }
        });
    }

    public void returnToMovieCatalog(ActionEvent actionEvent) {
        showSideUI("MovieCatalog");
    }

}


