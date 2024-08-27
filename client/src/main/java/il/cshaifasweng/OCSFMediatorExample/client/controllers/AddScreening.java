

package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.events.GetAllMoviesEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Movie;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Date;
import java.util.List;

import static il.cshaifasweng.OCSFMediatorExample.client.utils.UiUtil.showSideUI;

public class AddScreening {

    @FXML // fx:id="movieDate"
    private DatePicker movieDate; // Value injected by FXMLLoader

    @FXML // fx:id="movieNameCombobox"
    private ComboBox<Movie> movieNameCombobox; // Value injected by FXMLLoader

    private List<Movie> movies;

    Callback<ListView<Movie>, ListCell<Movie>> cellFactory = new Callback<ListView<Movie>, ListCell<Movie>>() {

        @Override
        public ListCell<Movie> call(ListView<Movie> l) {
            return new ListCell<Movie>() {

                @Override
                protected void updateItem(Movie item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setGraphic(null);
                    } else {
                        setText(item.getId() + "    " + item.getName());
                    }
                }
            } ;
        }
    };

    @FXML
    void initialize() {
        EventBus.getDefault().register(this);
        movieNameCombobox.setButtonCell(cellFactory.call(null));
        movieNameCombobox.setCellFactory(cellFactory);

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
        System.out.println("Got all movies from add");
        Platform.runLater(() -> {
            for (Movie movie : event.getMovies()) {

                movieNameCombobox.getItems().add(movie);
            }
        });
    }

}
