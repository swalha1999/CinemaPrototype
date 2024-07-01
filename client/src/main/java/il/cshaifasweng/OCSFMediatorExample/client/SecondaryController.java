package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.Movie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

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
        name.setText(movie.getName());
        datePicker.setValue(LocalDate.from(movie.getDate().toInstant()));
        if(!name.equals("")){
            movie.setName(name.getText());
        }
        LocalDate localDate = datePicker.getValue();
        if (localDate != null) {
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            movie.setDate(date);
        }
        Message message = new Message(2, "update movies");
        message.addMovie(movie);
        SimpleClient.getClient().sendToServer(message);
        setRoot("primary");
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}


