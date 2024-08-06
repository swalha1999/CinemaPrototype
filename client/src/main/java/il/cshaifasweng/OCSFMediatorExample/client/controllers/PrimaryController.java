package il.cshaifasweng.OCSFMediatorExample.client.controllers;


import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.client.events.AddMoviesEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.DeleteMoviesEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.UpdateMoviesEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.Movie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import il.cshaifasweng.OCSFMediatorExample.entities.Messages.Message;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static il.cshaifasweng.OCSFMediatorExample.client.SimpleChatClient.setRoot;

public class PrimaryController {

    @FXML
    private Button AddBtn; // Value injected by FXMLLoader

    @FXML
    private Button DeleteBtn; // Value injected by FXMLLoader

    @FXML
    private Button UpdateBtn; // Value injected by FXMLLoader

    @FXML
    private TableColumn<Movie, String> MovieName_col; // Value injected by FXMLLoader

    @FXML
    private TableColumn<Movie, String> ScreeningTime_col; // Value injected by FXMLLoader

    @FXML
    private TableView<Movie> table_users; // Value injected by FXMLLoader

    @FXML
    public void initialize() {
        EventBus.getDefault().register(this);
        MovieName_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        ScreeningTime_col.setCellValueFactory(new PropertyValueFactory<>("date"));

        try {
            SimpleClient.getClient().sendToServer(new Message(1, "get all movies"));
        } catch (IOException e) {
            System.err.println("Error sending message to server to get all the movies: " + e.getMessage());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAddMoviesEvent(AddMoviesEvent event) {
        List<Movie> moviesToUpdate = event.getMessage().getMovies();
        Map<Integer, Movie> existingMoviesMap = new HashMap<>();
        for (Movie existingMovie : table_users.getItems()) {
            existingMoviesMap.put(existingMovie.getId(), existingMovie);
        }

        for (Movie updatedMovie : moviesToUpdate) {
            Movie existingMovie = existingMoviesMap.get(updatedMovie.getId());
            if (existingMovie != null) {
                existingMovie.setName(updatedMovie.getName());
                existingMovie.setDate(updatedMovie.getDate());
            } else {
                table_users.getItems().add(updatedMovie);
            }
        }

        // Refresh the table view
        table_users.refresh();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDeleteMoviesEvent(DeleteMoviesEvent event) {
        List<Movie> moviesToDelete = event.getMessage().getMovies();
        table_users.getItems().removeAll(moviesToDelete);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateMoviesEvent(UpdateMoviesEvent event) {
        onAddMoviesEvent(new AddMoviesEvent(event.getMessage()));
    }

    public void cleanup() {
        EventBus.getDefault().unregister(this);
    }

    @FXML
    void AddMovies(ActionEvent event) throws IOException {
        setRoot("addView");
    }

    @FXML
    void Delete(ActionEvent event) throws IOException {
        Movie selectedMovie = getSelectedMovie();
        if (selectedMovie != null) {
            Message message = new Message(1, "delete movies");
            message.addMovie(selectedMovie);
            message.setData(String.valueOf(selectedMovie.getId()));
            System.out.println("Deleting movie: " + message.getMessage() + " movie ID: " + selectedMovie.getId());
            try {
                SimpleClient.getClient().sendToServer(message);
            } catch (IOException e) {
                System.err.println("Error sending delete message to server: " + e.getMessage());
            }
        }
    }

    @FXML
    void Update(ActionEvent event) throws IOException {
        Movie movie = getSelectedMovie();
        if (movie != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("secondary.fxml"));
            Parent root = loader.load();
            SecondaryController controller = loader.getController();
            controller.setMovie(movie);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    public Movie getSelectedMovie() {
        return table_users.getSelectionModel().getSelectedItem();
    }
}
