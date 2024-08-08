/**
 * Sample Skeleton for 'Login.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.LoginEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.UserRole;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.requests.LoginRequest;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

import static il.cshaifasweng.OCSFMediatorExample.client.SimpleChatClient.setRoot;

public class LoginController {

    @FXML // fx:id="LoginBtn"
    private Button LoginBtn; // Value injected by FXMLLoader

    @FXML // fx:id="LoginText"
    private Text LoginText; // Value injected by FXMLLoader

    @FXML // fx:id="Password"
    private PasswordField Password; // Value injected by FXMLLoader

    @FXML // fx:id="UserNameTxt"
    private TextField UserNameTxt; // Value injected by FXMLLoader

    @FXML // fx:id="forgotPassword"
    private Hyperlink forgotPassword; // Value injected by FXMLLoader

    @FXML // fx:id="registerBtn"
    private Button registerBtn; // Value injected by FXMLLoader

    @FXML
    public void initialize() {
        EventBus.getDefault().register(this); //TODO: add this to all controllers - please :)
    }

    @FXML
    void LoginAccount(ActionEvent event) throws IOException {
        LoginRequest loginRequest = new LoginRequest(UserNameTxt.getText(), Password.getText());
        SimpleClient.getClient().sendToServer(new Message(loginRequest, MessageType.LOGIN_REQUEST));
    }

    @FXML
    void createNewAccount(ActionEvent event) {
        Platform.runLater(()->{
            try {
                setRoot("Register");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Subscribe
    public void  onLoginEvent(LoginEvent response) {
        SessionKeysStorage.getInstance()
                .clearSession()
                .setSessionKey(response.getSessionKey())
                .setUsername(response.getUsername())
                .setRole(response.getRole());

        System.out.println(SessionKeysStorage.getInstance().toString());

        Platform.runLater(()->{
            try {
                if (response.isSuccess()) {
                    if( response.getRole() == UserRole.USER){
                        setRoot("UserMain");
                    } else {
                        setRoot("AdminMain");
                    }
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Login Failed");
                    alert.setHeaderText("Login Failed");
                    alert.setContentText(response.getMessage());
                    alert.show();
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}




