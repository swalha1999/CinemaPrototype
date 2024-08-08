package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.data.UserView;
import il.cshaifasweng.OCSFMediatorExample.client.events.GetAllUsersEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.User;
import il.cshaifasweng.OCSFMediatorExample.entities.UserRole;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.GetAllUsersRequest;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;

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
    public void initialize() throws IOException {
        EventBus.getDefault().register(this); //TODO: add this to all controllers - please :)

        GetAllUsersRequest getAllUsersRequset = new GetAllUsersRequest(SessionKeysStorage.getInstance().getSessionKey());
        SimpleClient.getClient().sendToServer(new Message(getAllUsersRequset, MessageType.GET_ALL_USERS_REQUEST));

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

    @Subscribe
    public void onGetAllUsersEvent(GetAllUsersEvent response){
        Platform.runLater(()->{
            List<User> users = response.getUsers();
            Users_Table.getItems().clear();
            for (User user : users) {
                Users_Table.getItems().add(new UserView(user));
            }
        });
    }

    @FXML
    void BlockUser(ActionEvent event) {

    }

    public void MakeAdmin(ActionEvent actionEvent) {
    }

    public void RemoveUser(ActionEvent actionEvent) {
    }

    public void UnlockUser(ActionEvent actionEvent) {

    }
}
