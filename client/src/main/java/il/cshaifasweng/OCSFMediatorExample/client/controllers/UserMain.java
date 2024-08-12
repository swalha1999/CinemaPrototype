/**
 * Sample Skeleton for 'UserMain.fxml' Controller Class
 */

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
import javafx.scene.layout.StackPane;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;


import javafx.scene.layout.BorderPane;

import static il.cshaifasweng.OCSFMediatorExample.client.CinemaMain.*;

public class UserMain {

    NotificationPane notificationPane;

    @FXML // fx:id="AvailbleMovies_Btn"
    private Button AvailbleMovies_Btn; // Value injected by FXMLLoader

    @FXML // fx:id="DashBoard_Btn"
    private Button DashBoard_Btn; // Value injected by FXMLLoader

    @FXML // fx:id="Logout_Btn"
    private Button Logout_Btn; // Value injected by FXMLLoader

    @FXML // fx:id="MyTickets_Btn"
    private Button MyTickets_Btn; // Value injected by FXMLLoader

    @FXML // fx:id="OnlineMovies_Btn"
    private Button OnlineMovies_Btn; // Value injected by FXMLLoader

    @FXML // fx:id="UpComingMovies_Btn"
    private Button UpComingMovies_Btn; // Value injected by FXMLLoader

    @FXML // fx:id="Welcome_Label"
    private Label Welcome_Label; // Value injected by FXMLLoader

    @FXML // fx:id="mainPane"
    private BorderPane mainPane; // Value injected by FXMLLoader

    @FXML // fx:id="UserLabel"
    private Label UserLabel; // Value injected by FXMLLoader

    @FXML
    private StackPane stackPaneMain; // Value injected by FXMLLoader


    @FXML
    public void initialize() {
        UserLabel.setText(SessionKeysStorage.getInstance().getUsername());
        EventBus.getDefault().register(this); //TODO: add this to all controllers - please :)
        notificationPane = new NotificationPane(stackPaneMain);
        loadUI("MovieCatalog");
        preLoadPages();
    }

    @FXML
    void ShowMyTickets(ActionEvent event) {
        loadUI("MyTickets");
    }

    @FXML
    public void ShowMyInbox(ActionEvent actionEvent) {
        loadUI("MyInbox");
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
    void ShowOnlineMoves(ActionEvent event) {
        loadUI("OnlineMovies");
    }

    @FXML
    void showDashBoard(ActionEvent event) {
        loadUI("SeatPicker");
    }

    @FXML
    void logOut(ActionEvent event) throws IOException {
        LogoutRequest logoutRequest = new LogoutRequest (SessionKeysStorage.getInstance().getSessionKey());
        int x=0;
        Client.getClient().sendToServer(new Message(logoutRequest, MessageType.LOGOUT_REQUEST));
        Platform.runLater(()->{
            setRoot("Login");
        });
    }

    @Subscribe
    public void onLogoutEvent(LogoutEvent response) {
        SessionKeysStorage.getInstance().clearSession();
        Platform.runLater(()->{
            setRoot("Login");
        });
    }

    @Subscribe
    public void onShowSideUIEvent(ShowSideUIEvent event) {
        loadUI(event.getUIName());
    }

    @Subscribe
    public void onShowNotification(ShowNotificationEvent event) {
        notificationPane.showNotification(event.getMessage(), event.isSuccessful());
    }

    public void loadUI(String ui) {
        Platform.runLater(() -> {
            mainPane.setCenter(loadFXMLPane(ui));
        });
    }

    public void preLoadPages() {
        loadFXML("MyTickets");
        loadFXML("MyInbox");
        loadFXML("MovieCatalog");
        loadFXML("UpcomingMovies");
        loadFXML("OnlineMovies");
        loadFXML("SeatPicker");
    }


}
