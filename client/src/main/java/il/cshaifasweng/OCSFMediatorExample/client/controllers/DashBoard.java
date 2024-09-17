package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class DashBoard {

    @FXML
    private PieChart ComplaintsTable;

    @FXML
    private LineChart<?, ?> LinksTable;

    @FXML
    private BarChart<?, ?> TicketSaleTable;

    @FXML
    private Label TopLabel;

    @FXML
    private Button backBtn;

    @FXML
    private ComboBox<?> locationComboi;

    @FXML
    void GoBack(ActionEvent event) {

    }

    @FXML
    void PickLocation(ActionEvent event) {

    }

}
