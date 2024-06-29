/**
 * Sample Skeleton for 'primary.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

import static il.cshaifasweng.OCSFMediatorExample.client.SimpleChatClient.setRoot;

public class PrimaryController {

    @FXML // fx:id="AddBtn"
    private Button AddBtn; // Value injected by FXMLLoader

    @FXML // fx:id="DeleteBtn"
    private Button DeleteBtn; // Value injected by FXMLLoader

    @FXML // fx:id="UpdateBtn"
    private Button UpdateBtn; // Value injected by FXMLLoader

    @FXML // fx:id="col_email"
    private TableColumn<?, ?> col_email; // Value injected by FXMLLoader

    @FXML // fx:id="col_username"
    private TableColumn<?, ?> col_username; // Value injected by FXMLLoader

    @FXML // fx:id="table_users"
    private TableView<?> table_users; // Value injected by FXMLLoader

    @FXML
    void AddMovies(ActionEvent event) throws IOException {
        setRoot("secondary");
    }

    @FXML
    void Delete(ActionEvent event) {

    }

    @FXML
    void Update(ActionEvent event) {

    }

    @FXML
    void getSelected(MouseEvent event) {

    }

}
