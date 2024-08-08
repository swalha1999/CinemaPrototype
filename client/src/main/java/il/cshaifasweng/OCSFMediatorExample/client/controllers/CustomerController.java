package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.data.UserView;
import il.cshaifasweng.OCSFMediatorExample.entities.UserRole;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.GetAllUsersRequset;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

public class CustomerController {
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
    @FXML
    public void initialize() {
//        AdminLabel.setText(SessionKeysStorage.getInstance().getUsername());
//        EventBus.getDefault().register(this); //TODO: add this to all controllers - please :)
//
//        UserName_col.setCellValueFactory(new PropertyValueFactory<>("userName"));
//        FirstName_col.setCellValueFactory(new PropertyValueFactory<>("firstName"));
//        LastName_col.setCellValueFactory(new PropertyValueFactory<>("lastName"));
//        Email_col.setCellValueFactory(new PropertyValueFactory<>("email"));
//        Phone_col.setCellValueFactory(new PropertyValueFactory<>("phone"));
//        Role_col.setCellValueFactory(new PropertyValueFactory<>("role"));
//        IsLogged_col.setCellValueFactory(new PropertyValueFactory<>("isLogged"));
//        IsBlocked_col.setCellValueFactory(new PropertyValueFactory<>("isLocked"));
//        isDeleted_col.setCellValueFactory(new PropertyValueFactory<>("isDeleted"));
    }

    @FXML
    void AddMovies_form(ActionEvent event) {
//        AddMovies_Form.setVisible(true);
//        EditScreening_Form.setVisible(false);
//        AvailbleMovies_Form.setVisible(false);
//        Customer_Form.setVisible(false);
//        DashBoard_Form.setVisible(false);
    }


    @FXML
    void AddUser(ActionEvent event) {

    }

    @FXML
    void AvailbleMovies_Form(ActionEvent event) {
//        AvailbleMovies_Form.setVisible(true);
//        Customer_Form.setVisible(false);
//        DashBoard_Form.setVisible(false);
//        AddMovies_Form.setVisible(false);
//        EditScreening_Form.setVisible(false);

    }

    @FXML
    void BlockUser(ActionEvent event) {

    }

    @FXML
    void Customers_Form(ActionEvent event) throws IOException {
//        AvailbleMovies_Form.setVisible(false);
//        Customer_Form.setVisible(true);
//        DashBoard_Form.setVisible(false);
//        AddMovies_Form.setVisible(false);
//        EditScreening_Form.setVisible(false);
//
//        GetAllUsersRequset getAllUsersRequset = new GetAllUsersRequset(SessionKeysStorage.getInstance().getSessionKey());
//        SimpleClient.getClient().sendToServer(new Message(getAllUsersRequset, MessageType.GET_ALL_USERS_REQUEST));
    }

    @FXML
    void DashBoard_Form(ActionEvent event) {
//        AvailbleMovies_Form.setVisible(false);
//        Customer_Form.setVisible(false);
//        DashBoard_Form.setVisible(true);
//        AddMovies_Form.setVisible(false);
//        EditScreening_Form.setVisible(false);
    }

    @FXML
    void EditScreening_Form(ActionEvent event) {
//        AvailbleMovies_Form.setVisible(false);
//        Customer_Form.setVisible(false);
//        DashBoard_Form.setVisible(false);
//        AddMovies_Form.setVisible(false);
//        EditScreening_Form.setVisible(true);
    }

    public void MakeAdmin(ActionEvent actionEvent) {
    }

    public void RemoveUser(ActionEvent actionEvent) {
    }

    public void UnlockUser(ActionEvent actionEvent) {

    }
}
