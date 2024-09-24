package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.Client;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.data.UserView;
import il.cshaifasweng.OCSFMediatorExample.client.events.GetAllUsersEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.NewUserAddedEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.RemoveUserEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.UpdatedUserEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.User;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.UserRole;
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

public class CustomerSupportUsersView {
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

    @FXML
    private ComboBox<UserRole> Customer_Role;

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

        UserName_col.setCellValueFactory(new PropertyValueFactory<>("userName"));
        FirstName_col.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        LastName_col.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        Email_col.setCellValueFactory(new PropertyValueFactory<>("email"));
        Phone_col.setCellValueFactory(new PropertyValueFactory<>("phone"));
        Role_col.setCellValueFactory(new PropertyValueFactory<>("role"));
        IsLogged_col.setCellValueFactory(new PropertyValueFactory<>("isLogged"));
        IsBlocked_col.setCellValueFactory(new PropertyValueFactory<>("isLocked"));
        isDeleted_col.setCellValueFactory(new PropertyValueFactory<>("isDeleted"));
        for (UserRole role : UserRole.values()) {
            Customer_Role.getItems().add(role);
        }
    }

    @Subscribe
    public void onGetAllUsersEvent(GetAllUsersEvent response) {
        Platform.runLater(() -> {
            List<User> users = response.getUsers();
            Users_Table.getItems().clear();
            for (User user : users) {
                Users_Table.getItems().add(new UserView(user));
            }
        });
    }

    @Subscribe
    public void onNewUserEvent(NewUserAddedEvent event) {
        Platform.runLater(() -> {
            Users_Table.getItems().add(new UserView(event.getUser()));
        });
    }

    @Subscribe
    public void onRemoveUserEvent(RemoveUserEvent event) {
        Platform.runLater(() -> {
            for (UserView userView : Users_Table.getItems()) {
                if (userView.getUsername().equals(event.getUsername())) {
                    Users_Table.getItems().remove(userView);
                    break;
                }
            }
        });
    }

    @Subscribe
    public void onUpdatedUserEvent(UpdatedUserEvent event) {
        Platform.runLater(() -> {
            for (UserView userView : Users_Table.getItems()) {
                if (userView.getId() == event.getUpdatedUser().getId()) {
                    userView.copyUser(event.getUpdatedUser());
                    break;
                }
            }
        });
    }

    @FXML
    void BlockUser(ActionEvent event) {
        UserView selectedUser = Users_Table.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            return;
        }

        User userToBlock = new User()
                .setUsername(selectedUser.getUsername())
                .setId(selectedUser.getId())
                .setBlocked(true);

        Message blockUserRequest = new Message(MessageType.BLOCK_USER_REQUEST)
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey())
                .setDataObject(userToBlock);

        Client.getClient().sendToServer(blockUserRequest);
    }


    public void RemoveUser(ActionEvent actionEvent) {
        UserView selectedUser = Users_Table.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            return;
        }

        User userToRemove = new User().setUsername(selectedUser.getUsername()).setId(selectedUser.getId());

        Message removeUserRequest = new Message(MessageType.REMOVE_USER_REQUEST)
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey())
                .setDataObject(userToRemove);

        Client.getClient().sendToServer(removeUserRequest);
    }

    public void UnblockUser(ActionEvent actionEvent) {
        // Get the selected user from the table
        UserView selectedUser = Users_Table.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            // If no user is selected, exit the method
            return;
        }

        // Create a User object and set its properties
        User userToUnblock = new User()
                .setUsername(selectedUser.getUsername())
                .setId(selectedUser.getId())
                .setBlocked(false); // Set the blocked status to false to unblock the user

        // Create a Message object for the unblock user request
        Message unblockUserRequest = new Message(MessageType.UNBLOCK_USER_REQUEST)
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey())
                .setDataObject(userToUnblock);

        // Send the unblock user request to the server
        Client.getClient().sendToServer(unblockUserRequest);
    }
    @FXML
    void ChangeRole(ActionEvent event) {
        UserView selectedUser = Users_Table.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            return;
        }

        // Create a User object with all relevant details from the selected user
        User userToUpdate = new User()
                .setId(selectedUser.getId())
                .setFirstName(selectedUser.getFirstName())
                .setLastName(selectedUser.getLastName())
                .setEmail(selectedUser.getEmail())
                .setRole(Customer_Role.getValue())
                .setUsername(selectedUser.getUsername());

        // Create a message with the user update request
        Message ChangeUserRoleRequest = new Message(MessageType.CHANGE_USER_ROLE_REQUEST)
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey())
                .setDataObject(userToUpdate);

        // Send the message to the server
        Client.getClient().sendToServer(ChangeUserRoleRequest);
    }
}
