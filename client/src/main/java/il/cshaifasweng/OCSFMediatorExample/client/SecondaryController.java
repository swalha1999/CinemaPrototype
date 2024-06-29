package il.cshaifasweng.OCSFMediatorExample.client;

import com.sun.source.tree.UsesTree;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

import static il.cshaifasweng.OCSFMediatorExample.client.SimpleChatClient.setRoot;

public class SecondaryController {

    @FXML // fx:id="AddBtn"
    private Button AddBtn; // Value injected by FXMLLoader

    @FXML
    void AddMovie(ActionEvent event) throws IOException {


        setRoot("primary");
    }

}
