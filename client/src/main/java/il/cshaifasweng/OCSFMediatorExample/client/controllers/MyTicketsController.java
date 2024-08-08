/**
 * Sample Skeleton for 'MyTickets.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.client.data.MovieView;
import il.cshaifasweng.OCSFMediatorExample.client.data.TicketView;
import il.cshaifasweng.OCSFMediatorExample.client.events.GetAllMoviesEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;

public class MyTicketsController {

    @FXML // fx:id="Id_Col"
    private TableColumn<TicketView, ?> Id_Col; // Value injected by FXMLLoader

    @FXML // fx:id="Screening_Col"
    private TableColumn<TicketView, ?> Screening_Col; // Value injected by FXMLLoader

    @FXML // fx:id="SeatNumber_Col"
    private TableColumn<TicketView, ?> SeatNumber_Col; // Value injected by FXMLLoader

    @FXML // fx:id="TicketsTable"
    private TableView<TicketView> TicketsTable; // Value injected by FXMLLoader

    @FXML
    public void initialize() {
        EventBus.getDefault().register(this);
        Id_Col.setCellValueFactory(new PropertyValueFactory<>("Id"));
        Screening_Col.setCellValueFactory(new PropertyValueFactory<>("Screening"));
        SeatNumber_Col.setCellValueFactory(new PropertyValueFactory<>("SeatNumber"));

        try {
            SimpleClient.getClient().sendToServer(new Message(1, "get all movies"));
        } catch (IOException e) {
            System.err.println("Error sending message to server to get all the movies: " + e.getMessage());
        }
    }

    @Subscribe
    public void onGetAllTicketsEvent(GetAllMoviesEvent response){
        Platform.runLater(()->{
            List<Movie> movies = response.getMovies();
            //MoviesTable.getItems().clear();
            for (Movie movie : movies) {
                //MoviesTable.getItems().add(new MovieView(movie));
            }
        });
    }
}
