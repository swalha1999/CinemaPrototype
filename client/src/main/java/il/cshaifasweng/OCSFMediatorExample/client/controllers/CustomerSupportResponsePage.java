package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import il.cshaifasweng.OCSFMediatorExample.client.data.SessionKeysStorage;
import il.cshaifasweng.OCSFMediatorExample.client.events.ShowSideUIEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.SupportTicket;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.User;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class CustomerSupportResponsePage {

    private User user;
    @FXML
    private Label welcomeMessage;

    @FXML
    private ComboBox<String> locationComboBox;

    @FXML
    private TextArea issueDescription;

    private SupportTicket ticket;

    @FXML
    private void initialize() {
        String currentUsername = SessionKeysStorage.getInstance().getUsername();

        // Set the welcome message, replacing "[Username]" with the actual username
        welcomeMessage.setText("Welcome " + currentUsername + "! , \n"+
                "We value your feedback and are here to assist with any concerns you may have. " +
                "Please feel free to share your experience, and we'll work to resolve the issue as quickly as possible.");
        EventBus.getDefault().register(this);

    }

    // Method to set the details of the support ticket
    public void setSupportTicketDetails(SupportTicket ticket) {
        this.ticket = ticket;

        // Update the UI with the details of the ticket
        welcomeMessage.setText("Hi " + ticket.getUser().getUsername() + ", let us know what we can improve.");
        issueDescription.setText(ticket.getDescription());

        // Populate the ComboBox with sample data (could be dynamic based on the ticket's content)
        locationComboBox.getItems().addAll("Location A", "Location B", "Location C");
        locationComboBox.setValue("Location A"); // Set default or based on ticket details
    }

    @FXML
    private void handleSubmit() {
        // Handle the submit action
        // You can access the ticket object here if needed
        String location = locationComboBox.getValue();
        String description = issueDescription.getText();

        // Logic to handle the support ticket submission (e.g., send an update to the server)
    }

    @Subscribe
    public void onUIShow(ShowSideUIEvent event,Object dataForPage) {
        if (!event.getUIName().equals("CustomerSupportResponsePage")) {
            return;
        }
        user = (User) dataForPage;


    }
}
