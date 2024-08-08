package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class AddMoviesController {
    @FXML // fx:id="MovieID_Label"
    private Label MovieID_Label; // Value injected by FXMLLoader

    @FXML // fx:id="MovieId_Txt"
    private TextField MovieId_Txt; // Value injected by FXMLLoader

    @FXML // fx:id="MovieNameCol"
    private TableColumn<?, ?> MovieNameCol; // Value injected by FXMLLoader

    @FXML // fx:id="MovieName_Label"
    private Label MovieName_Label; // Value injected by FXMLLoader

    @FXML // fx:id="MovieName_Txt"
    private TextField MovieName_Txt; // Value injected by FXMLLoader

    @FXML // fx:id="MoviesTable"
    private TableView<?> MoviesTable; // Value injected by FXMLLoader

    @FXML // fx:id="NotTableAnchor"
    private AnchorPane NotTableAnchor; // Value injected by FXMLLoader

    @FXML // fx:id="OnlineCol"
    private TableColumn<?, ?> OnlineCol; // Value injected by FXMLLoader

    @FXML // fx:id="Price_Label"
    private Label Price_Label; // Value injected by FXMLLoader

    @FXML // fx:id="Price_Txt"
    private TextField Price_Txt; // Value injected by FXMLLoader

    @FXML // fx:id="ScreeningDateCol"
    private TableColumn<?, ?> ScreeningDateCol; // Value injected by FXMLLoader

    @FXML // fx:id="ScreeningDate_Label"
    private Label ScreeningDate_Label; // Value injected by FXMLLoader

    @FXML // fx:id="ScreeningDate_Txt"
    private TextField ScreeningDate_Txt; // Value injected by FXMLLoader

    @FXML // fx:id="TableAnchor"
    private AnchorPane TableAnchor; // Value injected by FXMLLoader

    @FXML // fx:id="TicketPriceCol"
    private TableColumn<?, ?> TicketPriceCol; // Value injected by FXMLLoader

    @FXML // fx:id="TicketPrice_txt"
    private TextField TicketPrice_txt; // Value injected by FXMLLoader

    @FXML // fx:id="Ticket_Price_Label"
    private Label Ticket_Price_Label; // Value injected by FXMLLoader

    @FXML // fx:id="UrlCol"
    private TableColumn<?, ?> UrlCol; // Value injected by FXMLLoader

    public void ImportPicture(ActionEvent actionEvent) {

    }
}
