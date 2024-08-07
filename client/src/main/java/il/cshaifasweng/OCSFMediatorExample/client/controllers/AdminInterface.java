/**
 * Sample Skeleton for 'AdminInterface.fxml' Controller Class
 */

/**
 * Sample Skeleton for 'AdminInterface.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.data.UserView;
import il.cshaifasweng.OCSFMediatorExample.client.events.GetAllUsersEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.LogoutEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.User;
import il.cshaifasweng.OCSFMediatorExample.entities.UserRole;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.GetAllUsersRequset;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.LogoutRequest;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;

import static il.cshaifasweng.OCSFMediatorExample.client.SimpleChatClient.setRoot;

public class AdminInterface {

    @FXML // fx:id="A"
    private Button A; // Value injected by FXMLLoader

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

    @FXML // fx:id="BlockUser"
    private Button BlockUser; // Value injected by FXMLLoader

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

    @FXML // fx:id="Email_col"
    private TableColumn<UserView, String> Email_col; // Value injected by FXMLLoader

    @FXML // fx:id="FirstName_col"
    private TableColumn<UserView, String> FirstName_col; // Value injected by FXMLLoader

    @FXML // fx:id="IsBlocked_col"
    private TableColumn<UserView, Boolean> IsBlocked_col; // Value injected by FXMLLoader

    @FXML // fx:id="IsLogged_col"
    private TableColumn<UserView, Boolean> IsLogged_col; // Value injected by FXMLLoader

    @FXML // fx:id="LastName_col"
    private TableColumn<UserView, String> LastName_col; // Value injected by FXMLLoader

    @FXML // fx:id="MakeAdmin"
    private Button MakeAdmin; // Value injected by FXMLLoader

    @FXML // fx:id="Phone_col"
    private TableColumn<UserView, String> Phone_col; // Value injected by FXMLLoader

    @FXML // fx:id="RemoveUser"
    private Button RemoveUser; // Value injected by FXMLLoader

    @FXML // fx:id="Role_col"
    private TableColumn<UserView, UserRole> Role_col; // Value injected by FXMLLoader

    @FXML // fx:id="UnblockUser"
    private Button UnblockUser; // Value injected by FXMLLoader

    @FXML // fx:id="UserName_col"
    private TableColumn<UserView, String> UserName_col; // Value injected by FXMLLoader

    @FXML // fx:id="Users_Table"
    private TableView<UserView> Users_Table; // Value injected by FXMLLoader

    @FXML // fx:id="WelcomeLabel"
    private Label WelcomeLabel; // Value injected by FXMLLoader

    @FXML // fx:id="isDeleted_col"
    private TableColumn<UserView, Boolean> isDeleted_col; // Value injected by FXMLLoader

    @FXML // fx:id="logoutBtn"
    private Button logoutBtn; // Value injected by FXMLLoader

    @FXML // fx:id="MovieID_Label"
    private Label MovieID_Label; // Value injected by FXMLLoader

    @FXML // fx:id="MovieId_Txt"
    private TextField MovieId_Txt; // Value injected by FXMLLoader

    @FXML // fx:id="MovieNameCol"
    private TableColumn<?, ?> MovieNameCol; // Value injected by FXMLLoader

    @FXML // fx:id="MovieName_Label"
    private Label MovieName_Label; // Value injected by FXMLLoader

    @FXML // fx:id="MovieName_Txt"
    private TextField MovieName_Txt; // Value injected by FXMLLoader

    @FXML // fx:id="MoviesTable"
    private TableView<?> MoviesTable; // Value injected by FXMLLoader

    @FXML // fx:id="NotTableAnchor"
    private AnchorPane NotTableAnchor; // Value injected by FXMLLoader

    @FXML // fx:id="OnlineCol"
    private TableColumn<?, ?> OnlineCol; // Value injected by FXMLLoader

    @FXML // fx:id="Price_Label"
    private Label Price_Label; // Value injected by FXMLLoader

    @FXML // fx:id="Price_Txt"
    private TextField Price_Txt; // Value injected by FXMLLoader

    @FXML // fx:id="ScreeningDateCol"
    private TableColumn<?, ?> ScreeningDateCol; // Value injected by FXMLLoader

    @FXML // fx:id="ScreeningDate_Label"
    private Label ScreeningDate_Label; // Value injected by FXMLLoader

    @FXML // fx:id="ScreeningDate_Txt"
    private TextField ScreeningDate_Txt; // Value injected by FXMLLoader

    @FXML // fx:id="TableAnchor"
    private AnchorPane TableAnchor; // Value injected by FXMLLoader

    @FXML // fx:id="TicketPriceCol"
    private TableColumn<?, ?> TicketPriceCol; // Value injected by FXMLLoader

    @FXML // fx:id="TicketPrice_txt"
    private TextField TicketPrice_txt; // Value injected by FXMLLoader

    @FXML // fx:id="Ticket_Price_Label"
    private Label Ticket_Price_Label; // Value injected by FXMLLoader



    @FXML // fx:id="UrlCol"
    private TableColumn<?, ?> UrlCol; // Value injected by FXMLLoader


    @FXML
    public void initialize() {
        AdminLabel.setText(SessionKeysStorage.getInstance().getUsername());
        EventBus.getDefault().register(this); //TODO: add this to all controllers - please :)

        UserName_col.setCellValueFactory(new PropertyValueFactory<>("userName"));
        FirstName_col.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        LastName_col.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        Email_col.setCellValueFactory(new PropertyValueFactory<>("email"));
        Phone_col.setCellValueFactory(new PropertyValueFactory<>("phone"));
        Role_col.setCellValueFactory(new PropertyValueFactory<>("role"));
        IsLogged_col.setCellValueFactory(new PropertyValueFactory<>("isLogged"));
        IsBlocked_col.setCellValueFactory(new PropertyValueFactory<>("isLocked"));
        isDeleted_col.setCellValueFactory(new PropertyValueFactory<>("isDeleted"));
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
    void AddUser(ActionEvent event) {

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
    void BlockUser(ActionEvent event) {

    }

    @FXML
    void Customers_Form(ActionEvent event) throws IOException {
        AvailbleMovies_Form.setVisible(false);
        Customer_Form.setVisible(true);
        DashBoard_Form.setVisible(false);
        AddMovies_Form.setVisible(false);
        EditScreening_Form.setVisible(false);

        GetAllUsersRequset getAllUsersRequset = new GetAllUsersRequset(SessionKeysStorage.getInstance().getSessionKey());
        SimpleClient.getClient().sendToServer(new Message(getAllUsersRequset, MessageType.GET_ALL_USERS_REQUEST));
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
    void ImportPicture(ActionEvent event) {

    }

    @FXML
    void MakeAdmin(ActionEvent event) {

    }

    @FXML
    void RemoveUser(ActionEvent event) {

    }

    @FXML
    void UnblockUser(ActionEvent event) {

    }

    @Subscribe
    public void onGetAllUsersEvent(GetAllUsersEvent response){
        List<User> users = response.getUsers();
        Users_Table.getItems().clear();
        for (User user : users) {
            Users_Table.getItems().add(new UserView(user));
        }

    }




    @FXML
    void logOut(ActionEvent event) throws IOException {
        LogoutRequest logoutRequest = new LogoutRequest(SessionKeysStorage.getInstance().getSessionKey());
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






















