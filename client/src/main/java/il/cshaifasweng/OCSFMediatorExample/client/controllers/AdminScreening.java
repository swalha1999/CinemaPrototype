/**
 * Sample Skeleton for 'AdminScreening.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AdminScreening {

    @FXML // fx:id="AvailableSeats_Col"
    private TableColumn<?, ?> AvailableSeats_Col; // Value injected by FXMLLoader

    @FXML // fx:id="BookedSeats_Col"
    private TableColumn<?, ?> BookedSeats_Col; // Value injected by FXMLLoader

    @FXML // fx:id="Cinema_Col"
    private TableColumn<?, ?> Cinema_Col; // Value injected by FXMLLoader

    @FXML // fx:id="Hall_Col"
    private TableColumn<?, ?> Hall_Col; // Value injected by FXMLLoader

    @FXML // fx:id="MovieId_Col"
    private TableColumn<?, ?> MovieId_Col; // Value injected by FXMLLoader

    @FXML // fx:id="MoviesTable"
    private TableView<?> MoviesTable; // Value injected by FXMLLoader

    @FXML // fx:id="ScreeningDate_Col"
    private TableColumn<?, ?> ScreeningDate_Col; // Value injected by FXMLLoader

    @FXML // fx:id="StartTime_Col"
    private TableColumn<?, ?> StartTime_Col; // Value injected by FXMLLoader

}
