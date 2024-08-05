package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.IOException;

import static il.cshaifasweng.OCSFMediatorExample.client.SimpleChatClient.setRoot;
public class LoginController {

    @FXML
    private Button LoginBtn;

    @FXML
    private Text LoginText;

    @FXML
    private PasswordField Password;

    @FXML
    private Button RegisterNewAccount;

    @FXML
    private TextField UserNameTxt;

    @FXML
    private Hyperlink forgotPassword;

    @FXML
    void LoginAccount(ActionEvent event) throws IOException {
        Message message = new Message(2, "login");
        message.setUser(new User());
        message.getUser().setUsername(UserNameTxt.getText());
        message.setData(Password.getText());
        SimpleClient.getClient().sendToServer(message);
    }

}



