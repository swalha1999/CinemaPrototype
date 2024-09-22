package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.Client;
import il.cshaifasweng.OCSFMediatorExample.client.data.CinemaView;
import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.GetAllCinemasEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.GetAllSupportTicketsEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.GetCinemaSupportTicketsEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.GetCinemaTicketsEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Cinema;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.MovieTicket;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.SupportTicket;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class DashBoard {


    @FXML
    private BarChart<String, Number> LinksTable;

    @FXML
    private BarChart<String, Number> TicketSaleTable;

    @FXML
    private BarChart<String, Number> SupportTicketsTable;

    @FXML
    private Button backBtn;

    @FXML
    private ComboBox<CinemaView> locationComboBox;

    private CinemaView allLocations;

    @Subscribe
    public void onGetlocation(GetAllCinemasEvent event) {
        Platform.runLater(() -> {
            List<Cinema> cinemaList = event.getCinemas();
            List<CinemaView> cinemaViewList = cinemaList.stream()
                    .map(CinemaView::new)
                    .collect(Collectors.toList());

            allLocations = new CinemaView(null, -1, "All Locations", "", "", "", "", "");
            cinemaViewList.add(0, allLocations);

            locationComboBox.setItems(FXCollections.observableArrayList(cinemaViewList));
            if (!cinemaViewList.isEmpty()) {
                locationComboBox.getSelectionModel().selectFirst();
            }
        });
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
        CinemaView selectedCinema = locationComboBox.getSelectionModel().getSelectedItem();
        if (selectedCinema != null && !selectedCinema.equals(allLocations)) {
            String sessionKey = SessionKeysStorage.getInstance().getSessionKey();
            Integer selectedCinemaId = selectedCinema.getId().getValue();

            Message message = new Message(MessageType.CINEMA_TICKETS_INFO_REQUEST)
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
        if (selectedCinema != null && !selectedCinema.equals(allLocations)) {
            String sessionKey = SessionKeysStorage.getInstance().getSessionKey();
            Integer selectedCinemaId = selectedCinema.getId().getValue();

            Message message = new Message(MessageType.CINEMA_SUPPORT_TICKETS_INFO_REQUEST)
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
        makeChart(movieTickets, TicketSaleTable);
        makeChart(getOnlineTickets(movieTickets), LinksTable);
    }

    @Subscribe
    public void onGetCinemaSupportTicketEvent(GetCinemaSupportTicketsEvent event) {
        List<SupportTicket> supportTickets = event.getSupportTickets();
        makeSupportChart(supportTickets, SupportTicketsTable);
    }

    public List<MovieTicket> getOnlineTickets(List<MovieTicket> movieTickets) {
        return movieTickets.stream()
                .filter(ticket -> ticket.getScreening().getIsOnlineScreening())  // Directly check if the screening is online
                .collect(Collectors.toList());
    }

    public void makeChart(List<MovieTicket> movieTickets, BarChart<String, Number> TicketSaleTable) {
        // Debug: Print out the size of the movieTickets list
        System.out.println("Total movie tickets: " + movieTickets.size());

        // Prepare a series for the chart
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Tickets Sold Daily");

        // Get the current month and year to filter tickets by the current month
        LocalDate now = LocalDate.now();
        int currentMonth = now.getMonthValue();
        int currentYear = now.getYear();

        // Initialize an array to store the number of tickets per day of the month (1-31)
        long[] ticketsPerDay = new long[now.lengthOfMonth()];  // Adjusted to handle the length of the current month

        // Group tickets by the day of the month, ensuring they are from the current month and year
        movieTickets.stream()
                .filter(ticket -> {
                    LocalDate screeningDate = ticket.getScreening().getStartingAt().toLocalDate();
                    return screeningDate.getMonthValue() == currentMonth && screeningDate.getYear() == currentYear;
                })
                .forEach(ticket -> {
                    int dayOfMonth = ticket.getScreening().getStartingAt().getDayOfMonth();
                    ticketsPerDay[dayOfMonth - 1]++;
                });

        // Add the data for each day of the month to the series (1 to 31)
        for (int day = 1; day <= now.lengthOfMonth(); day++) {
            series1.getData().add(new XYChart.Data<>(String.valueOf(day), ticketsPerDay[day - 1]));
        }

        // Update the chart in the JavaFX thread
        Platform.runLater(() -> {
            TicketSaleTable.getData().clear();  // Clear any existing data
            TicketSaleTable.getData().add(series1);  // Add the new series
        });
    }

    public void makeSupportChart(List<SupportTicket> supportTickets, BarChart<String, Number> SupportTicketsTable) {
        // Debug: Print out the size of the supportTickets list
        System.out.println("Total support tickets: " + supportTickets.size());

        // Prepare a series for the chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Support Tickets Daily");

        // Get the current month and year to filter tickets by the current month
        LocalDate now = LocalDate.now();
        int currentMonth = now.getMonthValue();
        int currentYear = now.getYear();

        // Initialize an array to store the number of support tickets per day of the month (1-31)
        long[] ticketsPerDay = new long[now.lengthOfMonth()];  // Adjusted to handle the length of the current month

        // Group support tickets by the day of the month, ensuring they are from the current month and year
        supportTickets.stream()
                .filter(ticket -> {
                    LocalDate createdDate = ticket.getCreatedDate().toLocalDate();
                    return createdDate.getMonthValue() == currentMonth && createdDate.getYear() == currentYear;
                })
                .forEach(ticket -> {
                    int dayOfMonth = ticket.getCreatedDate().getDayOfMonth();
                    ticketsPerDay[dayOfMonth - 1]++;
                });

        // Add the data for each day of the month to the series (1 to 31)
        for (int day = 1; day <= now.lengthOfMonth(); day++) {
            series.getData().add(new XYChart.Data<>(String.valueOf(day), ticketsPerDay[day - 1]));
        }

        // Update the chart in the JavaFX thread
        Platform.runLater(() -> {
            SupportTicketsTable.getData().clear();  // Clear any existing data
            SupportTicketsTable.getData().add(series);  // Add the new series
        });
    }


    public void GoBack(ActionEvent actionEvent) {

    }
}
