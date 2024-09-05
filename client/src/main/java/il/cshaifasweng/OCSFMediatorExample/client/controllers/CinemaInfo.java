/**
 * Sample Skeleton for 'CinemaInfo.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.Client;
import il.cshaifasweng.OCSFMediatorExample.client.data.*;
import il.cshaifasweng.OCSFMediatorExample.client.events.*;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Cinema;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Hall;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Screening;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
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
    private TableView<ScreeningView> ScreeningTable;

    @FXML
    private TableColumn<ScreeningView, LocalDateTime> Start_Col;

    @FXML
    private TableColumn<ScreeningView, Integer> Price_Col;

    @FXML
    private TableColumn<ScreeningView, String> ScreeningName_Col;

    @FXML
    private Button EditCinemaBtn; // Value injected by FXMLLoader

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

        ScreeningName_Col.setCellValueFactory(new PropertyValueFactory<>("movieTitle"));
        Start_Col.setCellValueFactory(new PropertyValueFactory<>("screeningDate"));
        Price_Col.setCellValueFactory(new PropertyValueFactory<>("price"));


        cinemaTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection == null) {
                return;
            }
            hallTable.getItems().clear();
            for (Hall hall : newSelection.getCinema().getHalls()) {
                hallTable.getItems().add(new HallView(hall));
            }

            //select the first hall
            hallTable.getSelectionModel().selectFirst();
        });

        hallTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection == null) {
                return;
            }
            ScreeningTable.getItems().clear();
            try {
                for (Screening screening : newSelection.getHall().getScreenings()) {
                    ScreeningTable.getItems().add(new ScreeningView(screening));
                }
            }catch (Exception e) {
                Message message = new Message(MessageType.GET_SCREENING_FOR_HALL_REQUEST)
                        .setSessionKey(SessionKeysStorage.getInstance().getSessionKey()).setDataObject(newSelection.getHall());
                Client.getClient().sendToServer(message);
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
    private void editCinema() {
       CinemaView selectedCinema = cinemaTable.getSelectionModel().getSelectedItem();
        if (selectedCinema != null) {
            Platform.runLater(() -> {
                showSideUI("EditCinema", selectedCinema.getCinema());
            });
        }

    }

    @FXML
    void addHall(ActionEvent event) {
        showSideUI("AddHall", cinemaTable.getSelectionModel().getSelectedItem().getCinema());
    }

    @FXML
    void editHall(ActionEvent event) {
        HallView selectedHall = hallTable.getSelectionModel().getSelectedItem();
        if (selectedHall != null) {
            Platform.runLater(() -> {
                showSideUI("EditHall", selectedHall.getHall());
            });
        }
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
        showSideUI("AddScreening", hallTable.getSelectionModel().getSelectedItem().getHall());

    }

    @FXML
    void editScreening(ActionEvent event) {
        ScreeningView selectedScreening = ScreeningTable.getSelectionModel().getSelectedItem();
        if(selectedScreening != null){
            Platform.runLater(() -> {
                showSideUI("EditScreening", selectedScreening.getScreening());
            });
        }
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

    @Subscribe
    public void onGetScreeningForHall(GetScreeningForHallEvent event) {
        Platform.runLater(() -> {
          if (event.getScreenings().isEmpty()) {
                return;
          }

          Hall hall = event.getScreenings().getFirst().getHall();

            if (hallTable.getSelectionModel().getSelectedItem().getHall().getId() == hall.getId()) {

                hallTable.getSelectionModel().getSelectedItem().getHall().setScreenings(new HashSet<>(event.getScreenings()));

                ScreeningTable.getItems().clear();
                for (Screening screening : event.getScreenings()) {
                    ScreeningTable.getItems().add(new ScreeningView(screening));
                }
                return;
            }

            Hall hallToUpdate = getHallFromData(hall);
            if (hallToUpdate != null) {
                hallToUpdate.setScreenings(new HashSet<>(event.getScreenings()));
            }
        });
    }

    @Subscribe
    public void onAddHall(AddHallEvent event) {
        Platform.runLater(() -> {
            Hall hall = event.getHall();
            Cinema cinema = hall.getCinema();
            for (CinemaView cinemaView : cinemaTable.getItems()) {
                if (cinemaView.getCinema().getId() == cinema.getId()) {
                    cinemaView.getCinema().getHalls().add(hall);
                    break;
                }
            }
            if (cinemaTable.getSelectionModel().getSelectedItem().getCinema().getId() == cinema.getId()) {
                hallTable.getItems().add(new HallView(hall));
            }
        });
    }

    private Hall getHallFromData(Hall hall) {
        for (CinemaView cinemaView : cinemaTable.getItems()) {
            for (Hall hall_to_update : cinemaView.getCinema().getHalls()) {
                if (hall_to_update.getId() == hall.getId()) {
                    return hall_to_update;
                }
            }
        }
        return null;
    }
    @Subscribe
  public void onAddScreening(AddScreeningEvent event) {

    }
}
