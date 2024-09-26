package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.UserInfoReceivedEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.User;
import il.cshaifasweng.OCSFMediatorExample.client.Client;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class UserInfo {


    @FXML private Label usernameLabel;
    @FXML private Label emailLabel;
    @FXML private Label roleLabel;
    @FXML private Label phoneLabel;
    @FXML private Label firstNameLabel;
    @FXML private Label lastNameLabel;
    @FXML private Label balanceLabel;
    @FXML private Label ticketsPurchasedLabel;
    @FXML private Label bundlesPurchasedLabel;
    @FXML private Label onlineScreeningsPurchasedLabel;
    @FXML private Label remainingTicketsPurchasedByBundle;


    @FXML
    public void initialize() {
        EventBus.getDefault().register(this);
        requestUserInfo();
    }

    private void requestUserInfo() {
        Message message = new Message(MessageType.GET_USER_INFO_REQUEST)
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey());
        Client.getClient().sendToServer(message);
    }

    @Subscribe
    public void onUserInfoReceived(UserInfoReceivedEvent userInfoReceived) {

        if (!userInfoReceived.isSuccess()) {
            System.err.println("Failed to get user info: " + userInfoReceived.getMessage());
            return;
        }
        Platform.runLater(() -> {
            User user = userInfoReceived.getUser();
            usernameLabel.setText("Username: " + user.getUsername());
            emailLabel.setText("Email: " + user.getEmail());
            roleLabel.setText("Role: " + user.getRole().toString());
            phoneLabel.setText("Phone: " + user.getPhone());
            firstNameLabel.setText("First Name: " + user.getFirstName());
            lastNameLabel.setText("Last Name: " + user.getLastName());
            balanceLabel.setText("Balance: $" + String.format("%.2f", user.getBalance()));
            ticketsPurchasedLabel.setText("Tickets Purchased: " + user.getNumberOfTicketsPurchased());
            bundlesPurchasedLabel.setText("Bundles Purchased: " + user.getNumberOfBundlePurchased());
            onlineScreeningsPurchasedLabel.setText("Online Screenings Purchased: " + user.getNumberOfOnlineScreeningsPurchased());
            remainingTicketsPurchasedByBundle.setText("Remaining Tickets Purchased By Bundle: " + user.getRemainingTicketsPurchasedByBundle());
        });
    }
}