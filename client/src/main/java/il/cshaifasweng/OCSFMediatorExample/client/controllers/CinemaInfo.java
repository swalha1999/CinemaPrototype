/**
 * Sample Skeleton for 'CinemaInfo.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.Client;
import il.cshaifasweng.OCSFMediatorExample.client.data.CinemaView;
import il.cshaifasweng.OCSFMediatorExample.client.data.HallView;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.AddCinemaEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.GetAllCinemasEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.RemoveCinemaEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Cinema;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Hall;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;

import static il.cshaifasweng.OCSFMediatorExample.client.CinemaMain.setRoot;
import static il.cshaifasweng.OCSFMediatorExample.client.utils.UiUtil.showSideUI;

public class CinemaInfo {

    @FXML
    private TableView<CinemaView> cinemaTable; // Value injected by FXMLLoader

    @FXML
    private TableColumn<CinemaView, String> cityColumn; // Value injected by FXMLLoader

    @FXML
    private TableColumn<CinemaView, String> managerColumn; // Value injected by FXMLLoader

    @FXML
    private TableView<HallView> hallTable; // Value injected by FXMLLoader

    @FXML
    private TableColumn<HallView, String> hallName_Col; // Value injected by FXMLLoader

    @FXML
    private TableColumn<HallView, Integer> seatsColumn; // Value injected by FXMLLoader

    @FXML
    private TableView<?> ScreeningTable;

    @FXML
    private TableColumn<?, ?> MovieName_Col;

    @FXML
    private TableColumn<?, ?> Start_Col;

    @FXML
    private TableColumn<?, ?> End_Col;

    @FXML
    private TableColumn<?, ?> ScreeningName_Col;

    @FXML
    private Button AddCinemaBtn; // Value injected by FXMLLoader

    @FXML
    private Button BackBtn; // Value injected by FXMLLoader

    @FXML
    private Button EditCinemaBtn; // Value injected by FXMLLoader

    @FXML
    private Button HomeBtn; // Value injected by FXMLLoader

    @FXML
    private Button AddCinemaBtn1;

    @FXML
    private Button AddHallBtn;

    @FXML
    private Button AddScreeningBtn;

    @FXML
    private Button EditHallBtn;

    @FXML
    private Button EditScreeningBtn;

    @FXML
    private Button RemoveCienmaBtn;

    @FXML
    private Button RemoveHallBtn;

    @FXML
    private Button RemoveScreeningBtn;

    @FXML
    public void initialize() throws IOException {
        EventBus.getDefault().register(this); //TODO: add this to all controllers - please :)

        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        managerColumn.setCellValueFactory(new PropertyValueFactory<>("managerName"));

        hallName_Col.setCellValueFactory(new PropertyValueFactory<>("name"));
        seatsColumn.setCellValueFactory(new PropertyValueFactory<>("seats"));

        cinemaTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection == null) {
                return;
            }
            hallTable.getItems().clear();
            for (Hall hall : newSelection.getCinema().getHalls()) {
                hallTable.getItems().add(new HallView(hall));
            }
        });
    }

    @FXML
    void addCinema(ActionEvent event) {
        Platform.runLater(() -> {
            showSideUI("AddCinema");
        });
    }

    @FXML
    void editCinema(ActionEvent event) {
        // TODO: Implement edit cinema functionality
    }

    @FXML
    void RemoveCinema(ActionEvent event) {
        // get the selected cinema
        Cinema selectedCinema = cinemaTable.getSelectionModel().getSelectedItem().getCinema();

        // send a message to the server to remove the selected cinema
        Message message = new Message(MessageType.REMOVE_CINEMA_REQUEST)
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey())
                .setDataObject(selectedCinema);

        Client.getClient().sendToServer(message);

    }

    @FXML
    void addHall(ActionEvent event) {
        showSideUI("AddHall", cinemaTable.getSelectionModel().getSelectedItem().getCinema());
    }

    @FXML
    void editHall(ActionEvent event) {
        // TODO: Implement edit hall functionality
    }

    @FXML
    void removeHall(ActionEvent event) {
        Hall selectedHall = hallTable.getSelectionModel().getSelectedItem().getHall();

        Message message = new Message(MessageType.REMOVE_HALL_REQUEST)
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey())
                .setDataObject(selectedHall);

        Client.getClient().sendToServer(message);
    }

    @FXML
    void addScreening(ActionEvent event) {
        showSideUI("AddScreening");
    }

    @FXML
    void editScreening(ActionEvent event) {
        // TODO Implement edit screening functionality
    }


    @FXML
    void removeScreening(ActionEvent event) {
        // TODO: Implement remove screening functionality
    }

    @Subscribe
    public void onGetAllCinemas(GetAllCinemasEvent event) {
        Platform.runLater(() -> {
            List<Cinema> cinemas = event.getCinemas();
            cinemaTable.getItems().clear();
            for (Cinema cinema : cinemas) {
                cinemaTable.getItems().add(new CinemaView(cinema));
            }
        });
    }

    @Subscribe
    public void onAddCinema(AddCinemaEvent event) {
        Platform.runLater(() -> {
            cinemaTable.getItems().add(new CinemaView(event.getCinema()));
        });
    }

    @Subscribe
    public void onRemoveCinema(RemoveCinemaEvent event) {
        Platform.runLater(() -> {
            for (CinemaView cinemaView : cinemaTable.getItems()) {
                if (cinemaView.getCinema().getId() == event.getCinema().getId()) {
                    cinemaTable.getItems().remove(cinemaView);
                    break;
                }
            }
        });
    }
}
