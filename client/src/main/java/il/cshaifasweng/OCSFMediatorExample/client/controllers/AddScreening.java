

package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.Client;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.*;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Hall;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Screening;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import static il.cshaifasweng.OCSFMediatorExample.client.utils.UiUtil.showSideUI;

public class AddScreening {

    @FXML // fx:id="movieDate"
    private DatePicker movieDate; // Value injected by FXMLLoader

    @FXML // fx:id="priceField"
    private TextField priceField; // Value injected by FXMLLoader

    @FXML // fx:id="movieNameCombobox"
    private ComboBox<Movie> movieNameCombobox; // Value injected by FXMLLoader

    private List<Movie> movies;
    private Hall hall;

    @FXML
    private TextField HourField;

    @FXML
    private TextField MinuteField;

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
            };
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
        int hour = Integer.parseInt(HourField.getText());
        int minute = Integer.parseInt(MinuteField.getText());
        Screening screeningToAdd = new Screening();
        screeningToAdd.setPrice(Integer.parseInt(priceField.getText()));
        screeningToAdd.setStartingAt(movieDate.getValue().atTime(LocalTime.of(hour,minute,0)));
        screeningToAdd.setMovie(movieNameCombobox.getValue());
        screeningToAdd.setHall(hall);

        Message message = new Message(MessageType.ADD_SCREENING_REQUEST)
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey())
                .setDataObject(screeningToAdd);

        Client.getClient().sendToServer(message);

        Platform.runLater(() -> {
            showSideUI("CinemaInfo");
        });
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

    @Subscribe
    public void onAddMoviesEvent(AddMoviesEvent event) {
        Platform.runLater(() -> {
            movieNameCombobox.getItems().add(event.getMovie());
        });
    }

    @Subscribe
    public void onRemoveMoviesEvent(RemoveMovieEvent event) {
        Platform.runLater(() -> {
            movieNameCombobox.getItems().removeIf(movie -> movie.getId() == event.getMovie().getId());
        });
    }

    @Subscribe
    public void onUpdateMoviesEvent(UpdateMovieEvent event) {
        Platform.runLater(() -> {
            movieNameCombobox.getItems().removeIf(movie -> movie.getId() == event.getMovie().getId());
            movieNameCombobox.getItems().add(event.getMovie());
        });
    }

    @Subscribe
    public void onUIShow(ShowSideUIEvent event) {
        if(!event.getUIName().equals("AddScreening")){
            return;
        }
        this.hall = (Hall) event.getFirstObj();
    }
}


