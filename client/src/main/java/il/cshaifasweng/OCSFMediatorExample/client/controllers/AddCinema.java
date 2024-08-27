/**
 * Sample Skeleton for 'EditCinema.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import static il.cshaifasweng.OCSFMediatorExample.client.CinemaMain.setRoot;

public class AddCinema {

    @FXML // fx:id="cityField"
    private TextField cityField; // Value injected by FXMLLoader

    @FXML // fx:id="managerField"
    private TextField managerField; // Value injected by FXMLLoader

    @FXML
    void handleBack(ActionEvent event) {
        Platform.runLater(() -> {
            setRoot("CinemaInfo");
        });
    }

    @FXML
    void handleConfirm(ActionEvent event) {

    }

}
