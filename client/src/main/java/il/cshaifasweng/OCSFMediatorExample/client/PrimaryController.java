/**
 * Sample Skeleton for 'primary.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.client.events.DeleteMoviesEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.MessageEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.StickyMessageEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.UpdateMoviesEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static il.cshaifasweng.OCSFMediatorExample.client.SimpleChatClient.setRoot;

public class PrimaryController {

    @FXML // fx:id="AddBtn"
    private Button AddBtn; // Value injected by FXMLLoader

    @FXML // fx:id="DeleteBtn"
    private Button DeleteBtn; // Value injected by FXMLLoader

    @FXML // fx:id="UpdateBtn"
    private Button UpdateBtn; // Value injected by FXMLLoader

    @FXML // fx:id="MovieName_col"
    private TableColumn<?, ?> MovieName_col; // Value injected by FXMLLoader

    @FXML // fx:id="ScreeningTime_col"
    private TableColumn<?, ?> ScreeningTime_col; // Value injected by FXMLLoader

    @FXML // fx:id="table_users"
    private TableView<Movie> table_users; // Value injected by FXMLLoader

    @FXML
    public void initialize() {
        EventBus.getDefault().register(this);
        MovieName_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        ScreeningTime_col.setCellValueFactory(new PropertyValueFactory<>("date"));

        try {
            SimpleClient.getClient().sendToServer(new Message(1,"get all movies"));
        } catch (IOException e) {
            System.out.println("Error sending message to server to get all the movies");
        }

    }

    private void addUpdatedMoviesToTableView(List<Movie> movies) {
        Set<Movie> existingMovies = new HashSet<>(table_users.getItems());
        for (Movie movie : movies) {
            if (!existingMovies.contains(movie)) {
                table_users.getItems().add(movie);
                existingMovies.add(movie);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateMoviesEven(UpdateMoviesEvent event) {
        List<Movie> movies = event.getMessage().getMovies();
        addUpdatedMoviesToTableView(movies);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDeleteMoviesEven(DeleteMoviesEvent event) {
        List<Movie> moviesToDelete = event.getMessage().getMovies();
        table_users.getItems().removeAll(moviesToDelete);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStickyMessageEvent(StickyMessageEvent event) {
        EventBus.getDefault().removeStickyEvent(event);
    }

    public void cleanup() {
        EventBus.getDefault().unregister(this);
    }

    @FXML
    void AddMovies(ActionEvent event) throws IOException {
        SimpleClient.getClient().sendToServer(new Message(1,"get all movies"));

    }

    @FXML
    void Delete(ActionEvent event) throws IOException {
        Message message = new Message(1,"delete movies");
        message.addMovie(getSelectedMovie());
        message.setData("" + message.getMovies().getFirst().getId());
        System.out.println("Deleting movies :" + message.getMessage() + " movies: " + message.getMovies().getFirst().getId());
        SimpleClient.getClient().sendToServer(message);
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

    public Movie getSelectedMovie(){
        return table_users.getSelectionModel().getSelectedItem();
    }
}
