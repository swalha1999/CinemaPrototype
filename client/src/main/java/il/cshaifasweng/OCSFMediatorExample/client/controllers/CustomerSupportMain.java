package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.Client;
import il.cshaifasweng.OCSFMediatorExample.client.data.CinemaView;
import il.cshaifasweng.OCSFMediatorExample.client.data.HallView;
import il.cshaifasweng.OCSFMediatorExample.client.data.ScreeningView;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.*;
import il.cshaifasweng.OCSFMediatorExample.client.utils.NotificationPane;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Cinema;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Hall;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Screening;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.requests.LogoutRequest;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static il.cshaifasweng.OCSFMediatorExample.client.CinemaMain.*;
import static il.cshaifasweng.OCSFMediatorExample.client.CinemaMain.loadFXMLPane;
import static il.cshaifasweng.OCSFMediatorExample.client.utils.UiUtil.showSideUI;

public class CustomerSupportMain {
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
    private StackPane stackPaneMain;

    @FXML
    private Label user;

    NotificationPane notificationPane;
    @FXML
    private BorderPane mainPane;

    @FXML
    public void initialize() throws IOException {
        user.setText(SessionKeysStorage.getInstance().getUsername());
        EventBus.getDefault().register(this);
        notificationPane = new NotificationPane(stackPaneMain);
        preLoadPages();
    }
    @FXML
    private void showCustomers(ActionEvent event) {
        loadUI("CustomerSupportUsersView");
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
        loadFXMLPane("CustomerSupportUsersView");
        loadFXMLPane("CustomerSupportInbox");


        Message message = new Message();

        message = new Message(MessageType.GET_ALL_USERS_REQUEST)
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey());
        Client.getClient().sendToServer(message);

    }
    @FXML
    void showCustomerSupportInbox(ActionEvent event) {
        loadUI("CustomerSupportInbox");

    }

}
