/**
 * Sample Skeleton for 'primary.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.client.events.MessageEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.StickyMessageEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.UpdateMoviesEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

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
        AddBtn.setOnAction(event -> {
            try {
                SimpleClient.getClient().sendToServer(new Message(1,"update all movies"));
            } catch (IOException e) {
                System.out.println("Cant send message to server");
            }
        }); // replace with server calls
        MovieName_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        ScreeningTime_col.setCellValueFactory(new PropertyValueFactory<>("date"));
        //DeleteBtn.setOnAction(event -> EventBus.getDefault().postSticky(new StickyMessageEvent(new Message(1,"Hello world with a sticy maseaagee" )))); // replace with server calls
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
    public void onMessageEvent(UpdateMoviesEvent event) {
        List<Movie> movies = event.getMessage().getMovies();
        addUpdatedMoviesToTableView(movies);
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

    }

    @FXML
    void Delete(ActionEvent event) throws IOException {
        Message message = new Message(1,"delete movies");
        message.addMovie(getSelectedMovie());
        SimpleClient.getClient().sendToServer(message);
    }

    @FXML
    void Update(ActionEvent event) throws IOException {
        Movie movie = getSelectedMovie();
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("secondary.fxml"));
//            Parent root = loader.load();
//
//            // Get the controller of the secondary scene
//            SecondaryController secondaryController = loader.getController();
//
//            // Pass the data to the secondary controller
//            secondaryController.setData(inputField.getText());
//
//            Stage stage = (Stage) inputField.getScene().getWindow();
//            stage.setScene(new Scene(root));
//            stage.setTitle("Secondary Scene");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
            setRoot("secondary");
    }

    public Movie getSelectedMovie(){
        return table_users.getSelectionModel().getSelectedItem();
    }
}
