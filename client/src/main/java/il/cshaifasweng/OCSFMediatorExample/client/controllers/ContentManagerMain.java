/**
 * Sample Skeleton for 'ContentManagerMain.fxml' Controller Class
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

public class ContentManagerMain {

    @FXML // fx:id="CustomersBtn"
    private Button CustomersBtn; // Value injected by FXMLLoader

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
    private void ShowScreenings(ActionEvent event) {
        loadUI("CinemaInfo");
    }

    @FXML
    private void showMovies(ActionEvent event) {
        loadUI("AdminAddMovie");
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
            clearAllUICache();
            setRoot("Login");
        });
    }

    @Subscribe
    public void onLogoutEvent(LogoutEvent response) {
        SessionKeysStorage.getInstance().clearSession();
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

    public void loadUI(String ui) {
        Platform.runLater(() ->
                {
                    mainPane.setCenter(loadFXMLPane(ui));
                }
        );

    }

    public void preLoadPages() {
        loadFXMLPane("CinemaInfo");
        loadFXMLPane("AdminUsersView");
        loadFXMLPane("MovieCatalog");
        loadFXMLPane("Purchase");
        loadFXMLPane("AdminAddMovie");
        loadFXMLPane("AddScreening");
        loadFXMLPane("EditCinema");
        loadFXMLPane("EditHall");
        loadFXMLPane("MyInbox");
        loadFXMLPane("SupportPage");
        loadFXMLPane("UpcomingMovies");
        loadFXMLPane("OnlineMovies");
        loadFXMLPane("SeatPicker");
        loadFXMLPane("UpcomingMovies");
        loadFXMLPane("EditScreening");
        loadFXMLPane("AdminInbox");
        loadFXMLPane("EditPrice");


        Message message = new Message(MessageType.GET_ALL_CINEMAS_REQUEST)
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey());
        Client.getClient().sendToServer(message);

        message = new Message(MessageType.GET_ALL_USERS_REQUEST)
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey());
        Client.getClient().sendToServer(message);

        message = new Message(MessageType.GET_ALL_CINEMAS_REQUEST)
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey());
        Client.getClient().sendToServer(message);

        message = new Message(MessageType.GET_ALL_MOVIES_REQUEST)
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey());
        Client.getClient().sendToServer(message);



    }

}
