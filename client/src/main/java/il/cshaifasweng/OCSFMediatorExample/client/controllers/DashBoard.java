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
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DashBoard {

    @FXML
    private PieChart ComplaintsTable;

    @FXML
    private LineChart<?, ?> LinksTable;

    @FXML
    private BarChart<String, Number> TicketSaleTable;

    @FXML
    private Button backBtn;

    @FXML
    private ComboBox<CinemaView> locationComboi;

    private CinemaView allLocations;

    @Subscribe
    public void onGetlocation(GetAllCinemasEvent event) {
        List<Cinema> cinemaList = event.getCinemas();
        List<CinemaView> cinemaViewList = cinemaList.stream()
                .map(CinemaView::new)
                .collect(Collectors.toList());

        allLocations = new CinemaView(null, -1, "All Locations", "", "", "", "", "");
        cinemaViewList.add(0, allLocations);

        locationComboi.setItems(FXCollections.observableArrayList(cinemaViewList));
        if (!cinemaViewList.isEmpty()) {
            locationComboi.getSelectionModel().selectFirst();
        }
    }

    @FXML
    private void initialize() {
        EventBus.getDefault().register(this);
        fetchCinemas();
    }

    private void fetchCinemas() {
        Message message = new Message(MessageType.GET_ALL_CINEMAS_REQUEST)
                .setSessionKey(SessionKeysStorage.getInstance().getSessionKey());
        Client.getClient().sendToServer(message);
    }

    @FXML
    private void PickLocation(ActionEvent event) {
        CinemaView selectedCinema = locationComboi.getSelectionModel().getSelectedItem();
        if (selectedCinema != null && !selectedCinema.equals(allLocations)) {
            String sessionKey = SessionKeysStorage.getInstance().getSessionKey();
            Integer selectedCinemaId = selectedCinema.getId().getValue();

            Message message = new Message(MessageType.SHOW_CINEMA_INFO_REQUEST)
                    .setSessionKey(sessionKey)
                    .setDataObject(selectedCinemaId);

            Client client = Client.getClient();
            if (client != null) {
                client.sendToServer(message);
            } else {
                System.out.println("Client is not initialized.");
            }
        } else {
            System.out.println("Selected cinema is null or is 'All Locations'.");
        }
    }

    @Subscribe
    public void onShowCinemaInfo(GetCinemaTicketsEvent event) {
        List<MovieTicket> movieTickets = event.getTickets();
        Platform.runLater(() -> {
        // Filter out tickets older than 30 days
        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
        List<MovieTicket> recentTickets = movieTickets.stream()
                .filter(ticket -> ticket.getScreening().getStartingAt().toLocalDate().isAfter(thirtyDaysAgo))
                .collect(Collectors.toList());

        Map<LocalDate, Long> ticketsByDate = recentTickets.stream()
                .filter(ticket -> ticket.getScreening().getStartingAt() != null)
                .collect(Collectors.groupingBy(ticket -> ticket.getScreening().getStartingAt().toLocalDate(), Collectors.counting()));

            TicketSaleTable.getData().clear();

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Daily Ticket Sales");

            ticketsByDate.forEach((date, count) -> {
                String dateString = date.toString(); // Convert LocalDateTime to String
                XYChart.Data<String, Number> data = new XYChart.Data<>(dateString, count);
                series.getData().add(data);
            });

            // Add empty data for the last 30 days
            for (int i = 0; i < 30; i++) {
                LocalDate date = LocalDate.now().minusDays(i);
                XYChart.Data<String, Number> emptyData = new XYChart.Data<>(date.toString(), 0);
                series.getData().add(emptyData);
            }

            TicketSaleTable.getData().add(series);
        });
    }
    @FXML
    private void GoBack(ActionEvent event) {
        // Handle the Go Back action
    }
}