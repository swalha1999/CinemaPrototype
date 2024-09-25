package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.ShowSideUIEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.SupportTicket;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class CustomerSupportResponsePage {

    private User user;

    private SupportTicket ticket;
    @FXML // fx:id="replyBtn"
    private Button replyBtn; // Value injected by FXMLLoader

    @FXML // fx:id="replyDescription"
    private TextArea replyDescription; // Value injected by FXMLLoader

    @FXML // fx:id="userNameLabel"
    private Label userNameLabel; // Value injected by FXMLLoader

    @FXML
    void handleReply(ActionEvent event) {

    }

    @FXML
    private void initialize() {
        String currentUsername = SessionKeysStorage.getInstance().getUsername();

        EventBus.getDefault().register(this);

    }

    @Subscribe
    public void onUIShow(ShowSideUIEvent event,Object dataForPage) {
        if (!event.getUIName().equals("CustomerSupportResponsePage")) {
            return;
        }
        user = (User) dataForPage;


    }
}
