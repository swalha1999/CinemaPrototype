package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.Client;
import il.cshaifasweng.OCSFMediatorExample.client.data.CinemaView;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.GetAllCinemasEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.GetCinemaTicketsEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Cinema;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.MovieTicket;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
    private ComboBox<CinemaView> locationComboi;

    @FXML
    public void initialize() throws IOException {


        if (locationComboi == null) {
            System.out.println("ComboBox is null!");
        } else {
            System.out.println("ComboBox initialized.");
        }
        EventBus.getDefault().register(this);
        // Fetch and set cinemas
        Message message = new Message(MessageType.GET_ALL_CINEMAS_REQUEST)
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey());
        Client.getClient().sendToServer(message);
    }

    @FXML
    private void PickLocation(ActionEvent event) {
        // Get the selected cinema from the ComboBox
        CinemaView selectedCinema = locationComboi.getSelectionModel().getSelectedItem();
        System.out.println("Selected Cinema: " + selectedCinema);

        // Check if the selected cinema is valid and not 'All Locations'
        if (selectedCinema != null && !selectedCinema.equals(allLocations)) {
            // Retrieve the session key
            String sessionKey = SessionKeysStorage.getInstance().getSessionKey();
            System.out.println("Session Key: " + sessionKey);

            // Convert IntegerProperty to Integer
            Integer selectedCinemaId = selectedCinema.getId().getValue();
            System.out.println("Selected Cinema ID: " + selectedCinemaId);

            // Create a new message to send to the server
            Message message = new Message(MessageType.SHOW_CINEMA_INFO_REQUEST)
                    .setSessionKey(sessionKey)
                    .setDataObject(selectedCinemaId);

            System.out.println("Sending message: " + message);

            // Get the client instance and send the message
            Client client = Client.getClient();
            if (client != null) {
                client.sendToServer(message);
                System.out.println("Message sent.");
            } else {
                System.out.println("Client is not initialized.");
            }
        } else {
            System.out.println("Selected cinema is null or is 'All Locations'.");
        }
    }


    private CinemaView allLocations;

    @Subscribe
    public void onGetlocation(GetAllCinemasEvent event) {
        // Extract the list of Cinema entities from the event
        List<Cinema> cinemaList = event.getCinemas();

        // Convert the list of Cinema to CinemaView
        List<CinemaView> cinemaViewList = cinemaList.stream()
                .map(CinemaView::new)  // Using the constructor that accepts Cinema
                .collect(Collectors.toList());

        // Add an "All Locations" option as a dummy CinemaView
        allLocations = new CinemaView(null, -1, "All Locations", "", "", "", "", "");
        cinemaViewList.add(0, allLocations);  // Add "All Locations" at the top

        // Set the list of CinemaView objects into the ComboBox
        locationComboi.setItems(FXCollections.observableArrayList(cinemaViewList));

        // Optionally, select the first item ("All Locations")
        if (!cinemaViewList.isEmpty()) {
            locationComboi.getSelectionModel().selectFirst();
        }
    }

    @Subscribe
    // TODO mhmod hen al tickets al date tb3ha = null bntaim a3mlhn date zi ma bdk 3ben ma m7md idef al date 3l data base 3n tread ticket.setdate
    public void onShowCinemaInfo(GetCinemaTicketsEvent event) {
        List<MovieTicket> movieTickets = event.getTickets();
      // TODO : zbt alwd3 ia kber
        System.out.println(movieTickets);
    }

    @FXML
    private void GoBack(ActionEvent event) {
        // Handle the Go Back action
    }
}
