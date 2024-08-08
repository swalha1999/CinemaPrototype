package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class Screening {

    @FXML
    private TableColumn<?, ?> AvailvleSeats_Col;

    @FXML
    private TableColumn<?, ?> BookedSeats_Col;

    @FXML
    private TableColumn<?, ?> Cinema_Col;

    @FXML
    private TableColumn<?, ?> Hall_Col;

    @FXML
    private TableColumn<?, ?> MovieId_Col;

    @FXML
    private TableView<?> MoviesTable;

    @FXML
    private TableColumn<?, ?> ScreeningDate_Col;

    @FXML
    private TableColumn<?, ?> StartTime_Col;

}
