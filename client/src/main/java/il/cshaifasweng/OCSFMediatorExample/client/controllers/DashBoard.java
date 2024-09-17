package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.Client;
import il.cshaifasweng.OCSFMediatorExample.client.data.CinemaView;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.GetAllCinemasEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Cinema;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class DashBoard {

    @FXML
    public void initialize() throws IOException {
        EventBus.getDefault().register(this);
        System.out.println(locationComboi.getSelectionModel().getSelectedItem());
        Message message = new Message(MessageType.GET_ALL_CINEMAS_REQUEST)
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey());
        Client.getClient().sendToServer(message);
    }

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
    private ComboBox<CinemaView> locationComboi;

    @FXML
    void GoBack(ActionEvent event) {
        // Logic to handle the "Back" button
    }

    @FXML
    void PickLocation(ActionEvent event) {
        // Send a request to the server to get all cinema

    }

    @Subscribe
    public void onGetlocation(GetAllCinemasEvent event) {
        // Extract the list of Cinema entities from the event
        List<Cinema> cinemaList = event.getCinemas();

        // Convert the list of Cinema to CinemaView
        List<CinemaView> cinemaViewList = cinemaList.stream()
                .map(CinemaView::new)  // Using the constructor that accepts Cinema
                .collect(Collectors.toList());

        // Add an "All Locations" option as a dummy CinemaView
        CinemaView allLocations = new CinemaView(null, -1, "All Locations", "", "", "", "", "");
        cinemaViewList.add(0, allLocations);  // Add "All Locations" at the top

        // Set the list of CinemaView objects into the ComboBox
        locationComboi.setItems(FXCollections.observableArrayList(cinemaViewList));

        // Optionally, select the first item ("All Locations")
        if (!cinemaViewList.isEmpty()) {
            locationComboi.getSelectionModel().selectFirst();
        }
    }
}

