/**
 * Sample Skeleton for 'AddHall.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import static il.cshaifasweng.OCSFMediatorExample.client.CinemaMain.setRoot;

public class AddHall {

    @FXML // fx:id="hallNameField"
    private TextField hallNameField; // Value injected by FXMLLoader

    @FXML // fx:id="seatsNumberField"
    private TextField seatsNumberField; // Value injected by FXMLLoader

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
