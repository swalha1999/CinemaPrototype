/**
 * Sample Skeleton for 'primary.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

public class PrimaryController {

    @FXML // fx:id="col_email"
    private TableColumn<?, ?> col_email; // Value injected by FXMLLoader

    @FXML // fx:id="col_id"
    private TableColumn<?, ?> col_id; // Value injected by FXMLLoader

    @FXML // fx:id="col_password"
    private TableColumn<?, ?> col_password; // Value injected by FXMLLoader

    @FXML // fx:id="col_type"
    private TableColumn<?, ?> col_type; // Value injected by FXMLLoader

    @FXML // fx:id="col_username"
    private TableColumn<?, ?> col_username; // Value injected by FXMLLoader

    @FXML // fx:id="table_users"
    private TableView<?> table_users; // Value injected by FXMLLoader

    @FXML
    void Add_users(ActionEvent event) {

    }

    @FXML
    void Delete(ActionEvent event) {

    }

    @FXML
    void Edit(ActionEvent event) {

    }

    @FXML
    void getSelected(MouseEvent event) {

    }

}
