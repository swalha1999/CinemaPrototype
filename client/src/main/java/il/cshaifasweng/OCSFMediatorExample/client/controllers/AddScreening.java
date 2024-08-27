/**
 * Sample Skeleton for 'AddScreening.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import static il.cshaifasweng.OCSFMediatorExample.client.CinemaMain.setRoot;

public class AddScreening {

    @FXML // fx:id="endTimeField"
    private TextField endTimeField; // Value injected by FXMLLoader

    @FXML // fx:id="screeningNameField"
    private TextField screeningNameField; // Value injected by FXMLLoader

    @FXML // fx:id="startTimeField"
    private TextField startTimeField; // Value injected by FXMLLoader

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
