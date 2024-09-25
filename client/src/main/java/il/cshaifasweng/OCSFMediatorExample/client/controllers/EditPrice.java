package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.events.ShowSideUIEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Screening;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Objects;

public class EditPrice {


    @FXML
    private Button ConfirmButton;

    @FXML
    private TextField PriceText;

    @FXML
    void EditPrice(ActionEvent event) {

    }

    @FXML
    public void initialize() {
        EventBus.getDefault().register(this); //TODO: add this to all controllers - please :)
    }

    @Subscribe
    public void onSideUiChange (ShowSideUIEvent event){
        if(!Objects.equals(event.getUIName(), "EditPrice")){
            return;
        }


    }

}