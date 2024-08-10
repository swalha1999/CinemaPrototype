package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.GetAllUsersEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.User;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.requests.GetAllMoviesRequest;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;

public class AdminScreening {

    @FXML
    private TableColumn<?, ?> AvailableSeats_Col;

    @FXML
    private TableColumn<?, ?> BookedSeats_Col;

    @FXML
    private TableColumn<?, ?> Cinema_Col;

    @FXML
    private TableColumn<?, ?> Hall_Col;

    @FXML
    private TableColumn<?, ?> MovieId_Col;

    @FXML
    private TableView<?> MoviesTable;

    @FXML
    private TableColumn<?, ?> ScreeningDate_Col;

    @FXML
    private TableColumn<?, ?> StartTime_Col;

    public void initialize() throws IOException {
        EventBus.getDefault().register(this); //TODO: add this to all controllers - please :)

        //TODO: hey omar this is swalha this the message type is not correct - please fix :)
        GetAllMoviesRequest GetAllMoviesRequest = new GetAllMoviesRequest(SessionKeysStorage.getInstance().getSessionKey());
        SimpleClient.getClient().sendToServer(new Message(GetAllMoviesRequest, MessageType.GET_ALL_USERS_REQUEST));

        AvailableSeats_Col.setCellValueFactory(new PropertyValueFactory<>("AvailableSeats"));
        Cinema_Col.setCellValueFactory(new PropertyValueFactory<>("Cinema"));
        Hall_Col.setCellValueFactory(new PropertyValueFactory<>("Hall"));
        MovieId_Col.setCellValueFactory(new PropertyValueFactory<>("MovieId"));
        ScreeningDate_Col.setCellValueFactory(new PropertyValueFactory<>("ScreeningDate"));
        StartTime_Col.setCellValueFactory(new PropertyValueFactory<>("StartTime"));
    }

    //TODO: hey omar this is swalha this function is not working the event type is not correct - please fix :)
    @Subscribe
    public void GetAllMoviesRequest(GetAllUsersEvent response){
        Platform.runLater(()->{
            List<User> users = response.getUsers();
            MoviesTable.getItems().clear();
            for (User user : users) {
                //MoviesTable.getItems().add(new MovieView());
            }
        });
    }

}
