/**
 * Sample Skeleton for 'CinemaInfo.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.client.data.CinemaView;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.data.UserView;
import il.cshaifasweng.OCSFMediatorExample.client.events.GetAllCinemasEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Cinema;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.User;
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

public class CinemaInfo {

    @FXML // fx:id="AddCinemaBtn"
    private Button AddCinemaBtn; // Value injected by FXMLLoader

    @FXML // fx:id="BackBtn"
    private Button BackBtn; // Value injected by FXMLLoader

    @FXML // fx:id="EditCinemaBtn"
    private Button EditCinemaBtn; // Value injected by FXMLLoader

    @FXML // fx:id="HomeBtn"
    private Button HomeBtn; // Value injected by FXMLLoader

    @FXML // fx:id="cinemaNameColumn"
    private TableColumn<CinemaView, String> cinemaNameColumn; // Value injected by FXMLLoader

    @FXML // fx:id="cinemaTable"
    private TableView<CinemaView> cinemaTable; // Value injected by FXMLLoader

    @FXML // fx:id="cityColumn"
    private TableColumn<CinemaView, String> cityColumn; // Value injected by FXMLLoader

    @FXML // fx:id="detailsTable"
    private TableView<?> detailsTable; // Value injected by FXMLLoader

    @FXML // fx:id="hallColumn"
    private TableColumn<?, ?> hallColumn; // Value injected by FXMLLoader

    @FXML // fx:id="managerColumn"
    private TableColumn<?, ?> managerColumn; // Value injected by FXMLLoader

    @FXML // fx:id="seatsColumn"
    private TableColumn<?, ?> seatsColumn; // Value injected by FXMLLoader

    @FXML
    public void initialize() throws IOException {
        EventBus.getDefault().register(this); //TODO: add this to all controllers - please :)


        Message message = new Message(MessageType.GET_ALL_CINEMAS_REQUEST)
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey());
        SimpleClient.getClient().sendToServer(message);
   cinemaNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
   cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
   hallColumn.setCellValueFactory(new PropertyValueFactory<>("hall"));
    }

    @FXML
    void addCinema(ActionEvent event) {

    }

    @FXML
    void editCinema(ActionEvent event) {

    }

    @FXML
    void returnBack(ActionEvent event) {

    }

    @FXML
    void returnHome(ActionEvent event) {

    }
    @Subscribe
    public void onGetAllCinemas(GetAllCinemasEvent event) {
        Platform.runLater(() -> {
            List<Cinema> cinemas = event.getCinemas();
            cinemaTable.getItems().clear();
            for (Cinema cinema :cinemas ) {
                cinemaTable.getItems().add(new CinemaView(cinema));
            }
        });
    }

}
