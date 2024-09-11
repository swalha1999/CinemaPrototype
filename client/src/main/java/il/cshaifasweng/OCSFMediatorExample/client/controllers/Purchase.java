package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.Client;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.PurchaseMovieEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.ShowNotificationEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.ShowSideUIEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Screening;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Seat;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.time.LocalDateTime;
import java.util.Set;

import static il.cshaifasweng.OCSFMediatorExample.client.utils.PaymentUtil.isValidCard;
import static il.cshaifasweng.OCSFMediatorExample.client.utils.UiUtil.showNotification;
import static il.cshaifasweng.OCSFMediatorExample.client.utils.UiUtil.showSideUI;

public class Purchase {
  private Screening screeningData;
  private Set<Seat> selectedSeats;

  @FXML
  private TextField CVV_Txt;

  @FXML
  private Button ConfirmPurchaseBtn;

  @FXML
  private Label DurationLabel;

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
  private Label TotalAmountLabel;

  @FXML
  private Label NumberItemPurchLabel;

  @FXML
  public void initialize() {
    EventBus.getDefault().register(this);
  }

  @FXML
  void ConfirmPurchase(ActionEvent event) {
    // Check if the user has selected seats
    if (selectedSeats == null || selectedSeats.isEmpty()) {
      showNotification("Please select seats to purchase", false);
      return;
    }

    // Validate the credit card using the CVV input
    if (!isValidCard(CVV_Txt.getText())) {
      showNotification("The Card Number is Not Valid, Please Try Again", false);
      return;
    }

    // Assign selected seats to the screening
    screeningData.setSeats(selectedSeats.stream().toList());

    // Prepare details for the purchase event
    String movieTitle = screeningData.getMovie().getTitle();
    LocalDateTime startTime = screeningData.getStartingAt();
    LocalDateTime endTime = startTime.plusMinutes(screeningData.getTimeInMinute());

    // Check if the screening is online or in-person
    String movieLink = screeningData.getIsOnlineScreening()
            ? "https://cinema.example.com/watch/" + screeningData.getMovie().getId()
            : "In-Person Screening at " + screeningData.getCinema().getName();

    // Create the purchase movie event with movie details
    PurchaseMovieEvent purchaseMovieEvent = new PurchaseMovieEvent(
            movieTitle,
            startTime,
            endTime,
            SessionKeysStorage.getInstance().getUserEmail()
    );

    // **Post the event to notify other components (e.g., MyInbox)**
    EventBus.getDefault().post(purchaseMovieEvent);

    // Notify the user about the successful purchase
    showNotification("Purchase successful! A confirmation email has been sent to your inbox.", true);

    // Send the purchase request to the server
    Message request = new Message(MessageType.PURCHASE_TICKETS_REQUEST)
            .setDataObject(screeningData)
            .setSessionKey(SessionKeysStorage.getInstance().getSessionKey());

    Client.getClient().sendToServer(request);
  }


  @FXML
  void ReturnBack(ActionEvent event) {
    showSideUI("SeatPicker");
  }

  @Subscribe
  public void onShowSideUI(ShowSideUIEvent event) {
    if (!event.getUIName().equals("Purchase")) {
      return;
    }

    if (event.getFirstObj() instanceof Set) {
      System.out.println("First obj is set");
      selectedSeats = (Set<Seat>) event.getFirstObj();
    }

    if (event.getSecondObj() instanceof Screening) {
      System.out.println("Second obj is screening");
      screeningData = (Screening) event.getSecondObj();
    }

    double pricePerSeat =  screeningData.getPrice();
    MovieTitleLabel.setText(screeningData.getMovie().getTitle());
    SeatNumberLabel.setText(String.valueOf(selectedSeats.size()));
    PricePerSeatLabel.setText(String.valueOf(pricePerSeat));
    TotalPriceLabel.setText(String.valueOf(selectedSeats.size() * pricePerSeat));
    DurationLabel.setText(String.valueOf(screeningData.getMovie().getDurationInMinutes()));
    MovieTimeLabel.setText(String.valueOf(screeningData.getStartingAt()));
    OrderIdLabel.setText(String.valueOf(screeningData.getId()));
    TotalAmountLabel.setText(String.valueOf(selectedSeats.size() * pricePerSeat));
    NumberItemPurchLabel.setText(String.valueOf(selectedSeats.size()));
  }
}