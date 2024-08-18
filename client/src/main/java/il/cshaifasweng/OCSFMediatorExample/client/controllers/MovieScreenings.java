/**
 * Sample Skeleton for 'MovieScreenings.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;

public class MovieScreenings {

    @FXML // fx:id="Date_Col"
    private TableColumn<?, ?> Date_Col; // Value injected by FXMLLoader

    @FXML // fx:id="ScreeningTime_Col"
    private TableColumn<?, ?> ScreeningTime_Col; // Value injected by FXMLLoader

    @FXML // fx:id="movieImageView"
    private ImageView movieImageView; // Value injected by FXMLLoader

    @FXML // fx:id="purchaseBtn"
    private Button purchaseBtn; // Value injected by FXMLLoader

    @FXML
    void Purchase(ActionEvent event) {

    }

}
