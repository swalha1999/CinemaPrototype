/**
 * Sample Skeleton for 'Register.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.client.events.RegisterEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.requsets.RegisterRequest;
import il.cshaifasweng.OCSFMediatorExample.entities.utils.PasswordUtil;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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

    @FXML // fx:id="EmailText"
    private TextField EmailText; // Value injected by FXMLLoader

    @FXML
    public void initialize() {
        EventBus.getDefault().register(this); //TODO: add this to all controllers - please :)
    }


    @FXML
    void  confirmRegister(ActionEvent event) throws IOException {

        // TODO: temp code to fix later
        if(!PasswordText.getText().equals(ConfPasswordText.getText())){
            Platform.runLater(()->{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Password does not match");
                alert.setContentText("Please make sure the password is the same in both fields");
                alert.show();
            });
            return;
        }

        // TODO: temp code to fix later make a slider that shows the password strength with error message not a popup
        if (PasswordUtil.isWeak(PasswordText.getText())) {
            Platform.runLater(()->{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Password is weak");
                alert.setContentText("Please make sure the password is at least 8 characters long and contains at least one uppercase letter, one lowercase letter, one number and one special character");
                alert.show();
            });
            return;
        }

        RegisterRequest registerRequest =
                new RegisterRequest()
                .setFirstName(FirstNameText.getText())
                .setLastName(LastNameText.getText())
                .setUsername(UserNameText.getText())
                .setPassword(PasswordText.getText()).setEmail(EmailText.getText());


        SimpleClient.getClient().sendToServer(new Message(registerRequest, MessageType.REGISTER_REQUEST));

    }

    @FXML
    void returnLogin(ActionEvent event) {
        Platform.runLater(()->{
            try {
                setRoot("Login");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Subscribe
    public void OnRegister(RegisterEvent response) {
        Platform.runLater(()->{
            if (response.isSuccess()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("User registered successfully");
                alert.show();

                try {
                    setRoot("Login");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("User registration failed");
                alert.setContentText(response.getMessage());
                alert.show();
            }
        });
    }
}




