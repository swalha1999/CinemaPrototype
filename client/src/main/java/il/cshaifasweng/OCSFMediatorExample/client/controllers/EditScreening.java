package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.Client;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.GetAllMoviesEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.ShowSideUIEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Hall;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Screening;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.script.ScriptEngine;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import static il.cshaifasweng.OCSFMediatorExample.client.utils.UiUtil.showSideUI;

public class EditScreening {

    @FXML // fx:id="movieDate"
    private DatePicker movieDate; // Value injected by FXMLLoader

    @FXML // fx:id="priceField"
    private TextField priceField; // Value injected by FXMLLoader

    @FXML // fx:id="movieNameCombobox"
    private ComboBox<Movie> movieNameCombobox; // Value injected by FXMLLoader

    private Screening screening;

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
        screening.setPrice(Integer.parseInt(priceField.getText()));
        screening.setStartingAt(movieDate.getValue().atTime(LocalTime.of(12, 0)));
        screening.setMovie(movieNameCombobox.getValue());

        Message message = new Message(MessageType.UPDATE_SCREENING_RESPONSE)
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey())
                .setDataObject(screening);

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
    public void onSideUiChange (ShowSideUIEvent event){
        System.out.println("i GOT HERE!!!!");
        if(!Objects.equals(event.getUIName(), "EditScreening")){
            return;
        }
        screening = (Screening) event.getFirstObj();

        priceField.setText(Integer.toString(screening.getPrice()));

    }
}
