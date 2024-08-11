/**
 * Sample Skeleton for 'CinemaInfo.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.Client;
import il.cshaifasweng.OCSFMediatorExample.client.data.CinemaView;
import il.cshaifasweng.OCSFMediatorExample.client.data.HallView;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.GetAllCinemasEvent;
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

public class CinemaInfo {

    @FXML
    public TableView<?> ScreeningTable;

    @FXML
    public TableColumn<?,?> MovieName_Col;

    @FXML
    public TableColumn<?,?> Start_Col;

    @FXML
    public TableColumn<?,?> End_Col;

    @FXML // fx:id="AddCinemaBtn"
    private Button AddCinemaBtn; // Value injected by FXMLLoader

    @FXML // fx:id="BackBtn"
    private Button BackBtn; // Value injected by FXMLLoader

    @FXML // fx:id="EditCinemaBtn"
    private Button EditCinemaBtn; // Value injected by FXMLLoader

    @FXML // fx:id="HomeBtn"
    private Button HomeBtn; // Value injected by FXMLLoader

    @FXML // fx:id="cinemaTable"
    private TableView<CinemaView> cinemaTable; // Value injected by FXMLLoader

    @FXML // fx:id="cityColumn"
    private TableColumn<CinemaView, String> cityColumn; // Value injected by FXMLLoader

    @FXML // fx:id="detailsTable"
    private TableView<HallView> hallTable; // Value injected by FXMLLoader

    @FXML // fx:id="hallName_Col"
    private TableColumn<HallView, String> hallName_Col; // Value injected by FXMLLoader

    @FXML // fx:id="managerColumn"
    private TableColumn<CinemaView, String> managerColumn; // Value injected by FXMLLoader

    @FXML // fx:id="seatsColumn"
    private TableColumn<HallView, Integer> seatsColumn; // Value injected by FXMLLoader

    @FXML
    public void initialize() throws IOException {
        EventBus.getDefault().register(this); //TODO: add this to all controllers - please :)


        Message message = new Message(MessageType.GET_ALL_CINEMAS_REQUEST)
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey());
        Client.getClient().sendToServer(message);

        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        hallName_Col.setCellValueFactory(new PropertyValueFactory<>("name"));
        managerColumn.setCellValueFactory(new PropertyValueFactory<>("managerName"));
        seatsColumn.setCellValueFactory(new PropertyValueFactory<>("seats"));

        cinemaTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection)->{
                hallTable.getItems().clear();
                for(Hall hall : newSelection.getCinema().getHalls()) {
                    hallTable.getItems().add(new HallView(hall));
                }
        });
    }

    @FXML
    void addCinema(ActionEvent event) {
        Platform.runLater(() -> {
            setRoot("AddHall");
        });
    }

    @FXML
    void editCinema(ActionEvent event) {
        //TODO: make a page for it
    }

    @FXML
    void returnBack(ActionEvent event) {
        //TODO: use this
    }

    @FXML
    void returnHome(ActionEvent event) {
        //TODO: this
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
