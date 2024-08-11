/**
 * Sample Skeleton for 'CinemaInfo.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CinemaInfo {

    @FXML // fx:id="AddCinemaBtn"
    private Button AddCinemaBtn; // Value injected by FXMLLoader

    @FXML // fx:id="BackBtn"
    private Button BackBtn; // Value injected by FXMLLoader

    @FXML // fx:id="EditCinemaBtn"
    private Button EditCinemaBtn; // Value injected by FXMLLoader

    @FXML // fx:id="HomeBtn"
    private Button HomeBtn; // Value injected by FXMLLoader

    @FXML // fx:id="cinemaNameColumn"
    private TableColumn<?, ?> cinemaNameColumn; // Value injected by FXMLLoader

    @FXML // fx:id="cinemaTable"
    private TableView<?> cinemaTable; // Value injected by FXMLLoader

    @FXML // fx:id="cityColumn"
    private TableColumn<?, ?> cityColumn; // Value injected by FXMLLoader

    @FXML // fx:id="detailsTable"
    private TableView<?> detailsTable; // Value injected by FXMLLoader

    @FXML // fx:id="hallColumn"
    private TableColumn<?, ?> hallColumn; // Value injected by FXMLLoader

    @FXML // fx:id="managerColumn"
    private TableColumn<?, ?> managerColumn; // Value injected by FXMLLoader

    @FXML // fx:id="seatsColumn"
    private TableColumn<?, ?> seatsColumn; // Value injected by FXMLLoader

    @FXML
    void addCinema(ActionEvent event) {

    }

    @FXML
    void editCinema(ActionEvent event) {

    }

    @FXML
    void returnBack(ActionEvent event) {

    }

    @FXML
    void returnHome(ActionEvent event) {

    }

}
