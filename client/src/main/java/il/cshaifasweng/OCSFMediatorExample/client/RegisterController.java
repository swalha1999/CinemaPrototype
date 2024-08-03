/**
 * Sample Skeleton for 'Register.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class RegisterController {

    @FXML // fx:id="ConfPasswordLabel"
    private Label ConfPasswordLabel; // Value injected by FXMLLoader

    @FXML // fx:id="ConfPasswordText"
    private TextField ConfPasswordText; // Value injected by FXMLLoader

    @FXML // fx:id="ConfirmationBut"
    private Button ConfirmationBut; // Value injected by FXMLLoader

    @FXML // fx:id="ConfirmationBut1"
    private Button ConfirmationBut1; // Value injected by FXMLLoader

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

    @FXML // fx:id="chechMark"
    private ImageView chechMark; // Value injected by FXMLLoader

    @FXML // fx:id="firstNameLabel"
    private Label firstNameLabel; // Value injected by FXMLLoader

    @FXML
    void ConfrimRegister(ActionEvent event) throws IOException {

        Message message = new Message(2, "register a new user");
        message.setUser(new User());
        message.getUser().setFirstName(FirstNameText.getText());
        message.getUser().setLastName(LastNameText.getText());
        message.getUser().setUsername(UserNameText.getText());
        message.setData(PasswordText.getText());
        SimpleClient.getClient().sendToServer(message);

    }

}
