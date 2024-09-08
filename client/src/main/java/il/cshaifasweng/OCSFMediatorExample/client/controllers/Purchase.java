package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.events.ShowSideUIEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Screening;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Set;

import static il.cshaifasweng.OCSFMediatorExample.client.utils.UiUtil.showSideUI;

public class Purchase {
  private Screening screeningData; // To hold the screening data

  @FXML
  private TextField CVV_Txt;

  @FXML
  private Button ConfirmPurchaseBtn;

  @FXML
  private Label DurationLabel;

  @FXML
  private Button HomeBtn;

  @FXML
  private Label MovieTimeLabel;

  @FXML
  private Label MovieTitleLabel;

  @FXML
  private Label OrderIdLabel;

  @FXML
  private Label PricePerSeatLabel;

  @FXML
  private Button ReturnBackBtn;

  @FXML
  private Label SeatNumberLabel;

  @FXML
  private Label TotalPriceLabel;

  @FXML
  private TextField cardNumber;

  @FXML
  private TextField expiryDate;

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
    showSideUI("HomeScreen");
  }

  @Subscribe
  public void onShowSideUI(ShowSideUIEvent event) {
    if (!event.getUIName().equals("Purchase")) {
      return;
    }
      Set<Pane> selectedSeats = null;
      if (event.getFirstObj() instanceof Set) {
        selectedSeats = (Set<Pane>) event.getFirstObj();
        SeatNumberLabel.setText(String.valueOf(selectedSeats.size()));  // Update seat count label
      }

      if (event.getSecondObj() instanceof Screening) {
       screeningData =(Screening) event.getSecondObj() ;// Retrieve movie data
        MovieTitleLabel.setText(screeningData.getMovie().getTitle());  // Update movie title label
      }

      // Update pricing (assuming you have a method to get the price per seat)
      double pricePerSeat =  screeningData.getPrice() ;  // Default to 10.0 if no screeningData
      PricePerSeatLabel.setText(String.valueOf(pricePerSeat));
      TotalPriceLabel.setText(String.valueOf(selectedSeats.size() * pricePerSeat));
      DurationLabel.setText(String.valueOf(screeningData.getMovie().getDurationInMinutes()));
      MovieTimeLabel.setText(String.valueOf(screeningData.getStartingAt()));
  }

  @FXML
  public void initialize() {
    EventBus.getDefault().register(this);
  }

  public void dispose() {
    EventBus.getDefault().unregister(this);
  }
}