package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.SimpleChatClient;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.LogoutEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.requests.LogoutRequest;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.Objects;

import static il.cshaifasweng.OCSFMediatorExample.client.SimpleChatClient.loadFXMLPane;
import static il.cshaifasweng.OCSFMediatorExample.client.SimpleChatClient.setRoot;

public class AdminMain {

    @FXML
    private BorderPane mainPane;

    @FXML
    private Label AdminLabel;

    @FXML
    private Button logoutBtn;

    @FXML
    public void initialize() {
//        AdminLabel.setText(SessionKeysStorage.getInstance().getUsername());
        EventBus.getDefault().register(this);
        loadUI("DashBoard");
    }

    @FXML
    private void showDashBoard(ActionEvent event) {
        loadUI("DashBoard");
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
        loadUI("AdminScreening");
    }

    @FXML
    private void showCustomers(ActionEvent event) {
        loadUI("AdminUsersView");
    }

    @FXML
    void logOut(ActionEvent event) throws IOException {
        LogoutRequest logoutRequest = new LogoutRequest(SessionKeysStorage.getInstance().getSessionKey());
        SimpleClient.getClient().sendToServer(new Message(logoutRequest, MessageType.LOGOUT_REQUEST));
        Platform.runLater(() -> {
            try {
                setRoot("Login");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Subscribe
    public void onLogoutEvent(LogoutEvent response) {
        SessionKeysStorage.getInstance().clearSession();
        Platform.runLater(() -> {
            try {
                setRoot("Login");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void loadUI(String ui) {
        Platform.runLater(() ->
                {
                    mainPane.setCenter(loadFXMLPane(ui));
                }
        );

    }

}
