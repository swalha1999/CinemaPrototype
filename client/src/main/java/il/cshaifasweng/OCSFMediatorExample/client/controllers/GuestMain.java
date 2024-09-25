package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.Client;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.LogoutEvent;
import il.cshaifasweng.OCSFMediatorExample.client.utils.NotificationPane;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.requests.LogoutRequest;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

import static il.cshaifasweng.OCSFMediatorExample.client.CinemaMain.*;

public class GuestMain {

    @FXML
    private Button AvailbleMovies_Btn;

    @FXML
    private Button Logout_Btn;

    @FXML
    private Button OnlineMovies_Btn;

    @FXML
    private Button UpComingMovies_Btn;

    @FXML
    private Label UserLabel;

    @FXML
    private Label Welcome_Label;

    @FXML
    private BorderPane mainPane;

    @FXML
    private BorderPane rootPane;

    @FXML
    private StackPane stackPaneMain;

    NotificationPane notificationPane;


    @FXML
    public void initialize() {
        UserLabel.setText(SessionKeysStorage.getInstance().getUsername());
        EventBus.getDefault().register(this); //TODO: add this to all controllers - please :)
        notificationPane = new NotificationPane(stackPaneMain);
        loadUI("MovieCatalog");
        preLoadPages();
    }

    public void loadUI(String ui) {
        Platform.runLater(() -> {
            mainPane.setCenter(loadFXMLPane(ui));
        });
    }

    @FXML
    void showAvailableMovies(ActionEvent event) {
        loadUI("MovieCatalog");
    }
    @FXML
    void ShowUpcomingMovies(ActionEvent event) {
        loadUI("UpcomingMovies");
    }
    @FXML
    void ShowOnlineMoves(ActionEvent event) {loadUI("OnlineMovies");}

    @FXML
    void logOut(ActionEvent event) throws IOException {
        LogoutRequest logoutRequest = new LogoutRequest (SessionKeysStorage.getInstance().getSessionKey());
        Client.getClient().sendToServer(new Message(logoutRequest, MessageType.LOGOUT_REQUEST));
        Platform.runLater(()->{
            clearAllUICache();
            setRoot("Login");
        });

    }

    @Subscribe
    public void onLogoutEvent(LogoutEvent response) {
        SessionKeysStorage.getInstance().clearSession();
        Platform.runLater(()->{
            clearAllUICache();
            setRoot("Login");
        });
    }


    public void preLoadPages() {

        loadFXMLPane("MovieCatalog");
        loadFXMLPane("UpcomingMovies");
        loadFXMLPane("OnlineMovies");
        loadFXMLPane("SeatPicker");

        Message message;

        message = new Message(MessageType.GET_ALL_MOVIES_REQUEST)
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey());

        Client.getClient().sendToServer(message);

    }

}