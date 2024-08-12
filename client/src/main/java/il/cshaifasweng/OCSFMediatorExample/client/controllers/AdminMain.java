package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.Client;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.LogoutEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.ShowNotificationEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.ShowSideUIEvent;
import il.cshaifasweng.OCSFMediatorExample.client.utils.NotificationPane;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.requests.LogoutRequest;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
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
import static il.cshaifasweng.OCSFMediatorExample.client.CinemaMain.loadFXML;

public class AdminMain {

    NotificationPane notificationPane;

    @FXML
    private BorderPane mainPane;

    @FXML
    private Label AdminLabel;

    @FXML
    private Button logoutBtn;

    @FXML
    private StackPane stackPaneMain; // Value injected by FXMLLoader

    @FXML
    public void initialize() {
        EventBus.getDefault().register(this);
        notificationPane = new NotificationPane(stackPaneMain);
        loadUI("DashBoard");
        preLoadPages();
    }

    @FXML
    private void showDashBoard(ActionEvent event) {
        loadUI("Purchase");
    }

    @FXML
    private void showMovies(ActionEvent event) {
        loadUI("AdminAddMovie");
    }

    @FXML
    private void showAvailableMovies(ActionEvent event) {
        loadUI("MovieCatalog");
    }

    @FXML
    private void ShowScreenings(ActionEvent event) {
        loadUI("CinemaInfo");
    }

    @FXML
    private void showCustomers(ActionEvent event) {
        loadUI("AdminUsersView");
    }

    @FXML
    void logOut(ActionEvent event) throws IOException {
        LogoutRequest logoutRequest = new LogoutRequest(SessionKeysStorage.getInstance().getSessionKey());
        Client.getClient().sendToServer(new Message(logoutRequest, MessageType.LOGOUT_REQUEST));
        Platform.runLater(() -> {
            setRoot("Login");
        });
    }

    @Subscribe
    public void onLogoutEvent(LogoutEvent response) {
        SessionKeysStorage.getInstance().clearSession();
        Platform.runLater(() -> {
            setRoot("Login");
        });
    }

    @Subscribe
    public void onShowNotification(ShowNotificationEvent event) {
        notificationPane.showNotification(event.getMessage(), event.isSuccessful());
    }

    @Subscribe
    public void onShowSideUIEvent(ShowSideUIEvent event) {
        loadUI(event.getUIName());
    }

    public void loadUI(String ui) {
        Platform.runLater(() ->
                {
                    mainPane.setCenter(loadFXMLPane(ui));
                }
        );

    }

    public void preLoadPages() {
        loadFXMLPane("AdminAddMovie");
        loadFXMLPane("AdminUsersView");
        loadFXMLPane("CinemaInfo");
        loadFXMLPane("DashBoard");
        loadFXMLPane("MovieCatalog");
        loadFXMLPane("Purchase");
    }

}
