/**
 * Sample Skeleton for 'Register.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

import static il.cshaifasweng.OCSFMediatorExample.client.SimpleChatClient.setRoot;

public class RegisterController {

    @FXML // fx:id="ConfPasswordLabel"
    private Label ConfPasswordLabel; // Value injected by FXMLLoader

    @FXML // fx:id="ConfPasswordText"
    private TextField ConfPasswordText; // Value injected by FXMLLoader

    @FXML // fx:id="FirstNameText"
    private TextField FirstNameText; // Value injected by FXMLLoader

    @FXML // fx:id="LastNameLabel"
    private Label LastNameLabel; // Value injected by FXMLLoader

    @FXML // fx:id="LastNameText"
    private TextField LastNameText; // Value injected by FXMLLoader

    @FXML // fx:id="PasswordLabel"
    private Label PasswordLabel; // Value injected by FXMLLoader

    @FXML // fx:id="PasswordText"
    private TextField PasswordText; // Value injected by FXMLLoader

    @FXML // fx:id="RegisterLogo"
    private Label RegisterLogo; // Value injected by FXMLLoader

    @FXML // fx:id="UserNameLabel"
    private Label UserNameLabel; // Value injected by FXMLLoader

    @FXML // fx:id="UserNameText"
    private TextField UserNameText; // Value injected by FXMLLoader

    @FXML // fx:id="firstNameLabel"
    private Label firstNameLabel; // Value injected by FXMLLoader

    @FXML // fx:id="loginBtn"
    private Button loginBtn; // Value injected by FXMLLoader

    @FXML // fx:id="registerBtn"
    private Button registerBtn; // Value injected by FXMLLoader

    @FXML
    void ConfrimRegister(ActionEvent event) throws IOException {

        Message message = new Message(2, "register a new user");
        message.setUser(new User());
        message.getUser().setFirstName(FirstNameText.getText());
        message.getUser().setLastName(LastNameText.getText());
        message.getUser().setUsername(UserNameText.getText());
        message.setData(PasswordText.getText());
        SimpleClient.getClient().sendToServer(message);

        Platform.runLater(()->{
            try {
                setRoot("Login");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    @FXML
    void returnLogin(ActionEvent event) {
        Platform.runLater(()->{
            try {
                setRoot("AdminPage");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}




