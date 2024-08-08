/**
 * Sample Skeleton for 'UserInterface.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.SimpleChatClient;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.LogoutEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.requsets.LogoutRequest;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.Objects;

import static il.cshaifasweng.OCSFMediatorExample.client.SimpleChatClient.setRoot;

/**
 * Sample Skeleton for 'UserInterface.fxml' Controller Class
 */

import javafx.scene.layout.BorderPane;

public class UserMainController {

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
    void ShowMyTickets(ActionEvent event) {
        loadUI("MyTickets");
    }

    @FXML
    void showAvailableMovies(ActionEvent event) {
        loadUI("UserAvailbleMovies");
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
        loadUI("UserDashBoard");
    }


    @FXML
    public void initialize() {
        UserLabel.setText(SessionKeysStorage.getInstance().getUsername());
        System.out.println(SessionKeysStorage.getInstance().getUsername());
        EventBus.getDefault().register(this); //TODO: add this to all controllers - please :)
    }

    @FXML
    void logOut(ActionEvent event) throws IOException {
        LogoutRequest logoutRequest = new LogoutRequest (SessionKeysStorage.getInstance().getSessionKey());
        int x=0;
        SimpleClient.getClient().sendToServer(new Message(logoutRequest, MessageType.LOGOUT_REQUEST));
        Platform.runLater(()->{
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
        Platform.runLater(()->{
            try {
                setRoot("Login");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
    private void loadUI(String ui) {
        try {
            Pane pane = FXMLLoader.load(
                    Objects.requireNonNull(
                            SimpleChatClient.class.getResource(ui + ".fxml")
                    )
            );
            mainPane.setCenter(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
