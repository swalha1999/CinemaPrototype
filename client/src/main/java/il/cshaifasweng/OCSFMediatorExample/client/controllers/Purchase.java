package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.events.ShowSideUIEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Movie;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static il.cshaifasweng.OCSFMediatorExample.client.utils.UiUtil.showSideUI;

public class Purchase {
  private Movie movieData; // To hold the movie data
  private int seatsNum;

  @FXML // fx:id="CVV_Txt"
  private TextField CVV_Txt; // Value injected by FXMLLoader

  @FXML // fx:id="ConfirmPurchaseBtn"
  private Button ConfirmPurchaseBtn; // Value injected by FXMLLoader

  @FXML // fx:id="DurationLabel"
  private Label DurationLabel; // Value injected by FXMLLoader

  @FXML // fx:id="HomeBtn"
  private Button HomeBtn; // Value injected by FXMLLoader

  @FXML // fx:id="MovieTimeLabel"
  private Label MovieTimeLabel; // Value injected by FXMLLoader

  @FXML // fx:id="MovieTitleLabel"
  private Label MovieTitleLabel; // Value injected by FXMLLoader

  @FXML // fx:id="NumberItemPurchLabel"
  private Label NumberItemPurchLabel; // Value injected by FXMLLoader

  @FXML // fx:id="OrderIdLabel"
  private Label OrderIdLabel; // Value injected by FXMLLoader

  @FXML // fx:id="PricePerSeatLabel"
  private Label PricePerSeatLabel; // Value injected by FXMLLoader

  @FXML // fx:id="ReturnBackBtn"
  private Button ReturnBackBtn; // Value injected by FXMLLoader

  @FXML // fx:id="SeatNumberLabel"
  private Label SeatNumberLabel; // Value injected by FXMLLoader

  @FXML // fx:id="TotalAmountLabel"
  private Label TotalAmountLabel; // Value injected by FXMLLoader

  @FXML // fx:id="TotalPriceLabel"
  private Label TotalPriceLabel; // Value injected by FXMLLoader

  @FXML // fx:id="cardNumber"
  private TextField cardNumber; // Value injected by FXMLLoader

  @FXML // fx:id="expiryDate"
  private TextField expiryDate; // Value injected by FXMLLoader

  @FXML
  void ConfirmPurchase(ActionEvent event) {
    // Handle the purchase confirmation logic
  }

  @FXML
  void ReturnBack(ActionEvent event) {
    showSideUI("SeatPicker");
  }

  @FXML
  void ReturnHome(ActionEvent event) {
    // Handle returning home
  }

  @Subscribe
  public void onShowSideUI(ShowSideUIEvent event) {
    if (event.getUIName().equals("Purchase") && event.getSecondObj() instanceof Movie) {
      movieData = (Movie) event.getSecondObj();
      MovieTitleLabel.setText(movieData.getTitle());
      int seatsnum =  (int)event.getFirstObj();
      TotalAmountLabel.setText(toString());
      SeatNumberLabel.setText(String.valueOf(seatsnum));
    }
  }

  @FXML
  public void initialize() {
    EventBus.getDefault().register(this);
  }

}
