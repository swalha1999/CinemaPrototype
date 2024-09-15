/**
 * Sample Skeleton for 'Login.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.Client;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.LoginEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.ShowNotificationEvent;
import il.cshaifasweng.OCSFMediatorExample.client.utils.NotificationPane;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.UserRole;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.requests.LoginRequest;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

import static il.cshaifasweng.OCSFMediatorExample.client.CinemaMain.clearAllUICache;
import static il.cshaifasweng.OCSFMediatorExample.client.CinemaMain.setRoot;
import static il.cshaifasweng.OCSFMediatorExample.client.utils.UiUtil.showNotification;

public class Login {

    @FXML
    private PasswordField Password; // Value injected by FXMLLoader

    @FXML
    private TextField UserNameTxt; // Value injected by FXMLLoader

    @FXML
    private StackPane stackPaneMain; // Value injected by FXMLLoader

    NotificationPane notificationPane;

    @FXML
    public void initialize() {
        EventBus.getDefault().register(this); //TODO: add this to all controllers - please :)
        notificationPane = new NotificationPane(stackPaneMain);
    }

    @FXML
    void LoginAccount(ActionEvent event) throws IOException {
        LoginRequest loginRequest = new LoginRequest(UserNameTxt.getText(), Password.getText());
        Client.getClient().sendToServer(new Message(loginRequest, MessageType.LOGIN_REQUEST));
    }

    @FXML
    void createNewAccount(ActionEvent event) {
        Platform.runLater(()->{
            setRoot("Register");
        });
    }

    @Subscribe
    public void  onLoginEvent(LoginEvent response) {
        SessionKeysStorage.getInstance()
                .clearSession()
                .setSessionKey(response.getSessionKey())
                .setUsername(response.getUsername())
                .setRole(response.getRole());

        System.out.println(SessionKeysStorage.getInstance().toString());

        Platform.runLater(()->{
            if (response.isSuccess()) {
                if( response.getRole() == UserRole.USER){
                    setRoot("UserMain");
                } else {
                    setRoot("AdminMain");
                }
            }
            showNotification(response.getMessage(), response.isSuccess());
        });
    }

    @Subscribe
    public void onShowNotification(ShowNotificationEvent event) {
        notificationPane.showNotification(event.getMessage(), event.isSuccessful());
    }
}


