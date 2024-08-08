/**
 * Sample Skeleton for 'addView.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Movie;
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

public class AddController {
    Movie movie = new Movie();
    @FXML // fx:id="AddBtn"
    private Button AddBtn; // Value injected by FXMLLoader

    @FXML // fx:id="datePicker"
    private DatePicker datePicker; // Value injected by FXMLLoader

    @FXML // fx:id="name"
    private TextField name; // Value injected by FXMLLoader

    @FXML
    void AddMovie(ActionEvent event) throws IOException {
        if(!name.equals("") && !datePicker.getValue().toString().isEmpty()){
            movie.setName(name.getText());
            LocalDate localDate = datePicker.getValue();
            if (localDate != null) {
                Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                movie.setDate(date);
            }
            Message message = new Message(2, "add movies");
            message.addMovie(movie);
            SimpleClient.getClient().sendToServer(message);
            setRoot("primary");
        }
    }
}
