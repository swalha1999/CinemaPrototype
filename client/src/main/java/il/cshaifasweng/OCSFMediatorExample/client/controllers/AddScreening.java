

package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import static il.cshaifasweng.OCSFMediatorExample.client.utils.UiUtil.showSideUI;

public class AddScreening {

    @FXML // fx:id="movieDate"
    private DatePicker movieDate; // Value injected by FXMLLoader

    @FXML // fx:id="movieNameCombobox"
    private ComboBox<?> movieNameCombobox; // Value injected by FXMLLoader




    @FXML
    void handleBack(ActionEvent event) {
        Platform.runLater(() -> {
            showSideUI("CinemaInfo");
        });
    }

    @FXML
    void handleConfirm(ActionEvent event) {

    }

}
