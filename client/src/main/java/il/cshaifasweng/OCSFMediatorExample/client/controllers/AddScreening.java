

package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.events.GetAllMoviesEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Movie;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Date;

import static il.cshaifasweng.OCSFMediatorExample.client.utils.UiUtil.showSideUI;

public class AddScreening {

    @FXML // fx:id="movieDate"
    private DatePicker movieDate; // Value injected by FXMLLoader

    @FXML // fx:id="movieNameCombobox"
    private ComboBox<Movie> movieNameCombobox; // Value injected by FXMLLoader


    void initialize() {
        EventBus.getDefault().register(this);

        movieNameCombobox.getItems().add(new Movie("Hello", new Date()));
    }

    @FXML
    void handleBack(ActionEvent event) {
        Platform.runLater(() -> {
            showSideUI("CinemaInfo");
        });
    }

    @FXML
    void handleConfirm(ActionEvent event) {

    }

    @Subscribe
    public void onGetAllMovies(GetAllMoviesEvent event) {
        Platform.runLater(() -> {
            for (Movie movie : event.getMovies()) {
                movieNameCombobox.getItems().add(movie);
            }
        });
    }

}
