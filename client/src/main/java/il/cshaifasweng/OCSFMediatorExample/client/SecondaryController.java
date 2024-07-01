package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.Movie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;

import static il.cshaifasweng.OCSFMediatorExample.client.SimpleChatClient.setRoot;

public class SecondaryController {
    private Movie movie;
    @FXML // fx:id="UpdateBtn"
    private Button UpdateBtn; // Value injected by FXMLLoader

    @FXML // fx:id="datePicker"
    private DatePicker datePicker; // Value injected by FXMLLoader

    @FXML // fx:id="name"
    private TextField name; // Value injected by FXMLLoader

    @FXML
    void UpdateMovieTime(ActionEvent event) throws IOException {
        if(!name.equals("")){
            movie.setName(name.getText());
        }
        movie.setDate(datePicker);
        Message message = new Message(2, "update movies");
        message.addMovie(movie);
        SimpleClient.getClient().sendToServer(message);
        setRoot("primary");
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}


