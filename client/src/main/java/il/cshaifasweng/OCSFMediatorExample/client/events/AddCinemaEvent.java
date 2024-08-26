package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Cinema;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;

public class AddCinemaEvent {
    boolean isSuccess;
    String message;
    Cinema cinema;

    public AddCinemaEvent(Message message) {
        this.isSuccess = message.isSuccess();
        this.message = message.getMessage();
        this.cinema = (Cinema) message.getDataObject();
    }

    public String getMessage() {
        return message;
    }

    public Cinema getCinema() {
        return cinema;
    }

}
