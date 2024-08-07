/**
 * Sample Skeleton for 'AdminPage.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.LogoutEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.*;
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

public class AdminController {

    @FXML // fx:id="AddMoviesBtn"
    private Button AddMoviesBtn; // Value injected by FXMLLoader

    @FXML // fx:id="AddMovies_Form"
    private AnchorPane AddMovies_Form; // Value injected by FXMLLoader

    @FXML // fx:id="AdminLabel"
    private Label AdminLabel; // Value injected by FXMLLoader

    @FXML // fx:id="AvailbleMoviesBtn"
    private Button AvailbleMoviesBtn; // Value injected by FXMLLoader

    @FXML // fx:id="AvailbleMovies_Form"
    private AnchorPane AvailbleMovies_Form; // Value injected by FXMLLoader

    @FXML // fx:id="Customer_Form"
    private AnchorPane Customer_Form; // Value injected by FXMLLoader

    @FXML // fx:id="CustomersBtn"
    private Button CustomersBtn; // Value injected by FXMLLoader

    @FXML // fx:id="DashBoard"
    private Button DashBoard; // Value injected by FXMLLoader

    @FXML // fx:id="DashBoard_Form"
    private AnchorPane DashBoard_Form; // Value injected by FXMLLoader

    @FXML // fx:id="EditScreeningBtn"
    private Button EditScreeningBtn; // Value injected by FXMLLoader

    @FXML // fx:id="EditScreening_Form"
    private AnchorPane EditScreening_Form; // Value injected by FXMLLoader

    @FXML // fx:id="ExitBtn"
    private Button ExitBtn; // Value injected by FXMLLoader

    @FXML // fx:id="logoutBtn"
    private Button logoutBtn; // Value injected by FXMLLoader

    @FXML // fx:id="WelcomeLabel"
    private Label WelcomeLabel; // Value injected by FXMLLoader

    @FXML
    public void initialize() {
        EventBus.getDefault().register(this); //TODO: add this to all controllers - please :)
    }

    @FXML
    void AddMovies_form(ActionEvent event) {
      AddMovies_Form.setVisible(true);
      EditScreening_Form.setVisible(false);
      AvailbleMovies_Form.setVisible(false);
      Customer_Form.setVisible(false);
      DashBoard_Form.setVisible(false);
    }

    @FXML
    void AvailbleMovies_Form(ActionEvent event) {
        AvailbleMovies_Form.setVisible(true);
        Customer_Form.setVisible(false);
        DashBoard_Form.setVisible(false);
        AddMovies_Form.setVisible(false);
        EditScreening_Form.setVisible(false);

    }

    @FXML
    void Customers_Form(ActionEvent event) {
        AvailbleMovies_Form.setVisible(false);
        Customer_Form.setVisible(true);
        DashBoard_Form.setVisible(false);
        AddMovies_Form.setVisible(false);
        EditScreening_Form.setVisible(false);
    }

    @FXML
    void DashBoard_Form(ActionEvent event) {
        AvailbleMovies_Form.setVisible(false);
        Customer_Form.setVisible(false);
        DashBoard_Form.setVisible(true);
        AddMovies_Form.setVisible(false);
        EditScreening_Form.setVisible(false);
    }

    @FXML
    void EditScreening_Form(ActionEvent event) {
        AvailbleMovies_Form.setVisible(false);
        Customer_Form.setVisible(false);
        DashBoard_Form.setVisible(false);
        AddMovies_Form.setVisible(false);
        EditScreening_Form.setVisible(true);
    }

    @FXML
    void Exit(ActionEvent event) {

    }

    @FXML
    void logOut(ActionEvent event) throws IOException {
        LogoutRequest logoutRequest = new LogoutRequest (SessionKeysStorage.getInstance().getSessionKey());
        SimpleClient.getClient().sendToServer(new Message(logoutRequest, MessageType.LOGOUT_REQUEST));
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
