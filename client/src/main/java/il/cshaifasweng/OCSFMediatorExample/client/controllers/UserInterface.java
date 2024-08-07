/**
 * Sample Skeleton for 'UserInterface.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.LogoutEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.LogoutRequest;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

import static il.cshaifasweng.OCSFMediatorExample.client.SimpleChatClient.setRoot;

public class UserInterface {

    @FXML // fx:id="AddMovies_Form"
    private AnchorPane AddMovies_Form; // Value injected by FXMLLoader

    @FXML // fx:id="AdminLabel"
    private Label UserLabel; // Value injected by FXMLLoader

    @FXML // fx:id="AvailbleMoviesBtn"
    private Button AvailbleMoviesBtn; // Value injected by FXMLLoader

    @FXML // fx:id="AvailbleMovies_Form"
    private AnchorPane AvailbleMovies_Form; // Value injected by FXMLLoader

    @FXML // fx:id="Customer_Form"
    private AnchorPane Customer_Form; // Value injected by FXMLLoader

    @FXML // fx:id="DashBoard"
    private Button DashBoard; // Value injected by FXMLLoader

    @FXML // fx:id="DashBoard_Form"
    private AnchorPane DashBoard_Form; // Value injected by FXMLLoader

    @FXML // fx:id="EditScreening_Form"
    private AnchorPane EditScreening_Form; // Value injected by FXMLLoader

    @FXML // fx:id="OnlineMoviesBtn"
    private Button OnlineMoviesBtn; // Value injected by FXMLLoader

    @FXML // fx:id="UpcomingMoviesBtn"
    private Button UpcomingMoviesBtn; // Value injected by FXMLLoader

    @FXML // fx:id="WelcomeLabel"
    private Label WelcomeLabel; // Value injected by FXMLLoader

    @FXML // fx:id="logoutBtn"
    private Button logoutBtn; // Value injected by FXMLLoader

    @FXML
    public void initialize() {
        UserLabel.setText(SessionKeysStorage.getInstance().getUsername());
        System.out.println(SessionKeysStorage.getInstance().getUsername());
        EventBus.getDefault().register(this); //TODO: add this to all controllers - please :)
    }

    @FXML
    void AvailbleMovies_Form(ActionEvent event) {

    }

    @FXML
    void DashBoard_Form(ActionEvent event) {

    }

    @FXML
    void OnlineMovies_Form(ActionEvent event) {

    }

    @FXML
    void UpcomingMovies_Form(ActionEvent event) {

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

}
