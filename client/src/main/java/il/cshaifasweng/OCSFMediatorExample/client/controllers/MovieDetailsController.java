package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.Client;
import il.cshaifasweng.OCSFMediatorExample.client.data.ScreeningView;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.GetScreeningForMovieEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.ShowSideUIEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Screening;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import static il.cshaifasweng.OCSFMediatorExample.client.utils.UiUtil.getImage;
import static il.cshaifasweng.OCSFMediatorExample.client.utils.UiUtil.showSideUI;

public class MovieDetailsController {

    @FXML // fx:id="AvailableSeat_Col"
    private TableColumn<ScreeningView, ?> AvailableSeat_Col; // Value injected by FXMLLoader

    @FXML // fx:id="Cinema_Col"
    private TableColumn<ScreeningView, ?> Cinema_Col; // Value injected by FXMLLoader

    @FXML // fx:id="Hall_Col"
    private TableColumn<ScreeningView, ?> Hall_Col; // Value injected by FXMLLoader

    @FXML // fx:id="Price_Col"
    private TableColumn<ScreeningView, ?> Price_Col; // Value injected by FXMLLoader

    @FXML // fx:id="Screening_Col"
    private TableColumn<ScreeningView, ?> Screening_Col; // Value injected by FXMLLoader

    @FXML // fx:id="durationLabel"
    private Label durationLabel; // Value injected by FXMLLoader

    @FXML // fx:id="genreLabel"
    private Label genreLabel; // Value injected by FXMLLoader

    @FXML // fx:id="movieImageView"
    private ImageView movieImageView; // Value injected by FXMLLoader

    @FXML // fx:id="ratingLabel"
    private Label ratingLabel; // Value injected by FXMLLoader

    @FXML // fx:id="releaseDateLabel"
    private Label releaseDateLabel; // Value injected by FXMLLoader

    @FXML // fx:id="returnBtn"
    private Button returnBtn; // Value injected by FXMLLoader

    @FXML // fx:id="screeningTable"
    private TableView<ScreeningView> screeningTable; // Value injected by FXMLLoader

    @FXML // fx:id="titleLabel"
    private Label titleLabel; // Value injected by FXMLLoader

    private Movie movieToDisplay;

    @FXML
    public void initialize() {
        EventBus.getDefault().register(this); //TODO: add this to all controllers - please :)

        Screening_Col.setCellValueFactory(new PropertyValueFactory<>("screeningDate"));
        Cinema_Col.setCellValueFactory(new PropertyValueFactory<>("cinema"));
        Hall_Col.setCellValueFactory(new PropertyValueFactory<>("hall"));
        //Price_Col.setCellValueFactory(new PropertyValueFactory<>("price"));

        screeningTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Platform.runLater(()->{
                    showSideUI("SeatPicker");
                });
            }
        });
    }


    //TODO change the way we display the image in the movie details page

    @Subscribe
    public void getMovieDetails(ShowSideUIEvent event) {
        // this line is to make sure that the event is for this controller
        if (!event.getUIName().equals("MovieDetails")) {
            return;
        }

        Movie movie = (Movie) event.getDataForPage();

        Message message = new Message( MessageType.GET_SCREENING_FOR_MOVIE_REQUEST)
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey())
                .setDataObject(movie);

        Client.getClient().sendToServer(message);

        Platform.runLater(() -> {
            titleLabel.setText(movie.getTitle());
            genreLabel.setText(movie.getGenre().toString());
            releaseDateLabel.setText(movie.getReleaseDate().toString());
            durationLabel.setText(movie.getDurationInMinutes() + " minutes");
            // TODO :    fix :)
            ratingLabel.setText(movie.getId() + "/10");
            movieImageView.setImage(getImage(movie.getImageUrl()));
        });

    }

    @Subscribe
    public void onGetScreeningsForMovieEvent(GetScreeningForMovieEvent event){
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

    //TODO: request the screening times from the server -=DONE=-
    //TODO: display the screening times in the ListView -=DONE=-

}
