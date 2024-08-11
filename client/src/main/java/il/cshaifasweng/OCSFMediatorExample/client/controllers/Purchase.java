/**
 * Sample Skeleton for 'Purchase.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Purchase {

    @FXML // fx:id="CVV_Txt"
    private TextField CVV_Txt; // Value injected by FXMLLoader

    @FXML // fx:id="ConfrimPurchaseBtn"
    private Button ConfrimPurchaseBtn; // Value injected by FXMLLoader

    @FXML // fx:id="Duration_Txt"
    private TextField Duration_Txt; // Value injected by FXMLLoader

    @FXML // fx:id="HomeBtn"
    private Button HomeBtn; // Value injected by FXMLLoader

    @FXML // fx:id="MovieDetailsLabel"
    private Label MovieDetailsLabel; // Value injected by FXMLLoader

    @FXML // fx:id="MovieTime_Txt"
    private TextField MovieTime_Txt; // Value injected by FXMLLoader

    @FXML // fx:id="MovieTitleLabel"
    private Label MovieTitleLabel; // Value injected by FXMLLoader

    @FXML // fx:id="MovieTitle_txt"
    private TextField MovieTitle_txt; // Value injected by FXMLLoader

    @FXML // fx:id="NumberItemPurch_Txt"
    private TextField NumberItemPurch_Txt; // Value injected by FXMLLoader

    @FXML // fx:id="OrderId_Txt"
    private TextField OrderId_Txt; // Value injected by FXMLLoader

    @FXML // fx:id="PricePerSeat_Txt"
    private TextField PricePerSeat_Txt; // Value injected by FXMLLoader

    @FXML // fx:id="ReturnBackBtn"
    private Button ReturnBackBtn; // Value injected by FXMLLoader

    @FXML // fx:id="SeatNumber_Txt"
    private TextField SeatNumber_Txt; // Value injected by FXMLLoader

    @FXML // fx:id="TotalAmount_Txt"
    private TextField TotalAmount_Txt; // Value injected by FXMLLoader

    @FXML // fx:id="TotalPrice_Txt"
    private TextField TotalPrice_Txt; // Value injected by FXMLLoader

    @FXML // fx:id="cardNumber"
    private TextField cardNumber; // Value injected by FXMLLoader

    @FXML // fx:id="expiryDate"
    private TextField expiryDate; // Value injected by FXMLLoader

    @FXML
    void ConfromPurchase(ActionEvent event) {

    }

    @FXML
    void ReturnBack(ActionEvent event) {

    }

    @FXML
    void ReturnHome(ActionEvent event) {

    }

}
