/**
 * Sample Skeleton for 'Login.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.entities.Messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

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
    void LoginAccount(ActionEvent event) throws IOException {
        Message message = new Message(2, "login");
        message.setUser(new User());
        //message.getUser().setUsername(UserNameTxt.getText());
        message.setData(Password.getText());
        SimpleClient.getClient().sendToServer(message);
        Platform.runLater(()->{
            try {
                setRoot("primary");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
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

}




