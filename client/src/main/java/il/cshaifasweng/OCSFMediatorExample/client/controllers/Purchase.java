package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.Client;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
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
import javafx.scene.layout.Pane;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Set;

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
  public void initialize() {
    EventBus.getDefault().register(this);
  }

  @FXML
  void ConfirmPurchase(ActionEvent event) {
    screeningData.setSeats(selectedSeats.stream().toList());
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

    if (selectedSeats != null){
      selectedSeats.clear();
    }

    if (event.getFirstObj() instanceof Set) {
      selectedSeats = (Set<Seat>) event.getFirstObj();
    }

    if (event.getSecondObj() instanceof Screening) {
      screeningData =(Screening) event.getSecondObj();
    }

    double pricePerSeat =  screeningData.getPrice();
    MovieTitleLabel.setText(screeningData.getMovie().getTitle());
    SeatNumberLabel.setText(String.valueOf(selectedSeats.size()));
    PricePerSeatLabel.setText(String.valueOf(pricePerSeat));
    TotalPriceLabel.setText(String.valueOf(selectedSeats.size() * pricePerSeat));
    DurationLabel.setText(String.valueOf(screeningData.getMovie().getDurationInMinutes()));
    MovieTimeLabel.setText(String.valueOf(screeningData.getStartingAt()));
  }
}