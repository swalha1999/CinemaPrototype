/**
 * Sample Skeleton for 'BranchManagerMain.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.Client;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.LogoutEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.ShowNotificationEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.ShowSideUIEvent;
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

public class BranchManagerMain {

    @FXML // fx:id="AdminInbox"
    private Button AdminInbox; // Value injected by FXMLLoader

    @FXML // fx:id="CustomersBtn"
    private Button CustomersBtn; // Value injected by FXMLLoader

    @FXML // fx:id="DashBoardBtn"
    private Button DashBoardBtn; // Value injected by FXMLLoader

    @FXML // fx:id="LogOutBtn"
    private Button LogOutBtn; // Value injected by FXMLLoader

    @FXML // fx:id="MoviesBtn"
    private Button MoviesBtn; // Value injected by FXMLLoader

    @FXML // fx:id="ScreeningsBtn"
    private Button ScreeningsBtn; // Value injected by FXMLLoader

    @FXML // fx:id="mainPane"
    private BorderPane mainPane; // Value injected by FXMLLoader

    @FXML // fx:id="rootPane"
    private BorderPane rootPane; // Value injected by FXMLLoader

    @FXML // fx:id="stackPaneMain"
    private StackPane stackPaneMain; // Value injected by FXMLLoader

    @FXML // fx:id="user"
    private Label user; // Value injected by FXMLLoader
    NotificationPane notificationPane;

    @FXML
    public void initialize() throws IOException {
        user.setText(SessionKeysStorage.getInstance().getUsername());
        EventBus.getDefault().register(this);
        notificationPane = new NotificationPane(stackPaneMain);
        preLoadPages();
    }
    @FXML
    void logOut(ActionEvent event) throws IOException {
        LogoutRequest logoutRequest = new LogoutRequest(SessionKeysStorage.getInstance().getSessionKey());
        Client.getClient().sendToServer(new Message(logoutRequest, MessageType.LOGOUT_REQUEST));
        Platform.runLater(() -> {
            clearAllUICache();
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
    public void preLoadPages() {
       loadFXMLPane("BranchManagerDashBoard");
    }
    @FXML
    void showDashBoard(ActionEvent event) {
        loadUI("BranchManagerDashBoard");
    }
    public void loadUI(String ui) {
        Platform.runLater(() ->
                {
                    mainPane.setCenter(loadFXMLPane(ui));
                }
        );

    }

    @Subscribe
    public void onLogoutEvent(LogoutEvent response) {
        SessionKeysStorage.getInstance().clearSession();
        Platform.runLater(() -> {
            clearAllUICache();
            setRoot("Login");
        });
    }

}




